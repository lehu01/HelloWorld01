package com.zmsoft.ccd.module.register.dagger;

import com.zmsoft.ccd.module.register.fragment.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Module
public class RegisterPresenterModule {

    private final RegisterContract.View mView;

    public RegisterPresenterModule(RegisterContract.View view) {
        mView = view;
    }

    @Provides
    RegisterContract.View provideRegisterContract() {
        return mView;
    }

}
