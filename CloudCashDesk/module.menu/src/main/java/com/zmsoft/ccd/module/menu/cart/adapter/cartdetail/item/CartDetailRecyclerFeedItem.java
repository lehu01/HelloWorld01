package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerFeedItem {
    /**
     * 加料菜名
     */
    private String feedName;
    /**
     * 单价
     */
    private String price;
    /**
     * 单位
     */
    private double num;

    /**
     * 子菜的分类ID
     */
    private String categoryId;
    /**
     * 子菜的menuId
     */
    private String feedId;

    /**
     * 是否售完
     */
    private boolean isSoldOut = false;
    /**
     * 菜的唯一标识
     */
    private String index = "";

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
