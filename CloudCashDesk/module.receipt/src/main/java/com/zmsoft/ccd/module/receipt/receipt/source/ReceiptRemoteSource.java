package com.zmsoft.ccd.module.receipt.receipt.source;

import com.chiclaim.modularization.router.annotation.Route;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.BusinessConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.receipt.source.IReceiptSource;
import com.zmsoft.ccd.lib.base.bean.EmptyDiscountResponse;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.checkoutendpay.AfterEndPay;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.constant.HttpMethodConstants;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receipt.model.GetPayStatusRequest;
import com.zmsoft.ccd.module.receipt.receipt.model.GetVoucherInfoRequest;
import com.zmsoft.ccd.network.BaseHttpMethodConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;
import com.zmsoft.ccd.network.JsonHelper;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillRequest;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.OrderPayListRequest;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundRequest;
import com.zmsoft.ccd.receipt.bean.RefundResponse;
import com.zmsoft.ccd.receipt.bean.VerificationRequest;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;
import com.zmsoft.ccd.shop.bean.IndustryType;
import com.zmsoft.lib.pos.allin.helper.AllInTransHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/5/24.
 * <p>
 * 修改文件路径时需要修改
 */
@Route(path = BusinessConstant.ReceiptSource.RECEIPT_SOURCE)
public class ReceiptRemoteSource implements IReceiptSource {
    /**
     * 获取支付状态最多重试次数
     */
    private static final int MAX_RETRY_COUNT = 10;
    /**
     * 获取支付状态次数
     */
    private int mRetryCount = 0;

