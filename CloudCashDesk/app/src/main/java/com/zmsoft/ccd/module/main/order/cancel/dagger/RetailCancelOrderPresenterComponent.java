package com.zmsoft.ccd.module.main.order.cancel.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.ordercancel.dagger.CancelOrderSourceComponent;
import com.zmsoft.ccd.module.main.order.cancel.RetailCancelOrderActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:34
 */
@PresentScoped
@Component(dependencies = CancelOrderSourceComponent.class, modules = RetailCancelOrderPresenterModule.class)
public interface RetailCancelOrderPresenterComponent {

    void inject(RetailCancelOrderActivity activity);

}
