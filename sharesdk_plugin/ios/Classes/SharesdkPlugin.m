#import "SharesdkPlugin.h"
#import <ShareSDK/ShareSDKHeader.h>
#import <ShareSDKExtension/ShareSDK+Extension.h>
#import <MOBFoundation/MOBFoundation.h>
#import <objc/message.h>
#import <WechatConnector/WechatConnector.h>

typedef NS_ENUM(NSUInteger, PluginMethod) {
    PluginMethodGetVersion          = 0,
    PluginMethodShare               = 1,
    PluginMethodAuth                = 2,
    PluginMethodHasAuthed           = 3,
    PluginMethodCancelAuth          = 4,
    PluginMethodGetUserInfo         = 5,
    PluginMethodRegist              = 6,
    PluginMethodShowMenu            = 7,
    PluginMethodShowEditor          = 8,
    PluginMethodOpenMiniProgram     = 9,
    PluginMethodActivePlatforms     = 10,
    PluginMethodIsClientInstalled   = 11,

};

@interface SharesdkPlugin()<FlutterStreamHandler,ISSERestoreSceneDelegate>

@property (strong, nonatomic) NSDictionary *methodMap;

// 事件回调
@property (nonatomic, copy) void (^callBack) (id _Nullable event);

@end

@implementation SharesdkPlugin

+ (void)load{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        SEL sel = sel_registerName("addChannelWithSdkName:channel:");
        Method method = class_getClassMethod([MobSDK class],sel) ;
        if (method && method_getImplementation(method) != _objc_msgForward) {
            ((void (*)(id, SEL,id,id))objc_msgSend)([MobSDK class],sel,@"SHARESDK",@"4");
        }
    });
}

static NSString *const receiverStr = @"SSDKRestoreReceiver";

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"com.yoozoo.mob/sharesdk"
                                     binaryMessenger:[registrar messenger]];
    SharesdkPlugin* instance = [[SharesdkPlugin alloc] init];
    instance.methodMap = @{
                           @"getVersion":@(PluginMethodGetVersion),
                           @"share":@(PluginMethodShare),
                           @"auth":@(PluginMethodAuth),
                           @"hasAuthed":@(PluginMethodHasAuthed),
                           @"cancelAuth":@(PluginMethodCancelAuth),
                           @"getUserInfo":@(PluginMethodGetUserInfo),
                           @"regist":@(PluginMethodRegist),
                           @"activePlatforms":@(PluginMethodActivePlatforms),
                           @"showEditor":@(PluginMethodShowEditor),
                           @"showMenu":@(PluginMethodShowMenu),
                           @"openMiniProgram":@(PluginMethodOpenMiniProgram),
                           @"isClientInstalled":@(PluginMethodIsClientInstalled),

                           };
    [registrar addMethodCallDelegate:instance channel:channel];
    
    FlutterEventChannel* e_channel = [FlutterEventChannel eventChannelWithName:receiverStr binaryMessenger:[registrar messenger]];
    [e_channel setStreamHandler:instance];
    
    [instance addObserver];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    NSNumber *methodType = self.methodMap[call.method];
    
    if (methodType)
    {
        switch (methodType.intValue)
        {
            case PluginMethodGetVersion:
                [self _getVersion:result];
                break;
            case PluginMethodShare:
                [self _shareWithArgs:call.arguments result:result];
                break;
            case PluginMethodAuth:
                [self _authWithArgs:call.arguments result:result];
                break;
            case PluginMethodHasAuthed:
                [self _hasAuthedWithArgs:call.arguments result:result];
                break;
            case PluginMethodCancelAuth:
                [self _cancelAuthWithArgs:call.arguments result:result];
                break;
            case PluginMethodGetUserInfo:
                [self _getUserInfoWithArgs:call.arguments result:result];
                break;
            case PluginMethodRegist:
                [self _registWithArgs:call.arguments result:result];
                break;
            case PluginMethodActivePlatforms:
                [self _getActivePlatformsResult:result];
                break;
            case PluginMethodShowMenu:
                [self _showMenuWithArgs:call.arguments result:result];
                break;
            case PluginMethodShowEditor:
                [self _showEditorWithArgs:call.arguments result:result];
                break;
            case PluginMethodOpenMiniProgram:
                [self _openMiniProgramWithArgs:call.arguments result:result];
                break;
            case PluginMethodIsClientInstalled:
                [self _isClientInstalledWithArgs:call.arguments result:result];
                break;
            default:
                NSAssert(NO, @"The method requires an implementation ！");
                break;
        }
    }
    else
    {
        result(FlutterMethodNotImplemented);
    }
}

