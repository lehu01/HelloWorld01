package com.zmsoft.ccd.module.shortcutreceipt.dagger;

import com.zmsoft.ccd.module.shortcutreceipt.ShortCutReceiptContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ShortCutReceiptPresenterModule {

    private final ShortCutReceiptContract.View mView;

    public ShortCutReceiptPresenterModule(ShortCutReceiptContract.View view) {
        mView = view;
    }

    @Provides
    ShortCutReceiptContract.View provideFeedbackView(){
        return mView;
    }
}
