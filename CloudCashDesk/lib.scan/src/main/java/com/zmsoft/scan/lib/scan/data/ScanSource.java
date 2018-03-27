package com.zmsoft.scan.lib.scan.data;

import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;
import com.zmsoft.ccd.network.BaseHttpMethodConstant;
import com.zmsoft.ccd.network.CcdNetCallBack;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/21 10:42
 */
public class ScanSource implements IScanSource {

    @Override
    public void getScanSeatByQr(String paramString, final Callback<ScanSeat> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.QRcode.PARA_STRING, paramString);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.QRcode.METHOD_QR_SCAN_SEAT);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ScanSeat>() {
            @Override
            protected void onData(ScanSeat data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }

    @Override
    public void getScanMenuByQr(String paramString, final Callback<ScanMenu> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BaseHttpMethodConstant.QRcode.PARA_STRING, paramString);
        RequestModel requestModel = HttpHelper.getDefaultRequestModel(paramMap, BaseHttpMethodConstant.QRcode.METHOD_QR_SCAN_MENU);
        NetworkService.getDefault().request(requestModel, new CcdNetCallBack<ScanMenu>() {
            @Override
            protected void onData(ScanMenu data) {
                callback.onSuccess(data);
            }

            @Override
            protected void onError(String errorCode, String errorMsg) {
                callback.onFailed(new ErrorBody(errorCode, errorMsg));
            }
        });
    }
}
