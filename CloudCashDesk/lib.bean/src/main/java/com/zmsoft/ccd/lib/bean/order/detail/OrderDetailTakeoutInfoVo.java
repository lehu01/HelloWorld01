package com.zmsoft.ccd.lib.bean.order.detail;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/4 15:11
 *     desc  : 外卖单信息
 * </pre>
 */
public class OrderDetailTakeoutInfoVo extends Base {
    /**
     * 配送方式
     * 2.自取
     */
    public static final int ONESELF = 2;

    /**
     * isThirdShipping : 2
     * address : 杭州拱墅区
     * phone : 13588263951
     * name : 王先生
     * sendTime : 0
     */

    private short isThirdShipping;
    private String address;
    private String phone;
    private String name;
    private long sendTime;

    public short getIsThirdShipping() {
        return isThirdShipping;
    }

    public void setIsThirdShipping(short isThirdShipping) {
        this.isThirdShipping = isThirdShipping;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
