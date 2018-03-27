package com.zmsoft.ccd.data.source.splash.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.splash.SplashSourceRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 14:10
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface SplashComponent {

    Context getContext();

    SplashSourceRepository getSplashSourceRepository();

}
