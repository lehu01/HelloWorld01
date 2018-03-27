package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailMenuListActivityPresenterModule {

    private final RetailMenuListActivityContract.View mView;

    public RetailMenuListActivityPresenterModule(RetailMenuListActivityContract.View view) {
        mView = view;
    }

    @Provides
    RetailMenuListActivityContract.View provideView() {
        return mView;
    }

}
