package com.zmsoft.ccd.module.main.message.detail.adapter.items;

import java.io.Serializable;

/**
 * 桌位消息详情——商品信息列表的item信息<br>
 * <p>
 * eg：
 * <pre>
 * 水煮花生
 * ￥8.00            1份
 * </pre>
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailFoodsItemInfoRecyclerItem implements Serializable {
    /**
     * 商品名字
     * 商品名称如果超过一行则显示……
     */
    private String name;
    /**
     * 单价
     */
    private String price;
    /**
     * 数量，数量+单位，如果是双单位商品，数量+点菜单位/重量单位
     */
    private String num;
    /**
     * 商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份。
     * 加料需展示每份加料的数量。
     * 依次显示规格、做法、加料、备注，用逗号隔开。
     */
    private String makeName;
    /**
     * 说明
     */
    private String memo;

    /**
     * 0/待发送；
     * 1/已发送待审核;
     * 2/下单超时;
     * 3/下单失败；
     * 9/下单成功
     */
    private short status;

    /**
     * 订单状态 eg: 待审核、已同意
     */
    private int orderStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
