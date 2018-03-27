package com.zmsoft.ccd.module.main.order.complete.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.ordercomplete.dagger.OrderCompleteSourceComponent;
import com.zmsoft.ccd.module.main.order.complete.OrderCompleteActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:59.
 */
@PresentScoped
@Component(dependencies = OrderCompleteSourceComponent.class, modules = OrderCompleteModule.class)
public interface OrderCompleteComponent {
    void inject(OrderCompleteActivity orderCompleteActivity);
}
