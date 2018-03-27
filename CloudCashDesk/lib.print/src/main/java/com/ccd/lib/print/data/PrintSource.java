package com.ccd.lib.print.data;

import com.ccd.lib.print.bean.UploadPrintNum;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.google.gson.reflect.TypeToken;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.bean.HttpBean;
import com.zmsoft.ccd.lib.base.bean.HttpResult;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.print.PrintAreaVo;
import com.zmsoft.ccd.lib.bean.print.PrintOrderVo;
import com.zmsoft.ccd.lib.bean.print.PrintTestVo;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.BaseHttpMethodConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/12 10:27
 */
public class PrintSource implements IPrintSource {

    private static final String METHOD_UPLOAD_PRINT_NUM_COUNT = "com.dfire.tp.client.service.ITradePlatformService.incrTotalPayPrintNum";
    private static final String METHOD_PRINT_DISHES_ORDER = "com.dfire.soa.cloudcash.sendMessage";
    private static final String METHOD_PRINT_ACCOUNT_ORDER = "com.dfire.soa.cloudcash.sendCustomerPageMessage";
    private static final String METHOD_PRINT_LOCAL_CASH_TEST = "com.dfire.soa.cloudcash.sendPrintTestPageMessage";
    private static final String METHOD_PRINT_PRINT_FINANCE = "com.dfire.soa.cloudcash.sendPrintFinancePageMessage";
    private static final String METHOD_PRINT_PRINT_BILL_BY_MESSAGE = "com.dfire.soa.cloudcash.sendPrintBillMessage";
    private static final String METHOD_PRINT_PRINT_BILL = "com.dfire.soa.cloudcash.printAccountBill";
    private static final String METHOD_PRINT_PRINT_CHANGE_SEAT = "com.dfire.soa.cloudcash.printChangeSeat";
    private static final String METHOD_LOCK_PRINT_TASK = "com.dfire.soa.cloudcash.lockPrintTask";
    private static final String METHOD_UPDATE_PRINT_TASK_STATUS = "com.dfire.soa.cloudcash.updateTaskStatus";

