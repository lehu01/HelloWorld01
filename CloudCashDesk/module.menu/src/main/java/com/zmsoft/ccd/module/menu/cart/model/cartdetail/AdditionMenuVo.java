package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;

/**
 * 加料菜对象
 *
 * @author DangGui
 * @create 2017/4/19.
 */
public class AdditionMenuVo implements Serializable {

    /**
     * 加料菜ID
     */
    private String menuId;

    /**
     * 加料菜名称
     */
    private String menuName;

    /**
     * 加料菜价格
     */
    private double menuPrice;

    /**
     * 是否售完
     */
    private boolean soldOut = false;

    /**
     * 上架状态
     */
    private int isOnShelf = 1;

    /**
     * 排序编码
     */
    private int sortCode;

    /**
     * 客户端加的字段，用来保存加料菜被点的数量
     */
    private double num;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }

    public int getIsOnShelf() {
        return isOnShelf;
    }

    public void setIsOnShelf(int isOnShelf) {
        this.isOnShelf = isOnShelf;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }
}
