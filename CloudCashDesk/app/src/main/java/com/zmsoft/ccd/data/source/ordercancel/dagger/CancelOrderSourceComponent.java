package com.zmsoft.ccd.data.source.ordercancel.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.ordercancel.CancelOrderSourceRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:32
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface CancelOrderSourceComponent {

    Context getContext();

    CancelOrderSourceRepository getCancelOrderSourceRepository();

}
