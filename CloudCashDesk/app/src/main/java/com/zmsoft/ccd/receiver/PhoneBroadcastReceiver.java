package com.zmsoft.ccd.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.zmsoft.ccd.data.source.carryout.TakeoutRemoteSource;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ProjectName:Ccd
 * Created by Jiaozi
 * on 08/05/2017.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {

    private TakeoutRemoteSource mRemoteSource;

    public PhoneBroadcastReceiver() {
        mRemoteSource = new TakeoutRemoteSource();
    }

    public void sendCallPhoneToServer(Context context, String incomingNumber) {

        if (TextUtils.isEmpty(incomingNumber)) {
            return;
        }
        if (incomingNumber.length() <= 5) {
            return;
        }

        if (!ConfigHelper.getReceiveCarryoutPhoneSetting(context)) {
            return;
        }

        mRemoteSource.sendTakeoutMobile(UserHelper.getEntityId(), incomingNumber)
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean ok) {
                        Log.e("PhoneBroadcastReceiver", "来电号码发送：" + ok);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果是来电
        TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Service.TELEPHONY_SERVICE);
        String incomingNumber = intent.getStringExtra("incoming_number");
        Log.e("PhoneBroadcastReceiver", incomingNumber + ":" + tManager.getCallState());
        //电话的状态
        switch (tManager.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
                //等待接听状态
                sendCallPhoneToServer(context, incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //接听状态
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //挂断状态
                break;
        }
    }
}
