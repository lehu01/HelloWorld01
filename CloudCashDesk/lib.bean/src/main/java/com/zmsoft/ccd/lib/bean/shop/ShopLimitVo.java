package com.zmsoft.ccd.lib.bean.shop;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/25 15:36
 *     desc  : 店铺试用或者正式使用
 * </pre>
 */
public class ShopLimitVo extends Base {

    //是否为试用店铺 1:是 0:否
    private int trialShop;
    private long expireTime;
    private long milliSecondsToExpire;

    public int getTrialShop() {
        return trialShop;
    }

    public void setTrialShop(int trialShop) {
        this.trialShop = trialShop;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getMilliSecondsToExpire() {
        return milliSecondsToExpire;
    }

    public void setMilliSecondsToExpire(long milliSecondsToExpire) {
        this.milliSecondsToExpire = milliSecondsToExpire;
    }
}
