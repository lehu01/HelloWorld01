package com.zmsoft.ccd.module.main.message.takeout.adapter.items;

import java.io.Serializable;

/**
 * 外卖消息详情
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class TakeoutDetailRecyclerItem implements Serializable {
    /**
     * 类型
     *
     * @see ItemType
     */
    private int mItemType;
    private TakeoutDetailOrderInfoRecyclerItem mOrderInfo;
    private TakeoutDetailComboFoodsTitleRecyclerItem mComboFoodsTitle;
    private TakeoutDetailFoodsItemInfoRecyclerItem mFoodsItemInfo;
    private TakeoutDetailComboFoodsItemInfoRecyclerItem mComboFoodsItemInfo;
    private TakeoutDetailPayDetailRecyclerItem mTakeoutDetailPayDetailRecyclerItem;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        this.mItemType = itemType;
    }

    public TakeoutDetailOrderInfoRecyclerItem getOrderInfo() {
        return mOrderInfo;
    }

    public void setOrderInfo(TakeoutDetailOrderInfoRecyclerItem orderInfo) {
        this.mOrderInfo = orderInfo;
    }

    public TakeoutDetailComboFoodsTitleRecyclerItem getComboFoodsTitle() {
        return mComboFoodsTitle;
    }

    public void setComboFoodsTitle(TakeoutDetailComboFoodsTitleRecyclerItem comboFoodsTitle) {
        this.mComboFoodsTitle = comboFoodsTitle;
    }

    public TakeoutDetailFoodsItemInfoRecyclerItem getFoodsItemInfo() {
        return mFoodsItemInfo;
    }

    public void setFoodsItemInfo(TakeoutDetailFoodsItemInfoRecyclerItem foodsItemInfo) {
        this.mFoodsItemInfo = foodsItemInfo;
    }

    public TakeoutDetailComboFoodsItemInfoRecyclerItem getComboFoodsItemInfo() {
        return mComboFoodsItemInfo;
    }

    public void setComboFoodsItemInfo(TakeoutDetailComboFoodsItemInfoRecyclerItem comboFoodsItemInfo) {
        this.mComboFoodsItemInfo = comboFoodsItemInfo;
    }

    public TakeoutDetailPayDetailRecyclerItem getTakeoutDetailPayDetailRecyclerItem() {
        return mTakeoutDetailPayDetailRecyclerItem;
    }

    public void setTakeoutDetailPayDetailRecyclerItem(TakeoutDetailPayDetailRecyclerItem takeoutDetailPayDetailRecyclerItem) {
        mTakeoutDetailPayDetailRecyclerItem = takeoutDetailPayDetailRecyclerItem;
    }

    public static class ItemType {
        public static final int TYPE_ORDER_INFO = 1; //订单信息
        public static final int TYPE_COMBO_FOOD_TITLE = 2; //套餐头部
        public static final int TYPE_FOOD_ITEM = 3; //普通菜item
        public static final int TYPE_COMBO_FOOD_ITEM = 4; //套餐item
        public static final int TYPE_PAY_DETAIL = 5; //底部，支付明细
    }
}
