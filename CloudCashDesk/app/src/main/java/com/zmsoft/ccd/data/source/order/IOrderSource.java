package com.zmsoft.ccd.data.source.order;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.FocusOrderRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListResponse;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.Order;
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

import java.util.List;

import rx.Observable;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:23
 */
public interface IOrderSource {

    /**
     * 获取挂单列表
     *
     * @param entityId 实体id
     */
    Observable<List<HangUpOrder>> getHangUpOrderList(String entityId);

    /**
     * 获取订单列表
     *
     * @param entityId    实体id
     * @param orderType   订单类型
     * @param orderStatus 订单状态
     * @param checkout    结账状态
     * @param pageIndex   页码
     * @param pageSize    数量
     */
    void getOrderList(String entityId, String orderType, String orderStatus, boolean checkout, boolean isCheckout, int pageIndex, int pageSize, String code, Callback<OrderListResult> callback);


    /** 获取订单列表
     * 传入对象，区分kind餐饮1和零售7
     * @param focusOrderRequest
     */
    Observable<OrderListResult> getFocusOrderList(FocusOrderRequest focusOrderRequest);


    /**获取零售单订单来源
     * @param retailOrderFromRequest
     */
    Observable<RetailOrderFromResponse> getRetailOrderFrom(RetailOrderFromRequest retailOrderFromRequest);


    /**获取云收银已结账单明细列表
     * @param request
     */
    Observable<GetBillDetailListResponse> getBillDetailList(GetBillDetailListRequest request);

    /**
     * 根据订单号搜索订单
     *
     * @param entityId  实体id
     * @param orderCode 订单号
     */
    void getOrderByCode(String entityId, String orderCode, Callback<Order> callback);

    /**
     * 获取订单详情(根据订单号)
     *
     * @param entityId   实体id
     * @param orderId    订单id
     * @param customerId 用户id
     */
    void getOrderDetail(String entityId, String orderId, String customerId, Callback<OrderDetail> callback);

    /**
     * 获取订单详情（根据座位编码）
     *
     * @param entityId   实体id
     * @param seatCode   座位code
     * @param customerId 用户id
     */
    void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId, Callback<OrderDetail> callback);

    /**
     * 反结账
     *
     * @param entityId   实体id
     * @param operatorId 用户id
     * @param orderId    订单id
     * @param reason     反结账原因
     */
    void reverseCheckOut(String entityId, String operatorId, String orderId, String reason, long modifyTime, Callback<ReverseCheckout> callback);

    /**
     * 结账完毕
     *
     * @param entityId   实体id
     * @param operatorId 用户id
     * @param orderId    订单id
     */
    void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback);

    /**
     * 获取备注列表
     *
     * @param callback
     */
    void getRemarkList(String entityId, Callback<List<RemarkMenu>> callback);

    /**
     * 获取服务费列表
     *
     * @param entityId 实体id
     * @param areaId   区域id
     * @param callback 回调
     */
    void getFeelPlanListByAreaId(String entityId, String areaId, Callback<List<FeePlan>> callback);

    /**
     * 更新服务费方案
     *
     * @param entityId   实体id
     * @param orderId    订单id
     * @param feePlanId  方案id
     * @param opUserId   操作员id
     * @param opType     操作类型
     * @param modifyTime 操作时间
     */
    void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId, int opType, long modifyTime, Callback<UpdateFeePlan> callback);

    /**
     * 催单
     *
     * @param entityId           实体id
     * @param orderId            订单id
     * @param userId             用户id
     * @param customerRegisterId 会员id
     */
    void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode, Callback<PushOrder> callback);

    /**
     * 撤单
     *
     * @param entityId   实体id
     * @param orderId    订单id
     * @param opUserId   操作员id，用户id
     * @param modifyTime 修改时间
     * @param reason     撤单原因
     */
    void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason, Callback<CancelOrder> callback);


    /**
     * 改单[订单中心改单]
     *
     * @param entityId    实体id
     * @param memo        备注
     * @param peopleCount 就餐人数
     * @param newSeatCode 新的的座位code
     * @param opUserId    操作员id
     * @param orderId     订单id
     * @param isWait      是否待上菜
     * @param opType      操作类型
     * @param modifyTime  修改时间
     */
    void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode, String opUserId, String orderId,
                            int isWait, int opType, long modifyTime, Callback<ChangeOrderByTrade> callback);

    /**
     * 改单[小二改单]
     *
     * @param entityId    实体id
     * @param userId      用户id/操作
     * @param oldSeatCode 老的seatCode
     * @param newSeatCode 新的seatCode
     * @param peopleCount 就餐人数
     * @param memo        备注
     * @param isWait      是否带菜
     */
    void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo, boolean isWait, Callback<Boolean> callback);


    /**
     * 开单
     *
     * @param entityId    实体id
     * @param userId      用户id
     * @param seatCode    座位code
     * @param peopleCount 就餐人数
     * @param memo        备注
     * @param isWait      是否待菜
     */
    void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, Callback<OpenOrderVo> callback);


    /**
     * 开单（零售）
     *
     * @param entityId    实体id
     * @param userId      用户id
     * @param seatCode    座位code
     * @param peopleCount 就餐人数
     * @param memo        备注
     * @param isWait      是否待菜
     */
    void createOrderForRetail(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, Callback<OpenOrderVo> callback);

}
