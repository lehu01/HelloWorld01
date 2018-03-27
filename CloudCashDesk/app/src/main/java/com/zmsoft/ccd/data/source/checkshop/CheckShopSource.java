package com.zmsoft.ccd.data.source.checkshop;

import android.os.Build;
import android.text.TextUtils;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.BuildConfig;
import com.zmsoft.ccd.app.AppEnv;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.constant.AppConstant;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.shop.Shop;
import com.zmsoft.ccd.lib.bean.shop.request.BindShopListRequest;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.lib.utils.DeviceUtil;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.CommonConstant;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/3 17:48
 */
@Singleton
public class CheckShopSource implements ICheckShopSource {

    @Override
    public void getBindShopList(String memberId, final Callback<List<Shop>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_MEMBER_ID, memberId);
        paramMap.put(HttpParasKeyConstant.PARAM_APPKEY_STR, AppEnv.getApiKey());
        paramMap.put(HttpParasKeyConstant.QUERY_BIND_ENTITY_LIST.APP_KEY_STR, AppEnv.getApiKey());
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckShop.METHOD_GET_BIND_SHOP_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<Shop>>() {
            @Override
            protected void onData(List<Shop> shops) {
                callback.onSuccess(shops);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

//    @Override
//    public void bindShop(BindShopRequest request, final Callback<CompositeLoginResultVo> callback) {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("login_type", request.getLogin_type());
//        paramMap.put("member_user_id", request.getMember_user_id());
//
//        Map<String, Object> tempMap = new HashMap<>();
//        tempMap.put("deviceId", StringUtils.isEmpty(DeviceUtil.getDeviceId(GlobalVars.context)) ? CommonConstant.UNKNOWN : DeviceUtil.getDeviceId(GlobalVars.context));
//        tempMap.put("appKey", AppEnv.getApiKey());
//        tempMap.put("sBR", String.format("%s-%s-%s", Build.MANUFACTURER, Build.BRAND, Build.MODEL));
//        tempMap.put("sOS", CommonConstant.ANDROID);
//        paramMap.put("base_param", JsonHelper.toJson(tempMap));
//
//        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckShop.METHOD_BIND_SHOP1);
//        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CompositeLoginResultVo>() {
//            @Override
//            protected void onData(CompositeLoginResultVo user) {
//                callback.onSuccess(user);
//            }
//
//            @Override
//            protected void onError(String errorCode, String errorMsg) {
//                callback.onFailed(new ErrorBody(errorCode, errorMsg));
//            }
//        });
//    }

    @Override
    public void bindShop(String memberId, String userId, String entityId, String originalUserId, final Callback<User> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_MEMBER_ID, memberId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_DEVICE_ID, DeviceUtil.getDeviceId(DeviceUtil.getDeviceId(GlobalVars.context)));
        paramMap.put(HttpParasKeyConstant.PARA_CLIENT_TYPE, AppConstant.ANDROID);
//        paramMap.put(HttpParasKeyConstant.PARA_IP, DeviceUtil.getIPAddress(true));
//        String mac = DeviceUtil.getMACAddress(null);
//        if (!StringUtils.isEmpty(mac)) {
//            paramMap.put(HttpParasKeyConstant.PARA_MAC, mac);
//        }
//        paramMap.put(HttpParasKeyConstant.PARA_BRAND, Build.BRAND);
//        paramMap.put(HttpParasKeyConstant.PARAM_ISKICK, true);
        if (!TextUtils.isEmpty(originalUserId))
            paramMap.put(HttpParasKeyConstant.PARAM_ORIGINAL_USER_ID, originalUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.CheckShop.METHOD_BIND_SHOP);
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
}
