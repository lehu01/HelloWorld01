package com.zmsoft.ccd.lib.bean.scan;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：扫码桌位对象
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/7 11:41
 */
public class ScanSeat extends Base {

    public static final int TYPE_SZ = 0;
    public static final int TYPE_SD = 1;
    public static final int TYPE_SHOP = 2;
    public static final int TYPE_QUEUE = 3;
    public static final int TYPE_TAKEOUT = 4;
    public static final int TYPE_OTHER = -1;

    private int type;
    private String entityId;
    private String seatCode;
    private String globalCode;
    private String queueNo;
    private boolean isNewQueue;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getGlobalCode() {
        return globalCode;
    }

    public void setGlobalCode(String globalCode) {
        this.globalCode = globalCode;
    }

    public String getQueueNo() {
        return queueNo;
    }

    public void setQueueNo(String queueNo) {
        this.queueNo = queueNo;
    }

    public boolean isNewQueue() {
        return isNewQueue;
    }

    public void setNewQueue(boolean newQueue) {
        isNewQueue = newQueue;
    }
}
