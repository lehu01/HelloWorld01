package com.zmsoft.ccd.module.main.order.find.dagger;

import com.zmsoft.ccd.module.main.order.find.OrderFindContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 15:45.
 */
@Module
public class OrderFindPresenterModule {
    private final OrderFindContract.View mView;

    public OrderFindPresenterModule(OrderFindContract.View view) {
        mView = view;
    }

    @Provides
    OrderFindContract.View provideOrderFindContractView() {
        return mView;
    }
}
