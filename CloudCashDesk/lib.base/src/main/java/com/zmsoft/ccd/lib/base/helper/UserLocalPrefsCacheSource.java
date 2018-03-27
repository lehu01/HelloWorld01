package com.zmsoft.ccd.lib.base.helper;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.BuildConfig;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.AESUtils;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/11/30 11:41
 */
public class UserLocalPrefsCacheSource {
    private static final String USER_PREF = "user_pref";
    private static final String USER_PREF_KEY = "user_pref_key";
    private static final String USER_PREF_LAST_KEY = "user_pref_last_key";
    private static final String USER_PREF_LAST_COUNTRY_CODE = "user_pref_last_country_code";        // 存放上次登录的国家码
    private static String lastUserName;
    private static User mUser;

    private static void readFromPref(Context context) {
        try {
            String str = context.getSharedPreferences(USER_PREF,
                    Context.MODE_PRIVATE).getString(USER_PREF_KEY, "");
            String decryptStr;
            if (isUserInfoEncrypt(str)) {
                decryptStr = AESUtils.decrypt(BuildConfig.AESEncryptKey, str);
                Logger.d("解密后用户信息-->" + decryptStr);
            } else {
                decryptStr = str;
            }
            User storeUser = new Gson().fromJson(decryptStr, User.class);
            if (storeUser == null) {
                deleteFromPref();
            } else {
                setUser(storeUser);
                if (!isUserInfoEncrypt(str)) {
                    saveToPref(context);
                }
            }
        } catch (Exception e) {
            Logger.w("read user info failed"+ e.getMessage());
        }
    }

    private static void deleteFromPref() {
        try {
            context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
                    .edit().remove(USER_PREF_KEY).apply();
        } catch (Exception e) {
            Logger.w("delete user info failed" + e.getMessage());
        }
    }

    private static void saveLastUserName(Context context) {
        if (lastUserName == null || lastUserName.length() == 0) {
            return;
        }
        try {
            context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
                    .edit().putString(USER_PREF_LAST_KEY, lastUserName)
                    .apply();
        } catch (Exception e) {
            Logger.w("save last login userName failed "+ e.getMessage());
        }
    }

    public static User getUser() {
        if (mUser == null) {
            readFromPref(context);
        }
        return mUser;
    }

    public static void setUser(User user) {
        mUser = user;
    }

    public static boolean isLogin() {
        return !(getUser() == null || TextUtils.isEmpty(getUser().getUserId()));
    }

    public static boolean isUserCached(Context context) {
        return context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).contains(USER_PREF_KEY);
    }

    public static void logout() {
        setUser(null);
        deleteFromPref();
    }

    public static void saveToPref(Context context) {
        if (mUser != null) {
            try {
                String str = new Gson().toJson(mUser);
                String encryptStr = AESUtils.encrypt(BuildConfig.AESEncryptKey, str);
                Logger.d("加密后用户信息-->" + encryptStr);
                context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
                        .edit().putString(USER_PREF_KEY, encryptStr).apply();
            } catch (Exception e) {
                Logger.w("save user info failed"+ e.getMessage());
            }
            lastUserName = mUser.getMobile();
            saveLastUserName(context);
        }
    }

    public static String readLastUserName(Context context) {
        try {
            lastUserName = context.getSharedPreferences(USER_PREF,
                    Context.MODE_PRIVATE).getString(USER_PREF_LAST_KEY, "");
        } catch (Exception e) {
            Logger.w("read last login userName failed"+ e.getMessage());
        }
        return lastUserName;
    }

    public static void deleteLastUserName(Context context) {
        try {
            context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
                    .edit().remove(USER_PREF_LAST_KEY).apply();
        } catch (Exception e) {
            Logger.w("delete last login userName failed "+ e.getMessage());
        }
    }

    public static String getEntityId(Context context) {
        if (null != getUser()) {
            return getUser().getEntityId();
        } else {
            return null;
        }
    }

    private static boolean isUserInfoEncrypt(String userInfoStr) {
        if (!userInfoStr.contains("userId")) {
            return true;
        }
        return false;
    }

    //================================================================================
    // country code
    //================================================================================
    public static void saveCountryCode(Context context, String countryCode) {
        if (countryCode == null || countryCode.length() == 0) {
            return;
        }
        try {
            context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
                    .edit().putString(USER_PREF_LAST_COUNTRY_CODE, countryCode)
                    .apply();
        } catch (Exception e) {
            Logger.w("save last login country code failed "+ e.getMessage());
        }
    }

    public static String readCountryCode(Context context) {
        String countryCode = "";
        try {
            countryCode = context.getSharedPreferences(USER_PREF,
                    Context.MODE_PRIVATE).getString(USER_PREF_LAST_COUNTRY_CODE, "");
        } catch (Exception e) {
            Logger.w("read last login country code failed"+ e.getMessage());
        }
        return countryCode;
    }
}
