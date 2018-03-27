package com.zmsoft.ccd.data.source.systemdircode.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.systemdircode.SystemDirCodeSourceRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 15:01
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface SystemDirCodeComponent {

    Context getContext();

    SystemDirCodeSourceRepository getSystemDirCodeSourceRepository();
}
