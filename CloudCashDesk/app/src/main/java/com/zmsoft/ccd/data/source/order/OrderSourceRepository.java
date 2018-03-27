package com.zmsoft.ccd.data.source.order;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
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

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/16 20:25
 */
@Singleton
public class OrderSourceRepository implements IOrderSource {

    private final IOrderSource mIOrderSource;

    @Inject
    public OrderSourceRepository(@Remote IOrderSource orderSource) {
        mIOrderSource = orderSource;
    }

    @Override
    public Observable<List<HangUpOrder>> getHangUpOrderList(String entityId) {
        return mIOrderSource.getHangUpOrderList(entityId);
    }

    @Override
    public void getOrderList(String entityId, String orderType, String orderStatus, boolean checkout, boolean isCheckout, int pageIndex, int pageSize, String code, Callback<OrderListResult> callback) {
        mIOrderSource.getOrderList(entityId, orderType, orderStatus, checkout, isCheckout, pageIndex, pageSize, code, callback);
    }

    @Override
    public Observable<OrderListResult> getFocusOrderList(FocusOrderRequest focusOrderRequest) {
        return mIOrderSource.getFocusOrderList(focusOrderRequest);
    }

    @Override
    public Observable<RetailOrderFromResponse> getRetailOrderFrom(RetailOrderFromRequest retailOrderFromRequest) {
        return mIOrderSource.getRetailOrderFrom(retailOrderFromRequest);
    }

    @Override
    public Observable<GetBillDetailListResponse> getBillDetailList(GetBillDetailListRequest request) {
        return mIOrderSource.getBillDetailList(request);
    }

    @Override
    public void getOrderByCode(String entityId, String orderCode, Callback<Order> callback) {
        mIOrderSource.getOrderByCode(entityId, orderCode, callback);
    }

    @Override
    public void getOrderDetail(String entityId, String orderId, String customerId, Callback<OrderDetail> callback) {
        mIOrderSource.getOrderDetail(entityId, orderId, customerId, callback);
    }

    @Override
    public void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId, Callback<OrderDetail> callback) {
        mIOrderSource.getOrderDetailBySeatCode(entityId, seatCode, customerId, callback);
    }

    @Override
    public void reverseCheckOut(String entityId, String operatorId, String orderId, String reason, long modifyTime, Callback<ReverseCheckout> callback) {
        mIOrderSource.reverseCheckOut(entityId, operatorId, orderId, reason, modifyTime, callback);
    }

    @Override
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback) {
        mIOrderSource.afterEndPay(entityId, operatorId, orderId, modifyTime, callback);
    }

    @Override
    public void getRemarkList(String entityId, Callback<List<RemarkMenu>> callback) {
        mIOrderSource.getRemarkList(entityId, callback);
    }

    @Override
    public void getFeelPlanListByAreaId(String entityId, String areaId, Callback<List<FeePlan>> callback) {
        mIOrderSource.getFeelPlanListByAreaId(entityId, areaId, callback);
    }

    @Override
    public void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId, int opType, long modifyTime, Callback<UpdateFeePlan> callback) {
        mIOrderSource.updateFeePlan(entityId, orderId, feePlanId, opUserId, opType, modifyTime, callback);
    }

    @Override
    public void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode, Callback<PushOrder> call) {
        mIOrderSource.pushOrder(entityId, orderId, userId, customerRegisterId, seatCode, call);
    }

    @Override
    public void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason, Callback<CancelOrder> call) {
        mIOrderSource.cancelOrder(entityId, orderId, opUserId, modifyTime, reason, call);
    }

    @Override
    public void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode, String opUserId
            , String orderId, int isWait, int opType, long modifyTime, Callback<ChangeOrderByTrade> callback) {
        mIOrderSource.changeOrderByTrade(entityId, memo, peopleCount, newSeatCode, opUserId, orderId, isWait, opType, modifyTime, callback);
    }

    @Override
    public void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo, boolean isWait, Callback<Boolean> callback) {
        mIOrderSource.changeOrderByShopCar(entityId, userId, oldSeatCode, newSeatCode, peopleCount, memo, isWait, callback);
    }

    @Override
    public void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, Callback<OpenOrderVo> callback) {
        mIOrderSource.createOrder(entityId, userId, seatCode, peopleCount, memo, isWait, callback);
    }

    @Override
    public void createOrderForRetail(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait, Callback<OpenOrderVo> callback) {
        mIOrderSource.createOrderForRetail(entityId, userId, seatCode, peopleCount, memo, isWait, callback);
    }
}
