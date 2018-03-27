package com.zmsoft.ccd.takeout.bean;

/**
 * @author DangGui
 * @create 2017/9/4.
 */

public class TakeoutCourier {

    private String id;
    /**
     * 配送平台编码
     * P000, 自配送,
     * P001, 顺丰配送
     * dada, 达达配送
     */
    private String platformCode;
    /**
     * 配送员姓名
     */
    private String name;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 配送费(元)，fee是null代表查询不到运费
     */
    private Double fee;

    private String errorMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
