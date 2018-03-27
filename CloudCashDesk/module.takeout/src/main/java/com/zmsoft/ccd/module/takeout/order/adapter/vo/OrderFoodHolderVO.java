package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/24.
 */

public class OrderFoodHolderVO extends BaseTakeoutHolderVO {

    private String foodName;
    private String foodSpecMake;
    private String foodNum;
    /**
     * 是否是套餐
     */
    private boolean isSuit;
    private double num;
    private String foodProperties;
    /**
     * 状态 3: 退菜
     */
    private int status;

    /**
     * 是否是双单位
     */
    private boolean isTwoAccount;

    /**
     * 条形码
     */
    private String  code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private List<OrderSubFoodHolderVO> children;

    public OrderFoodHolderVO(Takeout takeout) {
        super(takeout);
    }

    public List<OrderSubFoodHolderVO> getChildren() {
        return children;
    }

    public void setChildren(List<OrderSubFoodHolderVO> children) {
        this.children = children;
    }

    public boolean isSuit() {
        return isSuit;
    }

    public void setSuit(boolean suit) {
        isSuit = suit;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodSpecMake() {
        return foodSpecMake;
    }

    public void setFoodSpecMake(String foodSpecMake) {
        this.foodSpecMake = foodSpecMake;
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isTwoAccount() {
        return isTwoAccount;
    }

    public void setTwoAccount(boolean twoAccount) {
        isTwoAccount = twoAccount;
    }
}