- (void)_getVersion:(FlutterResult)result
{
    result([ShareSDK sdkVersion]);
}

- (void)_shareWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    NSInteger type = [args[@"platform"] integerValue];
    NSDictionary *params = [self _covertParams:args[@"params"]];
    
    [ShareSDK share:type parameters:params.mutableCopy onStateChanged:^(SSDKResponseState state, NSDictionary *userData, SSDKContentEntity *contentEntity, NSError *error) {
        if (state != SSDKResponseStateBegin)
        {
            NSDictionary *dic = @{
                                  @"state":@(state),
                                  @"userData":userData?:[NSNull null],
                                  @"contentEntity":contentEntity.dictionaryValue?:[NSNull null],
                                  @"error":[self _covertError:error]
                                  };
            result(dic);
        }
    }];
}

- (void)_authWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    NSInteger type = [args[@"platform"] integerValue];
    NSDictionary *settings = args[@"settings"];
    if (type == SSDKPlatformSubTypeWechatSession && [settings isKindOfClass:[NSDictionary class]] && [[settings allKeys] containsObject:@"appKey"]) {
        [ShareSDK registPlatforms:^(SSDKRegister *platformsRegister) { //微信
            [platformsRegister setupWeChatWithAppId:settings[@"appKey"] appSecret:nil  universalLink:settings[@"universalLink"]];
        }];
        [WeChatConnector setRequestAuthTokenOperation:^(NSString *authCode, void (^getUserinfo)(NSString *uid, NSString *token)) {
            result(@{
                     @"state":@(1),
                     @"user":@{@"code":authCode}
                     });
        }];
        //先执行auth方法，我们内部会判断，如果appsecret为nil，就会判断执行setRequestAuthTokenOperation
        [ShareSDK authorize:SSDKPlatformTypeWechat settings:nil onStateChanged:^(SSDKResponseState state, SSDKUser *user, NSError *error) {
            NSDictionary *dic = @{
                                  @"state":@(state),
                                  @"user":user.dictionaryValue?:[NSNull null],
                                  @"error":[self _covertError:error]
                                  };
            result(dic);
        }];
    } else {
        [ShareSDK authorize:type settings:[settings isKindOfClass:NSDictionary.class]?settings:nil onStateChanged:^(SSDKResponseState state, SSDKUser *user, NSError *error) {
            if (state != SSDKResponseStateBegin && state != SSDKResponseStateUpload)
            {
                NSDictionary *dic = @{
                                      @"state":@(state),
                                      @"user":user.dictionaryValue?:[NSNull null],
                                      @"error":[self _covertError:error]
                                      };
                result(dic);
            }
        }];
    }
}

- (void)_hasAuthedWithArgs:(NSNumber *)args result:(FlutterResult)result
{
    NSInteger state = SSDKResponseStateFail;
    BOOL hasAuthed = [ShareSDK hasAuthorized:args.integerValue];
    if (hasAuthed)
    {
        state = SSDKResponseStateSuccess;
    }
    NSDictionary *dic = @{
                          @"state":@(state),
                          @"user":[NSNull null],
                          @"error":[NSNull null]
                          };
    result(dic);
}

- (void)_cancelAuthWithArgs:(NSNumber *)args result:(FlutterResult)result
{
    [ShareSDK cancelAuthorize:args.integerValue result:^(NSError *error) {
        NSInteger state = SSDKResponseStateFail;
        if (error == nil)
        {
            state = SSDKResponseStateSuccess;
        }
        NSDictionary *dic = @{
                              @"state":@(state),
                              @"user":[NSNull null],
                              @"error":[self _covertError:error]
                              };
        result(dic);
    }];
}

