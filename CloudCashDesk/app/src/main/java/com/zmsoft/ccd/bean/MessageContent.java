package com.zmsoft.ccd.bean;

public class MessageContent {
    private String configContent;
    private String picFullPath;
    private String name;
    //操作人userId
    private String opUserId;

    public String getConfigContent() {
        return configContent;
    }

    public void setConfigContent(String configContent) {
        this.configContent = configContent;
    }

    public String getPicFullPath() {
        return picFullPath;
    }

    public void setPicFullPath(String picFullPath) {
        this.picFullPath = picFullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }
}