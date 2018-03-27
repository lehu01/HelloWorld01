package com.zmsoft.ccd.module.register.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.UserComponent;
import com.zmsoft.ccd.module.register.RegisterActivity;

import dagger.Component;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@PresentScoped
@Component(dependencies = UserComponent.class, modules = RegisterPresenterModule.class)
public interface RegisterComponent {

    void inject(RegisterActivity registerActivity);

}
