package com.zmsoft.ccd.module.instance.cancel.fragment.dagger;

import com.zmsoft.ccd.module.instance.cancel.fragment.CancelOrGiveInstanceContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
@Module
public class CancelOrGiveInstancePresenterModule {

    private final CancelOrGiveInstanceContract.View mView;

    public CancelOrGiveInstancePresenterModule(CancelOrGiveInstanceContract.View view) {
        mView = view;
    }

    @Provides
    CancelOrGiveInstanceContract.View provideCancelOrGiveInstanceContract() {
        return mView;
    }
}
