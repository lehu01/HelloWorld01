package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class KindVo implements Serializable {
    /**
     * 菜肴类型名称 冷菜 热菜
     */
    private String name;
    /**
     * 菜肴类型Id
     */
    private String kindMenuId;
    /**
     * 加菜流程 同类型菜肴已下单份数 key:menuName value:num
     */
    private Map<String, Integer> menuCounts = new TreeMap<>();//加菜流程 同类型菜肴已下单份数 menuName+num


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public Map<String, Integer> getMenuCounts() {
        return menuCounts;
    }

    public void setMenuCounts(Map<String, Integer> menuCounts) {
        this.menuCounts = menuCounts;
    }
}
