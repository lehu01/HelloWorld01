package com.zmsoft.ccd.module.main.home.dagger;

import com.zmsoft.ccd.module.main.home.presenter.RetailHomeContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailHomePresenterModule {

    private final RetailHomeContract.View mView;

    public RetailHomePresenterModule(RetailHomeContract.View view) {
        mView = view;
    }

    @Provides
    RetailHomeContract.View provideHomeContractView(){
        return mView;
    }
}
