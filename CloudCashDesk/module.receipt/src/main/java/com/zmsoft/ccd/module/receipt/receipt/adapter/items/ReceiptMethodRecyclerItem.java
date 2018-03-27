package com.zmsoft.ccd.module.receipt.receipt.adapter.items;

/**
 * 收款方式
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptMethodRecyclerItem {
    /**
     * 收款方式（比如 现金、银行卡、支付宝等）
     */
    private short method;
    /**
     * 方式name
     */
    private String name;
    /**
     * 支付类型id
     */
    private String kindPayId;

    public short getMethod() {
        return method;
    }

    public void setMethod(short method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKindPayId() {
        return kindPayId;
    }

    public void setKindPayId(String kindPayId) {
        this.kindPayId = kindPayId;
    }
}
