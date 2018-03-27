package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutFilterFragmentContract;

import dagger.Module;
import dagger.Provides;

@Module
public class TakeoutFilterFragmentPresenterModule {

    private final TakeoutFilterFragmentContract.View mView;

    public TakeoutFilterFragmentPresenterModule(TakeoutFilterFragmentContract.View view) {
        mView = view;
    }

    @Provides
    TakeoutFilterFragmentContract.View provideView() {
        return mView;
    }

}
