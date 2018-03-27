package com.zmsoft.ccd.lib.bean.instance;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/20 11:30
 */
public class PersonInstance extends Base {

    private String customerRegisterId; // 会员id
    private String customerName; // 会员姓名
    private String fileUrl; // 会员头像url
    private int instanceNum; // 点菜数量
    private long createTime; // 创建时间
    private int fromCustomer; // 点单是否来自小二/h5
    private List<Instance> instanceList; // 菜肴列表
    private String mobile; // 手机号码

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

    public List<Instance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
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
