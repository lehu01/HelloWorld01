package com.zmsoft.ccd.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.helper.PushMsgDispatchHelper;
import com.zmsoft.missile.MissileConsoles;

/**
 * ProjectName:MissileSample
 * Created by Jiaozi
 * on 12/04/2017.
 */
public class TargetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d("TargetReceiver****" + intent.getStringExtra(MissileConsoles.EXTRA_BODY));
        PushMsgDispatchHelper.processTCPCustomMessage(context, intent.getStringExtra(MissileConsoles.EXTRA_BODY));
    }
}
