package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items;

import java.io.Serializable;

/**
 * 将购物车对象重新拆分组装成自己recyclerView所需要的item，
 * 根据不同的itemType去区分itemView
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class CartRecyclerItem implements Serializable {
    /**
     * 类型
     *
     * @see ItemType
     */
    private int mItemType;
    /**
     * 购物车类目头部（比如 购物车、必选商品、热菜、冷菜等）
     */
    private CartCategoryRecyclerItem mCartCategoryRecyclerItem;
    /**
     * 订单信息
     */
    private CartOrderRecyclerItem mCartOrderRecyclerItem;
    /**
     * 普通菜
     */
    private CartNormalFoodRecyclerItem mCartNormalFoodRecyclerItem;
    /**
     * 套餐菜
     */
    private CartComboFoodRecyclerItem mCartComboFoodRecyclerItem;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        this.mItemType = itemType;
    }

    public CartCategoryRecyclerItem getCartCategoryRecyclerItem() {
        return mCartCategoryRecyclerItem;
    }

    public void setCartCategoryRecyclerItem(CartCategoryRecyclerItem cartCategoryRecyclerItem) {
        mCartCategoryRecyclerItem = cartCategoryRecyclerItem;
    }

    public CartOrderRecyclerItem getCartOrderRecyclerItem() {
        return mCartOrderRecyclerItem;
    }

    public void setCartOrderRecyclerItem(CartOrderRecyclerItem cartOrderRecyclerItem) {
        mCartOrderRecyclerItem = cartOrderRecyclerItem;
    }

    public CartNormalFoodRecyclerItem getCartNormalFoodRecyclerItem() {
        return mCartNormalFoodRecyclerItem;
    }

    public void setCartNormalFoodRecyclerItem(CartNormalFoodRecyclerItem cartNormalFoodRecyclerItem) {
        mCartNormalFoodRecyclerItem = cartNormalFoodRecyclerItem;
    }

    public CartComboFoodRecyclerItem getCartComboFoodRecyclerItem() {
        return mCartComboFoodRecyclerItem;
    }

    public void setCartComboFoodRecyclerItem(CartComboFoodRecyclerItem cartComboFoodRecyclerItem) {
        mCartComboFoodRecyclerItem = cartComboFoodRecyclerItem;
    }

    public static class ItemType {
        //购物车类目头部（比如 购物车、必选商品、热菜、冷菜等）
        public static final int TYPE_CATEGORY = 1;
        //订单信息
        public static final int TYPE_ORDER_INFO = 2;
        //普通菜
        public static final int TYPE_NORMAL_FOOD = 3;
        //套餐菜
        public static final int TYPE_COMBO_FOOD = 4;
    }
}
