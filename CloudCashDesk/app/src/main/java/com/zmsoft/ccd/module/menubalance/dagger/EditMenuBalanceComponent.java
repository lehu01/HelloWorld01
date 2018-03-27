package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.menubalance.dagger.MenuComponent;
import com.zmsoft.ccd.module.menubalance.EditMenuBalanceActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuComponent.class, modules = EditMenuBalancePresenterModule.class)
public interface EditMenuBalanceComponent {

    void inject(EditMenuBalanceActivity activity);
}
