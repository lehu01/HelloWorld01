package com.ccd.lib.print.bean;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/28 14:40
 *     desc  : 标签打印配置
 * </pre>
 */
public class LabelPrinterConfig extends SmallTicketPrinterConfig {

    private String labelType; // 标签纸型号

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
}
