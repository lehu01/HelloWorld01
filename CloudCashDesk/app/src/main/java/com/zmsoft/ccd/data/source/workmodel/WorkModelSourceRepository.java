package com.zmsoft.ccd.data.source.workmodel;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.data.source.user.UserDataSource;
import com.zmsoft.ccd.lib.bean.CommonResult;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/31 10:14
 */
@Singleton
public class WorkModelSourceRepository implements IWorkModelSource {

    private final IWorkModelSource mIWorkModelSource;
    private final UserDataSource mUserDataSource;

    @Inject
    public WorkModelSourceRepository(@Remote IWorkModelSource iWorkModelSource, @Remote UserDataSource userDataSource) {
        this.mIWorkModelSource = iWorkModelSource;
        mUserDataSource = userDataSource;
    }

    public void checkCashSupportVersion(String entityId, Callback<CommonResult> callback) {
        mUserDataSource.checkCashSupportVersion(entityId, callback);
    }

    @Override
    public void getWorkModel(String entityId, List<String> codeList, Callback<Map<String, String>> callback) {
        mIWorkModelSource.getWorkModel(entityId, codeList, callback);
    }

    @Override
    public void saveWorkModelConfig(String entityId, boolean openCloudCash, boolean useLocalCash, String userId, Callback<Boolean> callback) {
        mIWorkModelSource.saveWorkModelConfig(entityId, openCloudCash, useLocalCash, userId, callback);
    }
}
