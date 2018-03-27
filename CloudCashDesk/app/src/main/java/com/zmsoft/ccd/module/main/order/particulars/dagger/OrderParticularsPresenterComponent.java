package com.zmsoft.ccd.module.main.order.particulars.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.orderparticulars.dagger.OrderParticularsSourceComponent;
import com.zmsoft.ccd.module.main.order.particulars.OrderParticularsActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:12.
 */
@PresentScoped
@Component(dependencies = OrderParticularsSourceComponent.class, modules = OrderParticularsPresenterModule.class)
public interface OrderParticularsPresenterComponent {
    void inject(OrderParticularsActivity activity);
}
