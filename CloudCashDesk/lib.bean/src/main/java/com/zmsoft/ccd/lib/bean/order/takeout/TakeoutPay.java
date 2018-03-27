package com.zmsoft.ccd.lib.bean.order.takeout;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:32
 *     desc  : 外卖支付记录
 * </pre>
 */
public class TakeoutPay {

    /**
     * payUserName : 小李
     * kindPayId : 999316125de4e9ed015de54c61e50047
     * payTime : 1502955080410
     * fee : 3000
     * name : 会员卡
     * id : 999316125de4e9ed015de54c61e50047
     * type : 3
     */

    private String payUserName;
    private String kindPayId;
    private long payTime;
    private int fee;
    private String name;
    private String id;
    private int type;

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
