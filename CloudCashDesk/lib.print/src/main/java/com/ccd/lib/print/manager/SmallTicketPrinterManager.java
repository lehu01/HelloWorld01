package com.ccd.lib.print.manager;

import android.content.Context;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;

import java.util.concurrent.atomic.AtomicReference;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * Description：缓存网口或者蓝牙打印的数据，包括：打印类型，打印ip，打印店铺，打印字符等
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/6 15:15
 */
public class SmallTicketPrinterManager {

    private static final String LOG_TAG = SmallTicketPrinterManager.class.getSimpleName();
    private static final String PRINTER_SETTING_PREF = "printer_setting_pref";
    private static final String PRINTER_SETTING_PREF_KEY = "printer_setting_pref_key";
    private static AtomicReference<SmallTicketPrinterConfig> mPrinterRef = new AtomicReference<>();

    public static SmallTicketPrinterConfig getPrinterSetting() {
        if (mPrinterRef.get() == null) {
            readFromPref(GlobalVars.context);
        }
        if (mPrinterRef.get() == null) {
            return new SmallTicketPrinterConfig();
        }
        return mPrinterRef.get();
    }

    public static void saveToPref(Context context) {
        SmallTicketPrinterConfig printerSetting = mPrinterRef.get();
        if (printerSetting != null) {
            try {
                String str = new Gson().toJson(printerSetting);
                context.getSharedPreferences(PRINTER_SETTING_PREF, Context.MODE_PRIVATE)
                        .edit()
                        .putString(PRINTER_SETTING_PREF_KEY, str)
                        .apply();
            } catch (Exception e) {
                Logger.w(LOG_TAG + "save PrinterSetting info failed" + e.getMessage());
            }
        }
    }

    public static void readFromPref(Context context) {
        try {
            String str = context.getSharedPreferences(PRINTER_SETTING_PREF, Context.MODE_PRIVATE).getString(PRINTER_SETTING_PREF_KEY, "");
            SmallTicketPrinterConfig storeUser = new Gson().fromJson(str, SmallTicketPrinterConfig.class);
            if (storeUser == null) {
                deleteFromPref();
            } else {
                setPrinterSetting(storeUser);
            }
        } catch (Exception e) {
            Logger.w(LOG_TAG + "read PrinterSetting info failed" + e.getMessage());
        }
    }

    public static void deleteFromPref() {
        try {
            context.getSharedPreferences(PRINTER_SETTING_PREF, Context.MODE_PRIVATE)
                    .edit()
                    .remove(PRINTER_SETTING_PREF_KEY)
                    .apply();
        } catch (Exception e) {
            Logger.w(LOG_TAG + "delete PrinterSetting info failed" + e.getMessage());
        }
    }

    public static void setPrinterSetting(SmallTicketPrinterConfig printerSetting) {
        SmallTicketPrinterManager.mPrinterRef.set(printerSetting);
    }

}
