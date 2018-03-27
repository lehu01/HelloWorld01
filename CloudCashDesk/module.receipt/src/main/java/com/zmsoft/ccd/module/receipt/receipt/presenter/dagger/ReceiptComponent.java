package com.zmsoft.ccd.module.receipt.receipt.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.receipt.view.ReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = ReceiptPresenterModule.class)
public interface ReceiptComponent {

    void inject(ReceiptActivity receiptActivity);

}
