package com.zmsoft.ccd.module.shortcutreceipt.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.shortcutreceipt.dagger.ShortCutReceiptDataComponent;
import com.zmsoft.ccd.module.shortcutreceipt.ShortCutReceiptActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = ShortCutReceiptDataComponent.class, modules = ShortCutReceiptPresenterModule.class)
public interface ShortCutReceiptComponent {

    void inject(ShortCutReceiptActivity activity);
}
