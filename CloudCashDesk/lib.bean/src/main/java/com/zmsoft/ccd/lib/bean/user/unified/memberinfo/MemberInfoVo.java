package com.zmsoft.ccd.lib.bean.user.unified.memberinfo;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/4 10:50.
 */

public class MemberInfoVo extends Base {

    private interface STATUS_VALUE {
        int NOT_BIND_MOBILE = 5;
    }

    private Integer status;             // 状态 1没有正在工作中的店铺，到选店界面 2有工作中的店铺 3有其他人登录 4手机号未注册 5不规则手机号，800开头的手机号(未绑定手机号)
    private String mobile;              // 手机号
    private String name;                // 会员姓名
    private String memberId;            // 会员id
    private String countryCode;         // 区号
    private String iconUrl;             // 会员头像
    private String thirdPartyId;        // 微信id（unionid）

    public boolean needBindMobile() {
        return STATUS_VALUE.NOT_BIND_MOBILE == status;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }
}
