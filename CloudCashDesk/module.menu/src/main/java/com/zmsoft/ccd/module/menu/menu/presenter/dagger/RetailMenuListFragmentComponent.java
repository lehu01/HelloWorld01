package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.RetailMenuListFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = {MenuSourceComponent.class}, modules = RetailMenuListFragmentPresenterModule.class)
public interface RetailMenuListFragmentComponent {

    void inject(RetailMenuListFragment fragment);
}
