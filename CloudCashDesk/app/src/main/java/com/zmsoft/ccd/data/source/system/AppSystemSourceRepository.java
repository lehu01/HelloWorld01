package com.zmsoft.ccd.data.source.system;

import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/20 14:18
 */
@Singleton
public class AppSystemSourceRepository implements IAppSystemSource {

    private final IAppSystemSource mIAppSystemSource;

    @Inject
    public AppSystemSourceRepository(@Remote IAppSystemSource iAppSystemSource) {
        mIAppSystemSource = iAppSystemSource;
    }

    @Override
    public void checkAppUpdate(String entityId, String appCode, int version, Callback<CashUpdateInfoDO> callback) {
        mIAppSystemSource.checkAppUpdate(entityId, appCode, version, callback);
    }

    @Override
    public void checkNetworkLatency(String entityId, String appCode, int version, CheckNetworkLatencyCallback callback) {
        mIAppSystemSource.checkNetworkLatency(entityId, appCode, version, callback);
    }
}
