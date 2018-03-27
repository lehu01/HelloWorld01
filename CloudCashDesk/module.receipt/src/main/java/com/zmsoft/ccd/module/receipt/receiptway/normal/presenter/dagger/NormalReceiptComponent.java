package com.zmsoft.ccd.module.receipt.receiptway.normal.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receiptway.normal.view.NormalReceiptActivity;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = NormalReceiptPresenterModule.class)
public interface NormalReceiptComponent {

    void inject(NormalReceiptActivity normalReceiptActivity);

}
