/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) 2011, ${year} 大米
 *
 * 工程名称：	item-soa
 * 创建者： 大米 创建日期： ${date}
 * 创建记录：	创建类结构。
 *
 **/
package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/4/19.
 */
public class Menu extends BaseMenu {
    /**
     * 菜类名称
     */
    private String kindMenuName;
    /**
     * path
     */
    private String filePath;

    /**
     * 是否关联做法 1有，0没有
     */
    private int hasMake;

    /**
     * 是否关联规格 1有，0没有
     */
    private int hasSpec;

    /**
     * 是否有加料菜 1有，0没有
     */
    private int hasAddition;

    /**
     * 图片路径，查attach表
     */
    private String imagePath;

    /**
     * <code>推荐指数对应的字段 0/不设定、1/推荐、2/十分推荐、3/强烈推荐</code>.
     */
    private int recommendLevel = 0;

    /**
     * 辣描述
     */
    private String acridLevelString;

    /**
     * 特色标签
     */
    private String specialTagString;

    /**
     * <code>商品特色标签Id对应的字段</code>.
     */
    private String specialTagId;

    /**
     * <code>标签来源对应的字段 1/系统标签、2/商户自定义标签</code>.
     */
    private int tagSource;

    /**
     * 是否指定：默认0
     * 可以根据值从大到小排序
     */
    private int showTop;

    /**
     * <code>是否售完</code>.
     */
    private Boolean isSoldOut = false;

    /**
     * <code>视频文件路径</code>.
     */
    private String videoFilePath;

    /**
     * <code>起点份数对应的字段 </code>.
     */
    private int startNum;

    /**
     * <code>基础价格模式 1:自动根据原料计算;2:手工价</code>.
     */
    private int basePriceMode = 1;

    /**
     * <code>外卖是否可点对应的字段</code>.
     */
    private int isTakeout;

    /**
     * <code>手工录入的成本价</code>.
     */
    private double basePrice;

    /**
     * 套菜子菜一份包含这个菜的数量
     */
    private double perNum = 1;
    /**
     * 套菜子菜加价模式
     */
    private int addPriceMode;
    /**
     * 套菜子菜加价
     */
    private double addPrice;
    /**
     * 套菜可换菜id
     */
    private String suitMenuChangeId;
    /**
     * 套菜子菜的做法列表--有做法，没有设置的话，默认选择第一个
     */
    private List<MakeDataDto> makeDataDtos;
    /**
     * 规格项--有规格，没有设置的话,默认选择第一个
     */
    private String specDetailName;
    /**
     * 规格id
     */
    private String specDetailId;

    /**
     * 仅套餐中显示
     */
    private boolean mealOnly;

    /**
     * 单位Id
     */
    private String unitId;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    private Menu packingBox;

    /**
     * 打包盒价格（兼容本店外卖前版本）
     */
    private double packingBoxPrice;

    /**
     * 打包盒个数
     */
    private int packingBoxNum;

    /**
     * 客户端加的字段，用来保存双单位菜的结账单位 被点的数量
     */
    private double accountNum;

    private String id;

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getIsTakeout() {
        return isTakeout;
    }

    public void setIsTakeout(int isTakeout) {
        this.isTakeout = isTakeout;
    }

    public int getBasePriceMode() {
        return basePriceMode;
    }

    public void setBasePriceMode(int basePriceMode) {
        this.basePriceMode = basePriceMode;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public String getVideoFilePath() {
        return videoFilePath;
    }

    public void setVideoFilePath(String videoFilePath) {
        this.videoFilePath = videoFilePath;
    }

    public Boolean getIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(Boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    public String getSpecialTagString() {
        return specialTagString;
    }

    public void setSpecialTagString(String specialTagString) {
        this.specialTagString = specialTagString;
    }

    public String getSpecialTagId() {
        return specialTagId;
    }

    public void setSpecialTagId(String specialTagId) {
        this.specialTagId = specialTagId;
    }

    public int getTagSource() {
        return tagSource;
    }

    public void setTagSource(int tagSource) {
        this.tagSource = tagSource;
    }

    public int getShowTop() {
        return showTop;
    }

    public void setShowTop(int showTop) {
        this.showTop = showTop;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(int recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    public String getAcridLevelString() {
        return acridLevelString;
    }

    public void setAcridLevelString(String acridLevelString) {
        this.acridLevelString = acridLevelString;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getHasMake() {
        return hasMake;
    }

    public void setHasMake(int hasMake) {
        this.hasMake = hasMake;
    }

    public int getHasSpec() {
        return hasSpec;
    }

    public void setHasSpec(int hasSpec) {
        this.hasSpec = hasSpec;
    }

    public double getPerNum() {
        return perNum;
    }

    public void setPerNum(double perNum) {
        if (0 == perNum) {
            return;
        }
        this.perNum = perNum;
    }

    public int getAddPriceMode() {
        return addPriceMode;
    }

    public void setAddPriceMode(int addPriceMode) {
        this.addPriceMode = addPriceMode;
    }

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }

    public String getSuitMenuChangeId() {
        return suitMenuChangeId;
    }

    public void setSuitMenuChangeId(String suitMenuChangeId) {
        this.suitMenuChangeId = suitMenuChangeId;
    }

    public List<MakeDataDto> getMakeDataDtos() {
        return makeDataDtos;
    }

    public void setMakeDataDtos(List<MakeDataDto> makeDataDtos) {
        this.makeDataDtos = makeDataDtos;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public String getSpecDetailId() {
        return specDetailId;
    }

    public void setSpecDetailId(String specDetailId) {
        this.specDetailId = specDetailId;
    }

    public int getHasAddition() {
        return hasAddition;
    }

    public void setHasAddition(int hasAddition) {
        this.hasAddition = hasAddition;
    }

    public boolean getMealOnly() {
        return mealOnly;
    }

    public void setMealOnly(boolean mealOnly) {
        this.mealOnly = mealOnly;
    }

    public Menu getPackingBox() {
        return packingBox;
    }

    public void setPackingBox(Menu packingBox) {
        this.packingBox = packingBox;
    }

    public int getPackingBoxNum() {
        return packingBoxNum;
    }

    public void setPackingBoxNum(int packingBoxNum) {
        this.packingBoxNum = packingBoxNum;
    }

    public double getPackingBoxPrice() {
        return packingBoxPrice;
    }

    public void setPackingBoxPrice(double packingBoxPrice) {
        this.packingBoxPrice = packingBoxPrice;
    }

    public Boolean getSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(Boolean soldOut) {
        isSoldOut = soldOut;
    }

    public boolean isMealOnly() {
        return mealOnly;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
