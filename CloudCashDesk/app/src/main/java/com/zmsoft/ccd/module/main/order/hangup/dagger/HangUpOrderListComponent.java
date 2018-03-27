package com.zmsoft.ccd.module.main.order.hangup.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.order.dagger.OrderSourceComponent;
import com.zmsoft.ccd.module.main.order.hangup.HangUpOrderListActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@PresentScoped
@Component(dependencies = OrderSourceComponent.class, modules = HangUpOrderListPresenterModule.class)
public interface HangUpOrderListComponent {

    void inject(HangUpOrderListActivity hangUpOrderListActivity);

}
