package com.zmsoft.ccd.lib.bean.shop.request;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/26 15:48
 *     desc  :
 * </pre>
 */
public class BindShopBaseRequest extends Base {

    private String base_param;
    private String deviceId;
    private String appKey;
    private String sBR;
    private String sOS;
    private String sApv;

    public String getBase_param() {
        return base_param;
    }

    public void setBase_param(String base_param) {
        this.base_param = base_param;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getsBR() {
        return sBR;
    }

    public void setsBR(String sBR) {
        this.sBR = sBR;
    }

    public String getsOS() {
        return sOS;
    }

    public void setsOS(String sOS) {
        this.sOS = sOS;
    }

    public String getsApv() {
        return sApv;
    }

    public void setsApv(String sApv) {
        this.sApv = sApv;
    }
}
