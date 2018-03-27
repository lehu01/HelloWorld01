package com.zmsoft.ccd.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.bumptech.glide.request.target.ViewTarget;
import com.ccd.lib.print.util.printer.LocalPrinterUtils;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Components;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.ffan.printer10.PrintSdk;
import com.meituan.android.walle.ChannelInfo;
import com.meituan.android.walle.WalleChannelReader;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.server.manager.TinkerManager;
import com.tencent.tinker.server.manager.TinkerServerManager;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.CrashHandler;
import com.zmsoft.ccd.MyEventBusIndex;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.helper.CustomerServiceHelper;
import com.zmsoft.ccd.helper.LogOutHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.ApplicationUtil;
import com.zmsoft.ccd.module.MenuEventBusIndex;
import com.zmsoft.ccd.module.login.login.LoginActivity;
import com.zmsoft.ccd.module.receipt.ReceiptEventBusIndex;
import com.zmsoft.ccd.network.CcdNetWorkConfig;
import com.zmsoft.ccd.network.CommonConstant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

import static com.zmsoft.ccd.network.CommonConstant.PATCH_URL;


@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.zmsoft.app.ccd.GenCcdApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL)
@Components({"libbase", "app", "Menu", "Receipt", "Takeout"})
public class CcdApplication extends DefaultApplicationLike {

    private static CcdApplication sInstance;

    private AppComponent mAppComponent;

    public CcdApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                          long applicationStartElapsedTime, long applicationStartMillisTime,
                          Intent tinkerResultIntent, Resources[] resources, ClassLoader[] classLoader,
                          AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        //初始化全局applicationContext
        initGlobal();

        sInstance = this;
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.installTinker(this);

        //初始化TinkerPatch服务SDK 每隔访问2小时服务器是否有更新
        TinkerServerManager.installTinkerServer(getApplication(),
                Tinker.with(getApplication()),
                CommonConstant.APP_CODE,
                String.valueOf(BuildConfig.VERSION_CODE),
                UserHelper.getEntityId(),
                AppEnv.getCashApiBaseUrl() + PATCH_URL,
                2,
                BuildConfig.DEBUG);
        TinkerServerManager.checkTinkerUpdate(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initEveryThing();
    }

    private void initEveryThing() {
        String processName = ApplicationUtil.getCurProcessName(getApplication());
        //判断进程名，保证只在主进程初始化一次
        if (TextUtils.isEmpty(processName) || !processName.equals(getApplication().getPackageName())) {
            return;
        }
        //初始化统计
        initStatistics();
        //初始化Logger
        initLogger();
        //构建全局依赖
        buildAppComponent();
        //初始化路由
        iniRouter();
        //初始化LeakCanary
        initLeakCanary();
        //初始化网络框架
        initNetWork();
        //初始化GlideTag
        initGlideTag();
        //初始化Eventbus
        initEventbus();
        //初始化JPush
        initJPush();
        //初始化ApplicationUtil
        initApplicationUtil();
        //初始化数据库工具类
        initSQLiteUtil();
        //注册EventBus
        EventBusHelper.register(this);
        //非凡打印机
        initFeiFanSdk();
        // 初始化登录
//        initLogin();
        CustomerServiceHelper.registerCustomerService(getApplication().getApplicationContext());
    }

    private void buildAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(GlobalVars.context))
                .build();
    }

    private void iniRouter() {
        MRouter.getInstance().init(getApplication());
    }

    private void initStatistics() {
        CrashHandler.init();
    }

    /**
     * 初始化LeakCanary
     */
    private void initLeakCanary() {
        if (!LeakCanary.isInAnalyzerProcess(GlobalVars.context)) {
            LeakCanary.install(getApplication());
        }
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        Logger.Settings settings = Logger.init(CommonConstant.APP_LOGGER_TAG);
        if (BuildConfig.FLAVOR.equals("internal")) {
            settings.setLogLevel(LogLevel.FULL);
        } else {
            settings.hideThreadInfo().setLogLevel(LogLevel.NONE);
        }
    }

    /**
     * Glide加载的iamgeView调用了setTag()方法会和glide本身的tag冲突，因为Glide已经默认为ImageView设置了Tag
     * 在此做个tag的初始化可以避免该问题
     */
    private void initGlideTag() {
        ViewTarget.setTagId(R.id.glide_tag);
    }

    /**
     * must be the very first method to call
     */
    private void initGlobal() {
        GlobalVars.init(getApplication());
        AppEnv.init(BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE);
        final ChannelInfo channelInfo = WalleChannelReader.getChannelInfo(GlobalVars.context);
        String channel = null;
        if (channelInfo != null) {
            channel = channelInfo.getChannel();
        }
        AppEnv.setCurrentChannel(channel);
    }

    /**
     * 初始化网络框架
     */
    private void initNetWork() {
        CcdNetWorkConfig.initNetWork(GlobalVars.context, !AppEnv.isProduction(), BuildConfig.VERSION_CODE);
    }

    /**
     * 初始化Eventbus
     */
    private void initEventbus() {
        EventBus.builder()
                .addIndex(new MyEventBusIndex())  //app module
                .addIndex(new MenuEventBusIndex())  //menu module
                .addIndex(new ReceiptEventBusIndex()) //receipt module
                .installDefaultEventBus();
    }

    /**
     * 初始化JPush
     */
    private void initJPush() {
        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(!AppEnv.isProduction());
        // 初始化 JPush
        JPushInterface.init(GlobalVars.context);
    }

    private void initApplicationUtil() {
        ApplicationUtil.init(getApplication());
    }

    /**
     * 初始化数据库工具类
     */
    private void initSQLiteUtil() {
        LitePal.initialize(GlobalVars.context);
    }

    /**
     * 如果机器是非凡，初始化非凡打印机
     */
    private void initFeiFanSdk() {
        if (LocalPrinterUtils.isFeiFanPrinter()) {
            PrintSdk.getInstance().init(getApplication().getApplicationContext());
        }
    }

    public static CcdApplication getInstance() {
        return sInstance;
    }

    /**
     * 收到被踢下线的通知
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLogedOut(RouterBaseEvent.CommonEvent event) {
        if (event == RouterBaseEvent.CommonEvent.EVENT_LOG_OUT) {
            Activity currentActivity = RouterActivityManager.get().getActivity();
            if (null != currentActivity && !currentActivity.getClass().equals(LoginActivity.class)) {
                String errorCode = null;
                if (null != event.getObject()) {
                    errorCode = (String) event.getObject();
                }
                LogOutHelper.logOut(currentActivity, errorCode);
            }
        }
    }

//    /**
//     * 登录
//     */
//    private void initLogin() {
//        UmengUpdateAgent.setUpdateCheckConfig(false);
//        LoginAccess.init(getApplication().getApplicationContext(), getApplication());
//        TDFPlatform platform = TDFPlatform.getInstance();
//        platform.setBuild_environment(TLoginHelper.getEnvType());
//        platform.setContext(getApplication().getApplicationContext());
//        platform.setVersionName("1.0");
//        LoginAccess.initLoginGatewayMap(platform, platform.getVersionName(), AppEnv.getApiKey(), AppEnv.getApiSecret(), AppEnv.getApiSecret());
//        LoginAccess.initPreferences();
//        LoginAccess.toChangeLoginTitle(18, Color.WHITE, false);
//        Map<String, String> mapPath = new HashMap<>();
//        mapPath.put(LoginAccess.NO_MAIN_PATH_KEY, LoginAccess.NO_MAIN_PATH_VALUE);
//        LoginAccess.initPath(mapPath);
//    }
}
