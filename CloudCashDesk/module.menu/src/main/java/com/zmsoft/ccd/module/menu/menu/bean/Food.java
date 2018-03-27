package com.zmsoft.ccd.module.menu.menu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description：菜实体
 * <br/>
 * Created by kumu on 2017/4/7.
 */

public class Food implements Parcelable {

    /**
     * 数据来源标记
     */
    private int ownerType;

    /**
     * 菜品名称
     */
    private String name;

    /**
     * 口味
     */
    private String taste;

    /**
     * 点菜单位
     */
    private String buyAccount;

    /**
     * 结账单位
     */
    private String account;

    /**
     * 价格
     */
    private double price;

    /**
     * 套餐子菜一份包含这个菜的数量
     */
    private int perNum;

    /**
     * 套餐子菜加价模式
     */
    private int addPriceMode;

    /**
     * 限点数量
     */
    private int limitNum;

    /**
     * 是否售完
     */
    private boolean isSoldOut;

    /**
     * 套餐子菜加价
     */
    private double addPrice;

    /**
     * 套餐可换菜id
     */
    private String suitMenuChangeId;

    /**
     * 套菜子菜的做法列表，有做法，没有设置的话，默认选择第一个
     */
    private List<Recipe> makeDataDtos;

    /**
     * 规格项，没有设置的话，默认选第一个
     */
    private String specialDetailName;

    /**
     * 特价
     */
    private double specialPrice;

    /**
     * 会员价
     */
    private double memberPrice;

    /**
     * 预定价
     */
    private double reservePrice;

    /**
     * 烧菜耗时，分钟为单位
     */
    private int consume;


    /**
     * 是否双单位菜肴
     */
    private int isTwoAccount;

    /**
     * 双单位菜肴的默认结账数量
     */
    private int defaultNum;

    /**
     * 是否支持多规格
     */
    private int isUseSpec;

    /**
     * 是否特色菜
     */
    private int isStyle;

    /**
     * 是否可打折
     */
    private int isRatio;

    /**
     * 预订价是否可打折
     */
    private int isReserveRatio;

    /**
     * 是否特价菜
     */
    private int isSpecial;

    /**
     * 是否需要确认重量
     */
    private int isConfirm;

    /**
     * 是否可以赠菜
     */
    private int isGive;

    /**
     * 服务费收取方式，0表示不收取，1表示固定费用，2表示菜价百分比
     */
    private int serviceFeeMode;

    /**
     * 服务费收取值
     */
    private double serviceFee;

    /**
     * 是否接受预定
     */
    private int isReserve;

    /**
     * 退菜是否需要权限
     */
    private int isBackAuth;

    /**
     * 是否加料菜：非加料菜（0）；加料菜（1），如果是加料菜则不能作为普通菜点
     */
    private int isAdditional;


    /**
     * 是否套菜. 菜类属性：普通菜（0）；套菜（1）；配料（2）；所有（-1）
     */
    private int isInclude;


    /**
     * 菜类名称
     */
    private String kindMenuName;

    /**
     * 是否关联做法，1表示有，0表示没有
     */
    private int hasMake;

    /**
     * 是否关联规格，1表示有，0表示没有
     */
    private int hasSpec;

    /**
     * 是否有加料菜，1表示有，0表示没有
     */
    private int hasAddition;

    /**
     * 图片路径
     */
    protected String imagePath;

    /**
     * 推荐指数，0表示不设定，1表示推荐，2表示十分推荐，3表示强烈推荐
     */
    private int recommendLevel;

    /**
     * 辣描述
     */
    private String acridLevelString;

    /**
     * 0表示不辣，1表示微辣，2表示中辣，3表示重辣
     */
    private int acridLevel;

    /**
     * 特色标签（如本店推荐）
     */
    private String specialTagString;

    /**
     * 标签来源对应字段，1表示系统标签，2表示商户自定义标签
     */
    private int tagSource;

    /**
     * 起点份数
     */
    private int startNum;

    /**
     * 基础价格模式:1表示根据原料计算;2表示手工价
     */
    private int basePriceMode;

    /**
     * 外卖是否可点，0表示不可点，1表示可点
     */
    private int isTakeOut;

    /**
     * 手工录入的成本价
     */
    private double basePrice;

    /**
     * 版本号
     */
    private int lastVer;

    /**
     * 编码
     */
    private String code;
    /**
     * 是否下架 0表示下架，1表示上架状态
     */
    private int isSelf;