- (void)_getUserInfoWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    NSInteger type = [args[@"platform"] integerValue];
    [ShareSDK getUserInfo:type onStateChanged:^(SSDKResponseState state, SSDKUser *user, NSError *error) {
        if (state != SSDKResponseStateBegin)
        {
            NSDictionary *dic = @{
                                  @"state":@(state),
                                  @"user":user.dictionaryValue?:[NSNull null],
                                  @"error":[self _covertError:error],
                                  };
            result(dic);
        }
    }];
}

- (void)_registWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    [ShareSDK registPlatforms:^(SSDKRegister *platformsRegister) {
        [args enumerateKeysAndObjectsUsingBlock:^(id key, NSDictionary *obj, BOOL * _Nonnull stop) {
            
            [platformsRegister.platformsInfo setObject:obj.mutableCopy forKey:[NSString stringWithFormat:@"%@",key]];
        }];
    }];
}

- (void)_getActivePlatformsResult:(FlutterResult)result
{
    result([ShareSDK activePlatforms]);
}

- (void)_showEditorWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    SSDKPlatformType type = [args[@"platform"] integerValue];
    NSDictionary *params = [self _covertParams:args[@"params"]];
    SEL showEditorSEL = NSSelectorFromString(@"showShareEditor:otherPlatforms:shareParams:editorConfiguration:onStateChanged:");
    NSAssert([ShareSDK.class respondsToSelector:showEditorSEL], @"Need to import ShareSDKUI.framework");
    ((id(*)(id,
            SEL,
            SSDKPlatformType,
            NSArray *,
            NSMutableDictionary *,
            id,
            void(^)(SSDKResponseState,SSDKPlatformType,NSDictionary*,SSDKContentEntity*,NSError *,BOOL)))objc_msgSend)
    (ShareSDK.class,
     showEditorSEL,
     type,
     nil,
     params.mutableCopy,
     nil,
     ^(SSDKResponseState state,
       SSDKPlatformType platformType,
       NSDictionary *userData,
       SSDKContentEntity *contentEntity,
       NSError *error,
       BOOL end){
         
         if (state != SSDKResponseStateBegin && state != SSDKResponseStateUpload)
         {
             NSDictionary *dic = @{
                                   @"state":@(state),
                                   @"platform":@(platformType),
                                   @"error":[self _covertError:error],
                                   @"userData":userData?:[NSNull null],
                                   @"contentEntity":contentEntity.dictionaryValue?:[NSNull null],
                                   };
             result(dic);
         }
     });
    
}

- (void)_showMenuWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    NSArray *types = [args[@"platforms"] isKindOfClass:NSArray.class] ?args[@"platforms"]:nil;
    NSDictionary *params = [self _covertParams:args[@"params"]];
    SEL showMenuSEL = NSSelectorFromString(@"showShareActionSheet:customItems:shareParams:sheetConfiguration:onStateChanged:");
    NSAssert([ShareSDK.class respondsToSelector:showMenuSEL], @"Need to import ShareSDKUI.framework");
    
    ((id(*)(id,
            SEL,
            UIView *,
            NSArray *,
            NSMutableDictionary *,
            id,
            void(^)(SSDKResponseState,SSDKPlatformType,NSDictionary*,SSDKContentEntity*,NSError *,BOOL)))objc_msgSend)
    (ShareSDK.class
     ,showMenuSEL,
     nil,
     types,
     params.mutableCopy,
     nil,
     ^(SSDKResponseState state,
       SSDKPlatformType platformType,
       NSDictionary *userData,
       SSDKContentEntity *contentEntity,
       NSError *error,
       BOOL end){
         
         if (state != SSDKResponseStateBegin && state != SSDKResponseStateUpload)
         {
             NSDictionary *dic = @{
                                   @"state":@(state),
                                   @"platform":@(platformType),
                                   @"error":[self _covertError:error],
                                   @"userData":userData?:[NSNull null],
                                   @"contentEntity":contentEntity.dictionaryValue?:[NSNull null],
                                   };
             result(dic);
         }
     });
}

