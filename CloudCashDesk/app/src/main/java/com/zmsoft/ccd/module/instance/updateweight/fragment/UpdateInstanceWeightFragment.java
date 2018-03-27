package com.zmsoft.ccd.module.instance.updateweight.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.instance.updateweight.UpdateInstanceWeightActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:25
 */
public class UpdateInstanceWeightFragment extends BaseFragment implements UpdateInstanceWeightContract.View {

    @BindView(R.id.text_instance_name)
    TextView mTextInstanceName;
    @BindView(R.id.text_instance_origin_weight)
    TextView mTextInstanceOriginWeight;
    @BindView(R.id.text_instance_origin_weight_unit)
    TextView mTextInstanceOriginWeightUnit;
    @BindView(R.id.text_instance_new_weight_unit)
    TextView mTextInstanceNewWeightUnit;
    @BindView(R.id.edit_instance_new_weight)
    EditFoodNumberView mEditInstanceNewWeight;

    private Instance mInstance;
    private double mWeight;
    private UpdateInstanceWeightPresenter mUpdateInstanceWeightPresenter;

    public static UpdateInstanceWeightFragment newInstance(Instance instance) {
        UpdateInstanceWeightFragment fragment = new UpdateInstanceWeightFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateInstanceWeightActivity.EXTRA_INSTANCE, instance);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_instance_weight;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBundleData();
        updateView(mInstance);
    }

    private void initBundleData() {
        Bundle bundle = getArguments();
        mInstance = (Instance) bundle.getSerializable(UpdateInstanceWeightActivity.EXTRA_INSTANCE);
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void unBindPresenterFromView() {
        if (mUpdateInstanceWeightPresenter != null) {
            mUpdateInstanceWeightPresenter.unsubscribe();
        }
    }

    private boolean check() {
        if (!mEditInstanceNewWeight.hasInput()) {
            showToast(getString(R.string.instance_weight_not_null));
            return false;
        }
        mWeight = mEditInstanceNewWeight.getNumber();
        if (mWeight == 0) {
            showToast(getString(R.string.instance_weight_must_greater_than_zero));
            return false;
        }
        if (mWeight < 0) {
            showToast(getString(R.string.instance_weight_can_not_low_zero));
            return false;
        }
        return true;
    }

    private void updateView(Instance instance) {
        if (instance != null) {
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mEditInstanceNewWeight.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            mEditInstanceNewWeight.getEditText().setLayoutParams(ll);
            mEditInstanceNewWeight.getEditText().setPadding(50, 0, 30, 0);
            CustomViewUtil.initEditViewFocousAll(mEditInstanceNewWeight.getEditText());

            mTextInstanceName.setText(instance.getName());
            mTextInstanceOriginWeight.setText(String.valueOf(FeeHelper.getDecimalFee(instance.getAccountNum())));
            mTextInstanceOriginWeightUnit.setText(String.format(getString(R.string.origin_weight), instance.getAccountUnit()));
            mTextInstanceNewWeightUnit.setText(String.format(getString(R.string.new_weight), instance.getAccountUnit()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void save() {
        if (check()) {
            mUpdateInstanceWeightPresenter.updateInstanceWeight(UserHelper.getEntityId()
                    , mInstance.getId()
                    , UserHelper.getUserId()
                    , mInstance.getModifyTime()
                    , mWeight);
        }
    }

    @Override
    public void setPresenter(UpdateInstanceWeightContract.Presenter presenter) {
        mUpdateInstanceWeightPresenter = (UpdateInstanceWeightPresenter) presenter;
    }

    @Override
    public void updateInstanceWeightSuccess(UpdateInstanceWeight updateInstanceWeight) {
        showToast(getString(R.string.update_instance_weight_success));
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
        getActivity().finish();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }
}
