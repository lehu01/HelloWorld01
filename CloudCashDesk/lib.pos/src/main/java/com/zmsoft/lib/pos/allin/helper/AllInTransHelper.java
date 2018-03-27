package com.zmsoft.lib.pos.allin.helper;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.allinpay.usdk.core.data.RequestData;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.lib.pos.R;
import com.zmsoft.lib.pos.allin.BaseData;
import com.zmsoft.lib.pos.common.constant.PosRequestCodeConstant;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 17:03
 *     desc  : 通联帮助类
 * </pre>
 */
public class AllInTransHelper {

    /**
     * 通联支付界面
     *
     * @param fragment fragment
     * @param money    支付流水号
     * @param payType  支付类型
     */
    public static void gotoAllInPayActivity(Fragment fragment, String money, int payType) {
        gotoAllInPayActivity(fragment, PosRequestCodeConstant.PAY_CODE_BY_ALL_IN, money, payType);
    }

    /**
     * 通联支付界面
     *
     * @param fragment    activity
     * @param requestCode 回调code：可随便定义
     * @param money       支付流水号
     */
    public static void gotoAllInPayActivity(Fragment fragment, int requestCode, String money, int payType) {
        if (!isSupport()) {
            ToastUtils.showShortToastSafe(context, context.getString(R.string.fail_cant_use_the_pos));
            return;
        }
        RequestData requestData = new RequestData();
        requestData.putValue(BaseData.BUSINESS_ID, AllInDataHelper.getPayType(payType));
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
        gotoCancelAllInPayActivity(fragment, PosRequestCodeConstant.CANCEL_PAY_CODE_BY_ALL_IN, money, payNo, payType);
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
        requestData.putValue(BaseData.BUSINESS_ID, AllInDataHelper.getCancelPayType(payType));
        requestData.putValue(BaseData.AMOUNT, money);// 金额
        requestData.putValue(BaseData.ORIG_TRACE_NO, payNo);// 原流水号
        doTrans(fragment, requestCode, requestData);
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
    public static boolean isSupport() {
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
}
