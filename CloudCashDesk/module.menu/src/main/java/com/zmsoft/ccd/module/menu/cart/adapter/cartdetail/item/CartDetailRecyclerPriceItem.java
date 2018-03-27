package com.zmsoft.ccd.module.menu.cart.adapter.cartdetail.item;

/**
 * @author DangGui
 * @create 2017/4/19.
 */

public class CartDetailRecyclerPriceItem {
    /**
     * 菜名
     */
    private String foodName;
    /**
     * 辣椒指数
     */
    private int chili;
    /**
     * 推荐指数
     */
    private int recommendation;
    /**
     * 本店招牌
     */
    private String specialty;
    /**
     * 单价
     */
    private String price;
    /**
     * 单位
     */
    private String unit;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getChili() {
        return chili;
    }

    public void setChili(int chili) {
        this.chili = chili;
    }

    public int getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(int recommendation) {
        this.recommendation = recommendation;
    }

    public String isSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPrice() {
        return price == null ? "0" : price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
