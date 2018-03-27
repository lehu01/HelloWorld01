package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.module.menubalance.AddMenuBalanceContract;

import dagger.Module;
import dagger.Provides;

@Module
public class AddMenuBalancePresenterModule {

    private final AddMenuBalanceContract.View mView;

    public AddMenuBalancePresenterModule(AddMenuBalanceContract.View view) {
        mView = view;
    }

    @Provides
    AddMenuBalanceContract.View provideAddMenuBalanceView(){
        return mView;
    }
}
