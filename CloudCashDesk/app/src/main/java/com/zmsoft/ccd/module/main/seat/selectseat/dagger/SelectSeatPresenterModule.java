package com.zmsoft.ccd.module.main.seat.selectseat.dagger;

import com.zmsoft.ccd.module.main.seat.selectseat.fragment.SelectSeatContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:05
 */
@Module
public class SelectSeatPresenterModule {

    private final SelectSeatContract.View mView;

    public SelectSeatPresenterModule(SelectSeatContract.View view) {
        mView = view;
    }

    @Provides
    SelectSeatContract.View provideSelectSeatContractView() {
        return mView;
    }
}
