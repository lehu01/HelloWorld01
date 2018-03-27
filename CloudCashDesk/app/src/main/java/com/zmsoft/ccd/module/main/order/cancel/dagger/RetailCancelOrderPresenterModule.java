package com.zmsoft.ccd.module.main.order.cancel.dagger;

import com.zmsoft.ccd.module.main.order.cancel.fragment.RetailCancelOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:35
 */
@Module
public class RetailCancelOrderPresenterModule {

    private final RetailCancelOrderContract.View mView;

    public RetailCancelOrderPresenterModule(RetailCancelOrderContract.View view) {
        mView = view;
    }

    @Provides
    RetailCancelOrderContract.View provideCancelOrderContractView() {
        return mView;
    }
}
