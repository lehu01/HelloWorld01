package com.zmsoft.ccd.module.menu.cart.presenter.cartlist;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/17.
 */

public interface CartContract {
    interface View extends BaseView<Presenter> {
        void showCart(DinningTableVo dinningTableVo);

        void queryCartError(ErrorBody body);

        /**
         * 确认下单有没有成功
         *
         * @param submitOrderVo
         */
        void confimOrder(SubmitOrderVo submitOrderVo);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 购物车下单时提示xx菜已售完
         *
         * @param errorMessage
         */
        void alertSoldOut(String errorMessage);


        /**
         * 挂单成功
         */
        void successHangUpOrder();
    }

    interface Presenter extends BasePresenter {
        /**
         * 查询购物车
         *
         * @param seatCode
         * @param orderId
         */
        void queryCart(String seatCode, String orderId, boolean needJoinCartOnError);

        /**
         * 清理购物车
         *
         * @param seatCode
         * @param orderId
         */
        void clearCart(String seatCode, String orderId);

        /**
         * 修改购物车
         */
        void modifyCart(String seatCode, String orderId, ItemVo itemVo);

        /**
         * 购物车确认下单
         */
        void submitOrder(SubmitOrderRequest request, List<CartRecyclerItem> cartRecyclerItemList);

        /**
         * 购物车内修改自定义菜
         *
         * @param seatCode
         * @param peopleCount
         */
        void saveCustomMenuToCart(String seatCode, ItemVo itemVo, int peopleCount);

        /**
         * 挂单
         *
         * @param seatCode
         */
        void hangUpOrder(String seatCode);
    }
}
