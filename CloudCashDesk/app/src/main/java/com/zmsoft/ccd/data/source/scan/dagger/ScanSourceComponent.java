package com.zmsoft.ccd.data.source.scan.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.scan.ScanRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/29 17:14
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface ScanSourceComponent {

    Context getContext();

    ScanRepository getScanRepository();
}
