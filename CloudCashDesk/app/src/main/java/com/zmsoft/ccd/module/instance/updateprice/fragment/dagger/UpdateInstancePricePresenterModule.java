package com.zmsoft.ccd.module.instance.updateprice.fragment.dagger;

import com.zmsoft.ccd.module.instance.updateprice.fragment.UpdateInstancePriceContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
@Module
public class UpdateInstancePricePresenterModule {

    private final UpdateInstancePriceContract.View mView;

    public UpdateInstancePricePresenterModule(UpdateInstancePriceContract.View view) {
        mView = view;
    }

    @Provides
    UpdateInstancePriceContract.View provideUpdateInstancePriceContractView(){
        return mView;
    }
}
