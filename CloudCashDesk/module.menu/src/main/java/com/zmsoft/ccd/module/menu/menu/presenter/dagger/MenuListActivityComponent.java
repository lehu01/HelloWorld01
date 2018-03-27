package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.dagger.CommonSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.MenuListActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = CommonSourceComponent.class, modules = MenuListActivityPresenterModule.class)
public interface MenuListActivityComponent {

    void inject(MenuListActivity activity);
}
