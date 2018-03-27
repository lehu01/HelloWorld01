package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.RetailMenuListFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailMenuListFragmentPresenterModule {

    private final RetailMenuListFragmentContract.View mView;

    public RetailMenuListFragmentPresenterModule(RetailMenuListFragmentContract.View view) {
        mView = view;
    }

    @Provides
    RetailMenuListFragmentContract.View provideView() {
        return mView;
    }

}
