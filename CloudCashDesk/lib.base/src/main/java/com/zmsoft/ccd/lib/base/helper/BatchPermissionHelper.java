package com.zmsoft.ccd.lib.base.helper;

import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zmsoft.ccd.app.GlobalVars.context;
import static com.zmsoft.ccd.lib.utils.SPUtils.getInstance;

/**
 * @author DangGui
 * @create 2017/6/24.
 */

public final class BatchPermissionHelper {
    /**
     * 获取当前云收银内所有权限的ActionCode集合
     *
     * @return
     */
    public static List<String> getAllPermissionCode() {
        List<String> mBatchPermissionActionCode = new ArrayList<>();
        mBatchPermissionActionCode.add(Permission.CustomFood.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.PresentFood.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.CancelInstance.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.CancelOrder.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.UpdateInstancePrice.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.UpdateInstanceWeight.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.ChangeOrder.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.MenuBalance.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.EmptyDiscount.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.FreeOrder.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.OnAccount.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.CheckOut.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.ClearPay.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.EPayRefund.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.PrintAccount.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.TakeOut.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.AccountBills.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.ReprintSmallTicket.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.OrderBegin.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.AccountPrintFin.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.PushInstance.ACTION_CODE);
        mBatchPermissionActionCode.add(Permission.PushOrder.ACTION_CODE);
        return mBatchPermissionActionCode;
    }

    /**
     * 批量获取权限
     *
     * @param batchPermissionResponse
     * @return
     */
    public static void saveBatchPermission(HashMap<String, Boolean> batchPermissionResponse) {
        if (null == batchPermissionResponse) {
            return;
        }
        if (null != batchPermissionResponse.get(Permission.CustomFood.ACTION_CODE)) {
            saveToSp(Permission.CustomFood.ACTION_CODE, batchPermissionResponse.get(Permission.CustomFood.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.PresentFood.ACTION_CODE)) {
            saveToSp(Permission.PresentFood.ACTION_CODE, batchPermissionResponse.get(Permission.PresentFood.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.CancelInstance.ACTION_CODE)) {
            saveToSp(Permission.CancelInstance.ACTION_CODE, batchPermissionResponse.get(Permission.CancelInstance.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.CancelOrder.ACTION_CODE)) {
            saveToSp(Permission.CancelOrder.ACTION_CODE, batchPermissionResponse.get(Permission.CancelOrder.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.UpdateInstancePrice.ACTION_CODE)) {
            saveToSp(Permission.UpdateInstancePrice.ACTION_CODE, batchPermissionResponse.get(Permission.UpdateInstancePrice.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.UpdateInstanceWeight.ACTION_CODE)) {
            saveToSp(Permission.UpdateInstanceWeight.ACTION_CODE, batchPermissionResponse.get(Permission.UpdateInstanceWeight.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.ChangeOrder.ACTION_CODE)) {
            saveToSp(Permission.ChangeOrder.ACTION_CODE, batchPermissionResponse.get(Permission.ChangeOrder.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.MenuBalance.ACTION_CODE)) {
            saveToSp(Permission.MenuBalance.ACTION_CODE, batchPermissionResponse.get(Permission.MenuBalance.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.EmptyDiscount.ACTION_CODE)) {
            saveToSp(Permission.EmptyDiscount.ACTION_CODE, batchPermissionResponse.get(Permission.EmptyDiscount.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.OnAccount.ACTION_CODE)) {
            saveToSp(Permission.OnAccount.ACTION_CODE, batchPermissionResponse.get(Permission.OnAccount.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.FreeOrder.ACTION_CODE)) {
            saveToSp(Permission.FreeOrder.ACTION_CODE, batchPermissionResponse.get(Permission.FreeOrder.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.CheckOut.ACTION_CODE)) {
            saveToSp(Permission.CheckOut.ACTION_CODE, batchPermissionResponse.get(Permission.CheckOut.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.ClearPay.ACTION_CODE)) {
            saveToSp(Permission.ClearPay.ACTION_CODE, batchPermissionResponse.get(Permission.ClearPay.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.EPayRefund.ACTION_CODE)) {
            saveToSp(Permission.EPayRefund.ACTION_CODE, batchPermissionResponse.get(Permission.EPayRefund.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.PrintAccount.ACTION_CODE)) {
            saveToSp(Permission.PrintAccount.ACTION_CODE, batchPermissionResponse.get(Permission.PrintAccount.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.TakeOut.ACTION_CODE)) {
            saveToSp(Permission.TakeOut.ACTION_CODE, batchPermissionResponse.get(Permission.TakeOut.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.AccountBills.ACTION_CODE)) {
            saveToSp(Permission.AccountBills.ACTION_CODE, batchPermissionResponse.get(Permission.AccountBills.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.AccountPrintFin.ACTION_CODE)) {
            saveToSp(Permission.AccountPrintFin.ACTION_CODE, batchPermissionResponse.get(Permission.AccountPrintFin.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.PushInstance.ACTION_CODE)) {
            saveToSp(Permission.PushInstance.ACTION_CODE, batchPermissionResponse.get(Permission.PushInstance.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.PushOrder.ACTION_CODE)) {
            saveToSp(Permission.PushOrder.ACTION_CODE, batchPermissionResponse.get(Permission.PushOrder.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.AccountBills.ACTION_CODE)) {
            saveToSp(Permission.AccountBills.ACTION_CODE, batchPermissionResponse.get(Permission.AccountBills.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.ReprintSmallTicket.ACTION_CODE)) {
            saveToSp(Permission.ReprintSmallTicket.ACTION_CODE, batchPermissionResponse.get(Permission.ReprintSmallTicket.ACTION_CODE));
        }
        if (null != batchPermissionResponse.get(Permission.OrderBegin.ACTION_CODE)) {
            saveToSp(Permission.OrderBegin.ACTION_CODE, batchPermissionResponse.get(Permission.OrderBegin.ACTION_CODE));
        }
    }

    /**
     * 根据actionCode判断有无相应权限
     *
     * @param actionCode
     * @return
     */
    public static boolean getPermission(String actionCode) {
        return getInstance(context).getBoolean(actionCode);
    }

    private static void saveToSp(String actionCode, boolean permission) {
        SPUtils spUtils = getInstance(context);
        spUtils.putBoolean(actionCode, permission);
    }

}
