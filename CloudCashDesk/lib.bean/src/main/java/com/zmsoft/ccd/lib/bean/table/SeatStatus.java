package com.zmsoft.ccd.lib.bean.table;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：座位状态
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/22 15:52
 */
public class SeatStatus extends Base {

    public static final int NO_OPEN_ORDER = 1; // 未开桌
    public static final int CREATE_ORDER = 2; // 已开桌
    public static final int NO_CLEAR_SEAT = 3; // 未清台

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
