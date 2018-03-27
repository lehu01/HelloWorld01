package com.zmsoft.ccd.module.main.order.remark.dagger;

import com.zmsoft.ccd.module.main.order.remark.fragment.RetailRemarkContract;

import dagger.Module;
import dagger.Provides;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:37
 */
@Module
public class RetailRemarkPresenterModule {

    public final RetailRemarkContract.View mView;

    public RetailRemarkPresenterModule(RetailRemarkContract.View view) {
        mView = view;
    }

    @Provides
    RetailRemarkContract.View provideRetailRemarkPresenterModuleView() {
        return mView;
    }

}
