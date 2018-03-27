package com.zmsoft.ccd.lib.widget.time;

import android.os.CountDownTimer;

/**
 * Description：倒计时
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/28 14:02
 */
public class TimeCountDown extends CountDownTimer {

    private TimeListener timeListener;

    public TimeCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (timeListener != null) {
            timeListener.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (timeListener != null) {
            timeListener.onFinish();
        }
    }

    public void setTimeListener(TimeListener timeListener) {
        this.timeListener = timeListener;
    }

    public interface TimeListener {

        void onTick(long nowTime);

        void onFinish();

    }
}
