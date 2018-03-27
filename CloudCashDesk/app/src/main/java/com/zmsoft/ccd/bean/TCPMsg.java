package com.zmsoft.ccd.bean;

/**
 * TCP通道推送消息格式
 */
public class TCPMsg {
    private String sc;
    private int ty;
    private String id;
    /**
     * 0为未处理
     */
    private String su;
    private String t;
    //推送消息唯一标示，用来消息排重
    private String mid;
    private String cot;
    /**
     * title
     */
    private String tl;

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
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

    public String getCot() {
        return cot;
    }

    public void setCot(String cot) {
        this.cot = cot;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }
}