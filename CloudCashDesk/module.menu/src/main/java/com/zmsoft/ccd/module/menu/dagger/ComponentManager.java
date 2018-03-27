package com.zmsoft.ccd.module.menu.dagger;

import com.zmsoft.ccd.module.menu.cart.source.dagger.CartSourceComponent;
import com.zmsoft.ccd.module.menu.cart.source.dagger.DaggerCartSourceComponent;
import com.zmsoft.ccd.module.menu.menu.source.dagger.DaggerMenuSourceComponent;
import com.zmsoft.ccd.module.menu.menu.source.dagger.MenuSourceComponent;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class ComponentManager {

    private static ComponentManager instance;

    private MenuSourceComponent mMenuSourceComponent;
    private CartSourceComponent mCartSourceComponent;

    private ComponentManager() {
    }

    public static synchronized ComponentManager get() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }


    public MenuSourceComponent getMenuSourceComponent() {
        if (mMenuSourceComponent == null) {
            mMenuSourceComponent = DaggerMenuSourceComponent.builder().build();
        }
        return mMenuSourceComponent;
    }

    public CartSourceComponent getCartSourceComponent() {
        if (mCartSourceComponent == null) {
            mCartSourceComponent = DaggerCartSourceComponent.builder().build();
        }
        return mCartSourceComponent;
    }
}
