package com.zmsoft.ccd.data.source.workmodel.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.workmodel.WorkModelSourceRepository;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/31 10:16
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface WorkModelSourceComponent {

    Context getContext();

    WorkModelSourceRepository getWorkModelSourceRepository();

}
