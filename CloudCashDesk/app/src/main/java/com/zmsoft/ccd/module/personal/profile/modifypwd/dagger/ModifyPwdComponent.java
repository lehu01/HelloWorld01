package com.zmsoft.ccd.module.personal.profile.modifypwd.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.UserComponent;
import com.zmsoft.ccd.module.personal.profile.modifypwd.ModifyPwdActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = UserComponent.class, modules = ModifyPwdPresenterModule.class)
public interface ModifyPwdComponent {

    void inject(ModifyPwdActivity activity);
}
