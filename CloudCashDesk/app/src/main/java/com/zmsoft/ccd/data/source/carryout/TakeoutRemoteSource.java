package com.zmsoft.ccd.data.source.carryout;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;
import com.zmsoft.ccd.network.HttpHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public class TakeoutRemoteSource implements ITakeoutRemoteSource {

    @Override
    public Observable<List<TakeoutMobile>> getTakeoutMobileList(final String entityId) {

        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<TakeoutMobile>>>>() {
            @Override
            public HttpResult<HttpBean<List<TakeoutMobile>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpConstant.TakeoutPhoneList.ENTITY_ID, entityId);
                Type listType = new TypeToken<HttpResult<HttpBean<List<TakeoutMobile>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpConstant.TakeoutPhoneList.METHOD)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<List<TakeoutMobile>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<Boolean> saveTakeoutMobileConfig(final String entityId, final String mobile, final boolean openFlag) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Boolean>>>() {
            @Override
            public HttpResult<HttpBean<Boolean>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpConstant.UpdateTakeoutPhoneSetting.ENTITY_ID, entityId);
                paramMap.put(TakeoutHttpConstant.UpdateTakeoutPhoneSetting.MOBILE, mobile);
                paramMap.put(TakeoutHttpConstant.UpdateTakeoutPhoneSetting.OPEN_FLAG, openFlag);
                Type listType = new TypeToken<HttpResult<HttpBean<Boolean>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpConstant.UpdateTakeoutPhoneSetting.METHOD)
                        .newBuilder().responseType(listType).build();
                ResponseModel<HttpResult<HttpBean<Boolean>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<Boolean> sendTakeoutMobile(final String entityId, final String mobile) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Boolean>>>() {
            @Override
            public HttpResult<HttpBean<Boolean>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(TakeoutHttpConstant.SendTakeoutPhone.ENTITY_ID, entityId);
                paramMap.put(TakeoutHttpConstant.SendTakeoutPhone.MOBILE, mobile);
                Type type = new TypeToken<HttpResult<HttpBean<Boolean>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, TakeoutHttpConstant.SendTakeoutPhone.METHOD)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<Boolean>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }
}
