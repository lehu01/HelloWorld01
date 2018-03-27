package com.zmsoft.ccd.module.carryout.dagger;

import com.zmsoft.ccd.module.carryout.CarryoutSettingContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CarryoutSettingPresenterModule {

    private final CarryoutSettingContract.View mView;

    public CarryoutSettingPresenterModule(CarryoutSettingContract.View view) {
        mView = view;
    }

    @Provides
    CarryoutSettingContract.View provideView() {
        return mView;
    }

}
