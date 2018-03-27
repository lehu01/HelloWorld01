package com.zmsoft.lib.pos.allin.helper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.allinpay.usdk.core.data.ResponseData;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.lib.pos.R;
import com.zmsoft.lib.pos.allin.AllinConstant;
import com.zmsoft.lib.pos.allin.BaseData;
import com.zmsoft.lib.pos.allin.BusiData;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/22 11:53
 *     desc  : TongLian 数据处理
 * </pre>
 */
public class AllInDataHelper {

    /**
     * 解析通联回调数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public static ResponseData receiveData(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle extras = data.getExtras();
            if (extras == null) {
                Toast.makeText(context, context.getString(R.string.deal_exception_empty), Toast.LENGTH_LONG).show();
                return null;
            }
            //交易結果
            ResponseData responseData = (ResponseData) extras.getSerializable(ResponseData.KEY_ERTRAS);
            Logger.d("交易结果：" + responseData != null ? responseData.getValue(BaseData.REJCODE_CN) : "null");
            return responseData;
        }
        return null;
    }

    /**
     * 转通联支付类型
     *
     * @param payType payType
     * @return
     */
    public static String getPayType(int payType) {
        String payTypeStr = "";
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                payTypeStr = BusiData.BUSI_SALE_QR;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                payTypeStr = BusiData.BUSI_SALE;
                break;
        }
        return payTypeStr;
    }

    /**
     * 转通联撤销类型
     *
     * @param payType
     * @return
     */
    public static String getCancelPayType(int payType) {
        String payTypeStr = "";
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                payTypeStr = BusiData.BUSI_VOID_QR;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                payTypeStr = BusiData.BUSI_VOID_BANK;
                break;
        }
        return payTypeStr;
    }

    /**
     * 是否是通联支付的
     *
     * @param payType
     * @return
     */
    public static boolean isAllInPay(int payType) {
        return (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK);
    }

    /**
     * 获取支付类型
     *
     * @param data data
     * @return 转化自己的支付类型
     */
    public static short getPayType(ResponseData data) {
        short payType = 0;
        if (data == null) {
            return payType;
        }
        String bagType = data.getValue(BaseData.CUPS);
        if (!StringUtils.isEmpty(bagType)) {
            if (AllinConstant.PayType.ALIPAY.equals(bagType)) {
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY;
            } else if (AllinConstant.PayType.WEXIN.equals(bagType)) {
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT;
            }
        } else {
            payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK;

        }
        return payType;
    }

    /**
     * 获取通联支付的金额：单位分
     *
     * @param data data
     * @return 金额，分
     */
    public static int getPayMoney(ResponseData data) {
        int money = 0;
        if (data == null) {
            return money;
        }
        String moneyStr = data.getValue(BaseData.AMOUNT);
        if (StringUtils.isEmpty(moneyStr)) {
            return money;
        }
        try {
            money = Integer.parseInt(moneyStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return money;
    }

    /**
     * 获取通联交易凭证
     *
     * @param data data
     * @return 交易凭证：银行卡6位，支付宝微信18位
     */
    public static String getTraceNo(ResponseData data) {
        String payCode = "";
        if (data == null) {
            return payCode;
        }
        payCode = data.getValue(BaseData.TRACE_NO);
        return payCode;
    }

}
