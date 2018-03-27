package com.zmsoft.ccd.module.login.login.dagger;

import com.zmsoft.ccd.module.login.login.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 18/02/2017.
 */
@Module
public class LoginPresentModule {

    private final LoginContract.View mView;

    public LoginPresentModule(LoginContract.View view) {
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView(){
        return mView;
    }
}
