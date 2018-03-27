#如果打开dontoptimize选项会不能消除debug日志信息
#-dontoptimize
-dontskipnonpubliclibraryclasses

#不用预先检查
-dontpreverify
#关闭混淆
#-dontobfuscate
#不用输出通知
-dontnote
#不用输出详细的过程
-verbose
#忽略警告
-ignorewarning

-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

#系统v4包
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

#系统v7包
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

#四大组件和其子类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference0
-keep public class com.android.vending.licensing.ILicensingService

#保留推送lib中的类
-keep class com.zmsoft.jni.** { *; }
# 后期优化
-keep public class com.zmsoft.ccd.module.receipt.receipt.source.ReceiptRemoteSource


# 库工程
-keep class com.zmsoft.ccd.lib.base.**{*;}
-keep class com.zmsoft.ccd.lib.bean.**{*;}
-keep class com.zmsoft.ccd.receipt.bean.**{*;}
-keep class com.zmsoft.ccd.takeout.bean.**{*;}
-keep class com.zmsoft.ccd.lib.utils.**{*;}
-keep class com.zmsoft.ccd.lib.widget.**{*;}
-keep class com.zmsoft.ccd.lib.base.bean.**{*;}
-keep class com.zmsoft.ccd.bean.**{*;}
-keep class com.zmsoft.ccd.lib.print.**{*;}
-keep class com.zmsoft.ccd.lib.scan.**{*;}
-keep class com.dfire.mobile.cashupdate.bean.**{*;}
-keep class com.zmsoft.ccd.lib.pos.**{*;}

# 菜单模块
-keep class com.zmsoft.ccd.module.menu.menu.bean.**{*;}
-keep class com.zmsoft.ccd.module.menu.cart.model.**{*;}
# receipt
-keep class com.zmsoft.ccd.module.receipt.receipt.model.**{*;}




#不混淆泛型
-keepattributes Signature

#不混淆注解
-keepattributes *Annotation*

#静态方法
-keepclasseswithmembernames class * {
    native <methods>;
}

#不混淆layout中的onClick事件
-keepclasseswithmembernames class * extends android.app.Activity {
   public void *(android.view.View);
}

#不混淆自定义组件
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    <fields>;
}

-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

# Remove all debug logging from release builds
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

#R文件
-keep public class [com.zmsoft.ccd].R$*{
   public static final int *;
}
-keepclassmembers enum * {
   public static **[] values();
   public static ** valueOf(java.lang.String);
}

### OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
-dontwarn rx.**
-dontwarn javax.lang.model.element.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

### Glide
-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.**

### Litepal
-keep class org.litepal.** {
    *;
}
-keep class * extends org.litepal.crud.DataSupport {
    *;
}

# fastjson
-dontwarn com.alibaba.fastjson.**

### Gson
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.** { *; }
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


#Material弹窗
-dontwarn com.afollestad.materialdialogs.**

#微信
#-dontwarn com.tencent.**
#-keep class com.tencent.**{*;}

##Jpush
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#-dontwarn com.google.gson.jpush.**
#-keep class com.google.gson.jpush.** { *; }
#-dontwarn com.google.protobuf.jpush.**
#-keep class com.google.protobuf.jpush.** { *; }

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/LoneLyGoD/Documents/AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#JPush
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#Arouter
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

#MRouter
-keep public class com.chiclaim.modularization.router.**{*;}
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <methods>; }
-keepclasseswithmembernames class * { @com.chiclaim.modularization.router.annotation.* <fields>; }
-keep public class * implements com.chiclaim.modularization.router.IAutowired{ public <init>(**); }

#feifan printer
-dontwarn com.ffan.**
-keep class com.ffan.** { *; }
-dontwarn com.printer.sdk.**
-keep class com.printer.sdk.** { *; }
-dontwarn org.vudroid.pdfdroid.codec.**
-keep class org.vudroid.pdfdroid.codec.** { *; }




############################ Tinker Start ########################################################
-keepattributes *Annotation*
-dontwarn com.tencent.tinker.anno.AnnotationProcessor
-keep @com.tencent.tinker.anno.DefaultLifeCycle public class *
-keep public class * extends android.app.Application {
    *;
}
-keep class com.tencent.tinker.server.** { *; }
-keep public class com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}
-keep public class * implements com.tencent.tinker.loader.app.ApplicationLifeCycle {
    *;
}
-keep public class com.tencent.tinker.loader.TinkerLoader {
    *;
}
-keep public class * extends com.tencent.tinker.loader.TinkerLoader {
    *;
}
-keep public class com.tencent.tinker.loader.TinkerTestDexLoad {
    *;
}
-keep public class com.tencent.tinker.loader.TinkerTestAndroidNClassLoader {
    *;
}
-keep class com.tencent.tinker.loader.**
-keep class com.zmsoft.ccd.app.GenCcdApplication
-keep class com.zmsoft.app.ccd.GenCcdApplication
############################ Tinker End ########################################################




#Allin
-dontwarn com.pax.allinpay.trans.**
-keep class com.pax.allinpay.trans.** { *; }
-keep class com.allinpay.usdk.core.data.** { *; }
-keep class com.allinpay.usdk.core.model.** { *; }





