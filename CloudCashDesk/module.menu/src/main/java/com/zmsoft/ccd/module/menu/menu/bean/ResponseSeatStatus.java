package com.zmsoft.ccd.module.menu.menu.bean;

/**
 * Description：桌位状态
 * <br/>
 * Created by kumu on 2017/4/20.
 */

public class ResponseSeatStatus {

    /**
     * 1：未开桌
     */
    public static final int STATUS_UNUSED = 1;
    /**
     * 2：已开桌
     */
    public static final int STATUS_USED = 2;
    /**
     * 3：未清台
     */
    public static final int STATUS_UNCLEAR = 3;


    /**
     * 1：未开桌
     * 2：已开桌
     * 3：未清台
     * 若为1，则表示该桌没有下单
     *
     * @see ResponseSeatStatus#STATUS_UNUSED
     * @see ResponseSeatStatus#STATUS_USED
     * @see ResponseSeatStatus#STATUS_UNCLEAR
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
