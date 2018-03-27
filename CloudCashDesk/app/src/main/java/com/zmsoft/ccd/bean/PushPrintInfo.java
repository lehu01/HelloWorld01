package com.zmsoft.ccd.bean;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/12 21:17
 *     desc  :
 * </pre>
 */
public class PushPrintInfo extends Base {

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
     * 打印type
     */
    private int documentType;
    /**
     * 区域打印ip地址，可为空
     */
    private String ip;
    /**
     * id
     */
    private String id;

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

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getOrderKind() {
        return orderKind;
    }

    public void setOrderKind(int orderKind) {
        this.orderKind = orderKind;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
