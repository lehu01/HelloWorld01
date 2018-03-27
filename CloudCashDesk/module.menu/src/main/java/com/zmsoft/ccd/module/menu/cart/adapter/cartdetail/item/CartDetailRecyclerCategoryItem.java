package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerCategoryItem {
    /**
     * 分类抬头名称（加料、备注等）
     */
    private String categoryHeaderName;
    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类所属的类型，该字段纯粹是为了绘制UI时，控制需不需要展示分割线，
     * 如果categoryType是TYPE_REMARK == 6，即属于备注模块，则不展示分割线
     *
     * @see com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item.CartDetailRecyclerItem.ItemType
     */
    private int categoryType;

    public String getCategoryHeaderName() {
        return categoryHeaderName;
    }

    public void setCategoryHeaderName(String categoryHeaderName) {
        this.categoryHeaderName = categoryHeaderName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

}
