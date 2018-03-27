package com.zmsoft.ccd.module.main.order.summary.dagger;

import com.zmsoft.ccd.module.main.order.summary.OrderSummaryContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:42.
 */

@Module
public class OrderSummaryModule {

    private final OrderSummaryContract.View mView;

    public OrderSummaryModule(OrderSummaryContract.View view) {
        mView = view;
    }

    @Provides
    OrderSummaryContract.View provideOrderSummaryFragmentContractView() {
        return mView;
    }
}
