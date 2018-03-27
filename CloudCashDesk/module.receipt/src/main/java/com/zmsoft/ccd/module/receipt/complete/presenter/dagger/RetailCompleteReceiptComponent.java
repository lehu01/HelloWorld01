package com.zmsoft.ccd.module.receipt.complete.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.complete.view.RetailCompleteReceiptActivity;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = RetailCompleteReceiptPresenterModule.class)
public interface RetailCompleteReceiptComponent {

    void inject(RetailCompleteReceiptActivity normalReceiptActivity);

}
