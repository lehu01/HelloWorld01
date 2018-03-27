package com.zmsoft.ccd.module.menu.cart.presenter.cartlist.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.cart.source.dagger.CartSourceComponent;
import com.zmsoft.ccd.module.menu.cart.view.RetailCartActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = CartSourceComponent.class, modules = RetailCartListPresenterModule.class)
public interface RetailCartListComponent {

    void inject(RetailCartActivity activity);

}
