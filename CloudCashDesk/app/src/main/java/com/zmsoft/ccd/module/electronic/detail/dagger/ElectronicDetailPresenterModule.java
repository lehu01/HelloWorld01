package com.zmsoft.ccd.module.electronic.detail.dagger;

import com.zmsoft.ccd.module.electronic.detail.ElectronicDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ElectronicDetailPresenterModule {

    private final ElectronicDetailContract.View mView;

    public ElectronicDetailPresenterModule(ElectronicDetailContract.View view) {
        mView = view;
    }

    @Provides
    ElectronicDetailContract.View provideMsgCenterContractView(){
        return mView;
    }
}
