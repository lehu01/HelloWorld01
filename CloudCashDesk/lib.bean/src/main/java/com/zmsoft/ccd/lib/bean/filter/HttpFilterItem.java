package com.zmsoft.ccd.lib.bean.filter;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/16 11:24
 */
public class HttpFilterItem extends Base {

    private String name; // 筛选名称
    private String code; // 筛选code
    private String bizId; // 筛选业务id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }
}
