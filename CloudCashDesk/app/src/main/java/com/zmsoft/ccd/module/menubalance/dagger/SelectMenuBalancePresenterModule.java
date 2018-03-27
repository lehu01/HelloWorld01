package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.module.menubalance.AddMenuBalanceContract;
import com.zmsoft.ccd.module.menubalance.SelectMenuBalanceContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectMenuBalancePresenterModule {

    private final SelectMenuBalanceContract.View mView;

    public SelectMenuBalancePresenterModule(SelectMenuBalanceContract.View view) {
        mView = view;
    }

    @Provides
    SelectMenuBalanceContract.View provideAddMenuBalanceView(){
        return mView;
    }
}
