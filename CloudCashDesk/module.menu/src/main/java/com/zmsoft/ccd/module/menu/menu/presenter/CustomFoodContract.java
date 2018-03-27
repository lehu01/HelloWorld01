package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.menu.bean.MenuCategory;
import com.zmsoft.ccd.module.menu.menu.bean.MenuUnit;
import com.zmsoft.ccd.module.menu.menu.bean.PassThrough;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：自定义商品
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public interface CustomFoodContract {

    interface View extends BaseView<BasePresenter> {
        void loadMenuCategoriesSuccess(ArrayList<MenuCategory> list);

        void loadMenuCategoriesFailed(String errorCode, String errorMsg);

        void loadMenuUnitsSuccess(List<MenuUnit> list);

        void loadMenuUnitsFailed(String errorCode, String errorMsg);

        void loadPassThroughSuccess(List<PassThrough> list);

        void loadPassThroughFailed(String errorCode, String errorMsg);

        void saveCustomMenuToCartSuccess(DinningTableVo dinningTableVo);

        void saveCustomMenuToCartFailed(String errorCode, String errorMsg);
    }

    interface Presenter extends BasePresenter {

        void loadMenuCategories(String entityId, int[] types);

        void loadMenuUnits(String entityId);

        void loadPassThrough(String entityId);

        void saveCustomMenuToCart(String entityId, String userId,
                                  String ownerCustomerId, String seatCode,
                                  int peopleCount, String itemListJson);

    }



}
