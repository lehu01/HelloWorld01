package com.zmsoft.ccd.module.takeout.delivery.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.takeout.delivery.DeliveryActivity;
import com.zmsoft.ccd.module.takeout.order.source.dagger.TakeoutSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = TakeoutSourceComponent.class, modules = DeliveryPresenterModule.class)
public interface DeliveryComponent {

    void inject(DeliveryActivity activity);
}
