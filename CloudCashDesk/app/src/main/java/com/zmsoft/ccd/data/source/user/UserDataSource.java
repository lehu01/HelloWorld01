package com.zmsoft.ccd.data.source.user;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.unified.UnifiedLoginResponse;
import com.zmsoft.ccd.lib.bean.user.User;

import rx.Observable;

import java.util.List;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 05/02/2017.
 */
public interface UserDataSource {

    /**
     * 获取当前支持的手机国家码
     * @return
     */
    Observable<List<MobileArea>> getMobileCountries();

    /**
     * 用户登录
     *
     * @param mobile   手机号码
     * @param password 密码
     * @param callback 回调接口
     */
    void login(String mobileAreaNumber, String mobile, String password, Callback<User> callback);


    /**
     * 微信登录
     * @param thirdPartyCode        code
     * @return
     */
    Observable<UnifiedLoginResponse> wechatLogin(String thirdPartyCode);

    /**
     * 微信注册并绑定手机号
     * @param countryCode
     * @param mobile
     * @param verCode
     * @param password
     * @param wechatUnionId
     * @return
     */
    Observable<UnifiedLoginResponse> registerAndBindMobile(String countryCode, String mobile, String verCode, String password, String wechatUnionId);

    /**
     * 验证码登录
     * @param countryCode
     * @param mobile
     * @param verCode
     * @return
     */
    Observable<UnifiedLoginResponse> verifyCodeLogin(String countryCode, String mobile, String verCode);

    /**
     * 验证码登录，新账号注册并登录
     * @param countryCode
     * @param mobile
     * @param verCode
     * @param password
     * @return
     */
    Observable<UnifiedLoginResponse> verifyCodeRegister(String countryCode, String mobile, String verCode, String password);

    /**
     * 用户反馈
     *
     * @param content
     * @param email
     */
    void feedback(String content, String email, Callback<Boolean> callback);

    /**
     * 修改密码
     *
     * @param oldPwd 老密码
     * @param newPwd 新密码
     */
    void modifyPwd(String oldPwd, String newPwd, Callback<Boolean> callback);

    /**
     * 检测火收银是否是支持版本
     */
    void checkCashSupportVersion(String entityId, Callback<CommonResult> callback);

    /**
     * 发送验证码
     *
     * @param mobile     手机号
     * @param phoneArea  手机区域
     * @param isCheckRegister 是否需要验证注册
     * @param type       1:注册验证码 2:找回密码验证码
     */
    void sendVerifyCode(String mobile, String phoneArea, String isCheckRegister, int type, boolean isBindMobile, Callback<SmsCode> callback);

    /**
     * 注册
     *
     * @param mobile    手机号
     * @param phoneArea 区域
     * @param code      md5加密验证码
     * @param passWord  md5加密密码
     * @param callback
     */
    void register(String mobile, String phoneArea, String code, String passWord, Callback<String> callback);

    /**
     * 找回密码
     *
     * @param mobile
     * @param phoneArea
     * @param code
     * @param passWord
     * @param callback
     */
    void findPassWord(String mobile, String phoneArea, String code, String passWord, Callback<CommonResult> callback);


    /**
     * 是否在工作中
     *
     * @param entityId
     * @param type
     * @param callback
     */
    void isWorking(String entityId, int type, String userId, Callback<Boolean> callback);

    /**
     * 设置工作状态，1在线2离线
     *
     * @param entityId
     * @param type
     * @param userId
     * @param status
     * @param callback
     */
    void setWorking(String entityId, int type, String userId, int status, Callback<Integer> callback);

    /**
     * 检查绑定外卖消息
     * @param entityId
     * @param userId
     */
    void checkTakeOutBindSeat(String entityId, String userId);
}
