package com.zmsoft.ccd.module.main.home.adapter.items;

/**
 * 主页Item选项
 *
 * @author DangGui
 * @create 2017/8/2.
 */

public class HomeBizItem {
    /**
     * 名称
     */
    private String name;
    /**
     * 图标资源ID
     */
    private int iconRes;

    /**
     * 未读数量
     */
    private int unReadNum;

    /**
     * item类型
     *
     * @see ItemType
     */
    private int itemType;

    public HomeBizItem(String name, int iconRes, int itemType) {
        this.name = name;
        this.iconRes = iconRes;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public static class ItemType {
        public static final int ITEM_ACCOUNTED = 1; //已结账单
        public static final int ITEM_TAKE_OUT = 2; //外卖
        public static final int ITEM_HANG_UP_ORDER = 3; //挂起订单
        public static final int ITEM_ELECTRONIC_CASH_DETAIL = 4; //电子收款明细
        public static final int ITEM_FOODS_BALANCE = 5; //商品沽清
        public static final int ITEM_SHOPKEEPER = 6; //二维火掌柜
    }
}
