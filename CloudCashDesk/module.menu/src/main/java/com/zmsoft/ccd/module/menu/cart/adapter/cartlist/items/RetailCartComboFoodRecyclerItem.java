package com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items;

import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;

/**
 * 套餐菜
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class RetailCartComboFoodRecyclerItem extends BaseCartRecyclerItem {
    /**
     * 该菜的原始信息
     */
    private ItemVo mItemVo;
    /**
     * 点菜人的头像
     */
    private String customerAvatarUrl;
    /**
     * 点菜人的姓名，最多显示20个字符，超过20个字符显示“...”，如果是云收银点的，则在姓名前面标记服务员
     */
    private String customerName;
    /**
     * 菜名
     */
    private String foodName;
    /**
     * 点的菜的数量
     */
    private Double num;
    /**
     * 结账数量
     */
    private Double accountNum;
    /**
     * 点菜单位
     */
    private String unit = "";
    /**
     * 结账单位
     */
    private String accountUnit = "";
    /**
     * 显示该商品所点数量的总价
     */
    private String foodPrice;
    /**
     * 商品如果有规格、做法、加料、备注，需要按照此顺序依次全部显示，中间用逗号隔开
     * ，加料需要显示数量（例如：＋香菜1份）。超过一行时换行显示。
     */
    private String makeMethod;
    /**
     * 是否有子菜
     */
    private boolean hasSubMenu;

    private OrderParam mCreateOrderParam;

    public ItemVo getItemVo() {
        return mItemVo;
    }

    public void setItemVo(ItemVo itemVo) {
        mItemVo = itemVo;
    }

    public String getCustomerAvatarUrl() {
        return customerAvatarUrl;
    }

    public void setCustomerAvatarUrl(String customerAvatarUrl) {
        this.customerAvatarUrl = customerAvatarUrl;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Double accountNum) {
        this.accountNum = accountNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAccountUnit() {
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getMakeMethod() {
        return makeMethod;
    }

    public void setMakeMethod(String makeMethod) {
        this.makeMethod = makeMethod;
    }

    public boolean isHasSubMenu() {
        return hasSubMenu;
    }

    public void setHasSubMenu(boolean hasSubMenu) {
        this.hasSubMenu = hasSubMenu;
    }

    public OrderParam getCreateOrderParam() {
        return mCreateOrderParam;
    }

    public void setCreateOrderParam(OrderParam createOrderParam) {
        mCreateOrderParam = createOrderParam;
    }
}
