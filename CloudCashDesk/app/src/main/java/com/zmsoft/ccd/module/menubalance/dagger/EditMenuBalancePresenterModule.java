package com.zmsoft.ccd.module.menubalance.dagger;

import com.zmsoft.ccd.module.menubalance.EditMenuBalanceContract;
import com.zmsoft.ccd.module.menubalance.SelectMenuBalanceContract;

import dagger.Module;
import dagger.Provides;

@Module
public class EditMenuBalancePresenterModule {

    private final EditMenuBalanceContract.View mView;

    public EditMenuBalancePresenterModule(EditMenuBalanceContract.View view) {
        mView = view;
    }

    @Provides
    EditMenuBalanceContract.View provideAddMenuBalanceView(){
        return mView;
    }
}
