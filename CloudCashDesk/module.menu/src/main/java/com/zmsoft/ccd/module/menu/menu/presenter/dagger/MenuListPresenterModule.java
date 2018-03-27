package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.module.menu.menu.presenter.MenuListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuListPresenterModule {

    private final MenuListContract.View mView;

    public MenuListPresenterModule(MenuListContract.View view) {
        mView = view;
    }

    @Provides
    MenuListContract.View provideView() {
        return mView;
    }

}
