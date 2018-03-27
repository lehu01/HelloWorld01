package com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.dagger;


import com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.ScanReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ScanReceiptPresenterModule {

    private final ScanReceiptContract.View mView;

    public ScanReceiptPresenterModule(ScanReceiptContract.View view) {
        mView = view;
    }

    @Provides
    ScanReceiptContract.View provideCartContractView() {
        return mView;
    }

}
