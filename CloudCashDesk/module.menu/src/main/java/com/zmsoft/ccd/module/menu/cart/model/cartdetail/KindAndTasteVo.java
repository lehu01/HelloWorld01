package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;
import java.util.List;

/**
 * 口味，备注
 *
 * @author DangGui
 * @create 2017/4/21.
 */
public class KindAndTasteVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 口味分类ID
     */
    private String kindTasteId;

    /**
     * 口味分类名
     */
    private String kindTasteName;

    /**
     * 排序编码
     */
    private int sortCode;

    /**
     * 口味列表
     */
    private List<Taste> tasteList;

    public String getKindTasteId() {
        return kindTasteId;
    }

    public void setKindTasteId(String kindTasteId) {
        this.kindTasteId = kindTasteId;
    }

    public String getKindTasteName() {
        return kindTasteName;
    }

    public void setKindTasteName(String kindTasteName) {
        this.kindTasteName = kindTasteName;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public List<Taste> getTasteList() {
        return tasteList;
    }

    public void setTasteList(List<Taste> tasteList) {
        this.tasteList = tasteList;
    }
}
