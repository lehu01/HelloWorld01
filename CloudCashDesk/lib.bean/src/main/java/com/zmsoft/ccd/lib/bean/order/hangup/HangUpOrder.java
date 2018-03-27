package com.zmsoft.ccd.lib.bean.order.hangup;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 11:35
 *     desc  : 挂单bean
 * </pre>
 */
public class HangUpOrder extends Base {

    /**
     * retainUserName : 人
     * price : 20
     * seatCode : code
     * itemNameList : ["菜"]
     * retaimTime : 1502091114
     */

    private String retainUserName;
    private double price;
    private String seatCode;
    private long retainTime;
    private List<String> itemNameList;

    public String getRetainUserName() {
        return retainUserName;
    }

    public void setRetainUserName(String retainUserName) {
        this.retainUserName = retainUserName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public long getRetainTime() {
        return retainTime;
    }

    public void setRetainTime(long retainTime) {
        this.retainTime = retainTime;
    }

    public List<String> getItemNameList() {
        return itemNameList;
    }

    public void setItemNameList(List<String> itemNameList) {
        this.itemNameList = itemNameList;
    }
}