    @Override
    public Observable<Boolean> updateTaskStatus(final String entityId, final String userId, final String id, final short status, final String memo) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Boolean>>>() {
            @Override
            public HttpResult<HttpBean<Boolean>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("op_user_id", userId);
                paramMap.put("id", id);
                paramMap.put("status", status);
                if (!StringUtils.isEmpty(memo)) {
                    paramMap.put("memo", memo);
                }
                Type typeBean = new TypeToken<HttpResult<HttpBean<Boolean>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_UPDATE_PRINT_TASK_STATUS)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<Boolean>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<Boolean> lockPrintTask(final String entityId, final String userId,
                                             final String taskId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<Boolean>>>() {
            @Override
            public HttpResult<HttpBean<Boolean>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("user_id", userId);
                paramMap.put("print_task_id", taskId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<Boolean>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_LOCK_PRINT_TASK)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<Boolean>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintBill(final String entityId,
                                                                    final int type, final String opUserId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CommonResult>>>() {
            @Override
            public HttpResult<HttpBean<CommonResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("type", type);
                paramMap.put("operate_user_id", opUserId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CommonResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_PRINT_BILL_BY_MESSAGE)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CommonResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintFinanceOrder(
            final String entityId, final String orderId, final String opUserId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CommonResult>>>() {
            @Override
            public HttpResult<HttpBean<CommonResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("order_id", orderId);
                paramMap.put("operate_user_id", opUserId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CommonResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_PRINT_FINANCE)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CommonResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintTest(final String entityId,
                                                                    final String opUserId, final String ip, final int type,
                                                                    final int rowCount) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CommonResult>>>() {
            @Override
            public HttpResult<HttpBean<CommonResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("operate_user_id", opUserId);
                if (!StringUtils.isEmpty(ip)) {
                    paramMap.put("ip", ip);
                }
                paramMap.put("printer_type", type);
                paramMap.put("row_char_max_num", rowCount);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CommonResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_LOCAL_CASH_TEST)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CommonResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        }, false);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintDishesOrder(
            final String entityId, final String orderId, final int type, final String opUserId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CommonResult>>>() {
            @Override
            public HttpResult<HttpBean<CommonResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("order_id", orderId);
                paramMap.put("operate_type", type);
                paramMap.put("operate_user_id", opUserId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CommonResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_DISHES_ORDER)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CommonResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintAccountOrder(
            final String entityId, final String orderId, final String opUserId) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<CommonResult>>>() {
            @Override
            public HttpResult<HttpBean<CommonResult>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entity_id", entityId);
                paramMap.put("order_id", orderId);
                paramMap.put("operate_user_id", opUserId);
                Type typeBean = new TypeToken<HttpResult<HttpBean<CommonResult>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_ACCOUNT_ORDER)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<CommonResult>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<PrintOrderVo> printBillOrder(final String entityId, final String userId,
                                                   final int billType) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<PrintOrderVo>>>() {
            @Override
            public HttpResult<HttpBean<PrintOrderVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.Print.USER_ID, userId);
                paramMap.put("bill_date_type", billType);
                Type typeBean = new TypeToken<HttpResult<HttpBean<PrintOrderVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_PRINT_BILL)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<PrintOrderVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<UploadPrintNum> uploadPrintNum(final String entityId,
                                                     final String orderId, final int type) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<UploadPrintNum>>>() {
            @Override
            public HttpResult<HttpBean<UploadPrintNum>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("entityId", entityId);
                paramMap.put("orderId", orderId);
                paramMap.put("type", type);
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("param", JsonHelper.toJson(paramMap));
                Type typeBean = new TypeToken<HttpResult<HttpBean<UploadPrintNum>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(tempMap, METHOD_UPLOAD_PRINT_NUM_COUNT)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<UploadPrintNum>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<PrintOrderVo> printOrder(final int type, final String entityId,
                                               final String orderId, final String userId, final int reprint) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<PrintOrderVo>>>() {
            @Override
            public HttpResult<HttpBean<PrintOrderVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Print.DOCUMENT_TYPE, type);
                paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.Print.ORDER_ID, orderId);
                paramMap.put(BaseHttpMethodConstant.Print.USER_ID, userId);
                paramMap.put("reprint", reprint);
                Type typeBean = new TypeToken<HttpResult<HttpBean<PrintOrderVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Print.METHOD_PRINT_ORDER_SELF)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<PrintOrderVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public Observable<PrintOrderVo> printInstance(final int type, final String entityId,
                                                  final List<String> instanceIds, final String userId
            , final int reprint) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<PrintOrderVo>>>() {
            @Override
            public HttpResult<HttpBean<PrintOrderVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Print.DOCUMENT_TYPE, type);
                paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.Print.INSTANCE_ID_LIST, JsonHelper.toJson(instanceIds));
                paramMap.put(BaseHttpMethodConstant.Print.USER_ID, userId);
                paramMap.put("reprint", reprint);
                Type typeBean = new TypeToken<HttpResult<HttpBean<PrintOrderVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Print.METHOD_PRINT_INSTANCE_SELF)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<PrintOrderVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }

    @Override
    public void printTest(String entityId, final Callback<PrintTestVo> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Print.METHOD_PRINT_TEST);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<PrintTestVo>() {
            @Override
            protected void onData(PrintTestVo data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public Observable<PrintAreaVo> getPrintBySeatCode(final String entityId,
                                                      final String seatCode, final int orderKind) {
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
    public Observable<PrintOrderVo> printChangeSeat(final String entityId,
                                                    final String orderId, final String userId, final String oldSeatCode) {
        return RxUtils.wrapCallable(new Callable<HttpResult<HttpBean<PrintOrderVo>>>() {
            @Override
            public HttpResult<HttpBean<PrintOrderVo>> call() throws Exception {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(BaseHttpMethodConstant.Print.ENTITY_ID, entityId);
                paramMap.put(BaseHttpMethodConstant.Print.ORDER_ID, orderId);
                paramMap.put(BaseHttpMethodConstant.Print.USER_ID, userId);
                if (!StringUtils.isEmpty(oldSeatCode)) {
                    paramMap.put("old_seat_code", oldSeatCode);
                }
                Type typeBean = new TypeToken<HttpResult<HttpBean<PrintOrderVo>>>() {
                }.getType();
                RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, METHOD_PRINT_PRINT_CHANGE_SEAT)
                        .newBuilder().responseType(typeBean).build();
                ResponseModel<HttpResult<HttpBean<PrintOrderVo>>> responseModel = NetworkService.getDefault().request(requestModel);
                return responseModel.data();
            }
        });
    }
}
