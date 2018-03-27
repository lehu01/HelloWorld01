package com.zmsoft.ccd.module.register.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.User;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/28 11:14
 */
public class RegisterContract {

    public interface View extends BaseView<Presenter> {

        // 登录成功
        void loginSuccess(User user, String countryCode);

        // 找回密码成功
        void findPassWordSuccess();

        // 注册成功
        void registerSuccess(String data);

        // 发送验证码成功
        void sendVerifyCodeSuccess(SmsCode smsCode, int fragmentFromType);

        // toast
        void showToastMessage(String message);

        // 获取工作模式
        void workModelSuccess(String data);
    }

    interface Presenter extends BasePresenter {
        // 获取工作模式是否选择
        void getConfigSwitchVal(String entityId, String systemTypeId, String code);

        // 登录
        void doLogin(String countryCode, String userName, String passWord);

        // 验证码登录，手机未注册时需要填入password
        void verifyCodeLogin(String countryCode, String userName, String verifyCode);

        // 找回密码
        void findPassWord(String mobile, String countryCode, String code, String passWord);

        // 注册，成功后需要再调用一遍登录
        void register(String mobile, String phoneArea, String code, String passWord);

        // 检测
        boolean check(String phone, String code);

        // 检测
        boolean check(String phone, String code, String passWord);

        // 发送验证码
        void sendVerifyCode(String mobile, String countryCode, String isCheckRegister, int type, int fragmentFromType);

        // 注册新账号并绑定
        void registerAndBindMobile(String countryCode, String mobile, String verifyCode, String password, String wechatUnionId);

        // 验证码登录，新账号注册并登录
        void verifyCodeRegister(String countryCode, String userName, String verifyCode, String password);
    }
}
