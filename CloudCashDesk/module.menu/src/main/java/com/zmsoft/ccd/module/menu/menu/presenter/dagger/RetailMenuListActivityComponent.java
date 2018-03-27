package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.dagger.CommonSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.RetailMenuListActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = CommonSourceComponent.class, modules = RetailMenuListActivityPresenterModule.class)
public interface RetailMenuListActivityComponent {

    void inject(RetailMenuListActivity activity);
}
