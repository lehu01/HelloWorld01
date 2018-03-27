package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.MenuListFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = {MenuSourceComponent.class}, modules = MenuListPresenterModule.class)
public interface MenuListComponent {

    void inject(MenuListFragment menuListFragment);
}
