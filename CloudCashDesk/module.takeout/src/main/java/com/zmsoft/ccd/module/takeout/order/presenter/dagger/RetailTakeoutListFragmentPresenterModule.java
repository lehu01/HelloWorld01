package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.RetailTakeoutListFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailTakeoutListFragmentPresenterModule {

    private final RetailTakeoutListFragmentContract.View mView;

    public RetailTakeoutListFragmentPresenterModule(RetailTakeoutListFragmentContract.View view) {
        mView = view;
    }

    @Provides
    RetailTakeoutListFragmentContract.View provideView() {
        return mView;
    }

}
