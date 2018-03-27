package com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.receiptway.coupon.view.CouponReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = CouponReceiptPresenterModule.class)
public interface CouponReceiptComponent {

    void inject(CouponReceiptActivity normalReceiptActivity);

}
