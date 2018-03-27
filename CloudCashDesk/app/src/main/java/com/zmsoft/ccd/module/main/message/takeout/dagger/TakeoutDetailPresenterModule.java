package com.zmsoft.ccd.module.main.message.takeout.dagger;

import com.zmsoft.ccd.module.main.message.takeout.TakeoutDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutDetailPresenterModule {

    private final TakeoutDetailContract.View mView;

    public TakeoutDetailPresenterModule(TakeoutDetailContract.View view) {
        mView = view;
    }

    @Provides
    TakeoutDetailContract.View provideMsgCenterContractView(){
        return mView;
    }
}
