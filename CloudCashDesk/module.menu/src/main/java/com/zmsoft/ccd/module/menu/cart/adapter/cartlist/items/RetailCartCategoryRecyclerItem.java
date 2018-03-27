package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items;

/**
 * 购物车类目头部（比如 购物车、必选商品、热菜、冷菜等）
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class RetailCartCategoryRecyclerItem extends BaseCartRecyclerItem {
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 备注
     */
    private String remark;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
