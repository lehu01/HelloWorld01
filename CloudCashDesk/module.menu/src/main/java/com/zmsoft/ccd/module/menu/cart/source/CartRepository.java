package com.zmsoft.ccd.module.menu.cart.source;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.module.menu.cart.model.CartClearVo;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.JoinCartVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;

import java.util.List;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2017/4/17.
 */
@ModelScoped
public class CartRepository implements ICartSource {

    private final ICartSource mCartRemoteSource;

    @Inject
    CartRepository(@Remote ICartSource remoteSource) {
        this.mCartRemoteSource = remoteSource;
    }

    @Override
    public void queryCart(String seatCode, String orderId, boolean needJoinCartOnError, Callback<DinningTableVo> callback) {
        mCartRemoteSource.queryCart(seatCode, orderId, needJoinCartOnError, callback);
    }

    @Override
    public void joinCart(String seatCode, String orderId, Callback<JoinCartVo> callback) {
        mCartRemoteSource.joinCart(seatCode, orderId, callback);
    }

    @Override
    public void clearCart(String seatCode, String orderId, Callback<CartClearVo> callback) {
        mCartRemoteSource.clearCart(seatCode, orderId, callback);
    }

    @Override
    public void modifyCart(String seatCode, String orderId, String source, List<CartItem> cartItemList, Callback<DinningTableVo> callback) {
        mCartRemoteSource.modifyCart(seatCode, orderId, source, cartItemList, callback);
    }

    @Override
    public void submitOrder(SubmitOrderRequest request, Callback<SubmitOrderVo> callback) {
        mCartRemoteSource.submitOrder(request, callback);
    }

    @Override
    public void hangUpOrder(String seatCode, Callback<Boolean> callback) {
        mCartRemoteSource.hangUpOrder(seatCode, callback);
    }
}
