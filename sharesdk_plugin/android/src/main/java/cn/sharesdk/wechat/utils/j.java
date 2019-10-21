//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.sharesdk.wechat.utils;

import android.os.Bundle;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

public class j {
  private Platform a;
  private ShareParams b;
  private PlatformActionListener c;
  public AuthorizeListener d;
  private g e;

  public j(Platform var1) {
    this.a = var1;
  }

  public void a(AuthorizeListener var1) {
    this.d = var1;
  }

  public void a(ShareParams var1, PlatformActionListener var2) {
    this.b = var1;
    this.c = var2;
  }

  public void a(g var1) {
    this.e = var1;
  }

  public void a(WechatResp var1) {
    HashMap var2;
    Throwable var3;
    switch(var1.f) {
      case -5:
      case -1:
      default:
        var2 = new HashMap();
        var2.put("req", var1.getClass().getSimpleName());
        var2.put("errCode", var1.f);
        var2.put("errStr", var1.g);
        var2.put("transaction", var1.h);
        var3 = new Throwable((new Hashon()).fromHashMap(var2));
        if (this.c != null) {
          this.c.onError(this.a, 9, var3);
        }

        if (this.d != null) {
          this.d.onError(var3);
        }
        break;
      case -4:
        var2 = new HashMap();
        var2.put("errCode", var1.f);
        var2.put("errStr", var1.g);
        var2.put("transaction", var1.h);
        var3 = new Throwable((new Hashon()).fromHashMap(var2));
        switch(var1.a()) {
          case 1:
            if (this.d != null) {
              this.d.onError(var3);
            }

            return;
          default:
            return;
        }
      case -3:
        var2 = new HashMap();
        var2.put("errCode", var1.f);
        var2.put("errStr", var1.g);
        var2.put("transaction", var1.h);
        var3 = new Throwable((new Hashon()).fromHashMap(var2));
        switch(var1.a()) {
          case 1:
            if (this.d != null) {
              this.d.onError(var3);
            }

            return;
          case 2:
            if (this.c != null) {
              this.c.onError(this.a, 9, var3);
            }

            return;
          default:
            return;
        }
      case -2:
        switch(var1.a()) {
          case 1:
            if (this.d != null) {
              this.d.onCancel();
            }

            return;
          case 2:
            if (this.c != null) {
              this.c.onCancel(this.a, 9);
            }

            return;
          default:
            return;
        }
      case 0:
        switch(var1.a()) {
          case 1:
            if (this.d != null) {
              Bundle var4 = new Bundle();
              var1.b(var4);
              this.e.a(var4, this.d);
            }
            break;
          case 2:
            if (this.c != null) {
              var2 = new HashMap();
              var2.put("ShareParams", this.b);
              this.c.onComplete(this.a, 9, var2);
            }
        }
    }

  }

  public ShareParams a() {
    return this.b;
  }

  public Platform b() {
    return this.a;
  }

  public PlatformActionListener c() {
    return this.c;
  }
}
