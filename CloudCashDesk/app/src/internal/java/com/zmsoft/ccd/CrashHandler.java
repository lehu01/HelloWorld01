package com.zmsoft.ccd;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.UserHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 获取crash日志
 *
 * @author DangGui
 * @create 2017/6/23.
 */

public final class CrashHandler {
    private static String crashLogPath;
    private static final String CRASH_LOG_NAME = "ccd_crash_log.txt";
    private static final String BUGLY_KEY = "09a0fed0e6";

    public static void init() {
        intBugly();
//        if (null != GlobalVars.context.getExternalCacheDir()) {
//            crashLogPath = GlobalVars.context.getExternalCacheDir().getAbsolutePath()
//                    + File.separator + "ccd" + File.separator + "crashLog" + File.separator;
//        }
//        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

    private static void intBugly() {
        CrashReport.initCrashReport(GlobalVars.context, BUGLY_KEY, true);
        CrashReport.setIsDevelopmentDevice(GlobalVars.context, BuildConfig.DEBUG);
    }

    public static void updateCrashReportUserInfo(){
        CrashReport.putUserData(GlobalVars.context, "userId", UserHelper.getUserId());
        CrashReport.putUserData(GlobalVars.context, "entityId", UserHelper.getEntityId());
        CrashReport.putUserData(GlobalVars.context, "mobile", UserHelper.getMobile());
    }

    /**
     * 捕获错误信息的handler
     */
//    private static Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
//
//        @Override
//        public void uncaughtException(Thread thread, Throwable ex) {
//            String info = null;
//            ByteArrayOutputStream baos = null;
//            PrintStream printStream = null;
//            try {
//                baos = new ByteArrayOutputStream();
//                printStream = new PrintStream(baos);
//                ex.printStackTrace(printStream);
//                byte[] data = baos.toByteArray();
//                info = new String(data);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (printStream != null) {
//                        printStream.close();
//                    }
//                    if (baos != null) {
//                        baos.close();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            writeErrorLog(info);
//            ex.printStackTrace();
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//    };

    /**
     * 向文件中写入错误信息
     *
     * @param info
     */

    private static void writeErrorLog(String info) {
        if (TextUtils.isEmpty(crashLogPath)) {
            return;
        }
        File dir = new File(crashLogPath);
        if (!dir.exists()) {
            Logger.d("create crash file--->" + dir.mkdirs());
        }
        File file = new File(dir, getCurrentDateString() + CRASH_LOG_NAME);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(info.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    private static String getCurrentDateString() {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());
        Date nowDate = new Date();
        result = sdf.format(nowDate);
        result = result.replaceAll(" ", "_");
        return result.trim();
    }
}
