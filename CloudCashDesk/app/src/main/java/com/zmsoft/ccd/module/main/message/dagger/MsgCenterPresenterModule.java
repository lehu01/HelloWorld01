package com.zmsoft.ccd.module.main.message.dagger;

import com.zmsoft.ccd.module.main.message.MessageListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MsgCenterPresenterModule {

    private final MessageListContract.View mView;

    public MsgCenterPresenterModule(MessageListContract.View view) {
        mView = view;
    }

    @Provides
    MessageListContract.View provideMsgCenterContractView(){
        return mView;
    }
}
