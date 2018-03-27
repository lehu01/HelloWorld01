package com.zmsoft.ccd.data.source.seat.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 19:15
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface SeatSourceComponent {

    Context getContext();

    SeatSourceRepository getSeatSourceRepository();
}
