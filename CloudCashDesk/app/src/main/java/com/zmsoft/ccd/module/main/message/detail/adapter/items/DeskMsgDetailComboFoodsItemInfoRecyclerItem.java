package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * **
 * 桌位消息详情——套餐信息列表的item信息<br>
 * <p>
 * eg：
 * <pre>
 * 扇贝
 * 大份，加辣          x1       已下厨
 * </pre>
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailComboFoodsItemInfoRecyclerItem implements Serializable {

    /**
     * 套餐商品item的名字
     */
    private String foodName;
    /**
     * 套餐商品item的数量
     */
    private String foodNum;
    /**
     * 套餐商品item的备注
     * eg：大份，加辣
     */
    private String foodRemark;

    /**
     * 0/待发送；
     * 1/已发送待审核;
     * 2/下单超时;
     * 3/下单失败；
     * 9/下单成功
     */
    private short status;

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

    public String getFoodRemark() {
        return foodRemark;
    }

    public void setFoodRemark(String foodRemark) {
        this.foodRemark = foodRemark;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
