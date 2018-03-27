package com.zmsoft.ccd.module.electronic.helper;

import android.text.TextUtils;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.electronic.ElePayment;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentDetail;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentItem;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentNormalItem;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/8/19.
 */

public class DataMapLayer {
    /**
     * 电子收款明细列表数据转换
     *
     * @param getElePaymentListResponse
     * @return
     */
    public static List<ElePaymentItem> getElePaymentItemList(GetElePaymentListResponse getElePaymentListResponse) {
        List<ElePaymentItem> elePaymentItemList = null;
        List<ElePayment> elePaymentList = getElePaymentListResponse.getElePayments();
        if (null != elePaymentList && !elePaymentList.isEmpty()) {
            elePaymentItemList = new ArrayList<>(elePaymentList.size());
            for (int i = 0; i < elePaymentList.size(); i++) {
                ElePayment elePayment = elePaymentList.get(i);
                if (null != elePayment) {
                    ElePaymentItem jagUpItem = new ElePaymentItem();
                    jagUpItem.setType(ElePaymentItem.ItemType.TYPE_JAG_UP);

                    ElePaymentItem normalItem = new ElePaymentItem();
                    normalItem.setType(ElePaymentItem.ItemType.TYPE_NORMAL);
                    ElePaymentNormalItem elePaymentNormalItem = new ElePaymentNormalItem();
                    normalItem.setElePaymentNormalItem(elePaymentNormalItem);
                    elePaymentNormalItem.setPayId(elePayment.getPayId());
                    elePaymentNormalItem.setOrderId(elePayment.getOrderId());
                    elePaymentNormalItem.setPayType(elePayment.getType());
                    elePaymentNormalItem.setCode(elePayment.getCode());
                    if (null == elePayment.getName()) {
                        elePayment.setName("");
                    }
                    elePaymentNormalItem.setReceiptName(String.format(context.getResources().getString(R.string.electronic_receipt_name)
                            , elePayment.getName()));
                    if (!TextUtils.isEmpty(elePayment.getOrderCode())) {
                        elePaymentNormalItem.setOrderCode(String.format(context.getResources().getString(R.string.electronic_receipt_order_code)
                                , elePayment.getOrderCode()));
                    }
                    elePaymentNormalItem.setReceiptFee(FeeHelper.getDecimalFeeByFen(elePayment.getFee()));
                    elePaymentNormalItem.setSeatCode(elePayment.getSeatCode());
                    elePaymentNormalItem.setTime(TimeUtils.getTimeStr(elePayment.getPayTime()
                            , TimeUtils.FORMAT_TIME));
                    elePaymentNormalItem.setPayStatus(elePayment.getPayStatus());

                    ElePaymentItem jagDownItem = new ElePaymentItem();
                    jagDownItem.setType(ElePaymentItem.ItemType.TYPE_JAG_DOWN);

                    elePaymentItemList.add(jagUpItem);
                    elePaymentItemList.add(normalItem);
                    elePaymentItemList.add(jagDownItem);
                }
            }
        }
        return elePaymentItemList;
    }

    /**
     * 获取收款凭证信息
     *
     * @param getElePaymentResponse
     * @return
     */
    public static ElePaymentDetail getElePaymentDetail(GetElePaymentResponse getElePaymentResponse) {
        ElePaymentDetail elePaymentDetail = new ElePaymentDetail();
        elePaymentDetail.setPayType(getElePaymentResponse.getType());
        elePaymentDetail.setReceiptName(getElePaymentResponse.getName());
        elePaymentDetail.setPayFee(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                , FeeHelper.getDecimalFeeByFen(getElePaymentResponse.getFee())));
        elePaymentDetail.setPayStatus(getElePaymentResponse.getPayStatus());
        //会员
        StringBuilder memberNameSb = null;
        if (!TextUtils.isEmpty(getElePaymentResponse.getMemberName())) {
            memberNameSb = new StringBuilder();
            memberNameSb.append(getElePaymentResponse.getMemberName());
        }
        if (!TextUtils.isEmpty(getElePaymentResponse.getMobile())) {
            if (null != memberNameSb && memberNameSb.length() > 0) {
                memberNameSb.append(" (");
                memberNameSb.append(getElePaymentResponse.getMobile());
                memberNameSb.append(")");
            } else {
                if (null == memberNameSb) {
                    memberNameSb = new StringBuilder();
                }
                memberNameSb.append(getElePaymentResponse.getMobile());
            }
        }
        if (null != memberNameSb && memberNameSb.length() > 0) {
            elePaymentDetail.setMemberName(memberNameSb.toString());
        }
        elePaymentDetail.setOrderId(getElePaymentResponse.getOrderId());
        //单号
        if (!TextUtils.isEmpty(getElePaymentResponse.getOrderCode())) {
            elePaymentDetail.setOrderNo(String.format(context.getResources().getString(R.string.electronic_receipt_order_code)
                    , getElePaymentResponse.getOrderCode()));
        }
        //桌号
        elePaymentDetail.setSeatCode(getElePaymentResponse.getSeatCode());
        //交易单号
        elePaymentDetail.setPayCode(getElePaymentResponse.getCode());
        //会员卡号
        elePaymentDetail.setCardNo(getElePaymentResponse.getCardNo());
        //支付时间
        elePaymentDetail.setTime(TimeUtils.getTimeStr(getElePaymentResponse.getPayTime()
                , TimeUtils.FORMAT_DATE_TIME));
        elePaymentDetail.setCanRefund(getElePaymentResponse.isCanRefund());
        return elePaymentDetail;
    }
}
