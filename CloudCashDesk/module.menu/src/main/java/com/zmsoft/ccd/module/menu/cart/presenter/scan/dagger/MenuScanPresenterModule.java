package com.zmsoft.ccd.module.menu.cart.presenter.scan.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.scan.ScanContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuScanPresenterModule {

    private final ScanContract.View mView;

    public MenuScanPresenterModule(ScanContract.View view) {
        mView = view;
    }

    @Provides
    ScanContract.View provideCartContractView() {
        return mView;
    }

}
