package com.zmsoft.ccd.module.main.message.dagger;

import com.zmsoft.ccd.module.main.message.RetailMessageListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailMsgCenterPresenterModule {

    private final RetailMessageListContract.View mView;

    public RetailMsgCenterPresenterModule(RetailMessageListContract.View view) {
        mView = view;
    }

    @Provides
    RetailMessageListContract.View provideRetailMsgCenterContractView(){
        return mView;
    }
}
