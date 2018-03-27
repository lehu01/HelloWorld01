package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/25.
 */

public class SubmitOrderVo implements Serializable {
    /**
     * 下单时间
     */
    private long modifyTime;
    /**
     * 下单后生成的订单id
     */
    private String orderId;
    /**
     * 是否开桌，true表示是开桌表示加菜，请调用加菜下单接口，如果是false表示下单，服务端直接下单。
     */
    private boolean openTable;
    /**
     * 柜上是否开启了确认点菜完毕后切换到找单页面，true表示开启，false表示未开启。
     */
    private boolean turnToFindOrder;
    /**
     * 掌柜上是否开启了确认点菜完毕切换到收款界面
     */
    private boolean turnToCheckout;

    /**
     * 云收银本地下单或者加菜的菜ids
     */
    private List<String> instanceIds;

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isOpenTable() {
        return openTable;
    }

    public void setOpenTable(boolean openTable) {
        this.openTable = openTable;
    }

    public boolean isTurnToFindOrder() {
        return turnToFindOrder;
    }

    public void setTurnToFindOrder(boolean turnToFindOrder) {
        this.turnToFindOrder = turnToFindOrder;
    }

    public boolean isTurnToCheckout() {
        return turnToCheckout;
    }

    public void setTurnToCheckout(boolean turnToCheckout) {
        this.turnToCheckout = turnToCheckout;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }
}
