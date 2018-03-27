package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/19.
 */
public class MakeDataDto implements Serializable {
    /**
     * <code>烧法名称</code>.
     */
    private String name;
    /**
     * <code>烧法ID</code>.
     */
    private String makeId;
    /**
     * <code>烧法加价</code>.
     */
    private double makePrice;
    /**
     * <code>调价模式</code>.
     * <p>
     * 一次性加价：1
     * 每购买单位加价：2
     * 每结账单位加价：3
     * 不加价：0
     * </p>
     */
    private int makePriceMode;

    /**
     * 得到烧法名称.
     *
     * @return 烧法名称.
     */
    public String getName() {
        return name;
    }

    /**
     * 设置烧法名称.
     *
     * @param name 烧法名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到烧法ID.
     *
     * @return 烧法ID.
     */
    public String getMakeId() {
        return makeId;
    }

    /**
     * 设置烧法ID.
     *
     * @param makeId 烧法ID.
     */
    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    /**
     * 得到烧法加价.
     *
     * @return 烧法加价.
     */
    public double getMakePrice() {
        return makePrice;
    }

    /**
     * 设置烧法加价.
     *
     * @param makePrice 烧法加价.
     */
    public void setMakePrice(double makePrice) {
        this.makePrice = makePrice;
    }

    /**
     * 得到调价模式.
     *
     * @return 调价模式.
     */
    public int getMakePriceMode() {
        return makePriceMode;
    }

    /**
     * 设置调价模式.
     *
     * @param makePriceMode 调价模式.
     */
    public void setMakePriceMode(int makePriceMode) {
        this.makePriceMode = makePriceMode;
    }
}
