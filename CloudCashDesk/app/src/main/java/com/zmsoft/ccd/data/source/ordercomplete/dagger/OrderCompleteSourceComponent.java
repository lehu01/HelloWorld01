package com.zmsoft.ccd.data.source.ordercomplete.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.ordercomplete.OrderCompleteSourceRepository;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:43.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface OrderCompleteSourceComponent {
    Context getContext();

    OrderCompleteSourceRepository getOrderCompleteSourceRepository();
}
