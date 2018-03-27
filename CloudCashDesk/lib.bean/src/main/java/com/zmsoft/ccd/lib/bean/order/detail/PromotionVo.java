package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：优惠明细
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/16 15:39
 */
public class PromotionVo extends Base {

    public static final int VIP_PRICE = 1; // 会员价
    public static final int DISCOUNT_PLAN = 2; // 打折方案
    public static final int DISCOUNT = 3; // 打折

    private String id; // 优惠id
    private String name; // 优惠名称
    private String fee; // 优惠金额
    private int ratio; // 折扣率
    private int mode; // 优惠方式。1.使用会员价 2.使用打折方案 3.打折
    private int usePromotion;//是否使用优惠

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getUsePromotion() {
        return usePromotion;
    }

    public void setUsePromotion(int usePromotion) {
        this.usePromotion = usePromotion;
    }
}
