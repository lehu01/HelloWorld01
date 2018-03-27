package com.zmsoft.ccd.module.menu.cart.source;

import android.text.TextUtils;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.constant.HttpMethodConstantsMenu;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.module.menu.cart.model.CartClearVo;
import com.zmsoft.ccd.module.menu.cart.model.CartItem;
import com.zmsoft.ccd.module.menu.cart.model.CartItemModifyRequest;
import com.zmsoft.ccd.module.menu.cart.model.CartQueryRequest;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.JoinCartVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.ErrorBizHttpCode;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DangGui
 * @create 2017/4/17.
 */

public class CartRemoteSource implements ICartSource {
    /**
     * 查询购物车最多重试次数
     */
    private static final int MAX_RETRY_COUNT = 3;
    /**
     * 查询购物车次数
     */
    private int mRetryCount = 0;

    @Override
    public void queryCart(final String seatCode, final String orderId, final boolean needJoinCartOnError, final Callback<DinningTableVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        CartQueryRequest cartQueryRequest = new CartQueryRequest(seatCode, orderId, UserHelper.getEntityId()
                , UserHelper.getMemberId());
        String requestJson = JsonMapper.toJsonString(cartQueryRequest);
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_CART_QUERY_REQUEST, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.Cart.METHOD_QUERY_CART);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<DinningTableVo>() {
            @Override
            protected void onData(DinningTableVo dinningTableVo) {
                callback.onSuccess(dinningTableVo);
                //重置次数
                mRetryCount = 0;
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                //如果是购物车已过期，则主动调用joinTable接口激活购物车
                if (needJoinCartOnError && !TextUtils.isEmpty(errorCode) && errorCode.equals(ErrorBizHttpCode.ERR_CART106)) {
                    if (mRetryCount > MAX_RETRY_COUNT) {
                        callback.onFailed(new ErrorBody(errorCode, errorMsg));
                        //重置次数
                        mRetryCount = 0;
                    } else {
                        mRetryCount++;
                        joinCart(seatCode, orderId, new Callback<JoinCartVo>() {
                            @Override
                            public void onSuccess(JoinCartVo data) {
                                queryCart(seatCode, orderId, needJoinCartOnError, callback);
                            }

                            @Override
                            public void onFailed(ErrorBody body) {
                                callback.onFailed(new ErrorBody(errorCode, errorMsg));
                                //重置次数
                                mRetryCount = 0;
                            }
                        });
                    }
                } else {
                    callback.onFailed(new ErrorBody(errorCode, errorMsg));
                    //重置次数
                    mRetryCount = 0;
                }
            }
        });
    }

    @Override
    public void joinCart(String seatCode, String orderId, final Callback<JoinCartVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        CartQueryRequest cartQueryRequest = new CartQueryRequest(seatCode, orderId, UserHelper.getEntityId()
                , UserHelper.getMemberId());
        String requestJson = JsonMapper.toJsonString(cartQueryRequest);
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_CART_JOIN_REQUEST, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.Cart.METHOD_JOIN_CART);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<JoinCartVo>() {
            @Override
            protected void onData(JoinCartVo joinCartVo) {
                callback.onSuccess(joinCartVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void clearCart(String seatCode, String orderId, final Callback<CartClearVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        CartQueryRequest cartQueryRequest = new CartQueryRequest(seatCode, orderId, UserHelper.getEntityId()
                , UserHelper.getMemberId());
        String requestJson = JsonMapper.toJsonString(cartQueryRequest);
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_CART_CLEAR_REQUEST, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.Cart.METHOD_CLEAR_CART);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CartClearVo>() {
            @Override
            protected void onData(CartClearVo cartClearVo) {
                callback.onSuccess(cartClearVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void modifyCart(String seatCode, String orderId, String source, List<CartItem> cartItemList, final Callback<DinningTableVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        CartItemModifyRequest cartItemModifyRequest = new CartItemModifyRequest(seatCode, orderId, UserHelper.getEntityId()
                , UserHelper.getMemberId(), cartItemList, source);
        String requestJson = JsonMapper.toJsonString(cartItemModifyRequest);
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_CART_ITEM_MODIFY_REQUEST, requestJson);
        //由于购物车修改时，不想有loading弹框，所以把超时时间特殊处理，缩短为4s
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap
                , HttpMethodConstantsMenu.Cart.METHOD_MODIFY_CART, 1500);
//        requestModel = requestModel.newBuilder().connectTimeout(4000).readTimeout(4000).build();
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<DinningTableVo>() {
            @Override
            protected void onData(DinningTableVo dinningTableVo) {
                callback.onSuccess(dinningTableVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void submitOrder(SubmitOrderRequest request, final Callback<SubmitOrderVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_PARAMS, JsonMapper.toJsonString(request));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.Cart.METHOD_SUBMIT_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SubmitOrderVo>() {
            @Override
            protected void onData(SubmitOrderVo submitOrderVo) {
                callback.onSuccess(submitOrderVo);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void hangUpOrder(String seatCode, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_RETAIN_ENTIY_ID, UserHelper.getEntityId());
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_RETAIN_USER_ID, UserHelper.getUserId());
        paramMap.put(HttpMethodConstantsMenu.Cart.Paras.PARAS_RETAIN_SEAT_CODE, seatCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstantsMenu.Cart.METHOD_HANG_UP_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean result) {
                callback.onSuccess(result);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
