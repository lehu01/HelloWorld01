package com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.dagger;


import com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.NormalReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class NormalReceiptPresenterModule {

    private final NormalReceiptContract.View mView;

    public NormalReceiptPresenterModule(NormalReceiptContract.View view) {
        mView = view;
    }

    @Provides
    NormalReceiptContract.View provideCartContractView() {
        return mView;
    }

}
