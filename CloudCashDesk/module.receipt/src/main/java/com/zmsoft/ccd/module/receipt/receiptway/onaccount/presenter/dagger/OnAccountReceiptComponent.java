package com.zmsoft.ccd.module.receipt.receiptway.onaccount.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.view.OnAccountReceiptActivity;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = OnAccountReceiptPresenterModule.class)
public interface OnAccountReceiptComponent {

    void inject(OnAccountReceiptActivity normalReceiptActivity);

}
