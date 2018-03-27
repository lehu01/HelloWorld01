package com.zmsoft.ccd.module.main.order.summary.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.ordersummary.dagger.OrderSummarySourceComponent;
import com.zmsoft.ccd.module.main.order.summary.OrderSummaryActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:42.
 */

@PresentScoped
@Component(dependencies = OrderSummarySourceComponent.class, modules = OrderSummaryModule.class)
public interface OrderSummaryComponent {
    void inject(OrderSummaryActivity orderSummaryActivity);
}
