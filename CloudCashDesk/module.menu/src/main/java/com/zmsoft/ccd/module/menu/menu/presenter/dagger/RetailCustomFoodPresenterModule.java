package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.RetailCustomFoodContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailCustomFoodPresenterModule {

    private final RetailCustomFoodContract.View mView;

    public RetailCustomFoodPresenterModule(RetailCustomFoodContract.View view) {
        mView = view;
    }

    @Provides
    RetailCustomFoodContract.View provideView() {
        return mView;
    }

}
