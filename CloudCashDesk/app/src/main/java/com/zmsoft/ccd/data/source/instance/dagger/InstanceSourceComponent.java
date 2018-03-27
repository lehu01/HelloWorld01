package com.zmsoft.ccd.data.source.instance.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.instance.InstanceSourceRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:32
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface InstanceSourceComponent {

    Context getContext();

    InstanceSourceRepository getInstanceSourceRepository();

}
