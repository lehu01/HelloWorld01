package com.zmsoft.ccd.lib.bean;

import java.io.Serializable;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/6 11:54
 */
public class Base implements Serializable {

    /**
     * short true or false
     */
    public static final Short SHORT_TRUE = 1;
    public static final Short SHORT_FALSE = 0;
    /**
     * string true or false
     */
    public static final String STRING_TRUE = "1";
    public static final String STRING_FALSE = "0";
    /**
     * int true or false
     */
    public static final int INT_TRUE = 1;
    public static final int INT_FALSE = 0;

    private long modifyTime; // 服务端返回的修改时间

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
