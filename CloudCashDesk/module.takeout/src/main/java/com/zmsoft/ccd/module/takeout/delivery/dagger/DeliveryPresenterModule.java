package com.zmsoft.ccd.module.takeout.delivery.dagger;

import com.zmsoft.ccd.module.takeout.delivery.presenter.DeliveryContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DeliveryPresenterModule {

    private final DeliveryContract.View mView;

    public DeliveryPresenterModule(DeliveryContract.View view) {
        mView = view;
    }

    @Provides
    DeliveryContract.View provideDeliveryContractView() {
        return mView;
    }
}
