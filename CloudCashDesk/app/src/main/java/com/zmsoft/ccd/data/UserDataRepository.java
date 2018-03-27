package com.zmsoft.ccd.data;

import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.user.UserDataSource;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.unified.UnifiedLoginResponse;
import com.zmsoft.ccd.lib.bean.user.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 05/02/2017.
 */
@Singleton
public class UserDataRepository implements UserDataSource {

    // 掌柜
    public static final String METHOD_SEND_VERIFY_CODE = "com.dfire.boss.center.soa.login.service.IUnifiedLoginClientService.sendVerCode";
    public static final String METHOD_REGISTER = "com.dfire.boss.center.soa.login.service.IUnifiedLoginClientService.register";
    public static final String METHOD_FIND_PASS_WORD = "com.dfire.boss.center.soa.login.service.IUnifiedLoginClientService.modifyPassword";
    public static final String METHOD_UNIFIED_LOGIN = "com.dfire.boss.center.soa.login.service.IUnifiedLoginClientService.login";


    public static final String METHOD_LOGIN = "com.dfire.cashsoa.loginByAppKey"; //"com.dfire.cashsoa.login";//
    public static final String METHOD_IS_WORKING = "com.dfire.state.isWorking";
    public static final String METHOD_SET_WORKING = "com.dfire.state.setWorking";
    public static final String METHOD_CHECK_BIND_TAKEOUT_SEAT = "com.dfire.soa.cloudcash.checkTakeOutBindSeat";
    public static final String METHOD_GET_COUNTRIES = "com.dfire.bizconf.getCountries";         // 获取当前支持的手机国家码

    private final UserDataSource mUserRemoteSource;
    private final ICommonSource iCommonSource;

    @Inject
    UserDataRepository(@Remote UserDataSource userRemoteSource) {
        mUserRemoteSource = userRemoteSource;
        iCommonSource = new CommonRemoteSource();
    }

    @Override
    public Observable<List<MobileArea>> getMobileCountries() {
        return mUserRemoteSource.getMobileCountries();
    }

    @Override
    public void login(String countryCode, String mobile, String password, Callback<User> callback) {
        mUserRemoteSource.login(countryCode, mobile, password, callback);
    }

    @Override
    public Observable<UnifiedLoginResponse> wechatLogin(String thirdPartyCode) {
        return mUserRemoteSource.wechatLogin(thirdPartyCode);
    }

    @Override
    public Observable<UnifiedLoginResponse> registerAndBindMobile(String countryCode, String mobile, String verCode, String password, String wechatUnionId) {
        return mUserRemoteSource.registerAndBindMobile(countryCode, mobile, verCode, password, wechatUnionId);
    }

    @Override
    public Observable<UnifiedLoginResponse> verifyCodeLogin(String countryCode, String mobile, String verCode) {
        return mUserRemoteSource.verifyCodeLogin(countryCode, mobile, verCode);
    }

    @Override
    public Observable<UnifiedLoginResponse> verifyCodeRegister(String countryCode, String mobile, String verCode, String password) {
        return mUserRemoteSource.verifyCodeRegister(countryCode, mobile, verCode, password);
    }

    @Override
    public void feedback(String content, String email, Callback<Boolean> callback) {
        mUserRemoteSource.feedback(content, email, callback);
    }

    @Override
    public void modifyPwd(String oldPwd, String newPwd, Callback<Boolean> callback) {
        mUserRemoteSource.modifyPwd(oldPwd, newPwd, callback);
    }

    @Override
    public void checkCashSupportVersion(String entityId, Callback<CommonResult> callback) {
        mUserRemoteSource.checkCashSupportVersion(entityId, callback);
    }

    @Override
    public void sendVerifyCode(String mobile, String phoneArea, String isCheckRegister, int type, boolean isBindMobile, Callback<SmsCode> callback) {
        mUserRemoteSource.sendVerifyCode(mobile, phoneArea, isCheckRegister, type, isBindMobile, callback);
    }

    @Override
    public void register(String mobile, String phoneArea, String code, String passWord, Callback<String> callback) {
        mUserRemoteSource.register(mobile, phoneArea, code, passWord, callback);
    }

    @Override
    public void findPassWord(String mobile, String phoneArea, String code, String passWord, Callback<CommonResult> callback) {
        mUserRemoteSource.findPassWord(mobile, phoneArea, code, passWord, callback);
    }

    @Override
    public void isWorking(String entityId, int type, String userId, Callback<Boolean> callback) {
        mUserRemoteSource.isWorking(entityId, type, userId, callback);
    }

    @Override
    public void setWorking(String entityId, int type, String userId, int status, Callback<Integer> callback) {
        mUserRemoteSource.setWorking(entityId, type, userId, status, callback);
    }

    @Override
    public void checkTakeOutBindSeat(String entityId, String userId) {
        mUserRemoteSource.checkTakeOutBindSeat(entityId, userId);
    }

    public Observable<String> getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        return iCommonSource.getConfigSwitchVal(entityId, systemTypeId, code);
    }
}
