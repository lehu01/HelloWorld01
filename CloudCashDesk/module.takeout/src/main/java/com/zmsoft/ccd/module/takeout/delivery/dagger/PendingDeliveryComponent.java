package com.zmsoft.ccd.module.takeout.delivery.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.takeout.delivery.PendingDeliveryListActivity;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = PendingDeliveryPresenterModule.class)
public interface PendingDeliveryComponent {

    void inject(PendingDeliveryListActivity activity);
}
