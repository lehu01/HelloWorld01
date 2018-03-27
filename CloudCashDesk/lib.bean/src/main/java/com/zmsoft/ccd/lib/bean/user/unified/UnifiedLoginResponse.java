package com.zmsoft.ccd.lib.bean.user.unified;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.bean.user.unified.memberinfo.MemberInfoVo;
import com.zmsoft.ccd.lib.bean.user.unified.usershop.Employee;
import com.zmsoft.ccd.lib.bean.user.unified.usershop.Shop;
import com.zmsoft.ccd.lib.bean.user.unified.usershop.UserShopVo;

/**
 * http://console.gateway.2dfire-daily.com/#/home/api/252835003979798528
 * 1.二维火掌柜统一登录返回数据，因为新增了字段不能直接使用原有库中类，所以重新生成本类
 * 2.这个类直接转化成com.zmsoft.ccd.lib.bean.user.User
 * 3.后续掌柜的包字段更新的话，可以替代本类
 *
 * @author : heniu@2dfire.com
 * @time : 2017/11/29 14:57.
 */

public class UnifiedLoginResponse extends Base {

    // sex          UserShopVo.Employee.sex
    // status       目前不需要使用
    // hasPantry    目前不需要使用
    // entityId     UserShopVo.Shop.entityId
    // roleName     UserShopVo.User.roleName
    // shopCode     UserShopVo.Shop.code
    // token        memberToken
    // shopName     UserShopVo.Shop.name
    // userId       UserShopVo.user.id
    // memberUserId 目前不需要使用
    // userName     UserShopVo.user.name
    // memberId     MemberInfoVo.memberId
    // memberName   MemberInfoVo.name
    // mobile       MemberInfoVo.mobile
    // picFullPath  MemberInfoVo.iconUrl
    // shopEntityType   无法获取到，unifiedLoginSelectedShop同样可以处理
    // trialShop    UserShopVo.Shop.trialShop
    // isHideChainShop  UserShopVo.hideChainShop
    // unifiedLoginSelectedShop UserShopVo!=null, 替代shopEntityType

    private UserShopVo userShopVo;
    private MemberInfoVo memberInfoVo;
    private String memberToken;             // 会员token。登录二维火账户后生成该token

    public boolean needBindMobile() {
        return null != memberInfoVo && memberInfoVo.needBindMobile();
    }

    public String getCountryCode() {
        if (null == memberInfoVo) {
            return "";
        }
        return memberInfoVo.getCountryCode();
    }

    public String getUnionId() {
        if (null == memberInfoVo) {
            return "";
        }
        return memberInfoVo.getThirdPartyId();
    }

    public User becomeUser(String maleText, String femaleText, String unknownText) {
        User user = new User();
        // token        entityToken
        if (null != memberToken) {
            user.setToken(memberToken);
        }

        // unifiedLoginSelectedShop UserShopVo!=null, 替代shopEntityType
        if (null != userShopVo) {
            user.setUnifiedLoginSelectedShop(User.UNIFIED_LOGIN_SELECTED_SHOP.SELECTED);
        } else {
            user.setUnifiedLoginSelectedShop(User.UNIFIED_LOGIN_SELECTED_SHOP.UNSELECTED);
        }

        if (null != userShopVo) {
            // isHideChainShop  UserShopVo.hideChainShop
            if (null != userShopVo.getHideChainShop()) {
                user.setHideChainShop(userShopVo.getHideChainShop());
            }
            Employee employee = userShopVo.getEmployee();
            if (null != employee) {
                // sex          UserShopVo.Employee.sex
                user.setSex(employee.getSexString(maleText, femaleText, unknownText));
            }

            com.zmsoft.ccd.lib.bean.user.unified.usershop.User userShopUser = userShopVo.getUser();
            if (null != userShopUser) {
                // userId       UserShopVo.Employee.userId
                if (null != userShopUser.getId()) {
                    user.setUserId(userShopUser.getId());
                }
                // userName     UserShopVo.Employee.name
                if (null != userShopUser.getName()) {
                    user.setUserName(userShopUser.getName());
                }
                // roleName     UserShopVo.User.roleName
                if (null != userShopUser.getRoleName()) {
                    user.setRoleName(userShopUser.getRoleName());
                }
            }

            Shop shop = userShopVo.getShop();
            if (null != shop) {
                // entityId     UserShopVo.Shop.entityId
                if (null != shop.getEntityId()) {
                    user.setEntityId(shop.getEntityId());
                }
                // shopCode     UserShopVo.Shop.code
                if (null != shop.getCode()) {
                    user.setShopCode(shop.getCode());
                }
                // shopName     UserShopVo.Shop.name
                if (null != shop.getName()) {
                    user.setShopName(shop.getName());
                }
                // trialShop    UserShopVo.Shop.trialShop
                if (null != shop.getTrialShop()) {
                    user.setTrialShop(shop.getTrialShop());
                }
            }
        }
        if (null != memberInfoVo) {
            // memberId     MemberInfoVo.memberId
            if (null != memberInfoVo.getMemberId()) {
                user.setMemberId(memberInfoVo.getMemberId());
            }
            // memberName   MemberInfoVo.name
            if (null != memberInfoVo.getName()) {
                user.setMemberName(memberInfoVo.getName());
            }
            // mobile       MemberInfoVo.mobile
            if (null != memberInfoVo.getMobile()) {
                user.setMobile(memberInfoVo.getMobile());
            }
            // picFullPath  MemberInfoVo.iconUrl
            if (null != memberInfoVo.getIconUrl()) {
                user.setPicFullPath(memberInfoVo.getIconUrl());
            }
        }

        return user;
    }
}
