package com.zmsoft.ccd.module.shortcutreceipt.dagger;

import com.zmsoft.ccd.module.shortcutreceipt.RetailShortCutReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailShortCutReceiptPresenterModule {

    private final RetailShortCutReceiptContract.View mView;

    public RetailShortCutReceiptPresenterModule(RetailShortCutReceiptContract.View view) {
        mView = view;
    }

    @Provides
    RetailShortCutReceiptContract.View provideRetailShortCutReceiptView(){
        return mView;
    }
}
