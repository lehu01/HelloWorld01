package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.CustomFoodFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuSourceComponent.class, modules = CustomFoodPresenterModule.class)
public interface CustomFoodComponent {

    void inject(CustomFoodFragment menuListFragment);
}
