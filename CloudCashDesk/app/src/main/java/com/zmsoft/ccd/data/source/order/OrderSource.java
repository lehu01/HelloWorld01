package com.zmsoft.ccd.data.source.order;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.TypeToken;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.carryout.TakeoutHttpConstant;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.FocusOrderRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListResponse;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromRequest;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromResponse;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;
import com.zmsoft.ccd.lib.bean.order.feeplan.UpdateFeePlan;
import com.zmsoft.ccd.lib.bean.order.hangup.HangUpOrder;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.op.ChangeOrderByTrade;
import com.zmsoft.ccd.lib.bean.order.op.PushOrder;
import com.zmsoft.ccd.lib.bean.order.remark.RemarkMenu;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.ReverseCheckout;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import rx.Observable;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:24
 */
@Singleton
public class OrderSource implements IOrderSource {

    @Override
    public Observable<List<HangUpOrder>> getHangUpOrderList(final String entityId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<HangUpOrder>>>>() {
            @Override
            public HttpResult<HttpBean<List<HangUpOrder>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpConstant.TakeoutPhoneList.ENTITY_ID, entityId);
                Type listType = new TypeToken<HttpResult<HttpBean<List<HangUpOrder>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_HANG_UP_ORDER_LIST)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<HangUpOrder>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void getOrderList(String entityId, String orderType, String orderStatus, boolean checkout, boolean isCheckout, int pageIndex, int pageSize, String code, final Callback<OrderListResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        // 订单类型
        if (!StringUtils.isEmpty(orderType)) {
            paramMap.put(HttpParasKeyConstant.Hump.ORDER_CATEGORY, orderType);
        }
        // 订单状态
        if (!StringUtils.isEmpty(orderStatus)) {
            paramMap.put(HttpParasKeyConstant.Hump.BILL_STATUS, orderStatus);
        }
        // 【结账，未结账】或者【类型】
        if (isCheckout) {
            paramMap.put(HttpParasKeyConstant.Hump.CHECKOUT, checkout);
        }
        if (!StringUtils.isEmpty(code)) {
            paramMap.put("code", code);
            paramMap.put(HttpParasKeyConstant.Hump.PAGE_INDEX, 1);
        } else {
            paramMap.put(HttpParasKeyConstant.Hump.PAGE_INDEX, pageIndex);
        }
        paramMap.put("opUserId", UserHelper.getUserId());
        paramMap.put(HttpParasKeyConstant.Hump.PAGE_SIZE, pageSize);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_ORDER_LIST_QUERY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OrderListResult>() {

            @Override
            protected void onData(OrderListResult data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<OrderListResult> getFocusOrderList(final FocusOrderRequest focusOrderRequest) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<OrderListResult>>>() {
            @Override
            public HttpResult<HttpBean<OrderListResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(HttpParasKeyConstant.PARA_PARAM, JsonMapper.toJsonString(focusOrderRequest));
                Type type = new TypeToken<HttpResult<HttpBean<OrderListResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_LIST_QUERY)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<OrderListResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<RetailOrderFromResponse> getRetailOrderFrom(final RetailOrderFromRequest retailOrderFromRequest) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<RetailOrderFromResponse>>>() {
            @Override
            public HttpResult<HttpBean<RetailOrderFromResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(HttpParasKeyConstant.PARA_PARAM, JsonMapper.toJsonString(retailOrderFromRequest));
                Type type = new TypeToken<HttpResult<HttpBean<RetailOrderFromResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_GET_RETAIL_ORDER_FROM)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<RetailOrderFromResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<GetBillDetailListResponse> getBillDetailList(final GetBillDetailListRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<GetBillDetailListResponse>>>() {
            @Override
            public HttpResult<HttpBean<GetBillDetailListResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(HttpParasKeyConstant.PARA_PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<GetBillDetailListResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_GET_BILL_DETAIL_LIST)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<GetBillDetailListResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void getOrderByCode(String entityId, String orderCode, final Callback<com.zmsoft.ccd.lib.bean.order.Order> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_CODE, orderCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_LIST_BY_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<com.zmsoft.ccd.lib.bean.order.Order>() {

            @Override
            protected void onData(com.zmsoft.ccd.lib.bean.order.Order order) {
                callback.onSuccess(order);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getOrderDetail(String entityId, String orderId, String customerId, final Callback<OrderDetail> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_CUSTOMER_ID, customerId);
        paramMap.put(HttpParasKeyConstant.PARA_ORDER_ID, orderId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OrderDetail>() {
            @Override
            protected void onData(OrderDetail orderDetail) {
                callback.onSuccess(orderDetail);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId, final Callback<OrderDetail> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_CUSTOMER_ID, customerId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_DETAIL_BY_SEAT_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OrderDetail>() {
            @Override
            protected void onData(OrderDetail orderDetail) {
                callback.onSuccess(orderDetail);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void reverseCheckOut(String entityId, String opUserId, String orderId, String reason, long modifyTime,
                                final Callback<ReverseCheckout> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.Hump.REASON, reason);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_ORDER_REVERSE_CHECK_OUT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ReverseCheckout>() {
            @Override
            protected void onData(ReverseCheckout reverseCheckout) {
                callback.onSuccess(reverseCheckout);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime, final Callback<AfterEndPay> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.OPERATOR_ID, operatorId);
        paramMap.put(HttpParasKeyConstant.Hump.ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        Map<String, Object> temp = new HashMap<>();
        temp.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(temp, HttpMethodConstants.Order.METHOD_ORDER_AFTER_END_PAY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<AfterEndPay>() {
            @Override
            protected void onData(AfterEndPay afterEndPay) {
                callback.onSuccess(afterEndPay);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getRemarkList(String entityId, final Callback<List<RemarkMenu>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_GET_REMARK_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<RemarkMenu>>() {
            @Override
            protected void onData(List<RemarkMenu> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getFeelPlanListByAreaId(String entityId, String areaId, final Callback<List<FeePlan>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.AREA_ID, areaId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_GET_FEE_PLAN_LIST_BY_AREA_ID);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<FeePlan>>() {
            @Override
            protected void onData(List<FeePlan> feePlan) {
                callback.onSuccess(feePlan);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId,
                              int opType, long modifyTime, final Callback<UpdateFeePlan> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.Hump.FEE_PLAN_ID, feePlanId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_TYPE, opType);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_ORDER_ORDER_FEE_PLAN);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<UpdateFeePlan>() {
            @Override
            protected void onData(UpdateFeePlan data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode, final Callback<PushOrder> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        if (!StringUtils.isEmpty(customerRegisterId)) {
            paramMap.put(HttpParasKeyConstant.PARA_CUSTOMER_REGISTER_ID, customerRegisterId);
        }
        if (!StringUtils.isEmpty(seatCode)) {
            paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        }
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_ORDER_PUSH_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<PushOrder>() {
            @Override
            protected void onData(PushOrder data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason, final Callback<CancelOrder> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        paramMap.put(HttpParasKeyConstant.Hump.REASON, reason);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_ORDER_CANCEL_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CancelOrder>() {
            @Override
            protected void onData(CancelOrder data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode, String opUserId, String orderId,
                                   int isWait, int opType, long modifyTime, final Callback<ChangeOrderByTrade> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.MEMO, StringUtils.isEmpty(memo) ? "" : memo);
        if (peopleCount > 0) {
            paramMap.put(HttpParasKeyConstant.Hump.PEOPLE_COUNT, peopleCount);
        }
        if (!StringUtils.isEmpty(newSeatCode)) {
            paramMap.put(HttpParasKeyConstant.Hump.NEW_SEAT_CODE, newSeatCode);
        }
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.Hump.IS_WAIT, isWait);
        paramMap.put(HttpParasKeyConstant.Hump.OP_TYPE, opType);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);

        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Order.METHOD_CHANGE_ORDER_BY_TRADE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ChangeOrderByTrade>() {
            @Override
            protected void onData(ChangeOrderByTrade data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo,
                                     boolean isWait, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_OLD_SEAT_CODE, oldSeatCode);
        paramMap.put(HttpParasKeyConstant.PARA_NEW_SEAT_CODE, newSeatCode);
        paramMap.put(HttpParasKeyConstant.PARA_PEOPLE_COUNT, peopleCount);
        if (!StringUtils.isEmpty(memo)) {
            paramMap.put(HttpParasKeyConstant.PARA_MEMO, memo);
        }
        paramMap.put(HttpParasKeyConstant.PARA_IS_WAIT, isWait);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_CHANGE_ORDER_BY_SHOP);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, final Callback<OpenOrderVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        paramMap.put(HttpParasKeyConstant.PARA_PEOPLE_COUNT, peopleCount);
        if (!StringUtils.isEmpty(memo)) {
            paramMap.put(HttpParasKeyConstant.PARA_MEMO, memo);
        }
        paramMap.put(HttpParasKeyConstant.PARA_IS_WAIT, isWait);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_CREATE_ORDER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OpenOrderVo>() {
            @Override
            protected void onData(OpenOrderVo data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void createOrderForRetail(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, final Callback<OpenOrderVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        paramMap.put(HttpParasKeyConstant.PARA_PEOPLE_COUNT, peopleCount);
        if (!StringUtils.isEmpty(memo)) {
            paramMap.put(HttpParasKeyConstant.PARA_MEMO, memo);
        }
        paramMap.put(HttpParasKeyConstant.PARA_IS_WAIT, isWait);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Order.METHOD_CREATE_ORDER_FOR_RETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OpenOrderVo>() {
            @Override
            protected void onData(OpenOrderVo data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

}
