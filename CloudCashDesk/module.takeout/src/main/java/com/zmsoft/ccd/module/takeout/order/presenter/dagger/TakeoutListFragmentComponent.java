package com.zmsoft.ccd.module.takeout.order.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.takeout.order.ui.TakeoutListFragment;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = TakeoutListFragmentPresenterModule.class)
public interface TakeoutListFragmentComponent {

    void inject(TakeoutListFragment fragment);
}
