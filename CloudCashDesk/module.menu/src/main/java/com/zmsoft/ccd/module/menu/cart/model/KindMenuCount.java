package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class KindMenuCount implements Serializable {
    /**
     * menuId集合
     */
    Set<String> menuSet;
    /**
     * 菜类的名字
     */
    String kindMenuName;
    /**
     * 数量
     */
    int count;

    public Set<String> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<String> menuSet) {
        this.menuSet = menuSet;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
