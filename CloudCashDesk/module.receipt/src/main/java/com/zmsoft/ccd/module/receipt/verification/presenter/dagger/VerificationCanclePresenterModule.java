package com.zmsoft.ccd.module.receipt.verification.presenter.dagger;


import com.zmsoft.ccd.module.receipt.verification.presenter.VerificationCancleContract;

import dagger.Module;
import dagger.Provides;

@Module
public class VerificationCanclePresenterModule {

    private final VerificationCancleContract.View mView;

    public VerificationCanclePresenterModule(VerificationCancleContract.View view) {
        mView = view;
    }

    @Provides
    VerificationCancleContract.View provideCartContractView() {
        return mView;
    }

}
