package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.menubalance.dagger.MenuComponent;
import com.zmsoft.ccd.module.menubalance.SelectMenuBalanceActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuComponent.class, modules = SelectMenuBalancePresenterModule.class)
public interface SelectMenuBalanceComponent {

    void inject(SelectMenuBalanceActivity selectMenuBalanceActivity);
}
