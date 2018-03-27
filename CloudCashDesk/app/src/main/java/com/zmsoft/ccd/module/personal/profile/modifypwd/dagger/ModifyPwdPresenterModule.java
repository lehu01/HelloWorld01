package com.zmsoft.ccd.module.personal.profile.modifypwd.dagger;

import com.zmsoft.ccd.module.personal.profile.modifypwd.ModifyPwdContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ModifyPwdPresenterModule {

    private final ModifyPwdContract.View mView;

    public ModifyPwdPresenterModule(ModifyPwdContract.View view) {
        mView = view;
    }

    @Provides
    ModifyPwdContract.View provideModifyPwdView(){
        return mView;
    }
}
