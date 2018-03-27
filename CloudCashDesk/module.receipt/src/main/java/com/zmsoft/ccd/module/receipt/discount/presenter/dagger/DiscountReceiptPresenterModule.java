package com.zmsoft.ccd.module.receipt.discount.presenter.dagger;


import com.zmsoft.ccd.module.receipt.discount.presenter.DiscountReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DiscountReceiptPresenterModule {

    private final DiscountReceiptContract.View mView;

    public DiscountReceiptPresenterModule(DiscountReceiptContract.View view) {
        mView = view;
    }

    @Provides
    DiscountReceiptContract.View provideCartContractView() {
        return mView;
    }

}