############################ 开店SDK  Start ########################################################
# 添加混淆
#jackson
-keep @com.fasterxml.jackson.annotation.JsonIgnoreProperties class * { *; }
-keep class com.fasterxml.** { *; }
-keep class org.codehaus.** { *; }
-keep class  * extends com.openshop.common.KeepBase
-keepnames class com.fasterxml.jackson.** { *; }
-keepclassmembers public final enum com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility {
    public static final com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility *;
}
-dontskipnonpubliclibraryclassmembers
-printconfiguration
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}
-dontwarn com.fasterxml.jackson.databind.**
# General
-keepattributes SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,Signature,Exceptions,InnerClasses
-dontwarn com.openshop.common.**
#支付宝SDK
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.android.phone.**{ *;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-dontwarn com.alipay.android.phone.**
## retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontnote rx.**
#fresco
-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
-keepclassmembers class * {
    native <methods>;
}

-dontwarn com.facebook.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**
# 开店用的图片加载库
-keep class com.hs.libs.**{ *;}
-keep class org.apache.commons.** { *;}
-dontwarn com.hs.libs.**
############################ 开店SDK  End ########################################################

#百度TTS
-keep class com.baidu.tts.**{*;}
-keep class com.baidu.speechsynthesizer.**{*;}

############################ 在线客服  Start #########################################################
-keepattributes Annotation
-keep public class * extends android.app.Fragment
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep class com.android.vending.licensing.ILicensingService
-dontwarn android.webkit.WebView
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keep class com.sobot.** {*;}
############################ 在线客服  End #########################################################


############################ Okio  Start #########################################################
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
############################ Okio  End #########################################################

############################ 微信登录  End #########################################################
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
############################ 微信登录  End #########################################################


############################ 登录  Start #########################################################
##----------------------------------
##     关于U盟相关的混淆
## ----------------------------------
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#-keep public class javax.**
#-keep public class android.webkit.**
#-dontwarn android.support.v4.**
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#-keep public class com.umeng.socialize.* {*;}
#-keep class com.facebook.**
#-keep class com.facebook.** { *; }
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#-keep class com.umeng.socialize.handler.**
#-keep class com.umeng.socialize.handler.*
#-keep class com.umeng.weixin.handler.**
#-keep class com.umeng.weixin.handler.*
#-keep class com.umeng.qq.handler.**
#-keep class com.umeng.qq.handler.*
#-keep class UMMoreHandler{*;}
#-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-keep class com.tencent.mm.opensdk.** {
#   *;
#}
#-keep class com.tencent.wxop.** {
#   *;
#}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-dontwarn twitter4j.**
#-keep class twitter4j.** { *; }
#-keep class com.tencent.** {*;}
#-dontwarn com.tencent.**
#-keep class com.kakao.** {*;}
#-dontwarn com.kakao.**
#-keep public class com.umeng.com.umeng.soexample.R$*{
#    public static final int *;
#}
#-keep public class com.linkedin.android.mobilesdk.R$*{
#    public static final int *;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep class com.tencent.open.TDialog$*
#-keep class com.tencent.open.TDialog$* {*;}
#-keep class com.tencent.open.PKDialog
#-keep class com.tencent.open.PKDialog {*;}
#-keep class com.tencent.open.PKDialog$*
#-keep class com.tencent.open.PKDialog$* {*;}
#-keep class com.umeng.socialize.impl.ImageImpl {*;}
#-keep class com.sina.** {*;}
#-dontwarn com.sina.**
#-keep class  com.alipay.share.sdk.** {
#   *;
#}
#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#-keep class com.linkedin.** { *; }
#-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#-keepattributes Signature
###----------------------------------
##AliPay混淆
###----------------------------------
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#-keep class com.alipay.sdk.app.H5PayCallback {
#    <fields>;
#    <methods>;
#}
#-keep class com.alipay.android.phone.mrpc.core.** { *; }
#-keep class com.alipay.apmobilesecuritysdk.** { *; }
#-keep class com.alipay.mobile.framework.service.annotation.** { *; }
#-keep class com.alipay.mobilesecuritysdk.face.** { *; }
#-keep class com.alipay.tscenter.biz.rpc.** { *; }
#-keep class org.json.alipay.** { *; }
#-keep class com.alipay.tscenter.** { *; }
#-keep class com.ta.utdid2.** { *;}
#-keep class com.ut.device.** { *;}
###----------------------------------
##   保持实现"Serializable"接口的类不被混淆
###----------------------------------
#-keepnames class * implements java.io.Serializable
### ----------------------------------
###      butterknife
### ----------------------------------
#-dontwarn butterknife.internal.**
#-keep class **$$ViewInjector { *; }
#-keepnames class * { @butterknife.InjectView *;}
### ----------------------------------
###      commons-collections
### ----------------------------------
#-keep class org.apache.commons.** { *; }
#-dontwarn org.apache.commons.**
### 登录
#-keep class tdf.zmsoft.login.manager.vo.login.**{*;}
#-keep class tdf.zmsoft.login.manager.vo.login.CompositeLoginParam
#-dontwarn tdf.zmsoft.login.manager.vo.login.**
############################ 登录  End ########################################################