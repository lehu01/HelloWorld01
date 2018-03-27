package com.zmsoft.ccd.module.menu.cart.presenter.cartdetail.dagger;

import com.zmsoft.ccd.app.PresentScoped;
import com.zmsoft.ccd.module.menu.cart.source.dagger.CartSourceComponent;
import com.zmsoft.ccd.module.menu.cart.view.RetailCartDetailActivity;

import dagger.Component;

@PresentScoped
@Component(dependencies = CartSourceComponent.class, modules = RetailCartDetailPresenterModule.class)
public interface RetailCartDetailComponent {

    void inject(RetailCartDetailActivity activity);

}
