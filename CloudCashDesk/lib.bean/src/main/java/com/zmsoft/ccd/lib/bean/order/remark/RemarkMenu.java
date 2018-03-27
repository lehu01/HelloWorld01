package com.zmsoft.ccd.lib.bean.order.remark;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * 备注分组
 *
 * @email：danshen@2dfire.com
 * @time : 2017/4/15 13:50
 */
public class RemarkMenu extends Base {

    private String kindTasteName; // 口味分类名称
    private String kindTasteId; // 口味分类id
    private int sortCode; // 排序code
    private List<Memo> tasteList; // 口味列表

    public List<Memo> getTasteList() {
        return tasteList;
    }

    public void setTasteList(List<Memo> tasteList) {
        this.tasteList = tasteList;
    }

    public String getKindTasteName() {
        return kindTasteName;
    }

    public void setKindTasteName(String kindTasteName) {
        this.kindTasteName = kindTasteName;
    }

    public String getKindTasteId() {
        return kindTasteId;
    }

    public void setKindTasteId(String kindTasteId) {
        this.kindTasteId = kindTasteId;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

}
