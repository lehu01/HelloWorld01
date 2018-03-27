package com.zmsoft.ccd.module.receipt.complete.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.complete.view.CompleteReceiptActivity;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = CompletReceiptPresenterModule.class)
public interface CompletReceiptComponent {

    void inject(CompleteReceiptActivity normalReceiptActivity);

}
