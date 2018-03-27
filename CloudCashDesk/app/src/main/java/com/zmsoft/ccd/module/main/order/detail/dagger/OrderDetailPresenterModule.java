package com.zmsoft.ccd.module.main.order.detail.dagger;

import com.zmsoft.ccd.module.main.order.detail.OrderDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/8 10:28
 */
@Module
public class OrderDetailPresenterModule {

    private final OrderDetailContract.View mView;

    public OrderDetailPresenterModule(OrderDetailContract.View view) {
        mView = view;
    }

    @Provides
    OrderDetailContract.View provideOrderDetailContractView() {
        return mView;
    }

}
