package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.MenuListActivityContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuListActivityPresenterModule {

    private final MenuListActivityContract.View mView;

    public MenuListActivityPresenterModule(MenuListActivityContract.View view) {
        mView = view;
    }

    @Provides
    MenuListActivityContract.View provideView() {
        return mView;
    }

}
