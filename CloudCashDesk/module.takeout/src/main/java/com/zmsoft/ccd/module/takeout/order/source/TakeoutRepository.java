package com.zmsoft.ccd.module.takeout.order.source;

import com.zmsoft.ccd.data.source.Remote;
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

import javax.inject.Inject;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */
public class TakeoutRepository implements ITakeoutSource {

    private final ITakeoutSource remoteSource;

    @Inject
    TakeoutRepository(@Remote ITakeoutSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public Observable<FilterConditionResponse> getOrderStatusList(OrderStatusRequest requestOrderStatus) {
        return remoteSource.getOrderStatusList(requestOrderStatus);
    }

    @Override
    public Observable<FilterConditionResponse> getOrderConditions(OrderStatusRequest requestOrderStatus) {
        return remoteSource.getOrderConditions(requestOrderStatus);
    }

    @Override
    public Observable<OrderListResponse> getOrderList(OrderListRequest orderListRequest) {
        return remoteSource.getOrderList(orderListRequest);
    }

    @Override
    public Observable<OrderListResponse> searchOrder(SearchOrderRequest request) {
        return remoteSource.searchOrder(request);
    }

    @Override
    public Observable<OperateOrderResponse> changeOrderStatus(OperateOrderRequest request) {
        return remoteSource.changeOrderStatus(request);
    }

    @Override
    public Observable<CancelTakeoutOrderResponse> cancelTakeoutOrder(CancelTakeoutOrderRequest request) {
        return remoteSource.cancelTakeoutOrder(request);
    }

    @Override
    public Observable<DeliveryInfoResponse> getDeliveryInfo(DeliveryInfoRequest request) {
        return remoteSource.getDeliveryInfo(request);
    }

    @Override
    public Observable<GetDeliveryOrderListResponse> getDeliveryOrderList(GetDeliveryOrderListRequest request) {
        return remoteSource.getDeliveryOrderList(request);
    }

    @Override
    public Observable<GetDeliveryTypeResponse> getCourierList(GetDeliveryTypeRequest request) {
        return remoteSource.getCourierList(request);
    }

    @Override
    public Observable<DeliverTakeoutResponse> deliverTakeout(DeliverTakeoutRequest request) {
        return remoteSource.deliverTakeout(request);
    }
}
