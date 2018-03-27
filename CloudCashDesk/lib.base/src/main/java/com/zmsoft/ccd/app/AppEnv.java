package com.zmsoft.ccd.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.dfire.mobile.config.security.SecurityConfig;
import com.zmsoft.ccd.lib.base.helper.EnvSpHelper;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * Description：环境管理
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/2 10:33
 */
public final class AppEnv {

    public static final String MISSILE_KEY = "Z2Wb6LkoVUWq1wPixmUO";
    public static final String MISSILE_SECRET = "zagj6GYiuu7sLkYj";

    public static final int DEV = 1;
    public static final int PRE = -1;
    public static final int PUB = 0;
    public static final int DAILY = 2;
    public static final int CUSTOM = 3;

    private final static String KEY_API_ENV = "key_api_env";
    private static final String GATE_WAY_URL_DAILY = "http://gateway.2dfire-daily.com/";
    private static final String GATE_WAY_URL_PRE = "https://gateway.2dfire-pre.com/";
    private static final String GATE_WAY_URL_PUB = "https://gateway.2dfire.com/";
    public static final String GATE_WAY_URL_PUB_PING = "gateway.2dfire.com";

    private static final String GW_ENV_DEV = "fe9c41c9fff6417380392ae4017ba847";
    private static final String GW_ENV_DAILY = "daily";
    private static final String GW_ENV_PRE = "pre";
    private static final String GW_ENV_PUB = "publish";

    private static final String MISSILE_API_DEV = "missile.2dfire-daily.com";
    private static final short MISSILE_PORT_DEV = 10443;
    private static final String MISSILE_API_PRE = "missilegw.2dfire-pre.com";
    private static final short MISSILE_PORT_PRE = 443;
    private static final String MISSILE_API_PUB = "missilegate.2dfire.com";
    private static final short MISSILE_PORT_PUB = 443;

    private static final String CASH_BASE_API_DAILY = "http://api.2dfire-daily.com/cash-api";
    private static final String CASH_BASE_API_PRE = "https://api.2dfire-pre.com/cash-api";
    private static final String CASH_BASE_API_PUB = "https://api.2dfire.com/cash-api";
    private static int sEnv = DEV;
    private static String sCurrentChannel;
    private static String sBuildFlavor;
    private static String sBuildType;

    private AppEnv() {
    }

    public static int getEnv() {
        return sEnv;
    }

    public static void init(String buildFlavor, String buildType) {
        sBuildFlavor = buildFlavor;
        sBuildType = buildType;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        sEnv = prefs.getInt(KEY_API_ENV, DEV);
        if ("preview".equalsIgnoreCase(buildType)) {
            sEnv = PRE;
        } else if ("release".equalsIgnoreCase(buildType)) {
            sEnv = PUB;
        }
    }

    public static void switchTo(Context context, int env) {
        sEnv = env;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(KEY_API_ENV, AppEnv.sEnv).apply();
    }

    public static String getGateWayUrl() {
        if (isProduction()) {
            return GATE_WAY_URL_PUB;
        }
        int env = getEnv();
        if (env == PUB) {
            return GATE_WAY_URL_PUB;
        } else if (env == PRE) {
            return GATE_WAY_URL_PRE;
        } else if (env == DAILY || env == DEV || env == CUSTOM) {
            return GATE_WAY_URL_DAILY;
        }
        return GATE_WAY_URL_DAILY;
    }

    public static String getCashApiBaseUrl() {
        if (isProduction()) {
            return CASH_BASE_API_PUB;
        }
        int env = getEnv();
        if (env == PUB) {
            return CASH_BASE_API_PUB;
        } else if (env == PRE) {
            return CASH_BASE_API_PRE;
        } else if (env == DAILY || env == DEV || env == CUSTOM) {
            return CASH_BASE_API_DAILY;
        }
        return CASH_BASE_API_DAILY;
    }

    public static String getGateWayEnv() {
        if (isProduction()) {
            return GW_ENV_PUB;
        }
        int env = getEnv();
        if (env == PUB) {
            return GW_ENV_PUB;
        } else if (env == PRE) {
            return GW_ENV_PRE;
        } else if (env == DEV) {
            return GW_ENV_DEV;
        } else if (env == DAILY) {
            return GW_ENV_DAILY;
        } else if (env == CUSTOM) {
            return EnvSpHelper.getCustomEnv(GlobalVars.context);
        }
        return GW_ENV_DAILY;
    }

    public static String getApiKey() {
        return SecurityConfig.getAppKey();
    }

    public static String getApiSecret() {
        return SecurityConfig.getAppSecret();
    }

    public static String getMissileApi() {
        if (isProduction()) {
            return MISSILE_API_PUB;
        }
        int env = getEnv();
        if (env == PUB) {
            return MISSILE_API_PUB;
        } else if (env == PRE) {
            return MISSILE_API_PRE;
        } else if (env == DAILY || env == DEV || env == CUSTOM) {
            return MISSILE_API_DEV;
        }
        return MISSILE_API_PUB;
    }

    public static short getMissilePort() {
        if (isProduction()) {
            return MISSILE_PORT_PUB;
        }
        int env = getEnv();
        if (env == PUB) {
            return MISSILE_PORT_PUB;
        } else if (env == PRE) {
            return MISSILE_PORT_PRE;
        } else if (env == DAILY || env == DEV || env == CUSTOM) {
            return MISSILE_PORT_DEV;
        }
        return MISSILE_PORT_PUB;
    }

    public static String getGateWayPing() {
        return GATE_WAY_URL_PUB_PING;
    }

    public static boolean isProduction() {
        return "Production".equalsIgnoreCase(sBuildFlavor);
    }

    public static String getCurrentChannel() {
        return TextUtils.isEmpty(sCurrentChannel) ? "2dfire" : sCurrentChannel;
    }

    static void setCurrentChannel(String currentChannel) {
        sCurrentChannel = currentChannel;
    }
}
