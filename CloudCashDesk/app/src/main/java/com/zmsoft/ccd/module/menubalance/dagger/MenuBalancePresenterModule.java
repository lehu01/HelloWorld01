package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.module.menubalance.MenuBalanceContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuBalancePresenterModule {

    private final MenuBalanceContract.View mView;

    public MenuBalancePresenterModule(MenuBalanceContract.View view) {
        mView = view;
    }

    @Provides
    MenuBalanceContract.View provideAddMenuBalanceView(){
        return mView;
    }
}
