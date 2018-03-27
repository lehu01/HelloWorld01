package com.zmsoft.ccd.data.source.orderdetail;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.instance.IInstanceSource;
import com.zmsoft.ccd.data.source.msgcenter.IMsgCenterSource;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;
import com.zmsoft.ccd.lib.bean.order.feeplan.UpdateFeePlan;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.op.PushOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.ReverseCheckout;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/13 15:06
 */
@Singleton
public class OrderDetailSourceRepository implements IOrderDetailSource {

    private final IOrderSource mIOrderSource;
    private final IMsgCenterSource mIMsgCenterSource;
    private final IInstanceSource mIInstanceSource;
    private final ICommonSource mICommonSource;

    @Inject
    public OrderDetailSourceRepository(@Remote IOrderSource orderSource
            , @Remote IMsgCenterSource msgCenterSource
            , @Remote IInstanceSource iInstanceSource) {
        mIOrderSource = orderSource;
        mIMsgCenterSource = msgCenterSource;
        mIInstanceSource = iInstanceSource;
        mICommonSource = new CommonRemoteSource();
    }

    public void getOrderDetail(String entityId, String orderId, String customerId, Callback<OrderDetail> callback) {
        mIOrderSource.getOrderDetail(entityId, orderId, customerId, callback);
    }

    public void reverseCheckOut(String entityId, String operatorId, String orderId, String reason, long modifyTime, Callback<ReverseCheckout> callback) {
        mIOrderSource.reverseCheckOut(entityId, operatorId, orderId, reason, modifyTime, callback);
    }

    public void getReverseReasonList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback) {
        mICommonSource.getReasonList(entityId, dicCode, systemType, callback);
    }

    public void getFeelPlanList(String entityId, String areaId, Callback<List<FeePlan>> callback) {
        mIOrderSource.getFeelPlanListByAreaId(entityId, areaId, callback);
    }

    public void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId, int opType, long modifyTime, Callback<UpdateFeePlan> callback) {
        mIOrderSource.updateFeePlan(entityId, orderId, feePlanId, opUserId, opType, modifyTime, callback);
    }

    public void batchUpdateMessage(String messageIdListJson, int status, String resultMsg, Callback<Boolean> callback) {
        mIMsgCenterSource.batchUpdateMessage(messageIdListJson, status, resultMsg, callback);
    }

    public void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId, Callback<Boolean> callback) {
        mIInstanceSource.pushInstance(entityId, userId, menuIdList, customerRegisterId, seatCode, orderId, callback);
    }

    public void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode, Callback<PushOrder> call) {
        mIOrderSource.pushOrder(entityId, orderId, userId, customerRegisterId, seatCode, call);
    }

    public void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason, Callback<CancelOrder> call) {
        mIOrderSource.cancelOrder(entityId, orderId, opUserId, modifyTime, reason, call);
    }
}
