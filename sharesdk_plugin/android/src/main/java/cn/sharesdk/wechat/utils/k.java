//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.sharesdk.wechat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.utils.e;
import cn.sharesdk.framework.utils.f;
import cn.sharesdk.wechat.friends.Wechat;
import com.mob.MobSDK;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.DeviceHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class k {
  private static k a;
  private i b = new i();
  public j c;
  private String d;
  private String e;
  private boolean f;
  private int g;

  public static k a() {
    if (a == null) {
      a = new k();
    }

    return a;
  }

  public void a(String var1) {
    this.e = var1;
  }

  public void b(String var1) {
    this.d = var1;
  }

  public void a(boolean var1) {
    this.f = var1;
  }

  public void a(int var1) {
    this.g = var1;
  }

  private k() {
  }

  public void a(j var1) throws Throwable {
    this.c = var1;
    a var2 = new a();
    var2.a = "snsapi_userinfo";
    var2.b = "sharesdk_wechat_auth";
    this.b.a(var2, false);
  }

  public boolean b() {
    return this.b.a();
  }

  public void a(j var1, ShareParams var2, PlatformActionListener var3) throws Throwable {
    Platform var4 = var1.b();
    String var5 = "com.tencent.mm";
    int var6 = (Integer)var2.get("scene", Integer.class);
    String var7 = var6 == 1 ? "com.tencent.mm.ui.tools.ShareToTimeLineUI" : "com.tencent.mm.ui.tools.ShareImgUI";
    f var8 = new f();
    var8.a(var5, var7);
    var8.a(var2, var4);
    HashMap var9 = new HashMap();
    var9.put("ShareParams", var2);
    var3.onComplete(var4, 9, var9);
  }

  public void b(j var1) throws Throwable {
    Platform var2 = var1.b();
    ShareParams var3 = var1.a();
    PlatformActionListener var4 = var1.c();
    int var5 = var3.getShareType();
    if (var5 == 11 && this.e() < 620756993) {
      var5 = 4;
    }

    String var6 = var3.getTitle();
    String var7 = var3.getText();
    int var8 = var3.getScence();
    String var9 = var3.getImagePath();
    String var10 = var3.getImageUrl();
    Bitmap var11 = var3.getImageData();
    String var12 = var3.getMusicUrl();
    String var13 = var3.getUrl();
    String var14 = var3.getFilePath();
    String var15 = var3.getExtInfo();
    String var16;
    String var17;
    switch(var5) {
    case 1:
      this.a(var6, var7, var8, var1);
      break;
    case 2:
      if (var9 != null && var9.length() > 0) {
        this.a(MobSDK.getContext(), var6, var7, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.a(MobSDK.getContext(), var6, var7, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var16 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.a(MobSDK.getContext(), var6, var7, var16, var8, var1);
      } else {
        this.a(MobSDK.getContext(), var6, var7, "", var8, var1);
      }
      break;
    case 3:
    case 10:
    default:
      if (var4 != null) {
        IllegalArgumentException var22 = new IllegalArgumentException("shareType = " + var5);
        var4.onError(var2, 9, var22);
      }
      break;
    case 4:
      var16 = var2.getShortLintk(var13, false);
      var1.a().setUrl(var16);
      if (var9 != null && var9.length() > 0) {
        this.b(MobSDK.getContext(), var6, var7, var16, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.b(MobSDK.getContext(), var6, var7, var16, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var17 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.b(MobSDK.getContext(), var6, var7, var16, var17, var8, var1);
      } else {
        this.b(MobSDK.getContext(), var6, var7, var16, "", var8, var1);
      }
      break;
    case 5:
      var16 = var12 + " " + var13;
      var17 = var2.getShortLintk(var16, false);
      String var18 = var17.split(" ")[0];
      String var19 = var17.split(" ")[1];
      if (var9 != null && var9.length() > 0) {
        this.a(MobSDK.getContext(), var6, var7, var18, var19, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.a(MobSDK.getContext(), var6, var7, var18, var19, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        String var20 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.a(MobSDK.getContext(), var6, var7, var18, var19, var20, var8, var1);
      } else {
        this.a(MobSDK.getContext(), var6, var7, var18, var19, "", var8, var1);
      }
      break;
    case 6:
      var16 = var2.getShortLintk(var13, false);
      var1.a().setUrl(var16);
      if (var9 != null && var9.length() > 0) {
        this.a(MobSDK.getContext(), var6, var7, var16, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.a(MobSDK.getContext(), var6, var7, var16, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var17 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.a(MobSDK.getContext(), var6, var7, var16, var17, var8, var1);
      } else {
        this.a(MobSDK.getContext(), var6, var7, var16, "", var8, var1);
      }
      break;
    case 7:
      if (var8 == 1) {
        throw new Throwable("WechatMoments does not support SAHRE_APP");
      }

      if (var8 == 2) {
        throw new Throwable("WechatFavorite does not support SAHRE_APP");
      }

      if (var9 != null && var9.length() > 0) {
        this.b(MobSDK.getContext(), var6, var7, var14, var15, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.b(MobSDK.getContext(), var6, var7, var14, var15, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var16 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.b(MobSDK.getContext(), var6, var7, var14, var15, var16, var8, var1);
      } else {
        this.b(MobSDK.getContext(), var6, var7, var14, var15, "", var8, var1);
      }
      break;
    case 8:
      if (var8 == 1) {
        throw new Throwable("WechatMoments does not support SHARE_FILE");
      }

      if (var9 != null && var9.length() > 0) {
        this.c(MobSDK.getContext(), var6, var7, var14, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.c(MobSDK.getContext(), var6, var7, var14, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var16 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.c(MobSDK.getContext(), var6, var7, var14, var16, var8, var1);
      } else {
        this.c(MobSDK.getContext(), var6, var7, var14, "", var8, var1);
      }
      break;
    case 9:
      if (var8 == 1) {
        throw new Throwable("WechatMoments does not support SHARE_EMOJI");
      }

      if (var8 == 2) {
        throw new Throwable("WechatFavorite does not support SHARE_EMOJI");
      }

      if (var9 != null && var9.length() > 0) {
        this.b(MobSDK.getContext(), var6, var7, var9, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        NetworkHelper var21 = new NetworkHelper();
        var17 = var21.downloadCache(MobSDK.getContext(), var10, "images", true, (NetworkTimeOut)null);
        this.b(MobSDK.getContext(), var6, var7, var17, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.b(MobSDK.getContext(), var6, var7, var11, var8, var1);
      } else {
        this.b(MobSDK.getContext(), var6, var7, "", var8, var1);
      }
      break;
    case 11:
      if (var8 == 1) {
        throw new Throwable("WechatMoments does not support SAHRE_WXMINIPROGRAM");
      }

      if (var8 == 2) {
        throw new Throwable("WechatFavorite does not support SAHRE_WXMINIPROGRAM");
      }

      if (TextUtils.isEmpty(this.d)) {
        var4.onError(var2, 9, new Throwable("checkArgs fail, UserName or Path is invalid"));
        return;
      }

      var16 = var2.getShortLintk(var13, false);
      var1.a().setUrl(var16);
      if (var9 != null && var9.length() > 0) {
        this.a(MobSDK.getContext(), var16, this.d, this.e, var6, var7, var9, var8, var1);
      } else if (var11 != null && !var11.isRecycled()) {
        this.a(MobSDK.getContext(), var16, this.d, this.e, var6, var7, var11, var8, var1);
      } else if (var10 != null && var10.length() > 0) {
        var17 = BitmapHelper.downloadBitmap(MobSDK.getContext(), var10);
        this.a(MobSDK.getContext(), var16, this.d, this.e, var6, var7, var17, var8, var1);
      } else {
        this.a(MobSDK.getContext(), var16, this.d, this.e, var6, var7, "", var8, var1);
      }
      break;
    case 12:
      if (!TextUtils.isEmpty(this.d) && !TextUtils.isEmpty(this.e)) {
        this.a(this.d, this.e);
      } else {
        var4.onError(var2, 9, new Throwable("checkArgs fail, UserName or Path is invalid"));
      }
    }

  }

  private void a(String var1, String var2, int var3, j var4) throws Throwable {
    WXTextObject var5 = new WXTextObject();
    var5.text = var2;
    WXMediaMessage var6 = new WXMediaMessage();
    var6.title = var1;
    var6.mediaObject = var5;
    var6.description = var2;
    this.a(var6, "text", var3, var4);
  }

  private void a(Context var1, String var2, String var3, String var4, int var5, j var6) throws Throwable {
    WXImageObject var7 = new WXImageObject();
    if (var4.startsWith("/data/")) {
      var7.imageData = this.d(var4);
    } else {
      var7.imagePath = var4;
    }

    WXMediaMessage var8 = new WXMediaMessage();
    var8.mediaObject = var7;
    if (var5 != 0) {
      var8.title = var2;
      var8.description = var3;
    }

    var8.thumbData = this.a(var1, var4, false);
    this.a(var8, "img", var5, var6);
  }

  private void a(Context var1, String var2, String var3, Bitmap var4, int var5, j var6) throws Throwable {
    WXImageObject var7 = new WXImageObject();
    ByteArrayOutputStream var8 = new ByteArrayOutputStream();
    var4.compress(CompressFormat.JPEG, 85, var8);
    var8.flush();
    var8.close();
    var7.imageData = var8.toByteArray();
    WXMediaMessage var9 = new WXMediaMessage();
    var9.mediaObject = var7;
    if (var5 != 0) {
      var9.title = var2;
      var9.description = var3;
    }

    var9.thumbData = this.a(var1, var4, false);
    this.a(var9, "img", var5, var6);
  }

  private void a(Context var1, String var2, String var3, String var4, String var5, String var6, int var7, j var8) throws Throwable {
    WXMusicObject var9 = new WXMusicObject();
    var9.musicUrl = var5;
    var9.musicDataUrl = var4;
    WXMediaMessage var10 = new WXMediaMessage();
    var10.title = var2;
    var10.description = var3;
    var10.mediaObject = var9;
    var10.thumbData = this.a(var1, var6, false);
    this.a(var10, "music", var7, var8);
  }

  private void a(Context var1, String var2, String var3, String var4, String var5, Bitmap var6, int var7, j var8) throws Throwable {
    WXMusicObject var9 = new WXMusicObject();
    var9.musicUrl = var5;
    var9.musicDataUrl = var4;
    WXMediaMessage var10 = new WXMediaMessage();
    var10.title = var2;
    var10.description = var3;
    var10.mediaObject = var9;
    var10.thumbData = this.a(var1, var6, false);
    this.a(var10, "music", var7, var8);
  }

  private void a(Context var1, String var2, String var3, String var4, String var5, int var6, j var7) throws Throwable {
    WXVideoObject var8 = new WXVideoObject();
    var8.videoUrl = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    var9.thumbData = this.a(var1, var5, false);
    this.a(var9, "video", var6, var7);
  }

  private void a(Context var1, String var2, String var3, String var4, Bitmap var5, int var6, j var7) throws Throwable {
    WXVideoObject var8 = new WXVideoObject();
    var8.videoUrl = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    var9.thumbData = this.a(var1, var5, false);
    this.a(var9, "video", var6, var7);
  }

  private void b(Context var1, String var2, String var3, String var4, String var5, int var6, j var7) throws Throwable {
    WXWebpageObject var8 = new WXWebpageObject();
    var8.webpageUrl = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    if (var5 != null && (new File(var5)).exists()) {
      var9.thumbData = this.a(var1, var5, false);
      if (var9.thumbData == null) {
        throw new RuntimeException("checkArgs fail, thumbData is null");
      }

      if (var9.thumbData.length > 32768) {
        throw new RuntimeException("checkArgs fail, thumbData is too large: " + var9.thumbData.length + " > " + '耀');
      }
    }

    this.a(var9, "webpage", var6, var7);
  }

  private void b(Context var1, String var2, String var3, String var4, Bitmap var5, int var6, j var7) throws Throwable {
    WXWebpageObject var8 = new WXWebpageObject();
    var8.webpageUrl = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    if (var5 != null && !var5.isRecycled()) {
      var9.thumbData = this.a(var1, var5, false);
      if (var9.thumbData == null) {
        throw new RuntimeException("checkArgs fail, thumbData is null");
      }

      if (var9.thumbData.length > 32768) {
        throw new RuntimeException("checkArgs fail, thumbData is too large: " + var9.thumbData.length + " > " + '耀');
      }
    }

    this.a(var9, "webpage", var6, var7);
  }

  private void b(Context var1, String var2, String var3, String var4, String var5, String var6, int var7, j var8) throws Throwable {
    WXAppExtendObject var9 = new WXAppExtendObject();
    var9.filePath = var4;
    var9.extInfo = var5;
    WXMediaMessage var10 = new WXMediaMessage();
    var10.title = var2;
    var10.description = var3;
    var10.mediaObject = var9;
    var10.thumbData = this.a(var1, var6, false);
    this.a(var10, "appdata", var7, var8);
  }

  private void b(Context var1, String var2, String var3, String var4, String var5, Bitmap var6, int var7, j var8) throws Throwable {
    WXAppExtendObject var9 = new WXAppExtendObject();
    var9.filePath = var4;
    var9.extInfo = var5;
    WXMediaMessage var10 = new WXMediaMessage();
    var10.title = var2;
    var10.description = var3;
    var10.mediaObject = var9;
    var10.thumbData = this.a(var1, var6, false);
    this.a(var10, "appdata", var7, var8);
  }

  private void c(Context var1, String var2, String var3, String var4, String var5, int var6, j var7) throws Throwable {
    WXFileObject var8 = new WXFileObject();
    var8.filePath = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    var9.thumbData = this.a(var1, var5, false);
    this.a(var9, "filedata", var6, var7);
  }

  private void a(Context var1, String var2, String var3, String var4, String var5, String var6, Bitmap var7, int var8, j var9) throws Throwable {
    WXMiniProgramObject var10 = new WXMiniProgramObject();
    var10.webpageUrl = var2;
    if (!TextUtils.isEmpty(var3) && var3.endsWith("@app")) {
      var10.userName = var3;
    } else {
      var10.userName = var3 + "@app";
    }

    if (!TextUtils.isEmpty(var4)) {
      String var11;
      String[] var12;
      if ((var12 = var4.split("\\?")).length > 1) {
        var11 = var12[0] + ".html?" + var12[1];
      } else {
        var11 = var12[0] + ".html";
      }

      var10.path = var11;
      var10.withShareTicket = this.f;
      var10.miniprogramType = this.g;
    }

    WXMediaMessage var13 = new WXMediaMessage();
    var13.title = var5;
    var13.mediaObject = var10;
    var13.description = var6;
    if (var7 != null && !var7.isRecycled()) {
      var13.thumbData = this.a(var1, var7, true);
      if (var13.thumbData == null) {
        throw new RuntimeException("checkArgs fail, thumbData is null");
      }

      if (var13.thumbData.length > 131072) {
        throw new RuntimeException("checkArgs fail, thumbData is too large: " + var13.thumbData.length + " > " + 131072);
      }
    }

    this.a(var13, "webpage", var8, var9);
  }

  private void a(Context var1, String var2, String var3, String var4, String var5, String var6, String var7, int var8, j var9) throws Throwable {
    WXMiniProgramObject var10 = new WXMiniProgramObject();
    var10.miniprogramType = this.g;
    var10.webpageUrl = var2;
    if (!TextUtils.isEmpty(var3) && var3.endsWith("@app")) {
      var10.userName = var3;
    } else {
      var10.userName = var3 + "@app";
    }

    if (!TextUtils.isEmpty(var4)) {
      String var11;
      String[] var12;
      if ((var12 = var4.split("\\?")).length > 1) {
        var11 = var12[0] + ".html?" + var12[1];
      } else {
        var11 = var12[0] + ".html";
      }

      var10.path = var11;
      var10.withShareTicket = this.f;
      var10.miniprogramType = this.g;
    }

    WXMediaMessage var13 = new WXMediaMessage();
    var13.title = var5;
    var13.mediaObject = var10;
    var13.description = var6;
    var13.thumbData = this.a(var1, var7, true);
    this.a(var13, "webpage", var8, var9);
  }

  private void a(String var1, String var2) throws Throwable {
    cn.sharesdk.wechat.utils.h.a var3 = new cn.sharesdk.wechat.utils.h.a();
    var3.a = var1;
    var3.b = var2;
    var3.c = this.g;
    this.b.a(var3);
  }

  private void c(Context var1, String var2, String var3, String var4, Bitmap var5, int var6, j var7) throws Throwable {
    WXFileObject var8 = new WXFileObject();
    var8.filePath = var4;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.description = var3;
    var9.mediaObject = var8;
    var9.thumbData = this.a(var1, var5, false);
    this.a(var9, "filedata", var6, var7);
  }

  private void b(Context var1, String var2, String var3, String var4, int var5, j var6) throws Throwable {
    WXEmojiObject var7 = new WXEmojiObject();
    var7.emojiPath = var4;
    WXMediaMessage var8 = new WXMediaMessage();
    var8.title = var2;
    var8.mediaObject = var7;
    var8.description = var3;
    var8.thumbData = this.a(var1, var4, false);
    this.a(var8, "emoji", var5, var6);
  }

  private void b(Context var1, String var2, String var3, Bitmap var4, int var5, j var6) throws Throwable {
    WXEmojiObject var7 = new WXEmojiObject();
    byte[] var8 = this.a(var1, var4, false);
    var7.emojiData = var8;
    WXMediaMessage var9 = new WXMediaMessage();
    var9.title = var2;
    var9.mediaObject = var7;
    var9.description = var3;
    var9.thumbData = var8;
    this.a(var9, "emoji", var5, var6);
  }

  private byte[] d(String var1) {
    try {
      FileInputStream var2 = new FileInputStream(var1);
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      byte[] var4 = new byte[1024];

      for(int var5 = var2.read(var4); var5 > 0; var5 = var2.read(var4)) {
        var3.write(var4, 0, var5);
      }

      var3.flush();
      var2.close();
      var3.close();
      return var3.toByteArray();
    } catch (Throwable var6) {
      cn.sharesdk.framework.utils.e.b().d(var6);
      return null;
    }
  }

  private byte[] a(Context var1, String var2, boolean var3) throws Throwable {
    File var4 = new File(var2);
    if (!var4.exists()) {
      throw new FileNotFoundException();
    } else {
      CompressFormat var5 = BitmapHelper.getBmpFormat(var2);
      Bitmap var6 = BitmapHelper.getBitmap(var2);
      return this.a(var1, var6, var5, var3);
    }
  }

  private byte[] a(Context var1, Bitmap var2, boolean var3) throws Throwable {
    if (var2 == null) {
      throw new RuntimeException("checkArgs fail, thumbData is null");
    } else if (var2.isRecycled()) {
      throw new RuntimeException("checkArgs fail, thumbData is recycled");
    } else {
      return this.a(var1, var2, CompressFormat.PNG, var3);
    }
  }

  private byte[] a(Context var1, Bitmap var2, CompressFormat var3, boolean var4) throws Throwable {
    if (var2 == null) {
      throw new RuntimeException("checkArgs fail, thumbData is null");
    } else if (var2.isRecycled()) {
      throw new RuntimeException("checkArgs fail, thumbData is recycled");
    } else {
      ByteArrayOutputStream var5 = new ByteArrayOutputStream();
      var2.compress(var3, 100, var5);
      var5.flush();
      var5.close();
      byte[] var6 = var5.toByteArray();
      int var7 = var6.length;
      int var8 = 32768;
      if (var4) {
        var8 = 131072;
      }

      while(var7 > var8) {
        double var9 = (double)var7 / (double)var8;
        var2 = this.a(var2, var9);
        var5 = new ByteArrayOutputStream();
        var2.compress(var3, 100, var5);
        var5.flush();
        var5.close();
        var6 = var5.toByteArray();
        var7 = var6.length;
      }

      return var6;
    }
  }

  private Bitmap a(Bitmap var1, double var2) {
    int var4 = var1.getWidth();
    int var5 = var1.getHeight();
    double var6 = Math.sqrt(var2);
    int var8 = (int)((double)var4 / var6);
    int var9 = (int)((double)var5 / var6);
    Bitmap var10 = Bitmap.createScaledBitmap(var1, var8, var9, true);
    return var10;
  }

  public boolean c(String var1) {
    return this.b.a(var1);
  }

  public boolean a(WechatHandlerActivity var1) {
    return this.b.a(var1, this.c);
  }

  public boolean c() {
    return this.b.b();
  }

  public boolean d() {
    return this.b.c();
  }

  public final int e() {
    int var1 = 0;
    if (!(new Wechat()).isClientValid()) {
      return var1;
    } else {
      try {
        var1 = MobSDK.getContext().getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
      } catch (Exception var3) {
      }

      return var1;
    }
  }

  private void a(WXMediaMessage var1, String var2, int var3, j var4) throws Throwable {
    DeviceHelper var5 = DeviceHelper.getInstance(MobSDK.getContext());
    String var6 = var5.getPackageName() + ".wxapi.WXEntryActivity";
    Class var7 = null;

    try {
      var7 = Class.forName(var6);
    } catch (Throwable var10) {
      cn.sharesdk.framework.utils.e.b().d(var10);
      var7 = null;
    }

    if (var7 != null && !WechatHandlerActivity.class.isAssignableFrom(var7)) {
      String var8 = var6 + " does not extend from " + WechatHandlerActivity.class.getName();
      (new Throwable(var8)).printStackTrace();
    }

    d var11 = new d();
    var11.d = var2 + System.currentTimeMillis();
    var11.a = var1;
    var11.b = var3;
    this.c = var4;
    boolean var9 = false;
    if (var1.mediaObject instanceof WXMiniProgramObject) {
      var9 = true;
    }

    this.b.a(var11, var9);
  }
}
