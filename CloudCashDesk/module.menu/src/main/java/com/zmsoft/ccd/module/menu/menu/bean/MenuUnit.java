package com.zmsoft.ccd.module.menu.menu.bean;

/**
 * Description：点菜单位
 * <br/>
 * Created by kumu on 2017/4/17.
 */

public class MenuUnit {


    private long opTime;

    private long createTime;

    private String unitDesc;

    private int unitType;

    private int sortCode;

    private int lastVer;

    private int isValid;


    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
