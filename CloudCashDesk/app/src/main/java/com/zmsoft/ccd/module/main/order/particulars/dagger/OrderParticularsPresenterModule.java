package com.zmsoft.ccd.module.main.order.particulars.dagger;

import com.zmsoft.ccd.module.main.order.particulars.OrderParticularsContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:10.
 */
@Module
public class OrderParticularsPresenterModule {
    private final OrderParticularsContract.View mView;

    public OrderParticularsPresenterModule(OrderParticularsContract.View view) {
        mView = view;
    }

    @Provides
    OrderParticularsContract.View provideOrderParticularsContractView() {
        return mView;
    }
}
