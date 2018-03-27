package com.zmsoft.ccd.data.source.ordersearch;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 19:43.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface OrderSeatSearchComponent {
    Context getContext();

    SeatSourceRepository getSeatSourceRepository();

    OrderSourceRepository getOrderSourceRepository();
}
