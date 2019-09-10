//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.sharesdk.wechat.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.sharesdk.framework.utils.SSDKLog;

public class WechatHandlerActivity extends Activity {
  public WechatHandlerActivity() {
  }

  @SuppressLint("ResourceType") protected void onCreate(Bundle savedInstanceState) {
    this.setTheme(16973839);
    super.onCreate(savedInstanceState);

    handle();
  }

  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    this.setIntent(intent);

    handle();
  }

  private void handle() {
    Bundle bundle = getIntent().getExtras();
    int command = bundle.getInt("_wxapi_command_type", 0);
    if (command == 1) {
      int errcode = bundle.getInt("_wxapi_baseresp_errcode", 0);
      if (errcode == 0) {
        k.a().c.d.onComplete(bundle);
      } else if (errcode == -2) {
        k.a().c.d.onCancel();
      } else {
        k.a().c.d.onError(new Throwable(bundle.getString("_wxapi_baseresp_errstr")));
      }
    } else {
      try {
        k.a().a(this);
      } catch (Throwable var3) {
        SSDKLog.b().d(var3);
      }
    }

    this.finish();
  }

  public void onGetMessageFromWXReq(WXMediaMessage msg) {
  }

  public void onShowMessageFromWXReq(WXMediaMessage msg) {
  }
}
