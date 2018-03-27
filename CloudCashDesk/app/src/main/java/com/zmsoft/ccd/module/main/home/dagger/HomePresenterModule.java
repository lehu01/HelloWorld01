package com.zmsoft.ccd.module.main.home.dagger;

import com.zmsoft.ccd.module.main.home.presenter.HomeContract;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePresenterModule {

    private final HomeContract.View mView;

    public HomePresenterModule(HomeContract.View view) {
        mView = view;
    }

    @Provides
    HomeContract.View provideHomeContractView(){
        return mView;
    }
}
