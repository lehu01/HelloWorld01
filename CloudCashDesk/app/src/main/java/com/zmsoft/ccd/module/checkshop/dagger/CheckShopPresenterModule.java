package com.zmsoft.ccd.module.checkshop.dagger;

import com.zmsoft.ccd.module.checkshop.fragment.CheckShopContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@Module
public class CheckShopPresenterModule {

    private final CheckShopContract.View mView;

    public CheckShopPresenterModule(CheckShopContract.View view) {
        mView = view;
    }

    @Provides
    CheckShopContract.View provideCheckShopContractView() {
        return mView;
    }

}
