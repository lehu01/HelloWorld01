package com.zmsoft.ccd.lib.bean.order.takeout;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/31 11:30
 *     desc  :
 * </pre>
 */
public class TakeoutPromotion {

    /**
     * mode : 3
     * fee : 1000
     * name : 会员卡优惠
     * id : e6e8f7a0aaec485ebc728994585d718e
     * type : 0
     * ratio : 85
     */

    private int mode;
    private int fee;
    private String name;
    private String id;
    private int type;
    private int ratio;

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
