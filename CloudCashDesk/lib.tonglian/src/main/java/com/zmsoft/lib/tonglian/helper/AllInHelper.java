package com.zmsoft.lib.tonglian.helper;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.allinpay.usdk.core.data.RequestData;
import com.allinpay.usdk.core.data.ResponseData;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.lib.tonglian.BaseData;
import com.zmsoft.lib.tonglian.Busi_Data;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 17:03
 *     desc  : 通联帮助类
 * </pre>
 */
public class AllInHelper {

    public static final int ALL_IN_PAY_CODE = 6666;
    public static final int ALL_IN_CANCEL_PAY_CODE = 9999;

    /**
     * 通联支付界面
     *
     * @param fragment fragment
     * @param money    支付流水号
     * @param payType  支付类型
     */
    public static void gotoAllInPayActivity(Fragment fragment, String money, int payType) {
        gotoAllInPayActivity(fragment, ALL_IN_PAY_CODE, money, payType);
    }

    /**
     * 通联支付界面
     *
     * @param fragment    activity
     * @param requestCode 回调code：可随便定义
     * @param money       支付流水号
     */
    public static void gotoAllInPayActivity(Fragment fragment, int requestCode, String money, int payType) {
        if (!isAllInDevice()) {
            ToastUtils.showShortToastSafe(context, "操作失败，只有相应的POS机才能使用此收款方式");
            return;
        }
        RequestData requestData = new RequestData();
        requestData.putValue(BaseData.BUSINESS_ID, getPayType(payType));
        requestData.putValue(BaseData.AMOUNT, money);// 金额
        doTrans(fragment, requestCode, requestData);
    }


    /**
     * 跳转通联撤销支付
     *
     * @param fragment activity
     * @param money    金额
     * @param payNo    通联支付凭证号：000055
     * @param payType  退款类型
     */
    public static void gotoCancelAllInPayActivity(Fragment fragment, String money, String payNo, int payType) {
        gotoCancelAllInPayActivity(fragment, ALL_IN_CANCEL_PAY_CODE, money, payNo, payType);
    }

    /**
     * 跳转通联撤销支付
     *
     * @param fragment    activity
     * @param requestCode 回调code
     * @param money       金额
     * @param payNo       通联支付凭证号：000055
     */
    public static void gotoCancelAllInPayActivity(Fragment fragment, int requestCode, String money, String payNo, int payType) {
        RequestData requestData = new RequestData();
        requestData.putValue(BaseData.BUSINESS_ID, getCancelPayType(payType));
        requestData.putValue(BaseData.AMOUNT, money);// 金额
        requestData.putValue(BaseData.ORIG_TRACE_NO, payNo);// 原流水号
        doTrans(fragment, requestCode, requestData);
    }

    /**
     * 转通联支付类型
     *
     * @param payType payType
     * @return
     */
    private static String getPayType(int payType) {
        String payTypeStr = "";
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                payTypeStr = Busi_Data.BUSI_SALE_QR;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                payTypeStr = Busi_Data.BUSI_SALE;
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
    private static String getCancelPayType(int payType) {
        String payTypeStr = "";
        switch (payType) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                payTypeStr = Busi_Data.BUSI_VOID_QR;
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                payTypeStr = Busi_Data.BUSI_VOID_BANK;
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
        if (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT
                || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK) {
            return true;
        }
        return false;
    }

    /**
     * 通过Intent调用USDK
     */
    private static void doTrans(Fragment fragment, int requestCode, RequestData requestData) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.allinpay.usdk", "com.allinpay.usdk.MainActivity"));
            Bundle bundle = new Bundle();
            bundle.putSerializable(RequestData.KEY_ERTRAS, requestData);
            intent.putExtras(bundle);
            fragment.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是通联设备，是否能跳转
     *
     * @return
     */
    public static boolean isAllInDevice() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.allinpay.usdk", "com.allinpay.usdk.MainActivity"));
            ActivityInfo activityInfo = intent.resolveActivityInfo(context.getPackageManager(), intent.getFlags());
            if (activityInfo == null) {
                return false;
            }
            return activityInfo.exported;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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
                Toast.makeText(context, "交易结果异常-空", Toast.LENGTH_LONG).show();
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
     * 通联是否撤销订单
     * @param data
     * @return
     */
    public static boolean isCancelOrder(ResponseData data) {
        if (data == null) return false;
        return "XP".equalsIgnoreCase(data.getValue(BaseData.REJCODE));
    }
}
