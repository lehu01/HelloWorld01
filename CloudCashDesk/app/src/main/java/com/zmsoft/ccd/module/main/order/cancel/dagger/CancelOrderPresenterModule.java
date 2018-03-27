package com.zmsoft.ccd.module.main.order.cancel.dagger;

import com.zmsoft.ccd.module.main.order.cancel.fragment.CancelOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:35
 */
@Module
public class CancelOrderPresenterModule {

    private final CancelOrderContract.View mView;

    public CancelOrderPresenterModule(CancelOrderContract.View view) {
        mView = view;
    }

    @Provides
    CancelOrderContract.View provideCancelOrderContractView() {
        return mView;
    }
}
