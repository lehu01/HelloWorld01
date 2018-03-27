package com.zmsoft.lib.newland.helper;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.lib.newland.NewLandConstant;
import com.zmsoft.lib.newland.data.NewLandDataHelper;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/20 15:53
 *     desc  : 新大陆帮助类
 * </pre>
 */
public class NewLandHelper {

    // 新大陆
    public static final int PAY_CODE = 1111;
    public static final int CANCEL_PAY_CODE = 2222;
    private static final int PAY = 0;
    private static final int CANCEL_PAY = 1;
    private static final String NEW_LAND_PACKAGE = "com.newland.caishen";
    private static final String CASH_CLASS = "com.newland.caishen.ui.activity.MainActivity";


    //=============================================================================================
    // 新大陆：星pos
    //=============================================================================================

    /**
     * 是否是新大陆设备，是否能跳转
     */
    public static boolean isSupport() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(NEW_LAND_PACKAGE, CASH_CLASS));
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

    public static void gotoNewLand(Fragment fragment, int payType, String payMoney) {
        doTrans(fragment, PAY, payType, payMoney, "", PAY_CODE);
    }

    public static void gotoCancelNewLand(Fragment fragment, int payType, String payMoney, String tranceNo) {
        doTrans(fragment, CANCEL_PAY, payType, payMoney, tranceNo, CANCEL_PAY_CODE);
    }

    public static void doTrans(Fragment fragment, int transPayType, int payType, String payMoney, String tranceNo, int requestCode) {
        try {
            ComponentName component = new ComponentName(NEW_LAND_PACKAGE, CASH_CLASS);
            Intent intent = new Intent();
            intent.setComponent(component);
            Bundle bundle = new Bundle();
            // 定值
            bundle.putString(NewLandConstant.Key.MSG_TP, NewLandConstant.Code.PAY_REQUEST);
            bundle.putString(NewLandConstant.Key.PROC_TP, "00");
            bundle.putString(NewLandConstant.Key.APPID, "com.zmsoft.ccd");
            // 传入
            bundle.putString(NewLandConstant.Key.PAY_TP, String.valueOf(NewLandDataHelper.getNewLandPayType(payType)));
            bundle.putString(NewLandConstant.Key.AMT, payMoney);
            if (transPayType == PAY) {
                bundle.putString(NewLandConstant.Key.PROC_CD, NewLandDataHelper.getNewLandPayCode(payType));
            } else if (transPayType == CANCEL_PAY) {
                bundle.putString(NewLandConstant.Key.SYSTRACENO, tranceNo);
                bundle.putString(NewLandConstant.Key.PROC_CD, NewLandDataHelper.getNewLandCancelPayCode(payType));
            }
            intent.putExtras(bundle);
            fragment.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
