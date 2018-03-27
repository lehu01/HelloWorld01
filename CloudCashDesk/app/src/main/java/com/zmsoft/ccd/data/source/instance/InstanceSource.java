package com.zmsoft.ccd.data.source.instance;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 16:43
 */
@Singleton
public class InstanceSource implements IInstanceSource {

    @Override
    public void updateInstancePrice(String entityId, String instanceId, String opUserId, long modifyTime, int fee,
                                    final Callback<UpdateInstancePrice> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.INSTANCE_ID, instanceId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        paramMap.put(HttpParasKeyConstant.Hump.FEE, fee);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Instance.METHOD_UPDATE_INSTANCE_PRICE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<UpdateInstancePrice>() {
            @Override
            protected void onData(UpdateInstancePrice updateInstancePrice) {
                callback.onSuccess(updateInstancePrice);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void updateInstanceWeight(String entityId, String instanceId, String opUserId, long modifyTme, double weight,
                                     final Callback<UpdateInstanceWeight> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.INSTANCE_ID, instanceId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTme);
        paramMap.put(HttpParasKeyConstant.Hump.WEIGHT, weight);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Instance.METHOD_UPDATE_INSTANCE_WEIGHT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<UpdateInstanceWeight>() {
            @Override
            protected void onData(UpdateInstanceWeight data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void cancelInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime, double num, double accountNum,
                               final Callback<CancelOrGiveInstance> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.INSTANCE_ID, instanceId);
        paramMap.put(HttpParasKeyConstant.Hump.MEMO, reason);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        paramMap.put(HttpParasKeyConstant.Hump.NUM, num);
        paramMap.put(HttpParasKeyConstant.Hump.ACCOUNT_NUM, accountNum);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Instance.METHOD_CANCEL_INSTANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CancelOrGiveInstance>() {
            @Override
            protected void onData(CancelOrGiveInstance data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void giveInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime, double num, double accountNum,
                             final Callback<CancelOrGiveInstance> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.Hump.ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.Hump.OP_USER_ID, opUserId);
        paramMap.put(HttpParasKeyConstant.Hump.INSTANCE_ID, instanceId);
        paramMap.put(HttpParasKeyConstant.Hump.MEMO, reason);
        paramMap.put(HttpParasKeyConstant.Hump.MODIFY_TIME, modifyTime);
        paramMap.put(HttpParasKeyConstant.Hump.NUM, num);
        paramMap.put(HttpParasKeyConstant.Hump.ACCOUNT_NUM, accountNum);
        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put(HttpParasKeyConstant.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, HttpMethodConstants.Instance.METHOD_GIVE_INSTANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CancelOrGiveInstance>() {
            @Override
            protected void onData(CancelOrGiveInstance data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void pushInstance(String entityId, String userId, List<String> menuIdList, String customerRegisterId, String seatCode, String orderId, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, userId);
        paramMap.put(HttpParasKeyConstant.PARA_INSTANCE_ID_LIST, JsonHelper.toJson(menuIdList));
        if (!StringUtils.isEmpty(customerRegisterId)) {
            paramMap.put(HttpParasKeyConstant.PARA_CUSTOMER_REGISTER_ID, customerRegisterId);
        }
        if (!StringUtils.isEmpty(seatCode)) {
            paramMap.put(HttpParasKeyConstant.PARA_SEAT_CODE, seatCode);
        }
        paramMap.put(HttpParasKeyConstant.PARA_ORDER_ID, orderId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Instance.METHOD_PUSH_INSTANCE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
