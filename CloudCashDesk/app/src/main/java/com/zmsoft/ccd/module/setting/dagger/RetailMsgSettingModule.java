package com.zmsoft.ccd.module.setting.dagger;

import com.zmsoft.ccd.module.setting.RetailMsgSettingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huaixi on 2017/10/24.
 */

@Module
public class RetailMsgSettingModule {
    private final RetailMsgSettingContract.View mView;

    public RetailMsgSettingModule(RetailMsgSettingContract.View view) {
        mView = view;
    }

    @Provides
    RetailMsgSettingContract.View provideRetailMsgSettingContractView() {
        return mView;
    }
}
