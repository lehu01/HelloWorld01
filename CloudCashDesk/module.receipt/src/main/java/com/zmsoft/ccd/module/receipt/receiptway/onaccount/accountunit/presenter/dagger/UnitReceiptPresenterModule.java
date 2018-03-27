package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.dagger;


import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.UnitReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class UnitReceiptPresenterModule {

    private final UnitReceiptContract.View mView;

    public UnitReceiptPresenterModule(UnitReceiptContract.View view) {
        mView = view;
    }

    @Provides
    UnitReceiptContract.View provideCartContractView() {
        return mView;
    }

}
