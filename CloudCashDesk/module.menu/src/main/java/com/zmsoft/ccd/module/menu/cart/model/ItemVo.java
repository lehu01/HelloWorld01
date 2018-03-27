package com.zmsoft.ccd.module.menu.cart.model;

import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author DangGui
 * @create 2017/4/13.
 */

public class ItemVo implements Serializable {
    /**
     * 用户信息
     */
    private CustomerVo customerVo;
    /**
     * 兼容老版本 加上用户Id
     */
    private String customerRegisterId;
    /**
     * 套菜的子菜、普通菜的加料菜集合
     */
    private List<Item> childItems;
    /**
     * 支持老版本的必选菜逻辑 增加一个ID属性
     */
    private String id;
    /**
     * 1：普通菜 2：套菜 3:自定义菜 5：加料菜
     * <p>
     * <p>
     * <br/>
     * 菜单获取页面菜品类型分别是0 1 2  后续加入购物车都是对应 1 2 5
     * <br/>
     * 这个历史遗留问题，目前各业务线都是按照1 2 5 来的，除了商品中心
     *
     * @see Item#getKind()
     * @see com.zmsoft.ccd.module.menu.helper.CartHelper.CartFoodKind
     * @see com.zmsoft.ccd.module.menu.helper.CartHelper.MenuListFoodKind
     */
    private Short kind = 0;
    /**
     * 套菜、加料菜父菜ID
     */
    private String parentId = "";
    /**
     * 套菜计价模式
     */
    private Short priceMode = 0;
    /**
     * 菜类ID
     */
    private String kindMenuId = "";
    /**
     * 菜品ID
     */
    private String menuId = "";
    /**
     * 菜名
     */
    private String name = "";
    /**
     * 做法ID
     */
    private String makeId = "";
    /**
     * 做法
     */
    private String makeName = "";
    /**
     * 做法调价
     */
    private Double makePrice = 0.0;
    /**
     * 做法调价模式  0：不加价 1：一次性加价 2：每点菜单位加价 3：每结账单位加价
     */
    private short makePriceMode = 0;
    /**
     * 规格明细ID
     */
    private String specDetailId = "";
    /**
     * 规格名
     */
    private String specDetailName = "";
    /**
     * 规格调价模式
     */
    private Short specPriceMode = 0;
    /**
     * 规格调价模式 1：按比例调价 2：按加价调价
     */
    private Double specDetailPrice = 0.0;
    /**
     * 点菜数量
     */
    private Double num = 0.0;
    /**
     * 结账数量
     */
    private Double accountNum = 0.0;
    /**
     * 点菜单位
     */
    private String unit = "";
    /**
     * 结账单位
     */
    private String accountUnit = "";
    /**
     * 说明
     */
    private String memo = "";
    /**
     * 原始单价
     */
    private Double originalPrice = 0.0;
    /**
     * 单价
     */
    private Double price = 0.0;
    /**
     * 金额
     */
    private Double fee = 0.0;
    /**
     * 折后金额
     */
    private Double ratioFee = 0.0;
    /**
     * 可否打折
     */
    private short isRatio = 0;
    /**
     * 折扣率
     */
    private double ratio = 0.0;
    /**
     * 会员价
     */
    private double memberPrice = 0.0;
    /**
     * 状态 下架、估清、菜品不足 默认正常状态:-1
     */
    private short status = -1;
    /**
     * 实体ID
     */
    private String entityId = "";
    /**
     * 菜的唯一标识
     */
    private String index = "";

    /**
     * 起点份数
     */
    private int startNum;

    /**
     * 图片路径
     */
    private String imagePath = "";
    /**
     * 菜类名
     */
    private String kindMenuName = "";
    /**
     * 条形码
     */
    private String code;

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
    private String suitMenuDetailId = "";

    /**
     * 是否是必选菜 android 和 ios判断必选菜的字段不同 兼容老版本 isCompulsory
     */
    private boolean isCompulsory;

    /**
     * 累加步长（设置初始值）
     */
    private int stepLength = 1;

    /**
     * 是否先上菜，1表示暂不上菜，0表示立即上菜
     */
    private short isWait = 0;
    /**
     * 是否赠送这个菜 0 不赠送，1 赠送
     */
    private short present;

    /**
     * 模板标签 0非模板点餐 1模板点餐
     */
    private int tag = 0;

    /**
     * 商品类型
     */
    private int type = 0;
    /**
     * 标签
     */
    private Map<String, List<MemoLabel>> labels;
    /**
     * 传菜方案
     */
    private List<String> transferPlanIds;

