package com.zmsoft.ccd.data.source.user;

import android.os.Build;
import android.text.TextUtils;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.sdk.util.MD5Util;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.AppConstant;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.UserDataRepository;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;
import com.zmsoft.ccd.lib.bean.user.SmsCode;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.bean.user.unified.UnifiedLoginResponse;
import com.zmsoft.ccd.lib.utils.DeviceUtil;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.CommonConstant;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * ProjectName:CloudCashDesk
 * Created by Jiaozi
 * on 05/02/2017.
 */
@Singleton
public class UserRemoteSource implements UserDataSource {

    private NetworkService mNetworkService;

    @Inject
    public UserRemoteSource(NetworkService networkService) {
        mNetworkService = networkService;
    }

    @Override
    public Observable<List<MobileArea>> getMobileCountries() {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<MobileArea>>>>() {
            @Override
            public HttpResult<HttpBean<List<MobileArea>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                Type listType = new TypeToken<HttpResult<HttpBean<List<MobileArea>>>>() {}.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_GET_COUNTRIES)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<MobileArea>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void login(String mobileAreaNumber, String mobile, String password, final Callback<User> callback) {
        // 登录参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.COUNTRY_CODE, mobileAreaNumber);
        paramMap.put(HttpParasKeyConstant.MOBILE, mobile);
        paramMap.put(HttpParasKeyConstant.PASSWORD, MD5Util.encode(password));
        paramMap.put(HttpParasKeyConstant.DEVICE_ID, DeviceUtil.getDeviceId(DeviceUtil.getDeviceId(GlobalVars.context)));
        paramMap.put(HttpParasKeyConstant.CLIENT_TYPE, AppConstant.ANDROID);

//        paramMap.put(HttpParasKeyConstant.PARA_IP, DeviceUtil.getIPAddress(true));
//        String mac = DeviceUtil.getMACAddress(null);
//        if (!StringUtils.isEmpty(mac)) {
//            paramMap.put(HttpParasKeyConstant.PARA_MAC, mac);
//        }
//        paramMap.put(HttpParasKeyConstant.PARA_BRAND, Build.BRAND);
//        paramMap.put(HttpParasKeyConstant.PARAM_LOGIN_ISKICK, true);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_LOGIN);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<User>() {
            @Override
            protected void onData(User user) {
                callback.onSuccess(user);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<UnifiedLoginResponse> wechatLogin(String thirdPartyCode) {
        final Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> paramStrMap = new HashMap<>();
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE_VALUE.WECHAT_LOGIN);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.THIRD_PARTY_CODE, thirdPartyCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.THIRD_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.THIRD_TYPE_VALUE.WECHAT);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.IS_ENTERPRISE, UserHttpParamConstant.UNIFIED_LOGIN.IS_ENTERPRISE_VALUE.FALSE);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PARAM_STR, JsonHelper.toJson(paramStrMap));

