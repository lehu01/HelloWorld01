package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;

import java.util.ArrayList;

/**
 * Description：自定义商品
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public interface RetailCustomFoodContract {
    interface View extends BaseView<BasePresenter> {
        void loadMenuCategoriesSuccess(ArrayList<MenuCategory> list);

        void loadMenuCategoriesFailed(String errorCode, String errorMsg);

        void saveCustomMenuToCartSuccess(DinningTableVo dinningTableVo);

        void saveCustomMenuToCartFailed(String errorCode, String errorMsg);
    }

    interface Presenter extends BasePresenter {

        void loadMenuCategories(String entityId, int[] types);


        void saveCustomMenuToCart(String entityId, String userId,
                                  String ownerCustomerId, String seatCode,
                                  int peopleCount, String itemListJson);

    }
}
