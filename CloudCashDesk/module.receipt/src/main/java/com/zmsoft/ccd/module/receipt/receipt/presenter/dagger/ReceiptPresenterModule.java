package com.zmsoft.ccd.module.receipt.receipt.presenter.dagger;

import com.zmsoft.ccd.module.receipt.receipt.presenter.ReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ReceiptPresenterModule {

    private final ReceiptContract.View mView;

    public ReceiptPresenterModule(ReceiptContract.View view) {
        mView = view;
    }

    @Provides
    ReceiptContract.View provideCartContractView() {
        return mView;
    }

}
