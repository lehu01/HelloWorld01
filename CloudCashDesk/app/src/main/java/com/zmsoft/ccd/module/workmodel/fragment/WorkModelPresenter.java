package com.zmsoft.ccd.module.workmodel.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.workmodel.WorkModelSourceRepository;
import com.zmsoft.ccd.network.ErrorBizHttpCode;
import com.zmsoft.ccd.lib.bean.CommonResult;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/24 14:42
 */
public class WorkModelPresenter implements WorkModelContract.Presenter {

    private WorkModelContract.View mView;
    private final WorkModelSourceRepository mRepository;

    @Inject
    public WorkModelPresenter(WorkModelContract.View view, WorkModelSourceRepository repository) {
        this.mView = view;
        mRepository = repository;
    }

    @Inject
    void setPresenter() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void getWorkModelConfig(String entityId, List<String> codeList) {
        mRepository.getWorkModel(entityId, codeList, new Callback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data) {
                if (mView == null) {
                    return;
                }
                mView.getWorkModelConfigSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.showLoadDataErrorView(body.getMessage());
            }
        });
    }

    @Override
    public void checkCashSupportVersion(String entityId) {
        mView.showLoading(true);
        mRepository.checkCashSupportVersion(entityId, new Callback<CommonResult>() {
            @Override
            public void onSuccess(CommonResult data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.localCashVersionSupport();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (ErrorBizHttpCode.CLOUDCASH_1049.equals(body.getErrorCode()) || ErrorBizHttpCode.CLOUDCASH_1051.equals(body.getErrorCode())) {
                    mView.showDialogPrompt(body.getMessage());
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

    @Override
    public void saveWorkModelConfig(String entityId, boolean openCloudCash, boolean useLocalCash, String userId) {
        mView.showLoading(GlobalVars.context.getString(R.string.saving), true);
        mRepository.saveWorkModelConfig(entityId, openCloudCash, useLocalCash, userId, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.saveWorkModelConfigSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (ErrorBizHttpCode.CLOUDCASH_1049.equals(body.getErrorCode())
                        || ErrorBizHttpCode.CLOUDCASH_1050.equals(body.getErrorCode())
                        || ErrorBizHttpCode.CLOUDCASH_1052.equals(body.getErrorCode())) {
                    mView.showDialogPrompt(body.getMessage());
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }
}
