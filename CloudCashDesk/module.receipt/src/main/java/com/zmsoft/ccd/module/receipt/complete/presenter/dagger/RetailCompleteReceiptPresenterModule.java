package com.zmsoft.ccd.module.receipt.complete.presenter.dagger;


import com.zmsoft.ccd.module.receipt.complete.presenter.RetailCompleteReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailCompleteReceiptPresenterModule {

    private final RetailCompleteReceiptContract.View mView;

    public RetailCompleteReceiptPresenterModule(RetailCompleteReceiptContract.View view) {
        mView = view;
    }

    @Provides
    RetailCompleteReceiptContract.View provideCartContractView() {
        return mView;
    }

}
