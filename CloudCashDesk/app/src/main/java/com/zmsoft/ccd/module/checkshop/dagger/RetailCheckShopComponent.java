package com.zmsoft.ccd.module.checkshop.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.data.source.checkshop.dagger.ShopComponent;
import com.zmsoft.ccd.module.checkshop.RetailCheckShopActivity;

import dagger.Component;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:41
 */
@PresentScoped
@Component(dependencies = ShopComponent.class, modules = RetailCheckShopPresenterModule.class)
public interface RetailCheckShopComponent {

    void inject(RetailCheckShopActivity checkShopActivity);
}
