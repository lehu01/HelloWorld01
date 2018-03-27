package com.zmsoft.ccd.module.login.login;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.user.User;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/16 18:36
 */
public class LoginContract {

    public interface View extends BaseView<Presenter> {

        // 获取工作模式
        void workModelSuccess(String data);

        // 登录成功
        void loginSuccess(User user, String countryCode);

        // 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
        void loadDataError(String errorMessage);

        //显示 TOKEN 异常信息
        void showTokenError(String errorCode);

        void showInvalidateMsg(int messageId);

        // 微信绑定手机号
        void wechatBindMobile(String wechatCode);
    }

    public interface Presenter extends BasePresenter {

        // 获取工作模式是否选择
        void getConfigSwitchVal(String entityId, String systemTypeId, String code);

        // 存取国家码
        String getLastCountryCode();
        void  saveLastCountryCode(String countryCode);

        /**
         * 获取上次用户登录信息
         *
         * @return
         */
        String getLastUserInfo();

        /**
         * 验证登录信息合法性
         *
         * @param userName
         * @param passWord
         * @return
         */
        boolean checkLoginValidate(String userName, String passWord);

        /**
         * 登录
         *
         * @param userName 用户名
         * @param passWord 密码
         */
        void doLogin(String mobileAreaNumber, String userName, String passWord);

        /**
         * 保存当前用户登录信息
         *
         * @param user
         */
        // 保存当前用户登录信息
        void saveUser(User user);

        /**
         * 验证异地登录
         */
        // 验证异地登录
        void checkTokenValidate(String extraCode);

        /**
         * 微信登录
         * @param code
         */
        void wechatLogin(String code);

    }

}

