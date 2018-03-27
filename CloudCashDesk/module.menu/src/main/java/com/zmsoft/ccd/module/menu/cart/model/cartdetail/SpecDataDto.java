package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/19.
 */
public class SpecDataDto implements Serializable {
    /**
     * <code>规格名称</code>.
     */
    private String name;
    /**
     * <code>规格项ID</code>.
     */
    private String specItemId;
    /**
     * <code>调价模式</code>.
     */
    private int priceMode;
    /**
     * <code>价格调价</code>.
     */
    private double priceScale;

    /**
     * 得到规格名称.
     *
     * @return 规格名称.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置规格名称.
     *
     * @param name 规格名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到规格项ID.
     *
     * @return 规格项ID.
     */
    public String getSpecItemId() {
        return specItemId;
    }

    /**
     * 设置规格项ID.
     *
     * @param specItemId 规格项ID.
     */
    public void setSpecItemId(String specItemId) {
        this.specItemId = specItemId;
    }

    /**
     * 得到调价模式.
     *
     * @return 调价模式.
     */
    public int getPriceMode() {
        return priceMode;
    }

    /**
     * 设置调价模式.
     *
     * @param priceMode 调价模式.
     */
    public void setPriceMode(int priceMode) {
        this.priceMode = priceMode;
    }

    /**
     * 得到价格调整比例.
     *
     * @return 价格调整比例.
     */
    public double getPriceScale() {
        return priceScale;
    }

    /**
     * 设置价格调整比例.
     *
     * @param priceScale 价格调整比例.
     */
    public void setPriceScale(double priceScale) {
        this.priceScale = priceScale;
    }
}
