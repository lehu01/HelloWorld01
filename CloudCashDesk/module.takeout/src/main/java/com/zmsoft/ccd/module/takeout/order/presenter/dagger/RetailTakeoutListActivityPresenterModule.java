package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.RetailTakeoutListActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailTakeoutListActivityPresenterModule {

    private final RetailTakeoutListActivityContract.View mView;

    public RetailTakeoutListActivityPresenterModule(RetailTakeoutListActivityContract.View view) {
        mView = view;
    }

    @Provides
    RetailTakeoutListActivityContract.View provideView() {
        return mView;
    }

}
