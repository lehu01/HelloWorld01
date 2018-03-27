package com.zmsoft.ccd.module.splash.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.splash.dagger.SplashComponent;
import com.zmsoft.ccd.module.splash.SplashActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/19 16:21
 */
@PresentScoped
@Component(dependencies = SplashComponent.class, modules = SplashPresenterModule.class)
public interface SplashPresenterComponent {

    void inject(SplashActivity splashActivity);

}

