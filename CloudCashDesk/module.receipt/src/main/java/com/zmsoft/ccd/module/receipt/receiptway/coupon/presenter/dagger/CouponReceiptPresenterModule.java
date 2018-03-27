package com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter.dagger;


import com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter.CouponReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CouponReceiptPresenterModule {

    private final CouponReceiptContract.View mView;

    public CouponReceiptPresenterModule(CouponReceiptContract.View view) {
        mView = view;
    }

    @Provides
    CouponReceiptContract.View provideCartContractView() {
        return mView;
    }

}
