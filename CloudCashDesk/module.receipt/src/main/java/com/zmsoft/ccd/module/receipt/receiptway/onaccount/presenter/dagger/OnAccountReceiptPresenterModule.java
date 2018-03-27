package com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.dagger;


import com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.OnAccountReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class OnAccountReceiptPresenterModule {

    private final OnAccountReceiptContract.View mView;

    public OnAccountReceiptPresenterModule(OnAccountReceiptContract.View view) {
        mView = view;
    }

    @Provides
    OnAccountReceiptContract.View provideCartContractView() {
        return mView;
    }

}
