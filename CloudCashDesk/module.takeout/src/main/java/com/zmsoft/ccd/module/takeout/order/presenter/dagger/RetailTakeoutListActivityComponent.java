package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;
import com.zmsoft.ccd.module.takeout.order.ui.RetailTakeoutListActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = RetailTakeoutListActivityPresenterModule.class)
public interface RetailTakeoutListActivityComponent {

    void inject(RetailTakeoutListActivity activity);
}
