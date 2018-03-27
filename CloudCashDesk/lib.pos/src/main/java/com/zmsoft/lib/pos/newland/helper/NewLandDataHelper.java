package com.zmsoft.lib.pos.newland.helper;

import android.os.Bundle;

import com.google.gson.Gson;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.lib.pos.newland.NewLandConstant;
import com.zmsoft.lib.pos.newland.bean.NewLandPayDetail;
import com.zmsoft.lib.pos.newland.bean.NewLandResponse;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/24 14:25
 *     desc  : 新大陆数据处理帮助类app <---> new land
 * </pre>
 */
public class NewLandDataHelper {

    /**
     * 自己的收款类型转换为新大陆的类型
     *
     * @param payType 支付类型
     * @return
     */
    public static int getNewLandPayType(int payType) {
        int type = NewLandConstant.Type.BANK;
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
                type = NewLandConstant.Type.ALIPAY;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                type = NewLandConstant.Type.WEIXIN;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                type = NewLandConstant.Type.BANK;
                break;
        }
        return type;
    }

    /**
     * 是否是新大陆支付方式
     *
     * @param payType 支付类型
     * @return
     */
    public static boolean isNewLandPay(int payType) {
        return (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK);
    }

    /**
     * 获取新大陆支付code
     *
     * @param payType 支付类型
     * @return
     */
    public static String getNewLandPayCode(int payType) {
        String code = NewLandConstant.Code.PAY_BANK;
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                code = NewLandConstant.Code.PAY_SCAN;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                code = NewLandConstant.Code.PAY_BANK;
                break;
        }
        return code;
    }

    /**
     * 获取新大陆撤销支付code
     *
     * @param payType 支付类型
     * @return
     */
    public static String getNewLandCancelPayCode(int payType) {
        String code = NewLandConstant.Code.CACEL_PAY;
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                code = NewLandConstant.Code.CANCEL_PAY_SCAN;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                code = NewLandConstant.Code.CACEL_PAY;
                break;
        }
        return code;
    }

    /**
     * 解析新大陆返回数据
     *
     * @param data data
     * @return
     */
    public static NewLandResponse receiveData(Bundle data) {
        if (data == null) {
            return null;
        }
        NewLandResponse response = new NewLandResponse();
        response.setMsgTp(StringUtils.notNull(data.getString(NewLandConstant.Key.MSG_TP)));
        response.setPayType(StringUtils.notNull(data.getString(NewLandConstant.Key.PAY_TP)));
        response.setAmt(StringUtils.notNull(data.getString(NewLandConstant.Key.AMT)));
        NewLandPayDetail newLandPayDetail = getNewLandPayDetail(data.getString(NewLandConstant.Key.TXNDETAIL));
        if (newLandPayDetail != null) {
            String payTypeStr = response.getPayType();
            int type = 0;
            try {
                type = Integer.parseInt(payTypeStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (type == NewLandConstant.Type.ALIPAY_REPLY || type == NewLandConstant.Type.WEIXIN_REPLY) {
                response.setSysTraceNo(StringUtils.notNull(newLandPayDetail.getBatchno()) + StringUtils.notNull(newLandPayDetail.getSystraceno()));
            } else {
                response.setSysTraceNo(StringUtils.notNull(newLandPayDetail.getSystraceno()));
            }
        }
        return response;
    }

    /**
     * 新大陆交易详情
     *
     * @param data
     * @return
     */
    private static NewLandPayDetail getNewLandPayDetail(String data) {
        try {
            return new Gson().fromJson(data, NewLandPayDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取通联支付的金额：单位分
     *
     * @param data data
     * @return 金额，分
     */
    public static int getPayMoney(NewLandResponse data) {
        int money = 0;
        if (data == null) {
            return money;
        }
        if (StringUtils.isEmpty(data.getAmt())) {
            return money;
        }
        try {
            double payMoney = Double.parseDouble(data.getAmt());
            money = (int) (payMoney * 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return money;
    }

    /**
     * 获取支付类型
     *
     * @param data data
     * @return 转化自己的支付类型
     */
    public static short getPayType(NewLandResponse data) {
        short payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK;
        if (data == null) {
            return payType;
        }

        String payTypeStr = data.getPayType();
        int type = 0;
        try {
            type = Integer.parseInt(payTypeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (type) {
            case NewLandConstant.Type.ALIPAY_REPLY:
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY;
                break;
            case NewLandConstant.Type.WEIXIN_REPLY:
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT;
                break;
            case NewLandConstant.Type.BANK:
                payType = BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK;
                break;
        }
        return payType;
    }

    /**
     * 是否是扫码支付
     *
     * @param payType
     * @return
     */
    public static boolean isNewLandScan(int payType) {
        if (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY) {
            return true;
        }
        return false;
    }

}
