package com.zmsoft.ccd.module.menu.cart.presenter.cartlist.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.CartContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CartListPresenterModule {

    private final CartContract.View mView;

    public CartListPresenterModule(CartContract.View view) {
        mView = view;
    }

    @Provides
    CartContract.View provideCartContractView() {
        return mView;
    }

}