    /**
     * 客户端自己加上的字段<br />
     * 原始结账数量，因为服务端无法判断双单位菜的accountNum是否修改过，所以客户端需要在CartItem里
     * 增加一个标志位doubleUnitStatus来区分,表示双单位菜是否修改过 枚举0：未修改 1：修改过
     * originAccountNum用来存储最原始的accountNum，用来和修改后的accountNum进行比较，判断是否有修改
     */
    private double originAccountNum = 0.0;

    /**
     * 双单位标识，用以判断零售称重商品
     */
    private int doubleUnits;

    public List<Item> getChildItems() {
        return childItems;
    }

    public void setChildItems(List<Item> childItems) {
        this.childItems = childItems;
    }

    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Short getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(Short priceMode) {
        this.priceMode = priceMode;
    }

    public String getKindMenuId() {
        return kindMenuId;
    }

    public void setKindMenuId(String kindMenuId) {
        this.kindMenuId = kindMenuId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getMakePrice() {
        return makePrice;
    }

    public void setMakePrice(Double makePrice) {
        this.makePrice = makePrice;
    }

    public Short getMakePriceMode() {
        return makePriceMode;
    }

    public void setMakePriceMode(Short makePriceMode) {
        this.makePriceMode = makePriceMode;
    }

    public String getSpecDetailId() {
        return specDetailId;
    }

    public void setSpecDetailId(String specDetailId) {
        this.specDetailId = specDetailId;
    }

    public String getSpecDetailName() {
        return specDetailName;
    }

    public void setSpecDetailName(String specDetailName) {
        this.specDetailName = specDetailName;
    }

    public Short getSpecPriceMode() {
        return specPriceMode;
    }

    public void setSpecPriceMode(Short specPriceMode) {
        this.specPriceMode = specPriceMode;
    }

    public Double getSpecDetailPrice() {
        return specDetailPrice;
    }

    public void setSpecDetailPrice(Double specDetailPrice) {
        this.specDetailPrice = specDetailPrice;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public Double getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(Double accountNum) {
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

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getRatioFee() {
        return ratioFee;
    }

    public void setRatioFee(Double ratioFee) {
        this.ratioFee = ratioFee;
    }

    public Short getIsRatio() {
        return isRatio;
    }

    public void setIsRatio(Short isRatio) {
        this.isRatio = isRatio;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getIndex() {
        if (index == null) {
            index = UserHelper.getEntityId() + StringUtils.getRandomString(24);
        }
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKindMenuName() {
        return kindMenuName;
    }

    public void setKindMenuName(String kindMenuName) {
        this.kindMenuName = kindMenuName;
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

    public String getSuitMenuDetailId() {
        return suitMenuDetailId;
    }

    public void setSuitMenuDetailId(String suitMenuDetailId) {
        this.suitMenuDetailId = suitMenuDetailId;
    }

    public Boolean getIsCompulsory() {
        return isCompulsory;
    }

    public void setIsCompulsory(Boolean isCompulsory) {
        this.isCompulsory = isCompulsory;
    }

    public int getStepLength() {
        return stepLength;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public Short getIsWait() {
        return isWait;
    }

    public void setIsWait(Short isWait) {
        this.isWait = isWait;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public CustomerVo getCustomerVo() {
        return customerVo;
    }

    public void setCustomerVo(CustomerVo customerVo) {
        this.customerVo = customerVo;
    }


    public String getCustomerRegisterId() {
        return customerRegisterId;
    }

    public void setCustomerRegisterId(String customerRegisterId) {
        this.customerRegisterId = customerRegisterId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, List<MemoLabel>> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, List<MemoLabel>> labels) {
        this.labels = labels;
    }

    public short getPresent() {
        return present;
    }

    public void setPresent(short present) {
        this.present = present;
    }

    public List<String> getTransferPlanIds() {
        return transferPlanIds;
    }

    public void setTransferPlanIds(List<String> transferPlanIds) {
        this.transferPlanIds = transferPlanIds;
    }

    public Double getOriginAccountNum() {
        return originAccountNum;
    }

    public void setOriginAccountNum(Double originAccountNum) {
        this.originAccountNum = originAccountNum;
    }

    public int getDoubleUnits() {
        return doubleUnits;
    }

    public void setDoubleUnits(int doubleUnits) {
        this.doubleUnits = doubleUnits;
    }

    /**
     * 是否是双单位
     *
     * @return boolean
     */
    public boolean isTwoAccount() {
        return doubleUnits == 1;
    }
}
