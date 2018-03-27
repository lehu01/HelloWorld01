package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.CustomFoodContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomFoodPresenterModule {

    private final CustomFoodContract.View mView;

    public CustomFoodPresenterModule(CustomFoodContract.View view) {
        mView = view;
    }

    @Provides
    CustomFoodContract.View provideView() {
        return mView;
    }

}
