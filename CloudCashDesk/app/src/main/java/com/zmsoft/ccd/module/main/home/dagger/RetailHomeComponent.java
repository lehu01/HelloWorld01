package com.zmsoft.ccd.module.main.home.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.home.dagger.HomeSourceComponent;
import com.zmsoft.ccd.module.main.home.view.RetailHomeFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = HomeSourceComponent.class, modules = RetailHomePresenterModule.class)
public interface RetailHomeComponent {

    void inject(RetailHomeFragment fragment);
}
