package com.zmsoft.ccd.data.source.system;

import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.zmsoft.ccd.data.Callback;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/20 14:18
 */
public interface IAppSystemSource {

    /**
     * 检测更新
     *
     * @param entityId 实体id
     * @param appCode  appCode
     * @param version  version
     * @param callback 回调
     */
    void checkAppUpdate(String entityId, String appCode, int version, Callback<CashUpdateInfoDO> callback);

    void checkNetworkLatency(String entityId, String appCode, int version, CheckNetworkLatencyCallback callback);

    interface CheckNetworkLatencyCallback {
        void onResponse(long duration);
        void onFailure(Throwable throwable);
    }
}
