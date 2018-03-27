package com.zmsoft.ccd.module.main.order.detail.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.orderdetail.dagger.OrderDetailSourceComponent;
import com.zmsoft.ccd.module.main.order.detail.RetailOrderDetailActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/8 10:29
 */
@PresentScoped
@Component(dependencies = OrderDetailSourceComponent.class, modules = RetailOrderDetailPresenterModule.class)
public interface RetailOrderDetailPresenterComponent {

    void inject(RetailOrderDetailActivity activity);

}
