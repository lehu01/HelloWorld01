package com.zmsoft.ccd.module.main.message.takeout.dagger;

import com.zmsoft.ccd.module.main.message.takeout.RetailTakeoutMsgDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailTakeoutMsgDetailPresenterModule {

    private final RetailTakeoutMsgDetailContract.View mView;

    public RetailTakeoutMsgDetailPresenterModule(RetailTakeoutMsgDetailContract.View view) {
        mView = view;
    }

    @Provides
    RetailTakeoutMsgDetailContract.View provideMsgCenterContractView(){
        return mView;
    }
}
