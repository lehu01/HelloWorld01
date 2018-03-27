package com.zmsoft.ccd.data.source.print.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.print.PrintConfigSourceRepository;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface PrintConfigSourceComponent {

    Context getContext();

    PrintConfigSourceRepository getPrintConfigSourceRepository();

}
