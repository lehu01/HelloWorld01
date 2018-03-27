package com.zmsoft.ccd.bean;

import java.util.List;

/**
 * type = 1226，自动审核打印
 */
public class PrintMessageContent {
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
     * 座位code
     */
    private String seatCode;

    /**
     * 订单类型
     */
    private int orderKind;

    /**
     * 打印数据
     */
    private List<PushPrintInfo> printTaskList;


    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
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

    public int getOrderKind() {
        return orderKind;
    }

    public void setOrderKind(int orderKind) {
        this.orderKind = orderKind;
    }

    public List<PushPrintInfo> getPrintTaskList() {
        return printTaskList;
    }

    public void setPrintTaskList(List<PushPrintInfo> printTaskList) {
        this.printTaskList = printTaskList;
    }
}
