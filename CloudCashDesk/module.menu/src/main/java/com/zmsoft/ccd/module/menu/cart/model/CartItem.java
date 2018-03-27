package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 修改购物车 传参bean
 *
 * @author DangGui
 * @create 2017/4/21.
 */
public class CartItem implements Serializable {
    /**
     * 如果是套餐，则会有这个子菜数组
     */
    private List<CartItem> childCartVos;
    /**
     * 菜肴的用户Id
     */
    private String uid;
    /**
     * 菜肴的menuId
     */
    private String menuId;
    /**
     * 菜肴的名称
     */
    private String menuName;
    /**
     * 做法Id
     */
    private String makeId;
    /**
     * 规格Id
     */
    private String specId;
    /**
     * 数量
     */
    private Double num;
    /**
     * 菜肴类型Id
     */
    private String kindMenuId;
    /**
     * 套菜子菜加价模式
     */
    private int addPriceMode;
    /**
     * 套菜子菜加价
     */
    private double addPrice;
    /**
     * 分组id
     */
    private String suitMenuDetailId;
    /**
     * 菜肴类型 1：普通菜 2：套菜 5：加料菜（只能在子child出现）
     */
    private int kindType;
    /**
     * 唯一ID标识 新版本用index替换 兼容老版本先保留
     */
    private String id;
    /**
     * 0:普通；1:模板点餐(云收银默认都是0)
     */
    private int addType;
    /**
     * 索引 标识唯一的菜
     */
    private String index;
    /**
     * 必选菜标识
     */
    // TODO: 2017/12/8 改回老接口，后续等ios使用一段时间后需要改回String
    //private String compulsory = "false";
    private boolean compulsory;
    /**
     * 是否赠送这个菜 0 不赠送，1 赠送
     */
    private short present;
    /**
     * 是否先不上菜，1表示暂不上菜，0表示立即上菜
     */
    private Short isWait = 0;
    /**
     * 页面来源
     * {@link "http://k.2dfire.net/pages/viewpage.action?pageId=285081713"}
     */
    private String source;

    /**
     * 标签
     */
    private Map<String, List<MemoLabel>> labels;

    private String memo;

    private double accountNum;
    private String accountUnit;

    /**
     * 双单位菜是否修改过 <br />
     * 因为服务端无法判断双单位菜的accountNum是否修改过，所以客户端需要在CartItem里
     * 增加一个标志位doubleUnitStatus来区分,表示双单位菜是否修改过 枚举
     * 0：未修改 1：修改过
     */
    private int doubleUnitStatus;

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
    }

    public List<CartItem> getChildCartVos() {
        return childCartVos;
    }

    public void setChildCartVos(List<CartItem> childCartVos) {
        this.childCartVos = childCartVos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getSuitMenuDetailId() {
        return suitMenuDetailId;
    }

    public void setSuitMenuDetailId(String suitMenuDetailId) {
        this.suitMenuDetailId = suitMenuDetailId;
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

    public int getKindType() {
        return kindType;
    }

    public void setKindType(int kindType) {
        this.kindType = kindType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isCompulsory() {
        return compulsory;
//        if (null == compulsory) return false;
//        return compulsory.equals("true");
    }

    public void setCompulsory(boolean compulsory) {
        this.compulsory = compulsory;
//        this.compulsory = compulsory ? "true": "false";
    }

//    public void setCompulsory(String compulsory) {
//        this.compulsory = compulsory;
//    }

    public short getPresent() {
        return present;
    }

    public void setPresent(short present) {
        this.present = present;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Short getIsWait() {
        return isWait;
    }

    public void setIsWait(Short isWait) {
        this.isWait = isWait;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(double accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountUnit() {
        return accountUnit;
    }

    public void setAccountUnit(String accountUnit) {
        this.accountUnit = accountUnit;
    }

    public int getDoubleUnitStatus() {
        return doubleUnitStatus;
    }

    public void setDoubleUnitStatus(int doubleUnitStatus) {
        this.doubleUnitStatus = doubleUnitStatus;
    }
}