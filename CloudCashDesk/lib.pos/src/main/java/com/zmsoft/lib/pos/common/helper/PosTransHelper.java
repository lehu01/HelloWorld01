package com.zmsoft.lib.pos.common.helper;


import android.support.v4.app.Fragment;

import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.lib.pos.allin.helper.AllInDataHelper;
import com.zmsoft.lib.pos.common.PosTransCallBack;
import com.zmsoft.lib.pos.common.bean.PosCancelPay;
import com.zmsoft.lib.pos.common.bean.PosPay;
import com.zmsoft.lib.pos.allin.helper.AllInTransHelper;
import com.zmsoft.lib.pos.newland.helper.NewLandDataHelper;
import com.zmsoft.lib.pos.newland.helper.NewLandTransHelper;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 15:03
 *     desc  : pos跳转相关处理
 * </pre>
 */
public class PosTransHelper {

    private static PosTransCallBack mPosTransCallBack;


    /**
     * 跳转相应pos的撤销界面
     *
     * @param fragment fragment
     * @param pay      pay数据
     */
    public static void gotoPosCancelPayActivity(Fragment fragment, PosCancelPay pay, PosTransCallBack posTransCallBack) {
        mPosTransCallBack = posTransCallBack;
        switch (pay.getPayType()) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                if (!NewLandTransHelper.isSupport()) {
                    if (mPosTransCallBack != null) {
                        mPosTransCallBack.transFailure();
                    }
                    return;
                }
                NewLandTransHelper.gotoCancelNewLand(fragment
                        , pay.getPayType()
                        , FeeHelper.getDecimalFeeByFen(pay.getPayMoney())
                        , pay.getPayTransNo());
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                if (!AllInTransHelper.isSupport()) {
                    if (mPosTransCallBack != null) {
                        mPosTransCallBack.transFailure();
                    }
                    return;
                }
                AllInTransHelper.gotoCancelAllInPayActivity(fragment
                        , FeeHelper.getDecimalFeeByFen(pay.getPayMoney())
                        , pay.getPayTransNo()
                        , pay.getPayType());
                break;
        }
    }

    /**
     * 跳转相应pos的支付界面
     *
     * @param fragment fragment
     * @param pay      pay数据
     */
    public static void gotoPosPayActivity(Fragment fragment, PosPay pay) {
        switch (pay.getPayType()) {
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                NewLandTransHelper.gotoNewLand(fragment
                        , pay.getPayType()
                        , FeeHelper.getDecimalFeeByFen(pay.getPayMoney()));
                break;
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                AllInTransHelper.gotoAllInPayActivity(fragment
                        , FeeHelper.getDecimalFeeByFen(pay.getPayMoney())
                        , pay.getPayType());
                break;
        }
    }

    public static boolean isPosPay(int payType) {
        return AllInDataHelper.isAllInPay(payType) || NewLandDataHelper.isNewLandPay(payType);
    }
}
