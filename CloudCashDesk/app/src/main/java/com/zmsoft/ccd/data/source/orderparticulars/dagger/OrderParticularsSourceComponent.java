package com.zmsoft.ccd.data.source.orderparticulars.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.orderparticulars.OrderParticularsSourceRepository;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:32.
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface OrderParticularsSourceComponent {
    Context getContext();

    OrderParticularsSourceRepository getOrderParticularsSourceRepository();
}
