package com.zmsoft.ccd.lib.bean.user.unified.usershop;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/4 10:34.
 */

public class Employee extends Base {

    private interface SEX_INT {
        int UNKNOWN = 0;
        int MALE = 1;
        int FEMALE = 2;
    }

    private Integer sex;                // 0未知 1男 2女

    public String getSexString(String maleText, String femaleText, String unknownText) {
        if (null == sex) {
            return unknownText;
        }
        switch (sex) {
            case SEX_INT.MALE:
                return maleText;
            case SEX_INT.FEMALE:
                return femaleText;
            default:
                return unknownText;
        }
    }
}
