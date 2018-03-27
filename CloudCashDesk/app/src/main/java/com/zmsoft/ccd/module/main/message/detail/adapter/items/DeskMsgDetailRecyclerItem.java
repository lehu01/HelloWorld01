package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * 将桌位消息详情DeskMsgDetail对象重新拆分组装成自己recyclerView所需要的item，
 * 根据不同的itemType去区分itemView
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailRecyclerItem implements Serializable {
    /**
     * 类型
     *
     * @see ItemType
     */
    private int mItemType;
    private DeskMsgDetailOrderInfoRecyclerItem mOrderInfo;
    private DeskMsgDetailFoodsTitleRecyclerItem mFoodsTitle;
    private DeskMsgDetailComboFoodsTitleRecyclerItem mComboFoodsTitle;
    private DeskMsgDetailFoodsItemInfoRecyclerItem mFoodsItemInfo;
    private DeskMsgDetailComboFoodsItemInfoRecyclerItem mComboFoodsItemInfo;
    private DeskMsgDetailPayInfoRecyclerItem mPayInfoRecyclerItem;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        this.mItemType = itemType;
    }

    public DeskMsgDetailOrderInfoRecyclerItem getOrderInfo() {
        return mOrderInfo;
    }

    public void setOrderInfo(DeskMsgDetailOrderInfoRecyclerItem orderInfo) {
        this.mOrderInfo = orderInfo;
    }

    public DeskMsgDetailFoodsTitleRecyclerItem getFoodsTitle() {
        return mFoodsTitle;
    }

    public void setFoodsTitle(DeskMsgDetailFoodsTitleRecyclerItem foodsTitle) {
        this.mFoodsTitle = foodsTitle;
    }

    public DeskMsgDetailComboFoodsTitleRecyclerItem getComboFoodsTitle() {
        return mComboFoodsTitle;
    }

    public void setComboFoodsTitle(DeskMsgDetailComboFoodsTitleRecyclerItem comboFoodsTitle) {
        this.mComboFoodsTitle = comboFoodsTitle;
    }

    public DeskMsgDetailFoodsItemInfoRecyclerItem getFoodsItemInfo() {
        return mFoodsItemInfo;
    }

    public void setFoodsItemInfo(DeskMsgDetailFoodsItemInfoRecyclerItem foodsItemInfo) {
        this.mFoodsItemInfo = foodsItemInfo;
    }

    public DeskMsgDetailComboFoodsItemInfoRecyclerItem getComboFoodsItemInfo() {
        return mComboFoodsItemInfo;
    }

    public void setComboFoodsItemInfo(DeskMsgDetailComboFoodsItemInfoRecyclerItem comboFoodsItemInfo) {
        this.mComboFoodsItemInfo = comboFoodsItemInfo;
    }

    public DeskMsgDetailPayInfoRecyclerItem getPayInfoRecyclerItem() {
        return mPayInfoRecyclerItem;
    }

    public void setPayInfoRecyclerItem(DeskMsgDetailPayInfoRecyclerItem payInfoRecyclerItem) {
        mPayInfoRecyclerItem = payInfoRecyclerItem;
    }

    public static class ItemType {
        public static final int TYPE_ORDER_INFO = 1; //订单信息
        public static final int TYPE_FOOD_TITLE = 2; //普通菜头部
        public static final int TYPE_COMBO_FOOD_TITLE = 3; //套餐头部
        public static final int TYPE_FOOD_ITEM = 4; //普通菜item
        public static final int TYPE_COMBO_FOOD_ITEM = 5; //套餐item
        public static final int TYPE_PAY_ITEM = 6; //付款详情item
    }
}
