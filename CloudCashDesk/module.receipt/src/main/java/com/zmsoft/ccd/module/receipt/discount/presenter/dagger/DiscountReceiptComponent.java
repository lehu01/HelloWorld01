package com.zmsoft.ccd.module.receipt.discount.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.discount.view.DiscountReceiptActivity;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = DiscountReceiptPresenterModule.class)
public interface DiscountReceiptComponent {

    void inject(DiscountReceiptActivity normalReceiptActivity);

}
