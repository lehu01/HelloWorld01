package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/24.
 */

public class OrderSubFoodHolderVO extends BaseTakeoutHolderVO {

    private String foodName;
    private String foodNum;
    private boolean showDivider;
    private double num;
    private String foodProperties;

    public OrderSubFoodHolderVO(Takeout takeout) {
        super(takeout);
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
    }

    public boolean isShowDivider() {
        return showDivider;
    }

    public void setShowDivider(boolean showDivider) {
        this.showDivider = showDivider;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getFoodProperties() {
        return foodProperties;
    }

    public void setFoodProperties(String foodProperties) {
        this.foodProperties = foodProperties;
    }
}
