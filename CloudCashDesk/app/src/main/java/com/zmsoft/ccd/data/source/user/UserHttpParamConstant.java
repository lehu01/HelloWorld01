package com.zmsoft.ccd.data.source.user;

/**
 * 掌柜登录相关接口常量
 * @author : heniu@2dfire.com
 * @time : 2017/11/17 15:25.
 */

public interface UserHttpParamConstant {

    interface UNIFIED_LOGIN {
        String PARAM_STR = "param_str";
        String LOGIN_TYPE = "loginType";                    // 登录方式：1. 手机密码登录 2.手机验证码登录 3.微信code登录 4.使用token直接登录 5.验证码带注册登录 6.微信800的手机号换绑登录 7支付宝登录
        String THIRD_PARTY_CODE = "thirdPartyCode";         // 微信授权回调带回来的code
        String THIRD_PARTY_ID = "thirdPartyId";             // 微信unionId。换绑的时候需要传
        String THIRD_TYPE = "thirdType";                    // 三方类型 1微信 2支付宝
        String IS_ENTERPRISE = "isEnterprise";              // 是否为企业版 0否 1是
        String MEMBER_TOKEN = "memberToken";                // 会员token，使用token登录时必传
        String COUNTRY_CODE = "countryCode";                // 区号
        String MOBILE = "mobile";                           // 手机号
        String PASSWORD = "password";                       // 密码
        String VERCODE = "verCode";                         // 验证码

        String BASE_PARAM = "base_param";                   // 基础参数，网关那边取不到，再传一份
        String DEVICE_ID = "deviceId";                      // 设备号id，不同于公共参数的s_did。为原生deviceId去掉"-"
        String APP_KEY = "appKey";                          // api分配给每个应用的key
        String S_BR = "sBR";                                // 品牌
        String S_OS = "sOS";                                // 系统

        interface LOGIN_TYPE_VALUE {
            int VERIFY_CODE_LOGIN = 2;
            int WECHAT_LOGIN = 3;
            int VERIFY_CODE_REGISTER = 5;   // 验证码注册
            int WECHAT_BIND = 6;            // 只有未注册手机才能绑定微信
        }

        interface THIRD_TYPE_VALUE {
            int WECHAT = 1;
        }

        interface IS_ENTERPRISE_VALUE {
            int FALSE = 0;
        }
    }

    public interface SEND_VERIFY_CODE {
        public interface TYPE_VALUE {
            int MOBILE_UNREGSITERED = 0;        // 手机未注册时发送
            int MOBILE_REGSITERED = 1;          // 手机已经注册时发
        }

        public interface IS_REQUIRE_REGISTER_VALUE {
            String FALSE = "0";
            String TRUE = "1";
        }
    }
}
