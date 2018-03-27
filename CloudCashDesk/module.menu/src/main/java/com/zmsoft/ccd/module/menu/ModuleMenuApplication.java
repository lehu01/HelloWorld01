package com.zmsoft.ccd.module.menu;

import android.app.Application;


import com.bumptech.glide.request.target.ViewTarget;
import com.chiclaim.modularization.router.MRouter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.network.CcdNetWorkConfig;
import com.zmsoft.ccd.network.CommonConstant;

/**
 * @author DangGui
 * @create 2017/4/15.
 */

public class ModuleMenuApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        iniRouter();
        //初始化LeakCanary
        initLeakCanary();
        //初始化全局applicationContext
        initGlobal();
        //初始化网络框架
        initNetWork();
        //初始化Logger
        initLogger();
        //初始化GlideTag
        initGlideTag();
        //初始化Eventbus
        initEventbus();
    }

    private void iniRouter() {
        MRouter.getInstance().init(this);
    }

    /**
     * 初始化LeakCanary
     */
    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
        }
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        Logger.Settings settings = Logger.init(CommonConstant.APP_LOGGER_TAG);
        if (BuildConfig.DEBUG) {
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

    private void initGlobal() {
        GlobalVars.init(this);
    }

    /**
     * 初始化网络框架
     */
    private void initNetWork() {
        CcdNetWorkConfig.initNetWork(this, BuildConfig.DEBUG, BuildConfig.VERSION_CODE);
    }

    /**
     * 初始化Eventbus
     */
    private void initEventbus() {
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
