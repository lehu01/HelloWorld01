package com.zmsoft.ccd.module.electronic.detail.dagger;

import com.zmsoft.ccd.module.electronic.detail.RetailElectronicDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailElectronicDetailPresenterModule {

    private final RetailElectronicDetailContract.View mView;

    public RetailElectronicDetailPresenterModule(RetailElectronicDetailContract.View view) {
        mView = view;
    }

    @Provides
    RetailElectronicDetailContract.View provideRetailElectronicDetailContractView(){
        return mView;
    }
}
