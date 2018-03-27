package com.zmsoft.ccd.module.instance.updateprice.fragment;

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
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.module.instance.updateprice.UpdateInstancePriceIActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:24
 */
public class UpdateInstancePriceFragment extends BaseFragment implements UpdateInstancePriceContract.View {

    @BindView(R.id.text_instance_name)
    TextView mTextInstanceName;
    @BindView(R.id.text_instance_origin_price)
    TextView mTextInstanceOriginPrice;
    @BindView(R.id.text_instance_origin_price_unit)
    TextView mTextInstanceOriginPriceUnit;
    @BindView(R.id.edit_instance_new_price_unit)
    TextView mEditInstanceNewPriceUnit;
    @BindView(R.id.edit_instance_new_price)
    EditFoodNumberView mEditInstanceNewPrice;

    private Instance mInstance;
    private double mNewPrice;

    private UpdateInstancePricePresenter mUpdateInstancePricePresenter;

    public static UpdateInstancePriceFragment newInstance(Instance instance) {
        UpdateInstancePriceFragment mUpdateInstancePriceFragment = new UpdateInstancePriceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UpdateInstancePriceIActivity.EXTRA_INSTANCE, instance);
        mUpdateInstancePriceFragment.setArguments(bundle);
        return mUpdateInstancePriceFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_instance_price;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBundleData();
        updateView(mInstance);
    }

    private void initBundleData() {
        Bundle bundle = getArguments();
        mInstance = (Instance) bundle.getSerializable(UpdateInstancePriceIActivity.EXTRA_INSTANCE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        if (mUpdateInstancePricePresenter != null) {
            mUpdateInstancePricePresenter.unsubscribe();
        }
    }

    private void updateView(Instance instance) {
        if (instance != null) {
            mEditInstanceNewPrice.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mEditInstanceNewPrice.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            mEditInstanceNewPrice.getEditText().setLayoutParams(ll);
            mEditInstanceNewPrice.getEditText().setPadding(50, 0, 30, 0);
            CustomViewUtil.initEditViewFocousAll(mEditInstanceNewPrice.getEditText());

            mTextInstanceName.setText(instance.getName());
            mTextInstanceOriginPrice.setText(String.valueOf(FeeHelper.getDecimalFee(instance.getPrice())));
            mTextInstanceOriginPriceUnit.setText(String.format(getString(R.string.origin_price), instance.getUnit()));
            mEditInstanceNewPriceUnit.setText(String.format(getString(R.string.new_price), instance.getUnit()));
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

    private boolean check() {
        if (!mEditInstanceNewPrice.hasInput()) {
            showToast(getString(R.string.instance_price_not_null));
            return false;
        }
        mNewPrice = mEditInstanceNewPrice.getNumber();

        if (mNewPrice == 0) {
            showToast(getString(R.string.instance_price_must_greater_than_zero));
            return false;
        }
        if (mNewPrice < 0) {
            showToast(getString(R.string.instance_price_can_not_low_zero));
            return false;
        }
        return true;
    }

    public void save() {
        if (check()) {
            mUpdateInstancePricePresenter.updateInstancePrice(UserHelper.getEntityId()
                    , mInstance.getId()
                    , UserHelper.getUserId()
                    , mInstance.getModifyTime()
                    , (int) (mNewPrice * 100));
        }
    }

    @Override
    public void setPresenter(UpdateInstancePriceContract.Presenter presenter) {
        mUpdateInstancePricePresenter = (UpdateInstancePricePresenter) presenter;
    }

    @Override
    public void updateInstancePriceSuccess(UpdateInstancePrice updateInstancePrice) {
        showToast(getString(R.string.update_instance_price_success));
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
        getActivity().finish();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }
}
