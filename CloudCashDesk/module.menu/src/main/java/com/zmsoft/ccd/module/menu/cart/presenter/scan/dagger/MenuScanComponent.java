package com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.cart.view.MenuScannerActivity;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuSourceComponent.class, modules = MenuScanPresenterModule.class)
public interface MenuScanComponent {

    void inject(MenuScannerActivity menuScannerActivity);
}

