package com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.RetailCartDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailCartDetailPresenterModule {

    private RetailCartDetailContract.View mView;

    public RetailCartDetailPresenterModule(RetailCartDetailContract.View view) {
        mView = view;
    }

    @Provides
    RetailCartDetailContract.View provideCartContractView() {
        return mView;
    }

}
