package com.zmsoft.ccd.module.takeout.delivery.dagger;

import com.zmsoft.ccd.module.takeout.delivery.presenter.PendingDeliveryContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PendingDeliveryPresenterModule {

    private final PendingDeliveryContract.View mView;

    public PendingDeliveryPresenterModule(PendingDeliveryContract.View view) {
        mView = view;
    }

    @Provides
    PendingDeliveryContract.View provideDeliveryContractView(){
        return mView;
    }
}
