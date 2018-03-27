package com.zmsoft.ccd.module.electronic.dagger;

import com.zmsoft.ccd.module.electronic.ElectronicContract;

import dagger.Module;
import dagger.Provides;

@Module
public class ElectronicPresenterModule {

    private final ElectronicContract.View mView;

    public ElectronicPresenterModule(ElectronicContract.View view) {
        mView = view;
    }

    @Provides
    ElectronicContract.View provideMsgCenterContractView(){
        return mView;
    }
}
