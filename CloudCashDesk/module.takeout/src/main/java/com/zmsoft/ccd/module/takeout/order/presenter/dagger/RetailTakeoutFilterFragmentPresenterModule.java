package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.RetailTakeoutFilterFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailTakeoutFilterFragmentPresenterModule {

    private final RetailTakeoutFilterFragmentContract.View mView;

    public RetailTakeoutFilterFragmentPresenterModule(RetailTakeoutFilterFragmentContract.View view) {
        mView = view;
    }

    @Provides
    RetailTakeoutFilterFragmentContract.View provideView() {
        return mView;
    }

}
