package com.zmsoft.ccd.lib.bean.print;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/4 15:22
 */
public class PrintAreaVo extends Base {


    /**
     * modifyUser :
     * modifyTime : 1499079942
     * createTime : 1499079942
     * paperWidth : 58
     * isValid : 1
     * rowNum : 32
     * ipAddress : 33.8.88.8
     * entityId : 99929645
     * createUser :
     * id : 999296455cf6468f015d07df8f470011
     * lastVer : 1
     */

    private String modifyUser;
    private int createTime;
    private int paperWidth;
    private int isValid;
    private int rowNum;
    private String ipAddress;
    private String entityId;
    private String createUser;
    private String id;
    private int lastVer;

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getPaperWidth() {
        return paperWidth;
    }

    public void setPaperWidth(int paperWidth) {
        this.paperWidth = paperWidth;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }
}
