package com.zmsoft.ccd.data.source.orderfind;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.desk.DeskRepository;
import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/25 17:05.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface OrderFindComponent {

    Context getContext();

    DeskRepository getDeskRepository();

    SeatSourceRepository getSeatSourceRepository();

    OrderSourceRepository getOrderSourceRepository();
}
