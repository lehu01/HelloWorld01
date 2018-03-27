package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.menubalance.dagger.MenuComponent;
import com.zmsoft.ccd.module.menubalance.MenuBalanceActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuComponent.class, modules = MenuBalancePresenterModule.class)
public interface MenuBalanceComponent {

    void inject(MenuBalanceActivity fragment);
}
