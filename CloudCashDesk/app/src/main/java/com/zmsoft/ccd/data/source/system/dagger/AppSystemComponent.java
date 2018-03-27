package com.zmsoft.ccd.data.source.system.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.system.AppSystemSourceRepository;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/20 14:18
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface AppSystemComponent {

    Context getContext();

    AppSystemSourceRepository getAppSystemSourceRepository();

}
