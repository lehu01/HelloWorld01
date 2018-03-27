package com.zmsoft.ccd.lib.bean.print;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/12 16:30
 */
public class PrintTestVo extends Base {
    /**
     * 状态 1:待打印，2：已打印，3：打印失败
     */
    private short status;

    /**
     * 打印设备类型 1：云收银，2：云打印
     */
    private short type;

    /**
     * 传菜方案
     */
    private String pantryId;

    /**
     * 打印流文件地址
     */
    private String filePath;

    /**
     * 打印文件MD5
     */
    private String fileMd5;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 打印失败原因
     */
    private String memo;

    /**
     * 接收打印云收银用户ID
     */
    private String userId;

    /**
     * 操作人用户名
     */
    private String opUser;

    /**
     * 桌位码
     */
    private String seatCode;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getPantryId() {
        return pantryId;
    }

    public void setPantryId(String pantryId) {
        this.pantryId = pantryId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }
}
