package com.zmsoft.ccd.data.source.shortcutreceipt;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.constant.HttpMethodConstants;
import com.zmsoft.ccd.constant.HttpParasKeyConstant;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptRequest;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * @author DangGui
 * @create 2017/8/2.
 */
@Singleton
public class ShortCutReceiptRemoteSource implements IShortCutReceiptSource {

    @Override
    public void shortCutReceipt(int fee, final Callback<ShortCutReceiptResponse> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        ShortCutReceiptRequest shortCutReceiptRequest = new ShortCutReceiptRequest(fee, UserHelper.getEntityId()
                , UserHelper.getUserId());
        String requestJson = JsonMapper.toJsonString(shortCutReceiptRequest);
        paramMap.put(HttpParasKeyConstant.PARA_PARAM, requestJson);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, HttpMethodConstants.ShortCutReceipt.METHOD_SHORT_CUT_RECEIPT);

        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ShortCutReceiptResponse>() {
            @Override
            protected void onData(ShortCutReceiptResponse shortCutReceiptResponse) {
                callback.onSuccess(shortCutReceiptResponse);
            }

            @Override
            protected void onError(final String errorCode, final String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
