package com.zmsoft.ccd.lib.bean.register;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：注册会员
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/29 11:33
 */
public class RegisterMember extends Base {

    private String mobile;
    private String countryCode;
    private String id;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
