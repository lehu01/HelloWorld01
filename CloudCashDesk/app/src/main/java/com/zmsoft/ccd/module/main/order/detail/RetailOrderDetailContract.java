package com.zmsoft.ccd.module.main.order.detail;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.instance.PersonInstance;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;
import com.zmsoft.ccd.lib.bean.order.feeplan.UpdateFeePlan;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.pay.Pay;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/20 16:07
 */
public class RetailOrderDetailContract {

    public interface Presenter extends BasePresenter {

        /**
         * 清空支付
         */
        void clearPay(String orderId, Pay pay, List<Refund> refundList);

        /**
         * 云收银清空折扣
         *
         * @param orderId
         */
        void clearDiscount(String orderId);

        /**
         * 获取订单详情
         *
         * @param entityId   实体id
         * @param orderId    订单id
         * @param customerId 用户id
         */
        void getOrderDetail(String entityId, String orderId, String customerId);

        /**
         * 反结账
         *
         * @param entityId   实体id
         * @param operatorId 用户id
         * @param orderId    订单id
         * @param reason     反结账原因
         */
        void reverseCheckOut(String entityId, String operatorId, String orderId, String reason, long modifyTime);


        /**
         * 结账完毕
         *
         * @param entityId   实体id
         * @param operatorId 用户id
         * @param orderId    订单id
         */
        void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime);

        /**
         * 获取反结账原因列表
         *
         * @param entityId   实体id
         * @param dicCode    字典
         * @param systemType 系统类型
         */
        void getReverseReasonList(String entityId, String dicCode, int systemType);

        /**
         * 批量处理消息
         *
         * @param messageIdListJson json字符串
         * @param status            处理状态（已处理）
         * @param resultMsg         处理结果（已处理）
         */
        void batchUpdateMessage(String messageIdListJson, int status, String resultMsg);

        /**
         * 获取服务费列表
         *
         * @param entityId 实体id
         * @param areaId   区域id
         */
        void getFeePlanListByAreaId(String entityId, String areaId);

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
        void updateFeePlan(String entityId, String orderId, String feePlanId, String opUserId, int opType, long modifyTime);

        /**
         * 催菜
         *
         * @param entityId           实体id
         * @param userId             用户id，操作员id
         * @param menuIdList         菜肴数组，json字符串
         * @param customerRegisterId 会员id
         */
        void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId);

        /**
         * 催单
         *
         * @param entityId           实体id
         * @param orderId            订单id
         * @param userId             用户id
         * @param customerRegisterId 会员id
         */
        void pushOrder(String entityId, String orderId, String userId, String customerRegisterId, String seatCode);

        /**
         * 检测是否有必选菜
         */
        boolean isMustInstance(List<PersonInstance> personInstanceVoList);

    }

    public interface View extends BaseView<Presenter> {

        // 清空支付成功
        void clearPayListSuccess(RefundResponse data);

        // 清空优惠
        void clearDiscountSuccess(Object obj);

        // 更新订单详情
        void updateOrderDetailView(OrderDetail orderDetail, String errorMessage);

        // 显示反结账底部弹窗
        void showReasonDialog(List<Reason> list);

        // 反结账之后操作
        void reverseCheckOut();

        // 结账完毕操作
        void afterEndPay();

        // 结账完毕失败
        void opOrderFailure(String failureMessage);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);

        // 重新加载订单详情
        void reload();

        // 显示附加费底部弹窗
        void showFeePlanDialog(List<FeePlan> list);

        // 更新服务费成功
        void updateFeePlanSuccess(UpdateFeePlan updateFeePlan);

        // 催菜成功
        void pushInstanceSuccess();

        // 催单成功
        void pushOrderSuccess();
    }


}
