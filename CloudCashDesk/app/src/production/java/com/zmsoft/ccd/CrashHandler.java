package com.zmsoft.ccd;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.UserHelper;

import io.fabric.sdk.android.Fabric;

/**
 * @author DangGui
 * @create 2017/6/23.
 */

public final class CrashHandler {

    public static void init() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder().build();
        Answers answers = new Answers();
        Fabric.with(new Fabric.Builder(GlobalVars.context)
                .kits(crashlyticsKit, answers)
                .build());
    }

    public static void updateCrashReportUserInfo(){
        if ( null == Crashlytics.getInstance()) {
            Logger.d("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
            return;
        }
        Crashlytics.setUserIdentifier(UserHelper.getUserId());
        Crashlytics.setUserEmail(UserHelper.getEntityId());
        Crashlytics.setUserName(UserHelper.getMobile());
    }
}
