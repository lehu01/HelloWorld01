package com.ccd.lib.print.manager;

import android.content.Context;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;

import java.util.concurrent.atomic.AtomicReference;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 10:11
 *     desc  : 标签打印机管理
 * </pre>
 */
public class LabelPrintManager {

    private static final String LOG_TAG = LabelPrintManager.class.getSimpleName();
    private static final String PRINTER_SETTING_PREF = "printer_label_setting_pref";
    private static final String PRINTER_SETTING_PREF_KEY = "printer_label_setting_pref_key";
    private static AtomicReference<LabelPrinterConfig> mPrinterRef = new AtomicReference<>();

    public static LabelPrinterConfig getPrinterSetting() {
        if (mPrinterRef.get() == null) {
            readFromPref(GlobalVars.context);
        }
        if (mPrinterRef.get() == null) {
            return new LabelPrinterConfig();
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
            LabelPrinterConfig storeUser = new Gson().fromJson(str, LabelPrinterConfig.class);
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

    public static void setPrinterSetting(LabelPrinterConfig printerSetting) {
        mPrinterRef.set(printerSetting);
    }

}
