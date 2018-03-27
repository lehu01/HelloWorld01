package com.zmsoft.ccd.module.instance.updateweight.fragment.dagger;

import com.zmsoft.ccd.module.instance.updateweight.fragment.UpdateInstanceWeightContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
@Module
public class UpdateInstanceWeightPresenterModule {

    private final UpdateInstanceWeightContract.View mView;

    public UpdateInstanceWeightPresenterModule(UpdateInstanceWeightContract.View view) {
        mView = view;
    }

    @Provides
    UpdateInstanceWeightContract.View provideUpdateInstancePriceContractView(){
        return mView;
    }
}
