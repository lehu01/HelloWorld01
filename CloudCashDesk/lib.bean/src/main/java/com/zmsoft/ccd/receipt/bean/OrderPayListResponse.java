package com.zmsoft.ccd.receipt.bean;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/3.
 */

public class OrderPayListResponse {
    /**
     * 云收银账单支付详情列表，可以为空
     */
    private List<Pay> pays;
    /**
     * 订单的收款模式，0表示普通收款，1表示快捷收款
     */
    private short collectPayMode;
    /**
     * 应收金额(分)
     */
    private int needFee;
    /**
     * 当有足够的付款金额时是否自动结账完毕
     */
    private boolean autoCheckout;

    /**
     * 是否是外卖，0表示不是，1表示是
     */
    private short takeout;

    public List<Pay> getPays() {
        return pays;
    }

    public void setPays(List<Pay> pays) {
        this.pays = pays;
    }

    public short getCollectPayMode() {
        return collectPayMode;
    }

    public void setCollectPayMode(short collectPayMode) {
        this.collectPayMode = collectPayMode;
    }

    public int getNeedFee() {
        return needFee;
    }

    public void setNeedFee(int needFee) {
        this.needFee = needFee;
    }

    public short getTakeout() {
        return takeout;
    }

    public void setTakeout(short takeout) {
        this.takeout = takeout;
    }

    public boolean isAutoCheckout() {
        return autoCheckout;
    }

    public void setAutoCheckout(boolean autoCheckout) {
        this.autoCheckout = autoCheckout;
    }
}