- (id)_covertError:(NSError *)error
{
    if (error)
    {
        return @{@"code":@(error.code),@"userInfo":error.userInfo?:@{}};
    }
    
    return [NSNull null];
}

- (NSMutableDictionary *)_covertParams:(NSDictionary *)params
{
    NSMutableDictionary *tmp = params.mutableCopy;
    
    NSArray *urlKeys = @[@"url",@"audio_url",@"audio_flash_url",@"video_flash_url",@"video_asset_url"];
    NSArray *thumbImageKeys = @[@"thumb_image",@"wxmp_hdthumbimage"];
    NSArray *dataKeys = @[@"emoticon_data",@"file_data",@"source_file"];
    
    for (id key in params.allKeys)
    {
        if ([urlKeys containsObject:key])
        {
            tmp[key] = [NSURL URLWithString:[NSString stringWithFormat:@"%@",params[key]]];
        }
        
        if ([thumbImageKeys containsObject:key])
        {
            tmp[key] = [SSDKImage imageWithObject:params[key]];
        }
        
        if ([dataKeys containsObject:key])
        {
            tmp[key] = ((id(*)(id,SEL,id))objc_msgSend)(NSClassFromString(@"SSDKData"),NSSelectorFromString(@"dataWithObject:"),params[key]);
        }
        
        if ([key isEqualToString:@"images"])
        {
            tmp[key] = ((NSArray *(*)(id, SEL, id))objc_msgSend)(params.mutableCopy,NSSelectorFromString(@"_convertToImages:"),params[key]);
        }
        
        if ([params[key] isKindOfClass:NSDictionary.class])
        {
            tmp[key] = [self _covertParams:params[key]];
        }
    }
    
    if (params[@"sina_linkCard"] != nil && [params[@"sina_linkCard"] boolValue] == YES)
    {
        tmp[@"sina_linkCard"] = @1;
        if (tmp[@"images"] == nil && params[@"image_url"] != nil)
        {
            tmp[@"images"] = ((NSArray *(*)(id, SEL, id))objc_msgSend)(params.mutableCopy,NSSelectorFromString(@"_convertToImages:"),params[@"image_url"]);
        }
    }
    
    return tmp;
}

- (void)_openMiniProgramWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    void(^ complete)(BOOL) = ^(BOOL success) {
        result(@(success));
    };
    [WeChatConnector openMiniProgramWithUserName:args[@"userName"] path:args[@"path"] miniProgramType:[args[@"type"] intValue] extMsg:nil extDic:nil complete:complete];
}

- (void)_isClientInstalledWithArgs:(NSDictionary *)args result:(FlutterResult)result
{
    SSDKPlatformType type = [args[@"platform"] integerValue];
    result(@([ShareSDK isClientInstalled:type]));
}


#pragma mark - FlutterStreamHandler Protocol

- (FlutterError *)onListenWithArguments:(id)arguments eventSink:(FlutterEventSink)events
{
    self.callBack = events;
    return nil;
}

- (FlutterError * _Nullable)onCancelWithArguments:(id _Nullable)arguments
{
    return nil;
}


#pragma mark - 场景还原 添加监听
- (void)addObserver
{
    [ShareSDK setRestoreSceneDelegate:self];
}

#pragma mark - ISSERestoreSceneDelegate

/**
 闭环分享代理回调
 
 */
- (void)ISSEWillRestoreScene:(SSERestoreScene *)scene error:(NSError *)error
{
    NSMutableDictionary *resultDict = [NSMutableDictionary dictionary];
       
    if (scene.path.length > 0)
    {
        resultDict[@"path"] = scene.path;
    }
       
    if (scene.params && scene.params.count > 0)
    {
        resultDict[@"params"] = scene.params;
    }
       
    NSString *resultStr  = @"";
    if (resultDict.count > 0)
    {
        resultStr = [MOBFJson jsonStringFromObject:resultDict];
    }
    if (self.callBack)
    {
        self.callBack(resultDict);
    }
}



@end
