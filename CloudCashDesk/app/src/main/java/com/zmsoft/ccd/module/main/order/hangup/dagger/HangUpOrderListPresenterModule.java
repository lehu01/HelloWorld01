package com.zmsoft.ccd.module.main.order.hangup.dagger;

import com.zmsoft.ccd.module.main.order.hangup.fragment.HangUpOrderListContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@Module
public class HangUpOrderListPresenterModule {

    private final HangUpOrderListContract.View mView;

    public HangUpOrderListPresenterModule(HangUpOrderListContract.View view) {
        mView = view;
    }

    @Provides
    HangUpOrderListContract.View provideHangUpOrderListContractView() {
        return mView;
    }

}
