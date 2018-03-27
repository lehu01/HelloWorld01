package com.zmsoft.ccd.module.register.constant;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/12/5 16:03.
 */

public interface RegisterConstant {


    /**
     *  发送验证码后有三种结果：
     * 0.尚未知道
     * 1.手机号未注册
     * 2.手机号已注册
     */
    interface MOBILE_REGISTER_STATUS {
        int INIT = 0;
        int UNREGISTERED = 1;
        int REGISTERED = 2;
    }

    /**
     * 密码显示方式
     * 1.加密显示
     * 2.明文显示
     */
    interface PASSWORD_TYPE {
        int ENCRYPTION = 1;
        int NO_ENCRYPTION = 2;
    }
}
