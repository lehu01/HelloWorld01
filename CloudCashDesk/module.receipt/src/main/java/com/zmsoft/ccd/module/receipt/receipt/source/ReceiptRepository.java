package com.zmsoft.ccd.module.receipt.receipt.source;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.base.bean.EmptyDiscountResponse;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * @author DangGui
 * @create 2017/5/24.
 */
@ModelScoped
public class ReceiptRepository implements IReceiptSource {

    private final IReceiptSource mReceiptSource;

    @Inject
    ReceiptRepository(@Remote IReceiptSource remoteSource) {
        this.mReceiptSource = remoteSource;
    }

    @Override
    public void getCloudCash(String orderId, Callback<GetCloudCashBillResponse> callback) {
        mReceiptSource.getCloudCash(orderId, callback);
    }

    @Override
    public void getVoucherInfo(String kindPayId, Callback<GetVoucherInfoResponse> callback) {
        mReceiptSource.getVoucherInfo(kindPayId, callback);
    }

    @Override
    public void getSignBillSinger(String kindPayId, Callback<GetSignBillSingerResponse> callback) {
        mReceiptSource.getSignBillSinger(kindPayId, callback);
    }

    @Override
    public void getSignUnit(String param, Callback<GetSignBillSingerResponse> callback) {
        mReceiptSource.getSignUnit(param, callback);
    }

    @Override
    public void promoteBill(String param, Callback<CashPromotionBillResponse> callback) {
        mReceiptSource.promoteBill(param, callback);
    }

    @Override
    public void getKindDetailInfo(String param, Callback<GetKindDetailInfoResponse> callback) {
        mReceiptSource.getKindDetailInfo(param, callback);
    }

    @Override
    public void collectPay(String param, Callback<CloudCashCollectPayResponse> callback) {
        mReceiptSource.collectPay(param, callback);
    }

    @Override
    public void getPayStatus(String snapshotId, String outTradeNo, Callback<CloudCashCollectPayResponse> callback) {
        mReceiptSource.getPayStatus(snapshotId, outTradeNo, callback);
    }

    @Override
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback) {
        mReceiptSource.afterEndPay(entityId, operatorId, orderId, modifyTime, callback);
    }

    @Override
    public void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime, Callback<AfterEndPay> callback) {
        mReceiptSource.afterEndPayForRetail(entityId, operatorId, orderId, modifyTime, callback);
    }

    @Override
    public void emptyDiscount(String orderId, Callback<EmptyDiscountResponse> callback) {
        mReceiptSource.emptyDiscount(orderId, callback);
    }

    @Override
    public void refund(String orderId, List<Refund> refundList, Callback<RefundResponse> callback) {
        mReceiptSource.refund(orderId, refundList, callback);
    }

    @Override
    public void refund(String orderId, List<Refund> refundList, boolean checkLimit, Callback<RefundResponse> callback) {
        mReceiptSource.refund(orderId, refundList, checkLimit, callback);
    }

    @Override
    public void verification(String orderId, String promotionCode, Callback<VerificationResponse> callback) {
        mReceiptSource.verification(orderId, promotionCode, callback);
    }

    @Override
    public void getOrderPayedList(String orderId, Callback<OrderPayListResponse> callback) {
        mReceiptSource.getOrderPayedList(orderId, callback);
    }
}
