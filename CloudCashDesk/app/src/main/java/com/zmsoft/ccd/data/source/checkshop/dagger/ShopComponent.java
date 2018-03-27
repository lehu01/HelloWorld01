package com.zmsoft.ccd.data.source.checkshop.dagger;

import android.content.Context;

import com.zmsoft.ccd.app.AppComponent;
import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.source.checkshop.CheckShopRepository;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 18:14
 */
@ModelScoped
@Component(dependencies = AppComponent.class)
public interface ShopComponent {

    Context getContext();

    CheckShopRepository getCheckShopRepository();
}
