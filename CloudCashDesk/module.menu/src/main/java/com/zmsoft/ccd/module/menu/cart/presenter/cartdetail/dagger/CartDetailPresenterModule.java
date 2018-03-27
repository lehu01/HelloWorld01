package com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.dagger;

import com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.CartDetailContract;

import dagger.Module;
import dagger.Provides;

@Module
public class CartDetailPresenterModule {

    private final CartDetailContract.View mView;

    public CartDetailPresenterModule(CartDetailContract.View view) {
        mView = view;
    }

    @Provides
    CartDetailContract.View provideCartContractView() {
        return mView;
    }

}
