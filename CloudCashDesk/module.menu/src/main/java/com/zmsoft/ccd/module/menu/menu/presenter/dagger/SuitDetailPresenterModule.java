package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.SuitDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SuitDetailPresenterModule {

    private final SuitDetailContract.View mView;

    public SuitDetailPresenterModule(SuitDetailContract.View view) {
        mView = view;
    }

    @Provides
    SuitDetailContract.View provideView() {
        return mView;
    }

}
