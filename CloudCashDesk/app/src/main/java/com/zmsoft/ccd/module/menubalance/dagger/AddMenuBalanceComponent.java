package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.menubalance.dagger.MenuComponent;
import com.zmsoft.ccd.module.menubalance.AddMenuBalanceActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuComponent.class, modules = AddMenuBalancePresenterModule.class)
public interface AddMenuBalanceComponent {

    void inject(AddMenuBalanceActivity addMenuBalanceActivity);
}
