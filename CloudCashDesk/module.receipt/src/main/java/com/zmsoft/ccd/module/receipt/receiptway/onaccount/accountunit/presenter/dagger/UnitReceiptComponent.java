package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.view.UnitReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = UnitReceiptPresenterModule.class)
public interface UnitReceiptComponent {

    void inject(UnitReceiptActivity normalReceiptActivity);

}
