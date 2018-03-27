package com.zmsoft.ccd.module.shortcutreceipt.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.shortcutreceipt.dagger.ShortCutReceiptDataComponent;
import com.zmsoft.ccd.module.shortcutreceipt.RetailShortCutReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ShortCutReceiptDataComponent.class, modules = RetailShortCutReceiptPresenterModule.class)
public interface RetailShortCutReceiptComponent {

    void inject(RetailShortCutReceiptActivity activity);
}
