package com.zmsoft.ccd.module.menu.cart.source;


import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.module.menu.cart.model.CartClearVo;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.JoinCartVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/17.
 */

public interface ICartSource {
    /**
     * 查询购物车
     *
     * @param seatCode            桌号
     * @param orderId             单号
     * @param needJoinCartOnError 发现购物车已过期时，是否需要调用加入购物车接口，强制开启一个新的购物车
     * @param callback
     */
    void queryCart(String seatCode, String orderId, boolean needJoinCartOnError, final Callback<DinningTableVo> callback);

    /**
     * 加入购物车
     *
     * @param seatCode 桌号
     * @param orderId  单号
     * @param callback
     */
    void joinCart(String seatCode, String orderId, final Callback<JoinCartVo> callback);

    /**
     * 清理购物车
     *
     * @param seatCode 桌号
     * @param orderId  单号
     * @param callback
     */
    void clearCart(String seatCode, String orderId, final Callback<CartClearVo> callback);

    /**
     * 修改购物车
     *
     * @param seatCode 桌号
     * @param orderId  单号
     * @param callback
     */
    void modifyCart(String seatCode, String orderId, String source, List<CartItem> cartItemList
            , final Callback<DinningTableVo> callback);

    /**
     * 购物车 确认下单
     * @param callback
     */
    void submitOrder(SubmitOrderRequest request, final Callback<SubmitOrderVo> callback);

    /**
     * 挂单
     *
     * @param seatCode 桌号
     * @param callback
     */
    void hangUpOrder(String seatCode, final Callback<Boolean> callback);

}
