package com.zmsoft.ccd.module.login.mobilearea.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.UserComponent;
import com.zmsoft.ccd.module.login.mobilearea.MobileAreaActivity;

import dagger.Component;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/22 16:44.
 */
@PresentScoped
@Component(dependencies = UserComponent.class, modules = MobileAreaPresenterModule.class)
public interface MobileAreaPresenterComponent {

    void inject(MobileAreaActivity activity);
}
