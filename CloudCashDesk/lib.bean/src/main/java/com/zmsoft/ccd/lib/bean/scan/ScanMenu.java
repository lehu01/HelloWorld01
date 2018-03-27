package com.zmsoft.ccd.lib.bean.scan;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：扫码菜肴对象
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/7 11:41
 */
public class ScanMenu extends Base {

    private String entityId;
    private String menuId;
    private String shortUrl;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
