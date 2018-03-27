package com.zmsoft.ccd.module.personal.attention.dagger;

import com.zmsoft.ccd.module.personal.attention.AddAttenDeskContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DeskPresenterModule {

    private final AddAttenDeskContract.View mView;

    public DeskPresenterModule(AddAttenDeskContract.View view) {
        mView = view;
    }

    @Provides
    AddAttenDeskContract.View provideAddAttenDeskContractView(){
        return mView;
    }
}
