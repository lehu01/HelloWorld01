package com.zmsoft.ccd.module.main.order.hangup.dagger;

import com.zmsoft.ccd.module.main.order.hangup.fragment.RetailHangUpOrderListContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@Module
public class RetailHangUpOrderListPresenterModule {

    private final RetailHangUpOrderListContract.View mView;

    public RetailHangUpOrderListPresenterModule(RetailHangUpOrderListContract.View view) {
        mView = view;
    }

    @Provides
    RetailHangUpOrderListContract.View provideRetailHangUpOrderListContractView() {
        return mView;
    }

}
