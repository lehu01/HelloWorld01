package com.zmsoft.lib.pos.newland.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/27 10:19
 *     desc  : 新大陆交易详情
 * </pre>
 */
public class NewLandPayDetail implements Serializable {

    private String systraceno; // 凭证号
    private String batchno; // 批次号

    public String getSystraceno() {
        return systraceno;
    }

    public void setSystraceno(String systraceno) {
        this.systraceno = systraceno;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }
}
