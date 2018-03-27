package com.zmsoft.ccd.shop;

import com.zmsoft.ccd.shop.bean.IndustryType;

/**
 * Created by huaixi on 2017/11/13.
 */

public class IndustryTypeUtils {

    public static short getIndustryType(short type) {
        short resType = type;
        switch (type) {
            case IndustryType.CATERING:
                resType = 0;
                break;
            case IndustryType.RETAIL:
                resType = 1;
                break;
            default:
                break;
        }
        return resType;
    }
}
