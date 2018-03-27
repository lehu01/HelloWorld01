package com.zmsoft.ccd.module.receipt.receipt.adapter.items;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptRecyclerItem {
    /**
     * 类型
     *
     * @see ItemType
     */
    private int mItemType;

    /**
     * 收款头部（消费金额、服务费、打折等模块）
     */
    private ReceiptHeadRecyclerItem mHeadRecyclerItem;

    /**
     * 收款底部（收款方式）
     */
    private ReceiptMethodRecyclerItem mMethodRecyclerItem;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }

    public ReceiptHeadRecyclerItem getHeadRecyclerItem() {
        return mHeadRecyclerItem;
    }

    public void setHeadRecyclerItem(ReceiptHeadRecyclerItem headRecyclerItem) {
        mHeadRecyclerItem = headRecyclerItem;
    }

    public ReceiptMethodRecyclerItem getMethodRecyclerItem() {
        return mMethodRecyclerItem;
    }

    public void setMethodRecyclerItem(ReceiptMethodRecyclerItem methodRecyclerItem) {
        mMethodRecyclerItem = methodRecyclerItem;
    }

    public static class ItemType {
        //收款头部（消费金额、服务费、打折等模块）
        public static final int TYPE_HEAD = 1;
        //收款底部（收款方式）
        public static final int TYPE_RECEIPT_METHOD = 2;
        //收款底部补充view，如果付款方式为奇数个数时，最后会漏掉一个item
        public static final int TYPE_RECEIPT_EMPTY = 3;
    }
}