    public boolean isTwoAccount() {
        return isTwoAccount == 1;
    }

    public int getIsTwoAccount() {
        return isTwoAccount;
    }

    public void setIsTwoAccount(int isTwoAccount) {
        this.isTwoAccount = isTwoAccount;
    }

    public int getIsStyle() {
        return isStyle;
    }

    public void setIsStyle(int isStyle) {
        this.isStyle = isStyle;
    }

    public int getIsUseSpec() {
        return isUseSpec;
    }

    public void setIsUseSpec(int isUseSpec) {
        this.isUseSpec = isUseSpec;
    }

    /**
     * 是否有做法
     *
     * @return boolean
     */
    public boolean hasMake() {
        return hasMake == 1;
    }

    /**
     * 是否有规格
     *
     * @return boolean
     */
    public boolean hasSpec() {
        return hasSpec == 1;
    }


    /**
     * 是否是套餐
     */
    public boolean isSuit() {
        return isInclude == 1;
    }

    /**
     * 是否套菜. 菜类属性：普通菜（0）；套菜（1）；配料（2）；所有（-1）
     */
    public int getIsInclude() {
        return isInclude;
    }

    public void setIsInclude(int isInclude) {
        this.isInclude = isInclude;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getBuyAccount() {
        return buyAccount;
    }

    public void setBuyAccount(String buyAccount) {
        this.buyAccount = buyAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPerNum() {
        return perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    public int getAddPriceMode() {
        return addPriceMode;
    }

    public void setAddPriceMode(int addPriceMode) {
        this.addPriceMode = addPriceMode;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
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

    public List<Recipe> getMakeDataDtos() {
        return makeDataDtos;
    }

    public Recipe getFirstRecipe() {
        if (makeDataDtos != null && !makeDataDtos.isEmpty()) {
            return makeDataDtos.get(0);
        }
        return null;
    }

    public void setMakeDataDtos(List<Recipe> makeDataDtos) {
        this.makeDataDtos = makeDataDtos;
    }

    public String getSpecialDetailName() {
        return specialDetailName;
    }

    public void setSpecialDetailName(String specialDetailName) {
        this.specialDetailName = specialDetailName;
    }

    public double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public double getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public double getDefaultNum() {
        return defaultNum;
    }

    public void setDefaultNum(int defaultNum) {
        this.defaultNum = defaultNum;
    }

    public int getIsRatio() {
        return isRatio;
    }

    public void setIsRatio(int isRatio) {
        this.isRatio = isRatio;
    }

    public int getIsReserveRatio() {
        return isReserveRatio;
    }

    public void setIsReserveRatio(int isReserveRatio) {
        this.isReserveRatio = isReserveRatio;
    }

    public int getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
    }

    public int getIsGive() {
        return isGive;
    }

    public void setIsGive(int isGive) {
        this.isGive = isGive;
    }

    public int getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(int isReserve) {
        this.isReserve = isReserve;
    }

    public int getIsBackAuth() {
        return isBackAuth;
    }

    public void setIsBackAuth(int isBackAuth) {
        this.isBackAuth = isBackAuth;
    }

    public int getHasMake() {
        return hasMake;
    }

    public void setHasMake(int hasMake) {
        this.hasMake = hasMake;
    }

    public int getIsTakeOut() {
        return isTakeOut;
    }

    public void setIsTakeOut(int isTakeOut) {
        this.isTakeOut = isTakeOut;
    }

    public int getServiceFeeMode() {
        return serviceFeeMode;
    }

    public void setServiceFeeMode(int serviceFeeMode) {
        this.serviceFeeMode = serviceFeeMode;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }


    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(int isAdditional) {
        this.isAdditional = isAdditional;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public String isKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
    }

    public int getHasSpec() {
        return hasSpec;
    }

    public void setHasSpec(int hasSpec) {
        this.hasSpec = hasSpec;
    }

    public int getHasAddition() {
        return hasAddition;
    }

    public void setHasAddition(int hasAddition) {
        this.hasAddition = hasAddition;
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

    public int getAcridLevel() {
        return acridLevel;
    }

    public void setAcridLevel(int acridLevel) {
        this.acridLevel = acridLevel;
    }

    public String getSpecialTagString() {
        return specialTagString;
    }

    public void setSpecialTagString(String specialTagString) {
        this.specialTagString = specialTagString;
    }

    public int getTagSource() {
        return tagSource;
    }

    public void setTagSource(int tagSource) {
        this.tagSource = tagSource;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getBasePriceMode() {
        return basePriceMode;
    }

    public void setBasePriceMode(int basePriceMode) {
        this.basePriceMode = basePriceMode;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ownerType);
        dest.writeString(this.name);
        dest.writeString(this.taste);
        dest.writeString(this.buyAccount);
        dest.writeString(this.account);
        dest.writeDouble(this.price);
        dest.writeInt(this.perNum);
        dest.writeInt(this.addPriceMode);
        dest.writeInt(this.limitNum);
        dest.writeByte(this.isSoldOut ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.addPrice);
        dest.writeString(this.suitMenuChangeId);
        dest.writeTypedList(this.makeDataDtos);
        dest.writeString(this.specialDetailName);
        dest.writeDouble(this.specialPrice);
        dest.writeDouble(this.memberPrice);
        dest.writeDouble(this.reservePrice);
        dest.writeInt(this.consume);
        dest.writeInt(this.isTwoAccount);
        dest.writeInt(this.defaultNum);
        dest.writeInt(this.isUseSpec);
        dest.writeInt(this.isStyle);
        dest.writeInt(this.isRatio);
        dest.writeInt(this.isReserveRatio);
        dest.writeInt(this.isSpecial);
        dest.writeInt(this.isConfirm);
        dest.writeInt(this.isGive);
        dest.writeInt(this.serviceFeeMode);
        dest.writeDouble(this.serviceFee);
        dest.writeInt(this.isReserve);
        dest.writeInt(this.isBackAuth);
        dest.writeInt(this.isAdditional);
        dest.writeInt(this.isInclude);
        dest.writeString(this.kindMenuName);
        dest.writeInt(this.hasMake);
        dest.writeInt(this.hasSpec);
        dest.writeInt(this.hasAddition);
        dest.writeString(this.imagePath);
        dest.writeInt(this.recommendLevel);
        dest.writeString(this.acridLevelString);
        dest.writeInt(this.acridLevel);
        dest.writeString(this.specialTagString);
        dest.writeInt(this.tagSource);
        dest.writeInt(this.startNum);
        dest.writeInt(this.basePriceMode);
        dest.writeInt(this.isTakeOut);
        dest.writeDouble(this.basePrice);
        dest.writeInt(this.lastVer);
        dest.writeString(this.code);
        dest.writeInt(this.isSelf);

    }

    public Food() {
    }

    protected Food(Parcel in) {
        this.ownerType = in.readInt();
        this.name = in.readString();
        this.taste = in.readString();
        this.buyAccount = in.readString();
        this.account = in.readString();
        this.price = in.readDouble();
        this.perNum = in.readInt();
        this.addPriceMode = in.readInt();
        this.limitNum = in.readInt();
        this.isSoldOut = in.readByte() != 0;
        this.addPrice = in.readDouble();
        this.suitMenuChangeId = in.readString();
        this.makeDataDtos = in.createTypedArrayList(Recipe.CREATOR);
        this.specialDetailName = in.readString();
        this.specialPrice = in.readDouble();
        this.memberPrice = in.readDouble();
        this.reservePrice = in.readDouble();
        this.consume = in.readInt();
        this.isTwoAccount = in.readInt();
        this.defaultNum = in.readInt();
        this.isUseSpec = in.readInt();
        this.isStyle = in.readInt();
        this.isRatio = in.readInt();
        this.isReserveRatio = in.readInt();
        this.isSpecial = in.readInt();
        this.isConfirm = in.readInt();
        this.isGive = in.readInt();
        this.serviceFeeMode = in.readInt();
        this.serviceFee = in.readDouble();
        this.isReserve = in.readInt();
        this.isBackAuth = in.readInt();
        this.isAdditional = in.readInt();
        this.isInclude = in.readInt();
        this.kindMenuName = in.readString();
        this.hasMake = in.readInt();
        this.hasSpec = in.readInt();
        this.hasAddition = in.readInt();
        this.imagePath = in.readString();
        this.recommendLevel = in.readInt();
        this.acridLevelString = in.readString();
        this.acridLevel = in.readInt();
        this.specialTagString = in.readString();
        this.tagSource = in.readInt();
        this.startNum = in.readInt();
        this.basePriceMode = in.readInt();
        this.isTakeOut = in.readInt();
        this.basePrice = in.readDouble();
        this.lastVer = in.readInt();
        this.code = in.readString();
        this.isSelf = in.readInt();

    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
