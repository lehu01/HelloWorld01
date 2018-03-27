package com.zmsoft.ccd.lib.bean.filter;

import java.io.Serializable;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/28 10:36
 */
public class Filter implements Serializable {

    private String code; // 筛选父code
    private String name; // 筛选父标题
    private List<HttpFilterItem> systemMenuItemVOList; // 筛选列表内容

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HttpFilterItem> getSystemMenuItemVOList() {
        return systemMenuItemVOList;
    }

    public void setSystemMenuItemVOList(List<HttpFilterItem> systemMenuItemVOList) {
        this.systemMenuItemVOList = systemMenuItemVOList;
    }
}