    @Override
    public void getCloudCash(final String orderId, final Callback<GetCloudCashBillResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetCloudCashBillRequest getCloudCashBillRequest = new GetCloudCashBillRequest(orderId, UserHelper.getEntityId()
                , UserHelper.getUserId());
        if (AllInTransHelper.isSupport()) {
            getCloudCashBillRequest.setPlatform((short) 1);
        }
        //todo 新大陆暂时隐藏入口2017/11/29
//        else if (NewLandTransHelper.isSupport()) {
//            getCloudCashBillRequest.setPlatform((short) 2);
//        }
        String requestJson = JsonMapper.toJsonString(getCloudCashBillRequest);
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_CLOUDCASHBILL);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetCloudCashBillResponse>() {
            @Override
            protected void onData(GetCloudCashBillResponse getCloudCashBillResponse) {
                callback.onSuccess(getCloudCashBillResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getVoucherInfo(String kindPayId, final Callback<GetVoucherInfoResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetVoucherInfoRequest getVoucherInfoRequest = new GetVoucherInfoRequest(kindPayId, UserHelper.getEntityId());
        String requestJson = JsonMapper.toJsonString(getVoucherInfoRequest);
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_VOUCHER_INFO);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetVoucherInfoResponse>() {
            @Override
            protected void onData(GetVoucherInfoResponse getVoucherInfoResponse) {
                callback.onSuccess(getVoucherInfoResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getSignBillSinger(String kindPayId, final Callback<GetSignBillSingerResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetVoucherInfoRequest getVoucherInfoRequest = new GetVoucherInfoRequest(kindPayId, UserHelper.getEntityId());
        String requestJson = JsonMapper.toJsonString(getVoucherInfoRequest);
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_SIGN_BILL_SINGER);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetSignBillSingerResponse>() {
            @Override
            protected void onData(GetSignBillSingerResponse getSignBillSingerResponse) {
                callback.onSuccess(getSignBillSingerResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getSignUnit(String param, final Callback<GetSignBillSingerResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, param);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_SIGN_UNIT);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetSignBillSingerResponse>() {
            @Override
            protected void onData(GetSignBillSingerResponse getSignBillSingerResponse) {
                callback.onSuccess(getSignBillSingerResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void promoteBill(String param, final Callback<CashPromotionBillResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, param);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_PROMOTE_BILL);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CashPromotionBillResponse>() {
            @Override
            protected void onData(CashPromotionBillResponse cashPromotionBillResponse) {
                callback.onSuccess(cashPromotionBillResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getKindDetailInfo(String param, final Callback<GetKindDetailInfoResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, param);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_KINDDETAIL_INFO);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<GetKindDetailInfoResponse>() {
            @Override
            protected void onData(GetKindDetailInfoResponse getKindDetailInfoResponse) {
                callback.onSuccess(getKindDetailInfoResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void collectPay(String param, final Callback<CloudCashCollectPayResponse> callback) {
        //重置次数
        mRetryCount = 0;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, param);
        // TODO: 2017/12/7 餐饮和零售调用不同的接口，暂时在最底层做判断来处理
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap,
                UserHelper.getIndustry() == IndustryType.RETAIL ?
                        HttpMethodConstants.Receipt.METHOD_EMPTY_COLLECT_PAY_FOR_RETAIL : HttpMethodConstants.Receipt.METHOD_EMPTY_COLLECT_PAY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CloudCashCollectPayResponse>() {
            @Override
            protected void onData(final CloudCashCollectPayResponse data) {
                if (null != data) {
                    //如果是正在支付状态（使用第三方支付时比较常见），需要调用getPayStatus进行查询支付状态
                    if (data.getStatus() == ReceiptHelper.PayStatus.PAYING) {
                        lazyGetPayStatus(data, callback);
                    } else {
                        callback.onSuccess(data);
                    }
                } else {
                    callback.onSuccess(null);
                }
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getPayStatus(String snapshotId, String outTradeNo,
                             final Callback<CloudCashCollectPayResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetPayStatusRequest getPayStatusRequest = new GetPayStatusRequest(snapshotId, UserHelper.getEntityId(), outTradeNo);
        String requestJson = JsonMapper.toJsonString(getPayStatusRequest);
        paramMap.put(HttpMethodConstants.Receipt.Paras.PARAS_PARAMS, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Receipt.METHOD_GET_PAY_STATUS);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<CloudCashCollectPayResponse>() {
            @Override
            protected void onData(CloudCashCollectPayResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime
            , final Callback<AfterEndPay> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.Order.ENTITY_ID, entityId);
        paramMap.put(BaseHttpMethodConstant.Order.OPERATOR_ID, operatorId);
        paramMap.put(BaseHttpMethodConstant.Order.ORDER_ID, orderId);
        paramMap.put(BaseHttpMethodConstant.Order.MODIFY_TIME, modifyTime);
        Map<String, Object> temp = new HashMap<>();
        temp.put(BaseHttpMethodConstant.Order.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(temp, BaseHttpMethodConstant.Order.METHOD_ORDER_AFTER_END_PAY);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<AfterEndPay>() {
            @Override
            protected void onData(AfterEndPay afterEndPay) {
                callback.onSuccess(afterEndPay);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime
            , final Callback<AfterEndPay> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.Order.ENTITY_ID, entityId);
        paramMap.put(BaseHttpMethodConstant.Order.OPERATOR_ID, operatorId);
        paramMap.put(BaseHttpMethodConstant.Order.ORDER_ID, orderId);
        paramMap.put(BaseHttpMethodConstant.Order.MODIFY_TIME, modifyTime);
        Map<String, Object> temp = new HashMap<>();
        temp.put(BaseHttpMethodConstant.Order.PARA_PARAM, JsonHelper.toJson(paramMap));
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(temp, BaseHttpMethodConstant.Order.METHOD_ORDER_AFTER_END_PAY_FOR_RETAIL);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<AfterEndPay>() {
            @Override
            protected void onData(AfterEndPay afterEndPay) {
                callback.onSuccess(afterEndPay);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void emptyDiscount(String orderId, final Callback<EmptyDiscountResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        GetCloudCashBillRequest getCloudCashBillRequest = new GetCloudCashBillRequest(orderId, UserHelper.getEntityId()
                , UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(getCloudCashBillRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.Receipt.METHOD_EMPTY_DISCOUNT);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<EmptyDiscountResponse>() {
            @Override
            protected void onData(EmptyDiscountResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void refund(String orderId, List<Refund> refundList, final Callback<RefundResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        RefundRequest refundRequest = new RefundRequest(orderId, UserHelper.getEntityId()
                , UserHelper.getUserId(), refundList, ConfigHelper.hasOpenCashClean(context));
        String requestJson = JsonMapper.toJsonString(refundRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);
        String hostUrl = BaseHttpMethodConstant.Receipt.METHOD_EMPTY_REFUND;
        if (null != refundList && !refundList.isEmpty()) {
            Refund refund = refundList.get(0);
            short payType = refund.getType();
            if (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ) {
                hostUrl = BaseHttpMethodConstant.Receipt.METHOD_E_PAY_REFUND;
            }
        }
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, hostUrl);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<RefundResponse>() {
            @Override
            protected void onData(RefundResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void refund(String orderId, final List<Refund> refundList, boolean checkLimit, final Callback<RefundResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        RefundRequest refundRequest = new RefundRequest(orderId, UserHelper.getEntityId()
                , UserHelper.getUserId(), refundList, checkLimit);
        String requestJson = JsonMapper.toJsonString(refundRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);
        String hostUrl = BaseHttpMethodConstant.Receipt.METHOD_EMPTY_REFUND;
        if (null != refundList && !refundList.isEmpty()) {
            Refund refund = refundList.get(0);
            short payType = refund.getType();
            if (payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY
                    || payType == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_QQ) {
                hostUrl = BaseHttpMethodConstant.Receipt.METHOD_E_PAY_REFUND;
            }
        }
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, hostUrl);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<RefundResponse>() {
            @Override
            protected void onData(RefundResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void verification(String orderId, String promotionCode, final Callback<VerificationResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        VerificationRequest verificationRequest = new VerificationRequest(UserHelper.getEntityId()
                , promotionCode, orderId, UserHelper.getUserId(), (short) 0);
        String requestJson = JsonMapper.toJsonString(verificationRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);
        String hostUrl = HttpMethodConstants.Receipt.METHOD_VERIFICATION;
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, hostUrl);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<VerificationResponse>() {
            @Override
            protected void onData(VerificationResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getOrderPayedList(String orderId, final Callback<OrderPayListResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        OrderPayListRequest orderPayListRequest = new OrderPayListRequest(orderId, UserHelper.getEntityId()
                , UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(orderPayListRequest);
        paramMap.put(BaseHttpMethodConstant.Receipt.Paras.PARAS_PARAMS, requestJson);
        String hostUrl = HttpMethodConstants.Receipt.METHOD_ORDER_PAYLIST;
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, hostUrl);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<OrderPayListResponse>() {
            @Override
            protected void onData(OrderPayListResponse data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    private void lazyGetPayStatus(final CloudCashCollectPayResponse data
            , final Callback<CloudCashCollectPayResponse> callback) {
        //最多重试10次
        if (mRetryCount > MAX_RETRY_COUNT) {
            callback.onFailed(new ErrorBody(ReceiptHelper.CollectThirdPayOuttime.ERROR_CODE_OUTTIME
                    , context.getString(R.string.module_receipt_third_outtime)));
        } else {
            RxUtils.fromCallable(new Callable<Void>() {
                @Override
                public Void call() {
                    return null;
                }
            }).delay(2, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            mRetryCount++;
                            getPayStatus(data.getSnapshotId(), data.getOutTradeNo(), new Callback<CloudCashCollectPayResponse>() {
                                @Override
                                public void onSuccess(CloudCashCollectPayResponse data) {
                                    if (data.getStatus() == ReceiptHelper.PayStatus.PAYING) {
                                        lazyGetPayStatus(data, callback);
                                    } else {
                                        callback.onSuccess(data);
                                    }
                                }

                                @Override
                                public void onFailed(ErrorBody body) {
                                    lazyGetPayStatus(data, callback);
                                }
                            });
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        }
    }
}
