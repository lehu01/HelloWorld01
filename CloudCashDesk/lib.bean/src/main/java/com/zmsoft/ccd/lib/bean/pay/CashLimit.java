package com.zmsoft.ccd.lib.bean.pay;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public class CashLimit {

    private double collectLimit;
    private double currAmount;

    public double getCollectLimit() {
        return collectLimit;
    }

    public void setCollectLimit(double collectLimit) {
        this.collectLimit = collectLimit;
    }

    public double getCurrAmount() {
        return currAmount;
    }

    public void setCurrAmount(double currAmount) {
        this.currAmount = currAmount;
    }

    public boolean hasExceedCashLimit() {
        return currAmount >= collectLimit;
    }

}
