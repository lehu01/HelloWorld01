package com.zmsoft.lib.pos.newland.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/26 19:45
 *     desc  : 新大陆返回数据
 * </pre>
 */
public class NewLandResponse implements Serializable {

    private String msgTp; // 报文类型
    private String payType; // 支付方式
    private String sysTraceNo; // 微信支付宝：批次号+凭证号 或者 银行卡：交易凭证号
    private String amt; // 交易金额

    public String getMsgTp() {
        return msgTp;
    }

    public void setMsgTp(String msgTp) {
        this.msgTp = msgTp;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSysTraceNo() {
        return sysTraceNo;
    }

    public void setSysTraceNo(String sysTraceNo) {
        this.sysTraceNo = sysTraceNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
