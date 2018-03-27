package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;
import java.util.List;

/**
 * 加料菜类对象
 *
 * @author DangGui
 * @create 2017/4/19.
 */
public class AdditionKindMenuVo implements Serializable {

    /**
     * 加料菜类ID
     */
    private String kindMenuId;

    /**
     * 加料菜类名称
     */
    private String kindMenuName;

    /**
     * 排序编码
     */
    private int sortCode;

    /**
     * 加料菜类下的菜列表
     */
    private List<AdditionMenuVo> additionMenuList;

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public List<AdditionMenuVo> getAdditionMenuList() {
        return additionMenuList;
    }

    public void setAdditionMenuList(List<AdditionMenuVo> additionMenuList) {
        this.additionMenuList = additionMenuList;
    }
}
