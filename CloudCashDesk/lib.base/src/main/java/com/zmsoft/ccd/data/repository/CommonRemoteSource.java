package com.zmsoft.ccd.data.repository;

import com.chiclaim.modularization.router.annotation.Route;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.BusinessConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.bean.ReasonSortedRequest;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.SwitchRequest;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.print.PrintAreaVo;
import com.zmsoft.ccd.lib.bean.shop.FunctionFieldValue;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.BaseHttpMethodConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.CommonConstant;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * @author DangGui
 * @create 2017/5/4.
 */
@Route(path = BusinessConstant.CommonSource.COMMON_SOURCE)
public class CommonRemoteSource implements ICommonSource {

    @Override
    public Observable<Boolean> saveFunctionSwitchList(final String entityId, final List<SwitchRequest> codeList) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Boolean>>>() {
            @Override
            public HttpResult<HttpBean<Boolean>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("function_field_value_dto_list", JsonHelper.toJson(codeList));
                Type typeBean = new TypeToken<HttpResult<HttpBean<Boolean>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Config.METHOD_SAVE_CONFIG_FUNCATION_FIEL_CODE_LIST)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<Boolean>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<Map<String, String>> getFielCodeByList(final String entityId, final List<String> codeList) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Map<String, String>>>>() {
            @Override
            public HttpResult<HttpBean<Map<String, String>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("field_code_list", JsonHelper.toJson(codeList));
                Type typeBean = new TypeToken<HttpResult<HttpBean<Map<String, String>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Config.METHOD_CONFIG_FUNCATION_FIEL_CODE_LIST)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<Map<String, String>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void checkPermission(int systemType, String actionCode, final Callback<Boolean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(CommonConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(CommonConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(BaseHttpMethodConstant.CheckPermission.PARA_SYSTEM_TYPE, systemType);
        paramMap.put(BaseHttpMethodConstant.CheckPermission.PARA_ACTION_CODE, actionCode);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap
                , BaseHttpMethodConstant.CheckPermission.METHOD_CHECK_PERMISSION);

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

    @Override
    public void batchCheckPermission(int systemType, List<String> actionCodeList, final Callback<HashMap<String, Boolean>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(CommonConstant.PARA_USER_ID, UserHelper.getUserId());
        paramMap.put(CommonConstant.PARA_ENTITY_ID, UserHelper.getEntityId());
        paramMap.put(BaseHttpMethodConstant.CheckPermission.PARA_SYSTEM_TYPE, systemType);
        paramMap.put(BaseHttpMethodConstant.CheckPermission.PARA_BATCH_ACTION_CODE, JsonHelper.toJson(actionCodeList));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap
                , BaseHttpMethodConstant.CheckPermission.METHOD_BATCH_CHECK_PERMISSION);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<HashMap<String, Boolean>>() {
            @Override
            protected void onData(HashMap<String, Boolean> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<File> loadFile(final String loadFilePath, final File outFile) {
        return RxUtils.fromCallable(new Callable<File>() {
            @Override
            public File call() throws Exception {
                NetworkService.getDefault().download(loadFilePath, outFile);
                return outFile;
            }
        });
    }

    @Override
    public void getReasonList(String entityId, String dicCode, int systemType, final Callback<List<Reason>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.Reason.ENTITY_ID, entityId);
        paramMap.put(BaseHttpMethodConstant.Reason.DIC_CODE, dicCode);
        paramMap.put(BaseHttpMethodConstant.Reason.SYSTEM_TYPE, systemType);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Reason.METHOD_REASON_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<Reason>>() {
            @Override
            protected void onData(List<Reason> reverseReasons) {
                callback.onSuccess(reverseReasons);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public void getReasonSortedList(String entityId, String dicCode, int systemType, final Callback<List<Reason>> callback) {
        Map<String, Object> paramMap = new HashMap<>();

        ReasonSortedRequest reasonSortedRequest = new ReasonSortedRequest(entityId, dicCode, systemType, SystemDirCodeConstant.ORDER_BY);
        String requestJson = JsonMapper.toJsonString(reasonSortedRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);

        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.ReasonSorted.METHOD_REASON_LIST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<List<Reason>>() {
            @Override
            protected void onData(List<Reason> reverseReasons) {
                callback.onSuccess(reverseReasons);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorMsg));
            }
        });
    }

    @Override
    public Observable<PrintAreaVo> getPrintBySeatCode(final String entityId, final String seatCode, final int orderKind) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<PrintAreaVo>>>() {
            @Override
            public HttpResult<HttpBean<PrintAreaVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
                if (!StringUtils.isEmpty(seatCode)) {
                    paramMap.put(BaseHttpMethodConstant.Print.SEAT_CODE, seatCode);
                }
                if (StringUtils.isEmpty(seatCode)) {
                    paramMap.put(BaseHttpMethodConstant.Print.ORDER_KIND, orderKind);
                }
                Type typeBean = new TypeToken<HttpResult<HttpBean<PrintAreaVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Print.METHOD_PRINT_BY_AREA_ID)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<PrintAreaVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<FunctionFieldValue> getFunctionFileSwitch(final String entityId, final String fieldCode) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<FunctionFieldValue>>>() {
            @Override
            public HttpResult<HttpBean<FunctionFieldValue>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("field_code", fieldCode);
                Type typeBean = new TypeToken<HttpResult<HttpBean<FunctionFieldValue>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Config.METHOD_CONFIG_FUNCATION_FIEL_CODE)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<FunctionFieldValue>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void getSwitchByList(String entityId, List<String> codeList, final Callback<Map<String, String>> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("code_lst", JsonHelper.toJson(codeList));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Config.METHOD_CONFIG_VAL_BY_LIST_CODE);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Map<String, String>>() {
            @Override
            protected void onData(Map<String, String> data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<String> getConfigSwitchVal(final String entityId, final String systemTypeId, final String code) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<String>>>() {
            @Override
            public HttpResult<HttpBean<String>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("system_type_id", systemTypeId);
                paramMap.put("code", code);
                Type typeBean = new TypeToken<HttpResult<HttpBean<String>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Config.METHOD_CONFIG_VAL_BY_CODE)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<String>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CashLimit> getCashLimit(final String entityId, final String userId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CashLimit>>>() {
            @Override
            public HttpResult<HttpBean<CashLimit>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.CashLimit.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.CashLimit.USER_ID, userId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CashLimit>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.CashLimit.METHOD)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CashLimit>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<List<Reason>> getReasonList(final String entityId, final String dicCode, final int systemType) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<List<Reason>>>>() {
            @Override
            public HttpResult<HttpBean<List<Reason>>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Reason.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.Reason.DIC_CODE, dicCode);
                paramMap.put(BaseHttpMethodConstant.Reason.SYSTEM_TYPE, systemType);
                Type typeBean = new TypeToken<HttpResult<HttpBean<List<Reason>>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Reason.METHOD_REASON_LIST)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<List<Reason>>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void uploadChannelInfo(ChannelInfoRequest channelInfoRequest) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("json", new Gson().toJson(channelInfoRequest));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.ChannelInfo.METHOD_SAVE_APP_INFO);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<Integer>() {
            @Override
            protected void onData(Integer commonResult) {
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
            }
        });
    }
}
