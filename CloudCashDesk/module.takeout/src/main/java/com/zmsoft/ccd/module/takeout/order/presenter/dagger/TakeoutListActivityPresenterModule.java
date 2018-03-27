package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutListActivityPresenterModule {

    private final TakeoutListActivityContract.View mView;

    public TakeoutListActivityPresenterModule(TakeoutListActivityContract.View view) {
        mView = view;
    }

    @Provides
    TakeoutListActivityContract.View provideView() {
        return mView;
    }

}
