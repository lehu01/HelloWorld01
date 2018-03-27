package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.RetailCustomFoodFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuSourceComponent.class, modules = RetailCustomFoodPresenterModule.class)
public interface RetailCustomFoodComponent {

    void inject(RetailCustomFoodFragment menuListFragment);
}
