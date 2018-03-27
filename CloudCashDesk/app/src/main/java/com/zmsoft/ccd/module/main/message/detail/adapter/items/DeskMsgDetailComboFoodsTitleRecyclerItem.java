package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * 桌位消息详情——套餐信息列表的title信息<br>
 * <p>
 * eg：
 * <pre>
 * 合家套餐
 * ￥56.00            1份
 * </pre>
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailComboFoodsTitleRecyclerItem implements Serializable {
    /**
     * 商品名字
     * 商品名称如果超过一行则显示……
     */
    private String foodName;
    /**
     * 商品价格
     */
    private String foodPrice;
    /**
     * 数量，数量+单位，如果是双单位商品，数量+点菜单位/重量单位
     */
    private String foodNum;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(String foodNum) {
        this.foodNum = foodNum;
    }

}
