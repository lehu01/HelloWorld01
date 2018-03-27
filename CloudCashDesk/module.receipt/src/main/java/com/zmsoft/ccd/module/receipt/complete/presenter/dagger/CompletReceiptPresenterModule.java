package com.zmsoft.ccd.module.receipt.complete.presenter.dagger;


import com.zmsoft.ccd.module.receipt.complete.presenter.CompletReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CompletReceiptPresenterModule {

    private final CompletReceiptContract.View mView;

    public CompletReceiptPresenterModule(CompletReceiptContract.View view) {
        mView = view;
    }

    @Provides
    CompletReceiptContract.View provideCartContractView() {
        return mView;
    }

}
