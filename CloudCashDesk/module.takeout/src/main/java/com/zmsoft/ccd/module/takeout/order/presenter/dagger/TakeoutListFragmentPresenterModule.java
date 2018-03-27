package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutListFragmentPresenterModule {

    private final TakeoutListFragmentContract.View mView;

    public TakeoutListFragmentPresenterModule(TakeoutListFragmentContract.View view) {
        mView = view;
    }

    @Provides
    TakeoutListFragmentContract.View provideView() {
        return mView;
    }

}
