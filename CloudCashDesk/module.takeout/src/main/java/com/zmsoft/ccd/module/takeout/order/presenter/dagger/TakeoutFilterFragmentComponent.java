package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.takeout.order.ui.TakeoutListFilterFragment;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = TakeoutFilterFragmentPresenterModule.class)
public interface TakeoutFilterFragmentComponent {

    void inject(TakeoutListFilterFragment fragment);
}
