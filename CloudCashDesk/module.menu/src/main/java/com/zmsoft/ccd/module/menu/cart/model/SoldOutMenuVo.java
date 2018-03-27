package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class SoldOutMenuVo implements Serializable {
    /**
     * 父菜名  加料菜估清时可能有多个菜里面点了加料菜  返回json串会被get方法覆盖  目前不处理有2分加料  点了4分  全部作为估清处理
     */
    private String parentsName;
    /**
     * 估清菜名
     */
    private String name;
    /**
     * 1:普通菜  2:套餐  5:加料菜
     */
    private int kind;
    private String menuId;
    /**
     * 估清的菜数量
     */
    private int num;
    /**
     * 菜价
     */
    private int price;

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
