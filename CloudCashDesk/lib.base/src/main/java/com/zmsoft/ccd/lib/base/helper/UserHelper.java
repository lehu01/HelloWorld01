package com.zmsoft.ccd.lib.base.helper;

import android.text.TextUtils;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.SPUtils;
import com.zmsoft.ccd.network.CommonConstant;
import com.zmsoft.ccd.shop.bean.IndustryType;

import static com.zmsoft.ccd.app.GlobalVars.context;
import static com.zmsoft.ccd.lib.utils.SPUtils.getInstance;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/23.
 */

public class UserHelper {


    public static void saveToSp(User user) {
        UserLocalPrefsCacheSource.setUser(user);
        UserLocalPrefsCacheSource.saveToPref(GlobalVars.context);
        SPUtils spUtils = getInstance(context);
        spUtils.putString(CommonConstant.KEY_SP_TOKEN, user.getToken());
        spUtils.putString(CommonConstant.KEY_SP_USER_ID, user.getUserId());
        spUtils.putString(CommonConstant.KEY_SP_ENTITY_ID, user.getEntityId());
        spUtils.putString(CommonConstant.KEY_SP_MEMBER_ID, user.getMemberId());
        spUtils.putString(CommonConstant.KEY_SP_MOBILE, user.getMobile());
        spUtils.putString(CommonConstant.KEY_SP_USER_NAME, user.getUserName());
        spUtils.putInt(CommonConstant.KEY_SP_INDUSTRY, user.getIndustry());
        spUtils.putString(CommonConstant.KEY_SP_CURRENCY_SYMBOL, user.getCurrencySymbol());
    }

    /**
     * @return 0是餐饮，3是零售
     * @see IndustryType
     */
    public static int getIndustry() {
        return SPUtils.getInstance(context).getInt(CommonConstant.KEY_SP_INDUSTRY, 0);
    }

    /**
     * 保存工作模式
     *
     * @param isMixture 是否是混合模式
     */
    public static void saveWorkModeToSp(boolean isMixture) {
        SPUtils spUtils = getInstance(context);
        spUtils.putBoolean(CommonConstant.KEY_SP_WORK_MODE, isMixture);
    }

    public static String getMobile() {
        return getInstance(context).getString(CommonConstant.KEY_SP_MOBILE, "");
    }


    public static String getEntityId() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            return UserLocalPrefsCacheSource.getUser().getEntityId();
        } else {
            return "";
        }
    }

    public static String getMemberId() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            return UserLocalPrefsCacheSource.getUser().getMemberId();
        } else {
            return "";
        }
    }

    public static String getUserId() {
        return SPUtils.getInstance(context).getString(CommonConstant.KEY_SP_USER_ID, "");
    }


    public static String getToken() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            return UserLocalPrefsCacheSource.getUser().getToken();
        } else {
            return "";
        }
    }

    public static String getShopName() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            return UserLocalPrefsCacheSource.getUser().getShopName();
        } else {
            return "";
        }
    }

    public static boolean isHideChainShop() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            return UserLocalPrefsCacheSource.getUser().isHideChainShop();
        } else {
            return false;
        }
    }


    public static boolean getWorkStatus() {
        return BaseSpHelper.isWorking(context);
    }

    public static String getUserName() {
        return SPUtils.getInstance(context).getString(CommonConstant.KEY_SP_USER_NAME, "");
    }

    public static boolean getWorkModeIsMixture() {
        return SPUtils.getInstance(context).getBoolean(CommonConstant.KEY_SP_WORK_MODE, false);
    }

    /**
     * 保存货币符号
     */
    public static void saveCurrencySymbolToSp(String currencySymbol) {
        if (!TextUtils.isEmpty(currencySymbol)) {
            currencySymbol = FeeHelper.transferRMBSymbol(currencySymbol);
        }
        User user = UserLocalPrefsCacheSource.getUser();
        if (null != user) {
            user.setCurrencySymbol(currencySymbol);
            UserLocalPrefsCacheSource.setUser(user);
            UserLocalPrefsCacheSource.saveToPref(GlobalVars.context);
        }
        SPUtils spUtils = getInstance(context);
        spUtils.putString(CommonConstant.KEY_SP_CURRENCY_SYMBOL, currencySymbol);
    }

    /**
     * 从sp文件中读取货币符号
     *
     * @return
     */
    public static String getCurrencySymbolFromSp() {
        return SPUtils.getInstance(context).getString(CommonConstant.KEY_SP_CURRENCY_SYMBOL, "");
    }

    /**
     * 获取货币符号
     *
     * @return
     */
    public static String getCurrencySymbol() {
        if (null != UserLocalPrefsCacheSource.getUser()) {
            if (TextUtils.isEmpty(UserLocalPrefsCacheSource.getUser().getCurrencySymbol())) {
                return getCurrencySymbolFromSp();
            }
            return UserLocalPrefsCacheSource.getUser().getCurrencySymbol();
        } else {
            return "";
        }
    }
}