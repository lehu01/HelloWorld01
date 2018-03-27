/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) 2011, ${year} 大米
 *
 * 工程名称：	item-soa
 * 创建者： 大米 创建日期： ${date}
 * 创建记录：	创建类结构。
 *
 **/
package com.zmsoft.ccd.module.menu.cart.model.cartdetail;

/**
 * 菜肴.
 *
 * @author DangGui
 * @create 2017/4/19.
 */
public abstract class BaseMenu {
    /**
     * <code>菜类</code>.
     */
    private String kindMenuId;

    /**
     * <code>顺序码</code>.
     */
    private int sortCode;

    /**
     * <code>编码</code>.
     */
    private String code;

    /**
     * <code>名称</code>.
     */
    private String name;

    /**
     * <code>口味描述</code>.
     */
    private String taste;

    /**
     * <code>拼写</code>.
     */
    private String spell;

    /**
     * <code>拼写2</code>.
     */
    private String spell2;

    /**
     * <code>点菜单位</code>.
     */
    private String buyAccount;

    /**
     * <code>结帐单位</code>.
     */
    private String account;

    /**
     * <code>价格</code>.
     */
    private double price;

    /**
     * <code>特价</code>.
     */
    private double specialPrice;

    /**
     * <code>会员价</code>.
     */
    private double memberPrice;

    /**
     * <code>预定价格</code>.
     */
    private double reservePrice;

    /**
     * <code>照片附件</code>.
     */
    private String attachmentId;

    /**
     * <code>附件版本</code>.
     */
    private int attachmentVer;

    /**
     * <code>规格ID</code>.
     */
    private String specId;

    /**
     * <code>烧菜耗时(分钟为单位)</code>.
     */
    private int consume;

    /**
     * <code>提成方式</code>.
     */
    private int deductKind;

    /**
     * <code>提成比例</code>.
     */
    private double deduct;

    /**
     * <code>备注</code>.
     */
    private String memo;

    /**
     * <code>是否双单位菜肴</code>. <br />
     * 0表示非双单位，1表示双单位
     */
    private int isTwoAccount;

    /**
     * <code>双单位菜肴的默认结账数量(每点菜单位)</code>.
     */
    private double defaultNum;

    /**
     * <code>是否支持多规格</code>.
     */
    private int isUseSpec;

    /**
     * <code>已发送的菜肴退菜时是否修改剩余数量</code>.
     */
    private int balanceMode = -1;

    /**
     * <code>是否套菜</code>.
     */
    private int isInclude;

    /**
     * <code>是否特色菜</code>.
     */
    private int isStyle;

    /**
     * <code>加辣指数</code>.
     */
    private int acridLevel;

    /**
     * <code>是否可打折</code>.
     */
    private int isRatio;

    /**
     * <code>预定价是否可打折</code>.
     */
    private int isReserveRatio;

    /**
     * <code>是否特价菜</code>.
     */
    private int isSpecial;

    /**
     * <code>是否需要确认重量</code>.
     */
    private int isConfirm;

    /**
     * <code>是否可以赠菜</code>.
     * 0 false 1 true
     */
    private int isGive;

    /**
     * <code>服务费收取方式</code>.
     */
    private int serviceFeeMode;

    /**
     * <code>服务费收取值</code>.
     */
    private double serviceFee;

    /**
     * <code>是否下架</code>.<br />
     * 0已下架 1未下架
     */
    private int isSelf = 1;

    /**
     * <code>是否需要出单</code>.
     */
    private int isPrint = 1;

    /**
     * <code>点菜时是否允许修改单价</code>.
     */
    private int isChangePrice;

    /**
     * <code>是否接受预订</code>.
     */
    private int isReserve;

    /**
     * <code>退菜是否需要权限</code>.
     */
    private int isBackAuth = 1;

    /**
     * <code>是否加料菜(配菜)</code>.
     */
    private int isAdditional;

    /**
     * <code>出品部门Id</code>.
     */
    private String wareHouseId;

    /**
     * <code>图片路径</code>.
     */
    private String path;

    /**
     * <code>图片所在服务器</code>.
     */
    private String server;

    /**
     * <code>数据来源标记</code>.
     */
    private int ownerType = 1;

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getSpell2() {
        return spell2;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
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

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getAttachmentVer() {
        return attachmentVer;
    }

    public void setAttachmentVer(int attachmentVer) {
        this.attachmentVer = attachmentVer;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    public int getDeductKind() {
        return deductKind;
    }

    public void setDeductKind(int deductKind) {
        this.deductKind = deductKind;
    }

    public double getDeduct() {
        return deduct;
    }

    public void setDeduct(double deduct) {
        this.deduct = deduct;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getIsTwoAccount() {
        return isTwoAccount;
    }

    public void setIsTwoAccount(int isTwoAccount) {
        this.isTwoAccount = isTwoAccount;
    }

    public double getDefaultNum() {
        return defaultNum;
    }

    public void setDefaultNum(double defaultNum) {
        this.defaultNum = defaultNum;
    }

    public int getIsUseSpec() {
        return isUseSpec;
    }

    public void setIsUseSpec(int isUseSpec) {
        this.isUseSpec = isUseSpec;
    }

    public int getBalanceMode() {
        return balanceMode;
    }

    public void setBalanceMode(int balanceMode) {
        this.balanceMode = balanceMode;
    }

    public int getIsInclude() {
        return isInclude;
    }

    public void setIsInclude(int isInclude) {
        this.isInclude = isInclude;
    }

    public int getIsStyle() {
        return isStyle;
    }

    public void setIsStyle(int isStyle) {
        this.isStyle = isStyle;
    }

    public int getAcridLevel() {
        return acridLevel;
    }

    public void setAcridLevel(int acridLevel) {
        this.acridLevel = acridLevel;
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

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(int isPrint) {
        this.isPrint = isPrint;
    }

    public int getIsChangePrice() {
        return isChangePrice;
    }

    public void setIsChangePrice(int isChangePrice) {
        this.isChangePrice = isChangePrice;
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

    public int getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(int isAdditional) {
        this.isAdditional = isAdditional;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }
}
