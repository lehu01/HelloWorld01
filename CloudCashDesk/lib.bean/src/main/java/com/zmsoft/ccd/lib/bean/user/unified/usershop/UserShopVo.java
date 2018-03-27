package com.zmsoft.ccd.lib.bean.user.unified.usershop;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/4 10:30.
 */

public class UserShopVo extends Base {
    private Employee employee;
    private Shop shop;
    private User user;

    private Boolean hideChainShop;
    private String entityType;          // 实体类型 0单店 1连锁 2连锁下门店 3分公司

    public Employee getEmployee() {
        return employee;
    }

    public Shop getShop() {
        return shop;
    }

    public User getUser() {
        return user;
    }

    public Boolean getHideChainShop() {
        return hideChainShop;
    }

    public String getEntityType() {
        return entityType;
    }
}
