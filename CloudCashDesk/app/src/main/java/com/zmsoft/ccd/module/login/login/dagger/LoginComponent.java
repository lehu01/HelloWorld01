package com.zmsoft.ccd.module.login.login.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.UserComponent;
import com.zmsoft.ccd.module.login.login.LoginActivity;

import dagger.Component;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 18/02/2017.
 */
@PresentScoped
@Component(dependencies = UserComponent.class, modules = LoginPresentModule.class)
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
