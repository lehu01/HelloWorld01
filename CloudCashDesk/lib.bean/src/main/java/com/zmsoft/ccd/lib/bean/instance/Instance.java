package com.zmsoft.ccd.lib.bean.instance;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 11:18
 */
public class Instance extends Base {

    private String name; // 菜名
    private String makeName; // 做法
    private double num; // 点菜数量
    private double accountNum; // 结账数量
    private double price; // 单价
    private int status; // 菜品状态。1表示未确认菜，2表示正常，3表示退菜标志，8表示分单
    private String unit; // 点菜单位
    private String accountUnit; // 结账单位
    private String taste; // 口味
    private String memo; // 备注
    private String kindMenuName; // 菜品分类名称
    private short kind; // 菜品分类id
    private String parentId; // 套餐id或者普通菜id
    private String customerId; // 点菜用户idk
    private String specDetailName; // 规格
    private double fee;// 金额
    private String menuId; // 菜肴id，固定的
    private short isBuyNumberChanged; // 双单位菜是否修改过重量。0表示未修改，1表示修改过。
    private String id; // 菜肴id=instanceId生成的
    private short isWait; // 是否是待菜
    private boolean isDoubleSwitch; // 本地添加[掌柜上是否开启双单位修改显示]
    private int isGive; // 是否能赠菜
    private int isChangePrice; // 是否能够修改价格
    private int isTwoAccount; // 是否是双单位商品
    private int optionalType; // 是否是必选菜
    private List<Instance> childInstanceList; // 子菜集合
    private String menuCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public short getKind() {
        return kind;
    }

    public void setKind(short kind) {
        this.kind = kind;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Instance> getChildInstanceList() {
        return childInstanceList;
    }

    public void setChildInstanceList(List<Instance> childInstanceList) {
        this.childInstanceList = childInstanceList;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public short getIsBuyNumberChanged() {
        return isBuyNumberChanged;
    }

    public void setIsBuyNumberChanged(short isBuyNumberChanged) {
        this.isBuyNumberChanged = isBuyNumberChanged;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getIsWait() {
        return isWait;
    }

    public void setIsWait(short isWait) {
        this.isWait = isWait;
    }

    public boolean isDoubleSwitch() {
        return isDoubleSwitch;
    }

    public void setDoubleSwitch(boolean doubleSwitch) {
        isDoubleSwitch = doubleSwitch;
    }

    public int getIsGive() {
        return isGive;
    }

    public void setIsGive(int isGive) {
        this.isGive = isGive;
    }

    public int getIsChangePrice() {
        return isChangePrice;
    }

    public void setIsChangePrice(int isChangePrice) {
        this.isChangePrice = isChangePrice;
    }

    public int getIsTwoAccount() {
        return isTwoAccount;
    }

    public void setIsTwoAccount(int isTwoAccount) {
        this.isTwoAccount = isTwoAccount;
    }

    public int getOptionalType() {
        return optionalType;
    }

    public void setOptionalType(int optionalType) {
        this.optionalType = optionalType;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }
}