        Map<String, Object> baseParamMap = new HashMap<>();
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.DEVICE_ID, StringUtils.isEmpty(DeviceUtil.getDeviceId(GlobalVars.context)) ? CommonConstant.UNKNOWN : DeviceUtil.getDeviceId(GlobalVars.context));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.APP_KEY, AppEnv.getApiKey());
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_BR, String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_OS, CommonConstant.ANDROID);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.BASE_PARAM, JsonHelper.toJson(baseParamMap));

        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<UnifiedLoginResponse>>>() {
            @Override
            public HttpResult<HttpBean<UnifiedLoginResponse>> call() throws Exception {
                Type type = new TypeToken<HttpResult<HttpBean<UnifiedLoginResponse>>>() {}.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_UNIFIED_LOGIN)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<UnifiedLoginResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<UnifiedLoginResponse> registerAndBindMobile(String countryCode, String mobile, String verCode, String password, String wechatUnionId) {
        final Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> paramStrMap = new HashMap<>();
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE_VALUE.WECHAT_BIND);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.THIRD_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.THIRD_TYPE_VALUE.WECHAT);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.IS_ENTERPRISE, UserHttpParamConstant.UNIFIED_LOGIN.IS_ENTERPRISE_VALUE.FALSE);

        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.COUNTRY_CODE, countryCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.MOBILE, mobile);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.VERCODE, verCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PASSWORD, MD5Util.encode(password));
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.THIRD_PARTY_ID, wechatUnionId);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PARAM_STR, JsonHelper.toJson(paramStrMap));

        Map<String, Object> baseParamMap = new HashMap<>();
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.DEVICE_ID, StringUtils.isEmpty(DeviceUtil.getDeviceId(GlobalVars.context)) ? CommonConstant.UNKNOWN : DeviceUtil.getDeviceId(GlobalVars.context));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.APP_KEY, AppEnv.getApiKey());
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_BR, String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_OS, CommonConstant.ANDROID);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.BASE_PARAM, JsonHelper.toJson(baseParamMap));

        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<UnifiedLoginResponse>>>() {
            @Override
            public HttpResult<HttpBean<UnifiedLoginResponse>> call() throws Exception {
                Type type = new TypeToken<HttpResult<HttpBean<UnifiedLoginResponse>>>() {}.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_UNIFIED_LOGIN)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<UnifiedLoginResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<UnifiedLoginResponse> verifyCodeLogin(String countryCode, String mobile, String verCode) {
        final Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> paramStrMap = new HashMap<>();
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE_VALUE.VERIFY_CODE_LOGIN);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.COUNTRY_CODE, countryCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.MOBILE, mobile);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.VERCODE, verCode);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PARAM_STR, JsonHelper.toJson(paramStrMap));

        Map<String, Object> baseParamMap = new HashMap<>();
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.DEVICE_ID, StringUtils.isEmpty(DeviceUtil.getDeviceId(GlobalVars.context)) ? CommonConstant.UNKNOWN : DeviceUtil.getDeviceId(GlobalVars.context));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.APP_KEY, AppEnv.getApiKey());
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_BR, String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_OS, CommonConstant.ANDROID);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.BASE_PARAM, JsonHelper.toJson(baseParamMap));

        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<UnifiedLoginResponse>>>() {
            @Override
            public HttpResult<HttpBean<UnifiedLoginResponse>> call() throws Exception {
                Type type = new TypeToken<HttpResult<HttpBean<UnifiedLoginResponse>>>() {}.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_UNIFIED_LOGIN)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<UnifiedLoginResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<UnifiedLoginResponse> verifyCodeRegister(String countryCode, String mobile, String verCode, String password) {
        final Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> paramStrMap = new HashMap<>();
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE, UserHttpParamConstant.UNIFIED_LOGIN.LOGIN_TYPE_VALUE.VERIFY_CODE_REGISTER);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.COUNTRY_CODE, countryCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.MOBILE, mobile);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.VERCODE, verCode);
        paramStrMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PASSWORD, MD5Util.encode(password));
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.PARAM_STR, JsonHelper.toJson(paramStrMap));

        Map<String, Object> baseParamMap = new HashMap<>();
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.DEVICE_ID, StringUtils.isEmpty(DeviceUtil.getDeviceId(GlobalVars.context)) ? CommonConstant.UNKNOWN : DeviceUtil.getDeviceId(GlobalVars.context));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.APP_KEY, AppEnv.getApiKey());
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_BR, String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));
        baseParamMap.put(UserHttpParamConstant.UNIFIED_LOGIN.S_OS, CommonConstant.ANDROID);
        paramMap.put(UserHttpParamConstant.UNIFIED_LOGIN.BASE_PARAM, JsonHelper.toJson(baseParamMap));

        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<UnifiedLoginResponse>>>() {
            @Override
            public HttpResult<HttpBean<UnifiedLoginResponse>> call() throws Exception {
                Type type = new TypeToken<HttpResult<HttpBean<UnifiedLoginResponse>>>() {}.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_UNIFIED_LOGIN)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<UnifiedLoginResponse>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void feedback(String content, String email, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(HttpParasKeyConstant.personalCenter.PARA_USER_NAME, UserHelper.getUserName());
        if (!TextUtils.isEmpty(email)) {
            paramMap.put(HttpParasKeyConstant.personalCenter.PARA_USER_EMAIL, email);
        }
        paramMap.put(HttpParasKeyConstant.personalCenter.PARA_SUGGEST, content);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.PERSONAL_CENTER.METHOD_SEND_SUGGEST);
        mNetworkService.request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean isSuccess) {
                callback.onSuccess(isSuccess);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void modifyPwd(String oldPwd, String newPwd, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_MEMBER_ID, UserHelper.getMemberId());
        paramMap.put(HttpParasKeyConstant.personalCenter.PARA_OLD_PWD, MD5Util.encode(oldPwd));
        paramMap.put(HttpParasKeyConstant.personalCenter.PARA_NEW_PWD, MD5Util.encode(newPwd));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.PERSONAL_CENTER.METHOD_CHANGE_PWD);
        mNetworkService.request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean isSuccess) {
                callback.onSuccess(isSuccess);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void checkCashSupportVersion(String entityId, final Callback<CommonResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckUpdate.CHECK_SUPPORT_CASH_VERSION);
        mNetworkService.request(requestModel, new CcdNetCallBack<CommonResult>() {
            @Override
            protected void onData(CommonResult commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void sendVerifyCode(String mobile, String phoneArea, String isCheckRegister, int type, boolean isBindMobile, final Callback<SmsCode> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        paramMap.put("country_code", phoneArea);
        paramMap.put("is_require_register", isCheckRegister);
        paramMap.put("type", type);
        if (isBindMobile) {
            paramMap.put("special_tag", "WeiXinLogin");
        }
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_SEND_VERIFY_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SmsCode>() {
            @Override
            protected void onData(SmsCode commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void register(String mobile, String phoneArea, String code, String passWord, final Callback<String> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        paramMap.put("country_code", phoneArea);
        paramMap.put("ver_code", code);
        paramMap.put("password", MD5Util.encode(passWord).toLowerCase());
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_REGISTER);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<String>() {
            @Override
            protected void onData(String commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void findPassWord(String mobile, String phoneArea, String code, String passWord, final Callback<CommonResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("mobile", mobile);
        paramMap.put("country_code", phoneArea);
        paramMap.put("ver_code", code);
        paramMap.put("password", MD5Util.encode(passWord).toLowerCase());
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_FIND_PASS_WORD);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CommonResult>() {
            @Override
            protected void onData(CommonResult commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void isWorking(String entityId, int type, String userId, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("type", type);
        paramMap.put("user_id", userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_IS_WORKING);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void setWorking(String entityId, int type, String userId, int status, final Callback<Integer> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("type", type);
        paramMap.put("user_id", userId);
        paramMap.put("status", status);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_SET_WORKING);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {
            @Override
            protected void onData(Integer commonResult) {
                callback.onSuccess(commonResult);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void checkTakeOutBindSeat(String entityId, String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("user_id", userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, UserDataRepository.METHOD_CHECK_BIND_TAKEOUT_SEAT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Object>() {
            @Override
            protected void onData(Object commonResult) {
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
            }
        });
    }


}
