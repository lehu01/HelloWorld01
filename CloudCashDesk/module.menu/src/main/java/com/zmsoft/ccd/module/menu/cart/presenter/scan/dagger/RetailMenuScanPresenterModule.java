package com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.scan.RetailScanContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailMenuScanPresenterModule {

    private final RetailScanContract.View mView;

    public RetailMenuScanPresenterModule(RetailScanContract.View view) {
        mView = view;
    }

    @Provides
    RetailScanContract.View provideRetailScanContractView() {
        return mView;
    }

}
