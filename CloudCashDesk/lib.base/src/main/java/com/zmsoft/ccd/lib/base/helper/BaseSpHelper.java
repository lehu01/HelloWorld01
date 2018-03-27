package com.zmsoft.ccd.lib.base.helper;

import android.content.Context;

import com.zmsoft.ccd.lib.base.constant.BaseSpKey;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.network.CommonConstant;

/**
 * 账号登出时，会被全部清除
 * Description：本地sp存储管理
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/22 16:13
 */
public class BaseSpHelper {

    /**
     * 保存开启云收银的时间
     */
    public static void saveTurnCloudCashTime(Context context, long time) {
        SPUtils.getInstance(context).putLong(BaseSpKey.KEY_TURN_CLOUD_CASH, time);
    }

    /**
     * 获取开启云收银的时间
     */
    public static long getTurnCloudCashTime(Context context) {
        return SPUtils.getInstance(context).getLong(BaseSpKey.KEY_TURN_CLOUD_CASH);
    }

    /**
     * 获取开启云收银的时间
     */
    public static void removeTurnCloudCashTime(Context context) {
        SPUtils.getInstance(context).remove(BaseSpKey.KEY_TURN_CLOUD_CASH);
    }

    /**
     * 保存获取接口自动打印客户联开关时间
     */
    public static void saveSelfPrintAccountOrderSwitchTime(Context context, long time) {
        SPUtils.getInstance(context).putLong(BaseSpKey.KEY_SELF_PRINT_ACCOUNT_ORDER_TIME, time);
    }

    /**
     * 获取接口自动打印客户联开关时间
     */
    public static long getSelfPrintAccountOrderSwitchTime(Context context) {
        return SPUtils.getInstance(context).getLong(BaseSpKey.KEY_SELF_PRINT_ACCOUNT_ORDER_TIME);
    }

