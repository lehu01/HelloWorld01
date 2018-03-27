package com.zmsoft.ccd.module.takeout.delivery.adapter.items;

/**
 * @author DangGui
 * @create 2017/9/1.
 */

public class DeliveryOrderListItem {
    /**
     * ITEM类型
     *
     * @see com.zmsoft.ccd.module.takeout.delivery.adapter.DeliveryAdapter.ItemType
     */
    private int itemType;
    private DeliveryJagItem mDeliveryJagItem;
    private DeliveryOrderItem mDeliveryOrderItem;
    private DeliveryOrderPendingItem mPendingItem;
    private DeliverySelectAllItem mSelectAllItem;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public DeliveryJagItem getDeliveryJagItem() {
        return mDeliveryJagItem;
    }

    public void setDeliveryJagItem(DeliveryJagItem deliveryJagItem) {
        mDeliveryJagItem = deliveryJagItem;
    }

    public DeliveryOrderItem getDeliveryOrderItem() {
        return mDeliveryOrderItem;
    }

    public void setDeliveryOrderItem(DeliveryOrderItem deliveryOrderItem) {
        mDeliveryOrderItem = deliveryOrderItem;
    }

    public DeliveryOrderPendingItem getPendingItem() {
        return mPendingItem;
    }

    public void setPendingItem(DeliveryOrderPendingItem pendingItem) {
        mPendingItem = pendingItem;
    }

    public DeliverySelectAllItem getSelectAllItem() {
        return mSelectAllItem;
    }

    public void setSelectAllItem(DeliverySelectAllItem selectAllItem) {
        mSelectAllItem = selectAllItem;
    }
}
