package com.zmsoft.ccd.module.takeout.order.source;

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

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public interface ITakeoutSource {


    /**
     * 获取外卖订单状态列表
     */
    Observable<FilterConditionResponse> getOrderStatusList(OrderStatusRequest requestOrderStatus);

    /**
     * 获取订单过滤条件列表
     */
    Observable<FilterConditionResponse> getOrderConditions(OrderStatusRequest requestOrderStatus);


    /**
     * 订单列表
     */
    Observable<OrderListResponse> getOrderList(OrderListRequest requestOrderStatus);


    /**
     * 订单搜索
     */
    Observable<OrderListResponse> searchOrder(SearchOrderRequest request);

    /**
     * 操作订单状态
     */
    Observable<OperateOrderResponse> changeOrderStatus(OperateOrderRequest request);

    /**
     * 取消外卖单（撤单）
     */
    Observable<CancelTakeoutOrderResponse> cancelTakeoutOrder(CancelTakeoutOrderRequest request);

    /**
     * 获取配送信息
     */
    Observable<DeliveryInfoResponse> getDeliveryInfo(DeliveryInfoRequest request);

    /**
     * 获取配送订单列表
     */
    Observable<GetDeliveryOrderListResponse> getDeliveryOrderList(GetDeliveryOrderListRequest request);

    /**
     * 获取配送员列表
     */
    Observable<GetDeliveryTypeResponse> getCourierList(GetDeliveryTypeRequest request);

    /**
     * 配送外卖单
     */
    Observable<DeliverTakeoutResponse> deliverTakeout(DeliverTakeoutRequest request);

}
