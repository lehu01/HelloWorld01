package com.zmsoft.ccd.module.splash.dagger;

import com.zmsoft.ccd.module.splash.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/19 16:22
 */
@Module
public class SplashPresenterModule {

    private final SplashContract.View mView;

    public SplashPresenterModule(SplashContract.View view) {
        mView = view;
    }

    @Provides
    SplashContract.View provideSplashContractView(){
        return mView;
    }

}
