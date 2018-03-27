package com.zmsoft.ccd.data.source.print;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Singleton
public class PrintConfigSource implements IPrintConfigSource {

    @Override
    public void saveLabelPrintConfig(String entityId, String userId, int printerType, String ip, final Callback<LabelPrinterConfig> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("user_id", userId);
        paramMap.put("printer_type", printerType);
        if (!StringUtils.isEmpty(ip)) {
            paramMap.put("ip", ip);
        }
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Print.METHOD_SAVE_LABEL_PRINT_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<LabelPrinterConfig>() {
            @Override
            protected void onData(LabelPrinterConfig printerSetting) {
                callback.onSuccess(printerSetting);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getLabelPrintConfig(String entityId, String userId, final Callback<LabelPrinterConfig> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put("user_id", userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Print.METHOD_GET_LABEL_PRINT_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<LabelPrinterConfig>() {
            @Override
            protected void onData(LabelPrinterConfig printerSetting) {
                callback.onSuccess(printerSetting);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getPrintConfig(String entityId, String userId, final Callback<SmallTicketPrinterConfig> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpParasKeyConstant.PARA_ENTITY_ID, entityId);
        paramMap.put("user_id", userId);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Print.METHOD_GET_PRINT_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SmallTicketPrinterConfig>() {
            @Override
            protected void onData(SmallTicketPrinterConfig printerSetting) {
                callback.onSuccess(printerSetting);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void savePrintConfig(String entityId, String userId, int printerType, String ip, int rowCharMaxMun,
                                short isLocalCashPrint, short type, final Callback<SmallTicketPrinterConfig> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("entity_id", entityId);
        paramMap.put("user_id", userId);
        paramMap.put("printer_type_enum", printerType);
        if (!StringUtils.isEmpty(ip)) {
            paramMap.put("ip", ip);
        }
        paramMap.put("row_char_max_num", rowCharMaxMun);
        paramMap.put("is_local_cash_print", isLocalCashPrint);
        paramMap.put("type", type);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.Print.METHOD_SAVE_PRINT_CONFIG);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<SmallTicketPrinterConfig>() {
            @Override
            protected void onData(SmallTicketPrinterConfig printerSetting) {
                callback.onSuccess(printerSetting);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
