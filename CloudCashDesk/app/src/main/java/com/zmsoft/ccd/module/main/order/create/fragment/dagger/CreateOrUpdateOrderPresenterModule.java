package com.zmsoft.ccd.module.main.order.create.fragment.dagger;

import com.zmsoft.ccd.module.main.order.create.fragment.CreateOrUpdateOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:35
 */
@Module
public class CreateOrUpdateOrderPresenterModule {

    private final CreateOrUpdateOrderContract.View mView;

    public CreateOrUpdateOrderPresenterModule(CreateOrUpdateOrderContract.View view) {
        mView = view;
    }

    @Provides
    CreateOrUpdateOrderContract.View provideOrderListContractView() {
        return mView;
    }
}
