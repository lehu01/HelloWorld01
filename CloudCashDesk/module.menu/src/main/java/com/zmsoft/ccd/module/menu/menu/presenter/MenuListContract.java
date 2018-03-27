package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.menu.bean.Menu;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/15.
 */

public interface MenuListContract {

    interface View extends BaseView<Presenter> {

        void loadMenuListSuccess(List<Menu> menuList);

        void loadMenuListFailed(String errorCode, String errorMsg);

        void queryCartSuccess(DinningTableVo dinningTableVo);

        void queryCartFailed(String errorCode, String errorMsg);

        void addMenuToCartSuccess(DinningTableVo dinningTableVo);

        void addMenuToCartFailed(String errorCode, String errorMsg);

        void removeMenuFromCartSuccess(DinningTableVo data);

        void removeMenuFromCartFailed(String errorCode, String errorMsg);

        void modifyInputCartSuccess(DinningTableVo data);

        void modifyInputCartFailed(String errorCode, String errorMsg);

        void getSeatStatusSuccess(int status);

        void getSeatStatusFailed(String errorCode, String errorMsg);
    }

    interface Presenter extends BasePresenter {

        void loadMenuList(String entity, String seatCode, String orderId, boolean needCheckLocal);

        void loadMenuListSuccess(List<Menu> menuList);

        void loadMenuListFailed(String errorCode, String errorMsg);

        void queryCart(String seatCode, String orderId);

        void addMenuToCart(String seatCode, String orderId, List<CartItem> cartItemList);

        void removeMenuFromCart(String seatCode, String orderId, List<CartItem> cartItemList);

        void modifyInputCart(String seatCode, String orderId, List<CartItem> cartItemList);

        void getSeatStatus(String entityId, String seatCode);
    }
}
