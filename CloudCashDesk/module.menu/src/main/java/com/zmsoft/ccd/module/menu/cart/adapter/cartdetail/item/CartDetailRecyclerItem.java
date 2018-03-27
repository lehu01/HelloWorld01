package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

import java.io.Serializable;

/**
 * 将购物车对象重新拆分组装成自己recyclerView所需要的item，
 * 根据不同的itemType去区分itemView
 *
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerItem implements Serializable {
    /**
     * 类型
     *
     * @see ItemType
     */
    private int mItemType;

    /**
     * 商品详情页分类
     *
     * @see com.zmsoft.ccd.module.menu.helper.CartHelper.FoodDetailType
     */
    private int mDetailType;
    /**
     * 购物车单价
     */
    private CartDetailRecyclerPriceItem mCartDetailRecyclerPriceItem;
    /**
     * 购物车重量
     */
    private CartDetailRecyclerWeightItem mCartDetailRecyclerWeightItem;
    /**
     * 购物车规格、做法
     */
    private CartDetailRecyclerChoiceItem mCartDetailRecyclerChoiceItem;
    /**
     * 购物车分类
     */
    private CartDetailRecyclerCategoryItem mCartDetailRecyclerCategoryItem;
    /**
     * 购物车加料菜子菜
     */
    private CartDetailRecyclerFeedItem mCartDetailRecyclerFeedItem;
    /**
     * 购物车备注
     */
    private CartDetailRecyclerRemarkItem mCartDetailRecyclerRemarkItem;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }

    public int getDetailType() {
        return mDetailType;
    }

    public void setDetailType(int detailType) {
        mDetailType = detailType;
    }

    public CartDetailRecyclerChoiceItem getCartDetailRecyclerChoiceItem() {
        return mCartDetailRecyclerChoiceItem;
    }

    public void setCartDetailRecyclerChoiceItem(CartDetailRecyclerChoiceItem cartDetailRecyclerChoiceItem) {
        mCartDetailRecyclerChoiceItem = cartDetailRecyclerChoiceItem;
    }

    public CartDetailRecyclerPriceItem getCartDetailRecyclerPriceItem() {
        return mCartDetailRecyclerPriceItem;
    }

    public void setCartDetailRecyclerPriceItem(CartDetailRecyclerPriceItem cartDetailRecyclerPriceItem) {
        mCartDetailRecyclerPriceItem = cartDetailRecyclerPriceItem;
    }

    public CartDetailRecyclerWeightItem getCartDetailRecyclerWeightItem() {
        return mCartDetailRecyclerWeightItem;
    }

    public void setCartDetailRecyclerWeightItem(CartDetailRecyclerWeightItem cartDetailRecyclerWeightItem) {
        mCartDetailRecyclerWeightItem = cartDetailRecyclerWeightItem;
    }

    public CartDetailRecyclerCategoryItem getCartDetailRecyclerCategoryItem() {
        return mCartDetailRecyclerCategoryItem;
    }

    public void setCartDetailRecyclerCategoryItem(CartDetailRecyclerCategoryItem cartDetailRecyclerCategoryItem) {
        mCartDetailRecyclerCategoryItem = cartDetailRecyclerCategoryItem;
    }

    public CartDetailRecyclerFeedItem getCartDetailRecyclerFeedItem() {
        return mCartDetailRecyclerFeedItem;
    }

    public void setCartDetailRecyclerFeedItem(CartDetailRecyclerFeedItem cartDetailRecyclerFeedItem) {
        mCartDetailRecyclerFeedItem = cartDetailRecyclerFeedItem;
    }

    public CartDetailRecyclerRemarkItem getCartDetailRecyclerRemarkItem() {
        return mCartDetailRecyclerRemarkItem;
    }

    public void setCartDetailRecyclerRemarkItem(CartDetailRecyclerRemarkItem cartDetailRecyclerRemarkItem) {
        mCartDetailRecyclerRemarkItem = cartDetailRecyclerRemarkItem;
    }

    public static class ItemType {
        //购物车详情单价ITEM
        public static final int TYPE_PRICE = 1;
        //购物车详情重量
        public static final int TYPE_WEIGHT = 2;
        //购物车详情选择项（比如 规格、做法等）
        public static final int TYPE_CHOICE = 3;
        //购物车详情分类ITEM
        public static final int TYPE_CATEGORY = 4;
        //购物车详情加料菜
        public static final int TYPE_FEED = 5;
        //购物车详情备注
        public static final int TYPE_REMARK = 6;
    }
}
