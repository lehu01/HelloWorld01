package com.zmsoft.ccd.module.main.order.detail.dagger;

import com.zmsoft.ccd.module.main.order.detail.RetailOrderDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/8 10:28
 */
@Module
public class RetailOrderDetailPresenterModule {

    private final RetailOrderDetailContract.View mView;

    public RetailOrderDetailPresenterModule(RetailOrderDetailContract.View view) {
        mView = view;
    }

    @Provides
    RetailOrderDetailContract.View provideOrderDetailContractView() {
        return mView;
    }

}
