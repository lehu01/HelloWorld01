package com.zmsoft.lib.tonglian.helper;

import com.allinpay.usdk.core.data.ResponseData;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.lib.tonglian.BaseData;


/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/22 11:53
 *     desc  : TongLian 数据处理
 * </pre>
 */
public class AllInDataHelper {

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
            if ("ALP".equals(bagType)) {
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY;
            } else if ("WXP".equals(bagType)) {
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
