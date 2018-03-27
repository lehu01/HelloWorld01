package com.zmsoft.ccd.module.menu.cart.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.menu.cart.source.CartRemoteSource;
import com.zmsoft.ccd.module.menu.cart.source.ICartSource;

import dagger.Module;
import dagger.Provides;

@Module
public class CartSourceModule {

    @ModelScoped
    @Provides
    @Remote
    ICartSource provideRemoteDataSource() {
        return new CartRemoteSource();
    }
}
