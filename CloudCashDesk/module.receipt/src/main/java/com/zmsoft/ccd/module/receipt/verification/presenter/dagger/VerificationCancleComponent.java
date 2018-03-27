package com.zmsoft.ccd.module.receipt.verification.presenter.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.verification.view.VerificationCancleActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ReceiptSourceComponent.class, modules = VerificationCanclePresenterModule.class)
public interface VerificationCancleComponent {

    void inject(VerificationCancleActivity normalReceiptActivity);

}