    /**
     * 保存获取接口自动打印客户联开关
     */
    public static void saveSelfPrintAccountOrderSwitch(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_SELF_PRINT_ACCOUNT_ORDER, value);
    }

    /**
     * 保存获取接口自动打印客户联开关
     */
    public static String getSelfPrintAccountOrderSwitch(Context context) {
        return SPUtils.getInstance(context).getString(BaseSpKey.KEY_SELF_PRINT_ACCOUNT_ORDER);
    }

    /**
     * 保存工作模式
     */
    public static void saveWorkMode(Context context, boolean hybrid) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_HYBRID, hybrid);
        UserHelper.saveWorkModeToSp(hybrid);
    }

    /**
     * 获取工作模式，是否混合模式
     */
    public static boolean isHybrid(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_HYBRID);
    }

    /**
     * 保存获取接口自动打印点菜单开关时间
     */
    public static void saveSelfPrintDishesOrderSwitchTime(Context context, long time) {
        SPUtils.getInstance(context).putLong(BaseSpKey.KEY_SELF_PRINT_DISHES_ORDER_TIME, time);
    }

    /**
     * 获取接口自动打印点菜单开关时间
     */
    public static long getSelfPrintDishesOrderSwitchTime(Context context) {
        return SPUtils.getInstance(context).getLong(BaseSpKey.KEY_SELF_PRINT_DISHES_ORDER_TIME);
    }

    /**
     * 保存获取接口自动打印点菜单开关
     */
    public static void saveSelfPrintDishesOrderSwitch(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_SELF_PRINT_DISHES_ORDER, value);
    }

    /**
     * 保存获取接口自动打印点菜单开关
     */
    public static String getSelfPrintDishesOrderSwitch(Context context) {
        return SPUtils.getInstance(context).getString(BaseSpKey.KEY_SELF_PRINT_DISHES_ORDER);
    }

    /**
     * 保存获取接口自动打印财务联开关时间
     */
    public static void saveSelfPrintFinanceOrderSwitchTime(Context context, long time) {
        SPUtils.getInstance(context).putLong(BaseSpKey.KEY_SELF_PRINT_FINANCE_ORDER_TIME, time);
    }

    /**
     * 获取接口自动打印财务联开关时间
     */
    public static long getSelfPrintFinanceOrderSwitchTime(Context context) {
        return SPUtils.getInstance(context).getLong(BaseSpKey.KEY_SELF_PRINT_FINANCE_ORDER_TIME);
    }

    /**
     * 保存获取接口自动打印财务联开关
     */
    public static void saveSelfPrintFinanceOrderSwitch(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_SELF_PRINT_FINANCE_ORDER, value);
    }

    /**
     * 获取接口自动打印财务联开关
     */
    public static String getSelfPrintFinanceOrderSwitch(Context context) {
        return SPUtils.getInstance(context).getString(BaseSpKey.KEY_SELF_PRINT_FINANCE_ORDER);
    }

    /**
     * 保存现金清账开关
     */
    public static void saveCashCleanSwitch(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_TOGGLE_CASH_CLEAN, value);
    }

    /**
     * 保存外卖开关
     */
    public static void saveCarryOutSwitch(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_TOGGLE_CARRYOUT_PHONE_CALL, value);
    }

    /**
     * 保存是否关注零售单
     */
    public static void saveWatchedRetail(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_WATCHED_RETAIL, value);
    }

    /**
     * 获取是否关注零售单
     */
    public static boolean isWatchedRetail(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_WATCHED_RETAIL);
    }

    /**
     * 保存是否使用标签打印机
     */
    public static void saveUseLabelPrinter(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_USE_LABEL_PRINTER, value);
    }

    /**
     * 获取是否使用标签打印机
     */
    public static boolean isUseLabelPrinter(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_USE_LABEL_PRINTER);
    }

    /**
     * 保存双单位菜未修改提示开关
     */
    public static void saveDoubleUnitSwitch(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_DOUBLE_UNIT_SWITCH_PROMPT, value);
    }

    /**
     * 获取双单位菜未修改提示开关
     */
    public static boolean isDoubleUnitSwitch(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_DOUBLE_UNIT_SWITCH_PROMPT);
    }

    /**
     * 保存双单位菜未修改提示开关
     */
    public static void saveUpdateServiceFee(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_UPDATE_SERVICE_FEE, value);
    }

    /**
     * 获取双单位菜未修改提示开关
     */
    public static boolean isUpdateServiceFee(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_UPDATE_SERVICE_FEE);
    }

    /**
     * 保存是否限时用餐
     */
    public static void saveIsLimitTime(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_IS_LIMIT_TIME, value);
    }

    /**
     * 获取是否限时用餐
     */
    public static boolean isLimitTime(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_IS_LIMIT_TIME);
    }

    /**
     * 保存限时时间长度 min
     */
    public static void saveLimitTimeEnd(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_LIMIT_TIME_END, value);
    }

    /**
     * 获取限时时间长度 min
     */
    public static int getLimitTimeEnd(Context context) {
        String limitTimeEndStr = SPUtils.getInstance(context).getString(BaseSpKey.KEY_LIMIT_TIME_END);
        int limitTimeEnd;
        try {
            limitTimeEnd = Integer.parseInt(limitTimeEndStr);
        } catch (NumberFormatException e) {
            limitTimeEnd = 0;
        }
        return limitTimeEnd;
    }

    /**
     * 保存店铺试用期结束
     */
    public static void saveShopOutOfDate(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_SHOP_OUT_OF_DATE, value);
    }

    /**
     * 获取店铺试用期结束
     */
    public static boolean isShopOutOfDate(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_SHOP_OUT_OF_DATE);
    }

    /**
     * 保存工作状态
     */
    public static void saveWorkStatus(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(CommonConstant.KEY_SP_WORK_STATUS, value);
    }

    /**
     * 获取工作状态
     */
    public static boolean isWorking(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_SP_WORK_STATUS);
    }

    //========================================================================================
    // 是否本地收银打印
    //========================================================================================
    public static void saveLocalPrint(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_IS_LOCAL_CASH_PRINT, value);
    }

    public static boolean isLocalPrint(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_IS_LOCAL_CASH_PRINT);
    }

    //========================================================================================
    // 快速开单
    //========================================================================================
    public static void saveQuickOpenOrder(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_QUICK_OPEN_ORDER, value);
    }

    public static boolean isQuickOpenOrder(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_QUICK_OPEN_ORDER);
    }

    //========================================================================================
    // 开单时是否必须选桌
    //========================================================================================
    public static void saveOpenOrderMustSeat(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_OPEN_ORDER_MUST_SEAT, value);
    }

    public static boolean isOpenOrderMustSeat(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_OPEN_ORDER_MUST_SEAT);
    }

    //========================================================================================
    // 转桌、并单、催菜时打印单据
    //========================================================================================
    public static void saveTransSeatOrPushInstance(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_TRANS_SEAT_OR_PUSH_INSTANCE, value);
    }

    public static boolean isTransSeatOrPushInstance(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_TRANS_SEAT_OR_PUSH_INSTANCE);
    }

    //========================================================================================
    // 退菜时打印退菜单
    //========================================================================================
    public static void savePrintCancelInstance(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_PRINT_CANCEL_INSTANCE, value);
    }

    public static boolean isPrintCancelInstance(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_PRINT_CANCEL_INSTANCE);
    }

    //========================================================================================
    // 当有足够的付款金额时是否自动结账完毕
    //========================================================================================
    public static void saveEnoughMoneyEndAfterOrder(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_ENOUGH_MONEY_END_AFTER_ORDER, value);
    }

    public static boolean isEnoughMoneyEndAfterOrder(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_ENOUGH_MONEY_END_AFTER_ORDER);
    }

    //========================================================================================
    // 微信登录是否成功
    //========================================================================================
    public static void saveWechatLoginSuccess(Context context, boolean value) {
        SPUtils.getInstance(context).putBoolean(BaseSpKey.KEY_WECHAT_LOGIN_SUCCESS, value);
    }
    public static boolean isWechatLoginSuccess(Context context) {
        return SPUtils.getInstance(context).getBoolean(BaseSpKey.KEY_WECHAT_LOGIN_SUCCESS);
    }

    //========================================================================================
    // 微信登录是否成功
    //========================================================================================
    public static void saveWechatLoginCode(Context context, String value) {
        SPUtils.getInstance(context).putString(BaseSpKey.KEY_WECHAT_LOGIN_CODE, value);
    }
    public static String getWechatLoginCode(Context context) {
        return SPUtils.getInstance(context).getString(BaseSpKey.KEY_WECHAT_LOGIN_CODE);
    }
}
