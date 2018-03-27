package com.zmsoft.ccd.module.instance.updateweight.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.instance.InstanceSourceRepository;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;
import com.zmsoft.ccd.network.HttpHelper;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/12 14:20
 */
public class UpdateInstanceWeightPresenter implements UpdateInstanceWeightContract.Presenter {

    private UpdateInstanceWeightContract.View mView;
    private final InstanceSourceRepository mInstanceSourceRepository;

    @Inject
    public UpdateInstanceWeightPresenter(UpdateInstanceWeightContract.View view, InstanceSourceRepository instanceSourceRepository) {
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
    public void updateInstanceWeight(String entityId, String instanceId, String opUserId, long modifyTme, double weight) {
        mView.showLoading(GlobalVars.context.getString(R.string.updating), true);
        mInstanceSourceRepository.updateInstanceWeight(entityId, instanceId, opUserId, modifyTme, weight, new Callback<UpdateInstanceWeight>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_update_instance_weight));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(UpdateInstanceWeight data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.updateInstanceWeightSuccess(data);
            }
        });
    }
}
