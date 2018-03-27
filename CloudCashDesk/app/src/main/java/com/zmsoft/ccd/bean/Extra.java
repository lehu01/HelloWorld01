package com.zmsoft.ccd.bean;

/**
 * @author DangGui
 * @create 2017/7/8.
 */

public class Extra {
    private String sc;
    private int type;
    private String id;
    /**
     * 0为未处理
     */
    private String su;
    private String t;
    //推送消息唯一标示，用来消息排重
    private String mid;

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
