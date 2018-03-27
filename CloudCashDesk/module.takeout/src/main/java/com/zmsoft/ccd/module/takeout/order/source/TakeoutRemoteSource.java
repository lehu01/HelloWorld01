package com.zmsoft.ccd.module.takeout.order.source;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderRequest;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderResponse;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutRequest;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutResponse;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoRequest;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoResponse;
import com.zmsoft.ccd.takeout.bean.FilterConditionResponse;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListRequest;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListResponse;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeRequest;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeResponse;
import com.zmsoft.ccd.takeout.bean.OperateOrderRequest;
import com.zmsoft.ccd.takeout.bean.OperateOrderResponse;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderListResponse;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;
import com.zmsoft.ccd.takeout.bean.SearchOrderRequest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class TakeoutRemoteSource implements ITakeoutSource {
    @Override
    public Observable<FilterConditionResponse> getOrderStatusList(final OrderStatusRequest requestOrderStatus) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<FilterConditionResponse>>>() {
            @Override
            public HttpResult<HttpBean<FilterConditionResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.OrderStatusList.PARAM, JsonMapper.toJsonString(requestOrderStatus));
                Type type = new TypeToken<HttpResult<HttpBean<FilterConditionResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.OrderStatusList.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<FilterConditionResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<FilterConditionResponse> getOrderConditions(final OrderStatusRequest requestOrderStatus) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<FilterConditionResponse>>>() {
            @Override
            public HttpResult<HttpBean<FilterConditionResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.OrderConditions.PARAM, JsonMapper.toJsonString(requestOrderStatus));
                Type type = new TypeToken<HttpResult<HttpBean<FilterConditionResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.OrderConditions.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<FilterConditionResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<OrderListResponse> getOrderList(final OrderListRequest requestOrderList) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<OrderListResponse>>>() {
            @Override
            public HttpResult<HttpBean<OrderListResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.OrderList.PARAM, JsonMapper.toJsonString(requestOrderList));
                Type type = new TypeToken<HttpResult<HttpBean<OrderListResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.OrderList.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<OrderListResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<OrderListResponse> searchOrder(final SearchOrderRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<OrderListResponse>>>() {
            @Override
            public HttpResult<HttpBean<OrderListResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.SearchOrder.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<OrderListResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.SearchOrder.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<OrderListResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<OperateOrderResponse> changeOrderStatus(final OperateOrderRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<OperateOrderResponse>>>() {
            @Override
            public HttpResult<HttpBean<OperateOrderResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.OperationOrderStatus.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<OperateOrderResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.OperationOrderStatus.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<OperateOrderResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CancelTakeoutOrderResponse> cancelTakeoutOrder(final CancelTakeoutOrderRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CancelTakeoutOrderResponse>>>() {
            @Override
            public HttpResult<HttpBean<CancelTakeoutOrderResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.CancelTakeoutOrder.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<CancelTakeoutOrderResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.CancelTakeoutOrder.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<CancelTakeoutOrderResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<DeliveryInfoResponse> getDeliveryInfo(final DeliveryInfoRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<DeliveryInfoResponse>>>() {
            @Override
            public HttpResult<HttpBean<DeliveryInfoResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.DeliveryInfo.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<DeliveryInfoResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.DeliveryInfo.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<DeliveryInfoResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<GetDeliveryOrderListResponse> getDeliveryOrderList(final GetDeliveryOrderListRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<GetDeliveryOrderListResponse>>>() {
            @Override
            public HttpResult<HttpBean<GetDeliveryOrderListResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.DeliveryOrderList.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<GetDeliveryOrderListResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.DeliveryOrderList.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<GetDeliveryOrderListResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<GetDeliveryTypeResponse> getCourierList(final GetDeliveryTypeRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<GetDeliveryTypeResponse>>>() {
            @Override
            public HttpResult<HttpBean<GetDeliveryTypeResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.DeliveryOrderList.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<GetDeliveryTypeResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.CourierList.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<GetDeliveryTypeResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<DeliverTakeoutResponse> deliverTakeout(final DeliverTakeoutRequest request) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<DeliverTakeoutResponse>>>() {
            @Override
            public HttpResult<HttpBean<DeliverTakeoutResponse>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpParams.DeliveryOrderList.PARAM, JsonMapper.toJsonString(request));
                Type type = new TypeToken<HttpResult<HttpBean<DeliverTakeoutResponse>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpParams.DeliveryTakeout.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<DeliverTakeoutResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }
}
