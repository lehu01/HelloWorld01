package com.zmsoft.ccd.module.main.order.search.dagger;

import com.zmsoft.ccd.module.main.order.search.OrderSeatSearchContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 19:39.
 */
@Module
public class OrderSeatSearchPresenterModule {
    private final OrderSeatSearchContract.View mView;

    public OrderSeatSearchPresenterModule(OrderSeatSearchContract.View view) {
        mView = view;
    }

    @Provides
    OrderSeatSearchContract.View provideOrderSeatSearchContract() {
        return mView;
    }
}
