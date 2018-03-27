package com.zmsoft.ccd.module.main.order.find.dagger;

import com.zmsoft.ccd.module.main.order.find.RetailOrderFindContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 15:45.
 */
@Module
public class RetailOrderFindPresenterModule {
    private final RetailOrderFindContract.View mView;

    public RetailOrderFindPresenterModule(RetailOrderFindContract.View view) {
        mView = view;
    }

    @Provides
    RetailOrderFindContract.View provideRetailOrderFindContractView() {
        return mView;
    }
}
