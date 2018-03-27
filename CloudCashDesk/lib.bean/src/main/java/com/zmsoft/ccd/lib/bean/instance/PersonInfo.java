package com.zmsoft.ccd.lib.bean.instance;

import java.io.Serializable;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 15:26
 */
public class PersonInfo implements Serializable {

    private String customerRegisterId; // 会员id
    private String customerName; // 会员姓名
    private String fileUrl; // 会员头像url
    private int instanceNum; // 点菜数量
    private long createTime; // 创建时间
    private int fromCustomer; // 点单是否来自小二/h5
    private String mobile;

    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getInstanceNum() {
        return instanceNum;
    }

    public void setInstanceNum(int instanceNum) {
        this.instanceNum = instanceNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getFromCustomer() {
        return fromCustomer;
    }

    public void setFromCustomer(int fromCustomer) {
        this.fromCustomer = fromCustomer;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
