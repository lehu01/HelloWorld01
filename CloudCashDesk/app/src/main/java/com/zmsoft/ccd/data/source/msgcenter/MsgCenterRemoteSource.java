package com.zmsoft.ccd.data.source.msgcenter;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.message.AuditOrderRequest;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.lib.bean.message.SendMessage;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;


/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Singleton
public class MsgCenterRemoteSource implements IMsgCenterSource {

    @Override
    public void getMessageList(int msgCategory, int pageIndex, final com.zmsoft.ccd.data.Callback<List<DeskMessage>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(HttpParasKeyConstant.PAGE_INDEX, pageIndex);
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_STATUS, msgCategory);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_GET_MESSAGE_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<DeskMessage>>() {
            @Override
            protected void onData(List<DeskMessage> deskMessageList) {
                callback.onSuccess(deskMessageList);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void batchUpdateMessage(String messageIdListJson, int status, String resultMsg, final com.zmsoft.ccd.data.Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_STATUS, status);
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_MESSAGE_ID_LIST, messageIdListJson);
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_RESULT_MESSAGE, resultMsg);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_BATCH_UPDATE_MESSAGE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Boolean>() {
            @Override
            protected void onData(Boolean result) {
                callback.onSuccess(true);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void loadMsgDetail(String messageId, final com.zmsoft.ccd.data.Callback<DeskMsgDetail> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_MESSAGE_ID, messageId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_MESSAGE_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<DeskMsgDetail>() {
            @Override
            protected void onData(DeskMsgDetail deskMsgDetail) {
                if (deskMsgDetail != null) {
                    callback.onSuccess(deskMsgDetail);
                }
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void handleMessage(String messageId, boolean isAgree, final com.zmsoft.ccd.data.Callback<AuditOrderResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        AuditOrderRequest auditOrderRequest = new AuditOrderRequest(UserHelper.getEntityId()
                , isAgree, messageId, UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(auditOrderRequest);
        paramMap.put(HttpParasKeyConstant.PARA_PARAM, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap
                , HttpMethodConstants.MESSAGE_CENTER.METHOD_MESSAGE_AUDIT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<AuditOrderResponse>() {
            @Override
            protected void onData(AuditOrderResponse result) {
                callback.onSuccess(result);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void sendMessage(String entityId, String orderId, int type, String opUserId, final Callback<SendMessage> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.PARA_OPERATE_TYPE, type);
        paramMap.put(HttpParasKeyConstant.PARA_OPERATE_USER_ID, opUserId);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_SEND_MESSAGE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SendMessage>() {
            @Override
            protected void onData(SendMessage result) {
                callback.onSuccess(result);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void printAccountOrder(String entityId, String orderId, String opUserId, final Callback<CommonResult> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put(HttpParasKeyConstant.PARA_ORDER_ID, orderId);
        paramMap.put(HttpParasKeyConstant.PARA_OPERATE_USER_ID, opUserId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_PRINT_ACCOUNT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CommonResult>() {
            @Override
            protected void onData(CommonResult result) {
                callback.onSuccess(result);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void loadTakeoutMsgDetail(String msgId, final Callback<TakeoutMsgDetailResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(HttpParasKeyConstant.messageCenter.PARA_MESSAGE_ID, msgId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.MESSAGE_CENTER.METHOD_TAKE_OUT_MESSAGE_DETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<TakeoutMsgDetailResponse>() {
            @Override
            protected void onData(TakeoutMsgDetailResponse takeoutMsgDetailResponse) {
                if (takeoutMsgDetailResponse != null) {
                    callback.onSuccess(takeoutMsgDetailResponse);
                }
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
