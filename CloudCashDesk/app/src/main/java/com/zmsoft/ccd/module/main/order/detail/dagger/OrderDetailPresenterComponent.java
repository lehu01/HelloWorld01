package com.zmsoft.ccd.module.main.order.detail.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.orderdetail.dagger.OrderDetailSourceComponent;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/8 10:29
 */
@PresentScoped
@Component(dependencies = OrderDetailSourceComponent.class, modules = OrderDetailPresenterModule.class)
public interface OrderDetailPresenterComponent {

    void inject(OrderDetailActivity orderDetailActivity);

}
