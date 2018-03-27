package com.zmsoft.ccd.module.main.message.detail.dagger;

import com.zmsoft.ccd.module.main.message.detail.MessageDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MsgCenterDetailPresenterModule {

    private final MessageDetailContract.View mView;

    public MsgCenterDetailPresenterModule(MessageDetailContract.View view) {
        mView = view;
    }

    @Provides
    MessageDetailContract.View provideMsgCenterContractView(){
        return mView;
    }
}
