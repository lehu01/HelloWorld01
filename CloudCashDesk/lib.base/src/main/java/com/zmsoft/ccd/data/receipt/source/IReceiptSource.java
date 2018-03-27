package com.zmsoft.ccd.data.receipt.source;


import com.chiclaim.modularization.router.IProvider;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.base.bean.EmptyDiscountResponse;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface IReceiptSource extends IProvider {
    /**
     * 云收银获取账单
     *
     * @param orderId  订单id
     * @param callback
     */
    void getCloudCash(String orderId, final Callback<GetCloudCashBillResponse> callback);

    /**
     * 获取代金券面额列表
     *
     * @param kindPayId
     * @param callback
     */
    void getVoucherInfo(String kindPayId, final Callback<GetVoucherInfoResponse> callback);

    /**
     * 获取签字员工列表
     *
     * @param kindPayId
     * @param callback
     */
    void getSignBillSinger(String kindPayId, final Callback<GetSignBillSingerResponse> callback);

    /**
     * 获取挂账单位（人）列表
     *
     * @param param
     * @param callback
     */
    void getSignUnit(String param, final Callback<GetSignBillSingerResponse> callback);

    /**
     * 账单优惠（整单打折，卡优惠）
     *
     * @param param
     * @param callback
     */
    void promoteBill(String param, final Callback<CashPromotionBillResponse> callback);

    /**
     * 获取支付选项信息
     *
     * @param param
     * @param callback
     */
    void getKindDetailInfo(String param, final Callback<GetKindDetailInfoResponse> callback);

    /**
     * 云收银收款
     *
     * @param param
     * @param callback
     */
    void collectPay(String param, final Callback<CloudCashCollectPayResponse> callback);

    /**
     * 获取支付状态<br />
     * collectPay接口会返回一个status值:<br />
     * <p>
     * 支付状态，不可为空。<br />
     * public enum TradePayStatus {<br />
     * PAYING(2,"正在支付"),<br />
     * PAID(1,"支付成功"),<br />
     * FAIL(-1,"支付失败"),<br />
     * DEFAULT(-2,"默认值")<br />
     * ;
     * }
     * </p>
     * 如果status是2（正在支付），则需要调用getPayStatus接口再次获取支付状态，如果返回的status仍是2，再次调用
     * getPayStatus，循环3次，如果status还是2，则代表支付失败（PS:会出现status是2的情况一般是优惠券核销	，云收银收款目前没有
     * 优惠券核销，一般不会出现status是2的情况，为了兼容性，还是要做下status的判断和调用getPayStatus检查支付状态）
     *
     * @param snapshotId
     * @param callback
     */
    void getPayStatus(String snapshotId, String outTradeNo, final Callback<CloudCashCollectPayResponse> callback);

    /**
     * 结账完毕
     *
     * @param entityId   实体id
     * @param operatorId 用户id
     * @param orderId    订单id
     */
    void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback);

    /**
     * 结账完毕（零售）
     *
     * @param entityId   实体id
     * @param operatorId 用户id
     * @param orderId    订单id
     */
    void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback);

    /**
     * 云收银清空折扣
     *
     * @param orderId
     * @param callback
     */
    void emptyDiscount(String orderId, final Callback<EmptyDiscountResponse> callback);

    /**
     * 云收银退款
     *
     * @param orderId
     * @param callback
     */
    void refund(String orderId, List<Refund> refundList, final Callback<RefundResponse> callback);

    /**
     * 云收银退款
     *
     * @param orderId
     * @param callback
     */
    void refund(String orderId, List<Refund> refundList, boolean checkLimit, final Callback<RefundResponse> callback);

    /**
     * 优惠券核销
     *
     * @param orderId
     * @param callback
     */
    void verification(String orderId, String promotionCode, final Callback<VerificationResponse> callback);

    /**
     * 获取订单的全部支付列表
     *
     * @param orderId  订单id
     * @param callback
     */
    void getOrderPayedList(String orderId, final Callback<OrderPayListResponse> callback);

}
