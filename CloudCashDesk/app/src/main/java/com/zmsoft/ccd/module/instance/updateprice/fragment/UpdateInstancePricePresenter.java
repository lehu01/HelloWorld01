package com.zmsoft.ccd.module.instance.updateprice.fragment;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.instance.InstanceSourceRepository;
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;
import com.zmsoft.ccd.network.HttpHelper;

import javax.inject.Inject;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
public class UpdateInstancePricePresenter implements UpdateInstancePriceContract.Presenter {

    private UpdateInstancePriceContract.View mView;
    private final InstanceSourceRepository mInstanceSourceRepository;

    @Inject
    public UpdateInstancePricePresenter(UpdateInstancePriceContract.View view, InstanceSourceRepository instanceSourceRepository) {
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
    public void updateInstancePrice(String entityId, String instanceId, String opUserId, long modifyTime, int fee) {
        mView.showLoading(GlobalVars.context.getString(R.string.updating), true);
        mInstanceSourceRepository.updateInstancePrice(entityId, instanceId, opUserId, modifyTime, fee, new Callback<UpdateInstancePrice>() {
            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (HttpHelper.HttpErrorCode.ERR_PERMISSION_ERROR.equals(body.getErrorCode())) {
                    mView.loadDataError(GlobalVars.context.getString(R.string.permission_update_instance_price));
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }

            @Override
            public void onSuccess(UpdateInstancePrice data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.updateInstancePriceSuccess(data);
            }
        });
    }
}
