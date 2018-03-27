package com.zmsoft.ccd.lib.bean.message;

import java.io.Serializable;
import java.util.List;

/**
 * 桌位消息详情——商品列表
 *
 * @author DangGui
 * @create 2016/12/22.
 */

public class DeskMsgDetailFood implements Serializable {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品名字
     * 商品名称如果超过一行则显示……
     */
    private String name;
    private String menuId;
    private String makeId;
    /**
     * 商品如果有做法、规格、备注、加料在商品的下一行显示，超过一行时换行显示，例如大杯，加冰，椰果1份。
     * 加料需展示每份加料的数量。
     * 依次显示规格、做法、加料、备注，用逗号隔开。
     */
    private String makeName;
    /**
     * 会员价格
     */
    private double makePrice;
    private short makePriceMode;
    private String specDetailName;
    private String specDetailId;
    private short specPriceMode;
    private double specDetailPrice;
    /**
     * 数量，数量+单位，如果是双单位商品，数量+点菜单位/重量单位
     */
    private double num;
    /**
     * 结账数量
     */
    private double accountNum;

    /**
     * 单位
     */
    private String unit;

    /**
     * 结账单位
     */
    private String accountUnit;

    /**
     * 说明
     */
    private String memo;
    private double originalPrice;

    /**
     * 单价
     */
    private double price;
    private double memberPrice;
    /**
     * 金额
     */
    private double fee;
    private double ratioFee;
    private double ratio;
    private short isRatio;

    /**
     * 1.普通菜
     * 2.套菜
     * 3.自定义菜
     * 4.自定义套菜
     * 5.加料菜
     *
     * @see com.zmsoft.ccd.lib.bean.message.DeskMsgDetail.FoodType
     */
    private short kind;
    private short priceMode;
    private String kindMenuId;
    private String taste;

    /**
     * 0/待发送；
     * 1/已发送待审核;
     * 2/下单超时;
     * 3/下单失败；
     * 9/下单成功
     *
     * @see com.zmsoft.ccd.lib.bean.message.DeskMsgDetail.FoodState
     */
    private short status;
    private String errorMsg;
    private String kindMenuName;
    private String parentId;
    private String childId;
    private short serviceFeeMode;
    private short serviceFee;
    private short isBackAuth;
    private int type;
    private String ext;
    /**
     * 套餐子菜列表
     */
    private List<DeskMsgDetailFoodItem> menuList;
    /**
     * 加菜列表(加料)
     */
    private List<DeskMsgDetailFoodItem> feedList;
    /**
     * 店铺实体id
     */
    private String entityId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public double getMakePrice() {
        return makePrice;
    }

    public void setMakePrice(double makePrice) {
        this.makePrice = makePrice;
    }

    public short getMakePriceMode() {
        return makePriceMode;
    }

    public void setMakePriceMode(short makePriceMode) {
        this.makePriceMode = makePriceMode;
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

    public short getSpecPriceMode() {
        return specPriceMode;
    }

    public void setSpecPriceMode(short specPriceMode) {
        this.specPriceMode = specPriceMode;
    }

    public double getSpecDetailPrice() {
        return specDetailPrice;
    }

    public void setSpecDetailPrice(double specDetailPrice) {
        this.specDetailPrice = specDetailPrice;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAccountUnit() {
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getRatioFee() {
        return ratioFee;
    }

    public void setRatioFee(double ratioFee) {
        this.ratioFee = ratioFee;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public short getIsRatio() {
        return isRatio;
    }

    public void setIsRatio(short isRatio) {
        this.isRatio = isRatio;
    }

    public short getKind() {
        return kind;
    }

    public void setKind(short kind) {
        this.kind = kind;
    }

    public short getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(short priceMode) {
        this.priceMode = priceMode;
    }

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public short getServiceFeeMode() {
        return serviceFeeMode;
    }

    public void setServiceFeeMode(short serviceFeeMode) {
        this.serviceFeeMode = serviceFeeMode;
    }

    public short getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(short serviceFee) {
        this.serviceFee = serviceFee;
    }

    public short getIsBackAuth() {
        return isBackAuth;
    }

    public void setIsBackAuth(short isBackAuth) {
        this.isBackAuth = isBackAuth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public List<DeskMsgDetailFoodItem> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<DeskMsgDetailFoodItem> menuList) {
        this.menuList = menuList;
    }

    public List<DeskMsgDetailFoodItem> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<DeskMsgDetailFoodItem> feedList) {
        this.feedList = feedList;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
