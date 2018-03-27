package com.zmsoft.ccd.lib.bean.filter;

import java.io.Serializable;
import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/16 11:31
 */
public class HttpFilter implements Serializable {

    private List<Filter> menuList;

    public List<Filter> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Filter> menuList) {
        this.menuList = menuList;
    }
}
