package com.zmsoft.ccd.lib.bean.message;

import java.io.Serializable;

/**
 * 消息中心模块——消息，包括“关注的消息、外卖单消息、预点菜单消息、已处理消息、所有消息”
 *
 * @author DangGui
 * @create 2016/12/20.
 */

public class DeskMessage implements Serializable {


    private String id;
    /**
     * 消息类型，包括“服务铃、支付消息、加菜消息(非预付款的下单消息)、预付款消息”
     * 需处理的消息：
     * 1.加菜消息审核。如果5分钟内未对消息进行处理，则消息超时，变为已超时状态，且自动拒绝点菜。
     * 不需要处理的消息:
     * 1.服务铃
     * 2.预付款消息
     * 3.支付消息
     */
    private int ty;
    /**
     * 消息处理状态，包括“未处理、处理中、处理成功、已超时、处理失败”
     */
    private int su;
    /**
     * 处理结果
     */
    private String rm;
    /**
     * 创建时间,精确到小时和分钟
     */
    private long ct;
    /**
     * 修改时间
     */
    private long mt;
    /**
     * 桌位Code
     */
    private String sc;
    /**
     * 消息内容
     * 服务铃：根据小二发送过来的服务铃展示。
     * 支付消息：{支付方式}支付了{支付金额}元，如果有多种支付方式，用“，”隔开，是否付清。
     * 已付清，付款金额与账单的应收金额相同，则显示已付清。
     * 未付清，付款金额小于账单的应收金额，则显示未付清。
     * 加菜消息(非预付款的下单消息)：加菜，x个菜，数量以实际的为准。
     * 预付款消息：扫码下单，x个菜，{支付方式}支付{支付金额}元，如果有多种支付方式，用“，”隔开，是否付清，数量以实际的为准。
     * 已付清：已付清
     * 未付清：未付清
     */
    private String cot;//消息内容
    /**
     * 桌位名称
     */
    private String sn;
    private String orderCode;
    /**
     * orderId
     */
    private String b_id;
    /**
     * 标题
     */
    private String tl;

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

    public int getSu() {
        return su;
    }

    public void setSu(int su) {
        this.su = su;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getCot() {
        return cot;
    }

    public void setCot(String cot) {
        this.cot = cot;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getMt() {
        return mt;
    }

    public void setMt(long mt) {
        this.mt = mt;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }
}
