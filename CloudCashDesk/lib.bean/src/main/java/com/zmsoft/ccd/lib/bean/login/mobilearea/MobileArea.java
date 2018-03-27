package com.zmsoft.ccd.lib.bean.login.mobilearea;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/21 18:08.
 */

public class MobileArea extends Base {
    private String name;            // "中国"
    private String contryCode;      // "+86"
    private String eName;           // "CHINA"

    public MobileArea(String name, String number) {
        this.name = name;
        this.contryCode = number;
    }

    public String getArea() {
        return name;
    }

    public String getNumber() {
        return contryCode;
    }

    public String geteName() {
        return eName;
    }
}
