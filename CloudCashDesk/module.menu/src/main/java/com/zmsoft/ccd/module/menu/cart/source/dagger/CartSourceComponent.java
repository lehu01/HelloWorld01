package com.zmsoft.ccd.module.menu.cart.source.dagger;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.module.menu.cart.source.CartRepository;

import dagger.Component;

/**
 * @author DangGui
 * @create 2017/4/17.
 */
@ModelScoped
@Component(modules = {CartSourceModule.class})
public interface CartSourceComponent {

    CartRepository getCartRepository();
}