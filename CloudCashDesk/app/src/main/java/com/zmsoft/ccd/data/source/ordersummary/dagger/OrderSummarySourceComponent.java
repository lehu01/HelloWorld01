package com.zmsoft.ccd.data.source.ordersummary.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.ordersummary.OrderSummarySourceRepository;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:50.
 */

@ModelScoped
@Component(dependencies = AppComponent.class)
public interface OrderSummarySourceComponent {
    Context getContext();

    OrderSummarySourceRepository getOrderSummarySourceRepository();
}
