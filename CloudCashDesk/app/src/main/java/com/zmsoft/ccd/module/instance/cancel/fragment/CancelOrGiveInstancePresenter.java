package com.zmsoft.ccd.module.instance.cancel.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.instance.InstanceSourceRepository;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.network.HttpHelper;

import java.util.List;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:22
 */
public class CancelOrGiveInstancePresenter implements CancelOrGiveInstanceContract.Presenter {

    private static CancelOrGiveInstanceContract.View mView;
    private final InstanceSourceRepository mInstanceSourceRepository;

    @Inject
    public CancelOrGiveInstancePresenter(CancelOrGiveInstanceContract.View view, InstanceSourceRepository instanceSourceRepository) {
        mView = view;
        mInstanceSourceRepository = instanceSourceRepository;
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
    public void cancelInstance(String entityId, String userId, String instanceId, String reason, long modifyTime, double num, double accountNum) {
        mView.showLoading(GlobalVars.context.getString(R.string.canceling_instance), true);
        mInstanceSourceRepository.cancelInstance(entityId, userId, instanceId, reason, modifyTime, num, accountNum, new Callback<CancelOrGiveInstance>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_cancel_instance));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(CancelOrGiveInstance data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.cancelInstanceSuccess(data);
            }
        });
    }

    @Override
    public void giveInstance(String entityId, String userId, String menuId, String reason, long modifyTime, double num, double accountNum) {
        mView.showLoading(GlobalVars.context.getString(R.string.giving_instance), true);
        mInstanceSourceRepository.giveInstance(entityId, userId, menuId, reason, modifyTime, num, accountNum, new Callback<CancelOrGiveInstance>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_give_instance));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(CancelOrGiveInstance data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.giveInstanceSuccess(data);
            }
        });
    }

    @Override
    public void getReasonList(String entityId, String dicCode, int systemType) {
        mInstanceSourceRepository.getReasonList(entityId, dicCode, systemType, new Callback<List<Reason>>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadDataError(body.getMessage());
            }

            @Override
            public void onSuccess(List<Reason> data) {
                if (mView == null) {
                    return;
                }
                mView.getReasonListSuccess(data);
            }
        });
    }


}
