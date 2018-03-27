package com.zmsoft.ccd.module.main.order.search.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.ordersearch.OrderSeatSearchComponent;
import com.zmsoft.ccd.module.main.order.search.OrderSeatSearchActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 19:40.
 */

@PresentScoped
@Component(dependencies = OrderSeatSearchComponent.class, modules = OrderSeatSearchPresenterModule.class)
public interface OrderSeatSearchPresenterComponent {
    void inject(OrderSeatSearchActivity mainActivity);
}
