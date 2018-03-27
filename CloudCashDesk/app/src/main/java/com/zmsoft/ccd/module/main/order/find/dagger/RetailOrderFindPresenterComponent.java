package com.zmsoft.ccd.module.main.order.find.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.orderfind.OrderFindComponent;
import com.zmsoft.ccd.module.main.RetailMainActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 15:45.
 */
@PresentScoped
@Component(dependencies = OrderFindComponent.class, modules = RetailOrderFindPresenterModule.class)
public interface RetailOrderFindPresenterComponent {

//    void inject(OrderFindActivity orderFindActivity);

    void inject(RetailMainActivity mainActivity);
}
