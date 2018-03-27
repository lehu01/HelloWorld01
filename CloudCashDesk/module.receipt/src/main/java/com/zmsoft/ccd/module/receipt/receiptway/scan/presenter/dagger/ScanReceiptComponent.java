package com.zmsoft.ccd.module.receipt.receiptway.scan.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.receiptway.scan.view.ScanReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = ScanReceiptPresenterModule.class)
public interface ScanReceiptComponent {

    void inject(ScanReceiptActivity normalReceiptActivity);

}
