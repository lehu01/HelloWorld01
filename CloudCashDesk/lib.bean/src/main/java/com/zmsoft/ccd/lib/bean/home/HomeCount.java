package com.zmsoft.ccd.lib.bean.home;

/**
 * @author DangGui
 * @create 2017/8/28.
 */

public class HomeCount {
    /**
     * 外卖单数量
     */
    private int takeOutOrderCount;
    /**
     * 挂单数量
     */
    private int retainOrderCount;

    public int getTakeOutOrderCount() {
        return takeOutOrderCount;
    }

    public void setTakeOutOrderCount(int takeOutOrderCount) {
        this.takeOutOrderCount = takeOutOrderCount;
    }

    public int getRetainOrderCount() {
        return retainOrderCount;
    }

    public void setRetainOrderCount(int retainOrderCount) {
        this.retainOrderCount = retainOrderCount;
    }
}
