package com.zmsoft.ccd.module.menu.menu.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.ui.SuitDetailFragment;

import dagger.Component;

@PresentScoped
@Component(dependencies = MenuSourceComponent.class, modules = SuitDetailPresenterModule.class)
public interface SuitDetailComponent {

    void inject(SuitDetailFragment fragment);
}
