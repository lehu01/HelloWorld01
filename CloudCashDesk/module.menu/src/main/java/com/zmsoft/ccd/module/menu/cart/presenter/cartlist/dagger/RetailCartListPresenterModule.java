package com.zmsoft.ccd.module.menu.cart.presenter.cartlist.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.RetailCartContract;

import dagger.Module;
import dagger.Provides;

@Module
public class RetailCartListPresenterModule {

    private final RetailCartContract.View mView;

    public RetailCartListPresenterModule(RetailCartContract.View view) {
        mView = view;
    }

    @Provides
    RetailCartContract.View provideCartContractView() {
        return mView;
    }

}
