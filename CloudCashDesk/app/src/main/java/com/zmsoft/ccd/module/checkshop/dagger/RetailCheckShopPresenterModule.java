package com.zmsoft.ccd.module.checkshop.dagger;

import com.zmsoft.ccd.module.checkshop.fragment.RetailCheckShopContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@Module
public class RetailCheckShopPresenterModule {

    private final RetailCheckShopContract.View mView;

    public RetailCheckShopPresenterModule(RetailCheckShopContract.View view) {
        mView = view;
    }

    @Provides
    RetailCheckShopContract.View provideRetailCheckShopContractView() {
        return mView;
    }

}
