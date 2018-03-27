package com.zmsoft.ccd.module.main.order.complete.dagger;

import com.zmsoft.ccd.module.main.order.complete.OrderCompleteContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:58.
 */
@Module
public class OrderCompleteModule {
    private final OrderCompleteContract.View mView;

    public OrderCompleteModule(OrderCompleteContract.View view) {
        mView = view;
    }

    @Provides
    OrderCompleteContract.View provideOrderCompleteFragmentContractView() {
        return mView;
    }
}
