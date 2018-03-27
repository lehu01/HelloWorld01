package com.zmsoft.ccd.lib.bean.user.unified.usershop;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/4 10:57.
 */

public class Shop extends Base {
    private String entityId;
    private String code;
    private String name;
    private Integer trialShop;      //是否为试用店铺 1:是 0:否

    public String getEntityId() {
        return entityId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getTrialShop() {
        return trialShop;
    }
}
