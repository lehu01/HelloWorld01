package com.zmsoft.ccd.lib.bean.user;

import android.text.TextUtils;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/13 14:31
 */
public class User extends Base {

    public interface UNIFIED_LOGIN_SELECTED_SHOP {
        int INIT = 0;
        int SELECTED = 1;
        int UNSELECTED = 2;
    }

    public static final int SHOP_TYPE_STORES = 1; // 门店
    public static final int SHOP_TYPE_CHAIN = 2; // 连锁
    public static final int SHOP_TYPE_BRANCH = 3; // 分公司

    private String sex; // 性别
    private int status; // 状态
    private boolean hasPantry; //  是否有传菜方案
    private String entityId; // 设备id
    private String roleName; // 角色名称
    private String shopCode; // 店铺编码(不为空：代表有工作的店铺)
    private String token; // 身份令牌
    private String shopName; // 店铺名称
    private String userId; // 用户id
    private String memberUserId; // 用户和会员关联id
    private String userName; // 用户名
    private String memberId; // 会员id
    private String memberName; // 会员名称
    private String mobile; // 手机号
    private String picFullPath; // 头像的完整路径
    private int shopEntityType; // 店铺实体类型：1 普通门店 2 连锁 3 分公司     // 统一登录未获取该字段
    private int trialShop;     //是否为试用店铺 1:是 0:否
    private boolean isHideChainShop; // 是否隐藏连锁店铺,如果已经是个连锁店为false：开店使用

    /**
     * 0是餐饮，1是零售
     */
    private int industry;

    private String currencySymbol; //货币符号
    private int unifiedLoginSelectedShop = UNIFIED_LOGIN_SELECTED_SHOP.INIT;                         // 统一登录后，当前有选中供显示的店铺

    /**
     * 登录后，是否需要进入选店页面
     * 1.云收银登录  User.SHOP_TYPE_STORES != shopEntityType
     * 2.统一登录   没有shop信息
     *
     * @return
     */
    public boolean isNeedCheckShop() {
        // 1.云收银登录  User.SHOP_TYPE_STORES != shopEntityType
        if (UNIFIED_LOGIN_SELECTED_SHOP.INIT == unifiedLoginSelectedShop) {
            if (User.SHOP_TYPE_STORES != shopEntityType) {
                return true;
            }
        }
        // 2.统一登录   没有shop信息
        if (UNIFIED_LOGIN_SELECTED_SHOP.UNSELECTED == unifiedLoginSelectedShop) {
            return true;
        }
        return StringUtils.isEmpty(entityId);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isHasPantry() {
        return hasPantry;
    }

    public void setHasPantry(boolean hasPantry) {
        this.hasPantry = hasPantry;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(String memberUserId) {
        this.memberUserId = memberUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPicFullPath() {
        return picFullPath;
    }

    public void setPicFullPath(String picFullPath) {
        this.picFullPath = picFullPath;
    }

    public boolean isHideChainShop() {
        return isHideChainShop;
    }

    public void setHideChainShop(boolean hideChainShop) {
        isHideChainShop = hideChainShop;
    }

    public int getTrialShop() {
        return trialShop;
    }

    public void setTrialShop(int trialShop) {
        this.trialShop = trialShop;
    }

    public int getIndustry() {
        return industry;
    }

    public void setIndustry(int industry) {
        this.industry = industry;
    }

    public void setUnifiedLoginSelectedShop(int unifiedLoginSelectedShop) {
        this.unifiedLoginSelectedShop = unifiedLoginSelectedShop;
    }

    public String getCurrencySymbol() {
        if (!TextUtils.isEmpty(currencySymbol)) {
            currencySymbol = FeeHelper.transferRMBSymbol(currencySymbol);
        }
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
