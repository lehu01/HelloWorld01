package com.zmsoft.ccd.data.source.desk;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import rx.Observable;

/**
 * Description：桌位 的 Model层
 * <br/>
 * Created by kumu on 2017/2/20.
 */
@Singleton
public class DeskRemoteSource implements IDeskSource {

    public void loadAllDeskList(final com.zmsoft.ccd.data.Callback<List<SeatArea>> callback) {
        getDeskList(HttpMethodConstants.ATTENTION_DESK.METHOD_QUERY_ALL_SEATS, callback);
    }

    public void loadWatchDeskList(final com.zmsoft.ccd.data.Callback<List<SeatArea>> callback) {
        getDeskList(HttpMethodConstants.ATTENTION_DESK.METHOD_QUERY_ALL_SEATS, callback);
    }

    @Override
    public Observable<List<TabMenuVO>> loadWatchAreaList(final String entityId, final String userId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<TabMenuVO>>>>() {
            @Override
            public HttpResult<HttpBean<List<TabMenuVO>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
                paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
                Type type = new TypeToken<HttpResult<HttpBean<List<TabMenuVO>>>>() {}.getType();

                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ATTENTION_DESK.METHOD_QUERY_WATCH_AREA)
                        .newBuilder().responseType(type).build();
                ResponseModel<HttpResult<HttpBean<List<TabMenuVO>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }


    private void getDeskList(String method, final com.zmsoft.ccd.data.Callback<List<SeatArea>> callback) {
        //参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, method);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<SeatArea>>() {
            @Override
            protected void onData(List<SeatArea> allSeatsResponse) {
                if (allSeatsResponse != null) {
                    callback.onSuccess(allSeatsResponse);
                } else {
                    callback.onSuccess(null);
                }
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void bindCheckedSeats(String jsonString, final com.zmsoft.ccd.data.Callback<String> callback) {
        //参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(HttpParasKeyConstant.ATTENTION_DESK_MESSAGE_CENTER.PARA_SEAT_ID_LIST, jsonString);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ATTENTION_DESK.METHOD_BIND_SEAT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<String>() {

            @Override
            protected void onData(String data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void unBindSeats(List<ViewHolderSeat> deleteDeskList, String unBindjsonString, final com.zmsoft.ccd.data.Callback<String> callback) {
        //参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.ATTENTION_DESK_MESSAGE_CENTER.PARA_SEAT_ID_LIST, unBindjsonString);
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        // 登录接口
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ATTENTION_DESK.METHOD_UNBIND_SEAT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<String>() {
            @Override
            protected void onData(String data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
