package com.zmsoft.ccd.module.login.mobilearea.dagger;

import com.zmsoft.ccd.module.login.mobilearea.MobileAreaContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/22 16:43.
 */
@Module
public class MobileAreaPresenterModule {
    private final MobileAreaContract.View mView;

    public MobileAreaPresenterModule(MobileAreaContract.View view) {
        mView = view;
    }

    @Provides
    MobileAreaContract.View provideMobileAreaContractView() {
        return mView;
    }
}
