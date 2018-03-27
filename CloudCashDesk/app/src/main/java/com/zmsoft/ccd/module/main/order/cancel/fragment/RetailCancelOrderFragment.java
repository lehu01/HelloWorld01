package com.zmsoft.ccd.module.main.order.cancel.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.ReasonHelper;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.module.main.order.cancel.RetailCancelOrderActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 16:03
 */
public class RetailCancelOrderFragment extends BaseFragment implements RetailCancelOrderContract.View {

    @BindView(R.id.text_seat_name)
    TextView mTextSeatName;
    @BindView(R.id.text_order_number)
    TextView mTextOrderNumber;
    @BindView(R.id.text_serial_number)
    TextView mTextSerialNumber;
    @BindView(R.id.text_reason_title)
    TextView mTextReasonTitle;
    @BindView(R.id.text_cancel_reason)
    TextView mTextCancelReason;
    @BindView(R.id.relative_cancel_reason)
    RelativeLayout mRelativeCancelReason;
    @BindView(R.id.button_ok)
    Button mButtonOk;

    private String mOrderId;
    private String mSeatName;
    private int mOrderNumber;
    private long mModifyTime;
    private String mSerialNumber;

    private OptionsPickerView mOptionsPickerView;
    private RetailCancelOrderPresenter mPresenter;

    private boolean mIsSubmitOk = false; // 是否直接操作
    private String mReason;

    public static RetailCancelOrderFragment newInstance(String orderId, String seatName, int orderNumber, long modifyTime, String serialNumber) {
        RetailCancelOrderFragment fragment = new RetailCancelOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_ID, orderId);
        bundle.putString(RouterPathConstant.RetailCancelOrder.EXTRA_SEAT_NAME, seatName);
        bundle.putInt(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_NUMBER, orderNumber);
        bundle.putLong(RouterPathConstant.RetailCancelOrder.EXTRA_MODIFY_TIME, modifyTime);
        bundle.putString(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_SERIAL_NUMBER, serialNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_retail_cancel_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBundleData();
        updateView();
    }

    private void updateView() {
        mTextSeatName.setText(StringUtils.getNullStr(mSeatName));
        mTextOrderNumber.setText(StringUtils.appendStr(getString(R.string.No), mOrderNumber));
        mTextSerialNumber.setText(StringUtils.getNullStr(mSerialNumber));
    }

    private void initBundleData() {
        Bundle bundle = getArguments();
        mOrderId = bundle.getString(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_ID);
        mSeatName = bundle.getString(RouterPathConstant.RetailCancelOrder.EXTRA_SEAT_NAME);
        mOrderNumber = bundle.getInt(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_NUMBER);
        mModifyTime = bundle.getLong(RouterPathConstant.RetailCancelOrder.EXTRA_MODIFY_TIME);
        mSerialNumber = bundle.getString(RouterPathConstant.RetailCancelOrder.EXTRA_ORDER_SERIAL_NUMBER);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
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

    @OnClick(R.id.button_ok)
    void cancelOrder() {
        mReason = mTextCancelReason.getText().toString().trim();
        if (StringUtils.isEmpty(mReason)) {
            mIsSubmitOk = true;
            getReasonList();
        } else {
            submitOk();
        }
    }

    @OnClick(R.id.relative_cancel_reason)
    void cancelReason() {
        mIsSubmitOk = false;
        getReasonList();
    }

    @Override
    public void setPresenter(RetailCancelOrderContract.Presenter presenter) {
        mPresenter = (RetailCancelOrderPresenter) presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void cancelOrderSuccess(CancelOrder cancelOrder) {
        showToast(getString(R.string.cancel_order_success));
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void cancelOrderReasonListSuccess(List<Reason> list) {
        if (mIsSubmitOk) { // 点击ok
            showReasonForOK(list);
        } else { // 选择原因
            showReasonForSelect(list);
        }
    }

    private void showReasonForOK(List<Reason> list) {
        if (list != null && list.size() > 0) {
            showToast(getString(R.string.please_cancel_order_reason_prompt));
        } else {
            submitOk();
        }
    }

    private void showReasonForSelect(List<Reason> list) {
        if (list != null && list.size() > 0) {
            showReasonDialog(list);
        } else {
            showToast(getString(R.string.cancel_order_reason_prompt));
        }
    }

    private void showReasonDialog(final List<Reason> list) {
        if (mOptionsPickerView == null) {
            mOptionsPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), R.string.cancel_order_reason);
            mOptionsPickerView.setOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    if (options1 > list.size() - 1) {
                        return;
                    }
                    mTextCancelReason.setText(list.get(options1).getName());
                }
            });
        }
        mOptionsPickerView.setSelectOptions(ReasonHelper.getCheckIndex(mTextCancelReason.getText().toString().trim(), list));//默认选中项
        mOptionsPickerView.setPicker(list);
        mOptionsPickerView.show();
    }

    private void submitOk() {
        mPresenter.cancelOrder(UserHelper.getEntityId(), mOrderId, UserHelper.getUserId(), mModifyTime, mReason);
    }

    private void getReasonList() {
        mPresenter.getReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.CZ_REASON, SystemDirCodeConstant.TYPE_SYSTEM);
    }
}
