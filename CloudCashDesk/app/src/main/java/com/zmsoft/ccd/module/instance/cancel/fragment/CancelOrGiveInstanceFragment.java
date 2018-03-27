package com.zmsoft.ccd.module.instance.cancel.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.helper.ReasonHelper;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.view.CustomViewUtil;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.module.instance.cancel.CancelOrGiveInstanceActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:17
 */
public class CancelOrGiveInstanceFragment extends BaseFragment implements CancelOrGiveInstanceContract.View {

    @BindView(R.id.text_instance_name)
    TextView mTextInstanceName;
    @BindView(R.id.text_cancel_instance_number_unit)
    TextView mTextCancelInstanceNumberUnit;
    @BindView(R.id.edit_instance_number)
    EditFoodNumberView mEditInstanceNumber;
    @BindView(R.id.text_cancel_instance_weight_unit)
    TextView mTextCancelInstanceWeightUnit;
    @BindView(R.id.edit_instance_weight)
    EditFoodNumberView mEditInstanceWeight;
    @BindView(R.id.relative_instance_weight)
    RelativeLayout mRelativeInstanceWeight;
    @BindView(R.id.text_reason_title)
    TextView mTextReasonTitle;
    @BindView(R.id.text_cancel_reason)
    TextView mTextCancelReason;
    @BindView(R.id.relative_cancel_reason)
    RelativeLayout mRelativeCancelReason;
    @BindView(R.id.text_give_ps)
    TextView mTextGivePs;


    private Instance mInstance;
    private String mForm;
    private OptionsPickerView mOptionsPickerView;
    private CancelOrGiveInstancePresenter mPresenter;
    private double mNum = 0d;
    private double mAccountNum = 0d;
    private boolean mIsSubmitOk = false; // 是否直接提交赠送菜肴或退菜
    private String mReason;


    public static CancelOrGiveInstanceFragment newInstance(Instance instance, String from) {
        CancelOrGiveInstanceFragment fragment = new CancelOrGiveInstanceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CancelOrGiveInstanceActivity.EXTRA_INSTANCE, instance);
        bundle.putString(CancelOrGiveInstanceActivity.EXTRA_FROM, from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel_instance;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBundleData();
        updateView(mInstance);
    }

    private void initBundleData() {
        Bundle bundle = getArguments();
        mInstance = (Instance) bundle.getSerializable(CancelOrGiveInstanceActivity.EXTRA_INSTANCE);
        mForm = bundle.getString(CancelOrGiveInstanceActivity.EXTRA_FROM);
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

    @OnClick(R.id.relative_cancel_reason)
    void selectCancelReason() {
        mIsSubmitOk = false;
        getReasonList();
    }

    public void ok() {
        // init
        mNum = mEditInstanceNumber.getNumber();
        mAccountNum = mEditInstanceWeight.getNumber();

        // check
        if (check(mNum, mAccountNum)) {
            mReason = mTextCancelReason.getText().toString().trim();
            if (StringUtils.isEmpty(mReason)) { // 原因未选择
                mIsSubmitOk = true;
                getReasonList();
            } else { // 原因选择了
                submitOk();
            }
        }
    }

    /**
     * 获取赠菜/退菜原因列表
     */
    private void getReasonList() {
        if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
            mPresenter.getReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.TC_REASON, SystemDirCodeConstant.TYPE_SYSTEM);
        } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
            mPresenter.getReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.PRESENT_REASON, SystemDirCodeConstant.TYPE_SYSTEM);
        }
    }

    /**
     * 赠菜/退菜接口
     */
    private void submitOk() {
        if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
            if (Base.INT_TRUE == mInstance.getIsTwoAccount()) { // 双单位菜
                mPresenter.cancelInstance(UserHelper.getEntityId()
                        , UserHelper.getUserId()
                        , mInstance.getId()
                        , mTextCancelReason.getText().toString().trim()
                        , mInstance.getModifyTime()
                        , mNum
                        , mAccountNum);
            } else {
                mPresenter.cancelInstance(UserHelper.getEntityId()
                        , UserHelper.getUserId()
                        , mInstance.getId()
                        , mTextCancelReason.getText().toString().trim()
                        , mInstance.getModifyTime()
                        , mNum
                        , mNum);
            }
        } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
            if (Base.INT_TRUE == mInstance.getIsTwoAccount()) { // 双单位菜
                mPresenter.giveInstance(UserHelper.getEntityId()
                        , UserHelper.getUserId()
                        , mInstance.getId()
                        , mTextCancelReason.getText().toString().trim()
                        , mInstance.getModifyTime()
                        , mNum
                        , mAccountNum);
            } else {
                mPresenter.giveInstance(UserHelper.getEntityId()
                        , UserHelper.getUserId()
                        , mInstance.getId()
                        , mTextCancelReason.getText().toString().trim()
                        , mInstance.getModifyTime()
                        , mNum
                        , mNum);
            }
        }
    }

    private boolean check(double num, double accountNum) {
        boolean isGiveFlag = false;
        if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
            isGiveFlag = true;
        }
        // 退菜数量
        if (num == 0) {
            if (isGiveFlag) {
                showToast(getString(R.string.give_instance_num_is_not_null));
            } else {
                showToast(getString(R.string.cancel_instance_num_is_not_null));
            }
            return false;
        }
        if (num > mInstance.getNum()) {
            if (isGiveFlag) {
                showToast(getString(R.string.give_instance_num_big));
            } else {
                showToast(getString(R.string.cancel_instance_num_big));
            }
            return false;
        }
        // 双单位
        if (Base.INT_TRUE == mInstance.getIsTwoAccount()) {
            if (accountNum > mInstance.getAccountNum()) {
                if (isGiveFlag) {
                    showToast(getString(R.string.give_instance_weight_big));
                } else {
                    showToast(getString(R.string.cancel_instance_weight_big));
                }
                return false;
            }
            if (num == mInstance.getNum() && accountNum < mInstance.getAccountNum()) {
                if (isGiveFlag) {
                    showToast(getString(R.string.give_instance_num_all));
                } else {
                    showToast(getString(R.string.cancel_instance_num_all));
                }
                return false;
            }
            if (accountNum == mInstance.getAccountNum() && num < mInstance.getNum()) {
                if (isGiveFlag) {
                    showToast(getString(R.string.give_instance_weight_all));
                } else {
                    showToast(getString(R.string.cancel_instance_weight_all));
                }
                return false;
            }
        }
        return true;
    }

    private void updateView(Instance instance) {
        if (instance != null) {
            mTextInstanceName.setText(instance.getName());
            mEditInstanceNumber.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            mEditInstanceWeight.getEditText().setGravity(Gravity.CENTER_VERTICAL | Gravity.END);

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            mEditInstanceNumber.getEditText().setLayoutParams(ll);
            mEditInstanceWeight.getEditText().setLayoutParams(ll);
            mEditInstanceNumber.getEditText().setPadding(50, 0, 30, 0);
            mEditInstanceWeight.getEditText().setPadding(50, 0, 30, 0);

            mEditInstanceNumber.setNumberText(instance.getNum());
            mEditInstanceWeight.setNumberText(instance.getAccountNum());
            CustomViewUtil.initEditViewFocousAll(mEditInstanceNumber.getEditText());
            CustomViewUtil.initEditViewFocousAll(mEditInstanceWeight.getEditText());

            if (Base.INT_TRUE == mInstance.getIsTwoAccount()) { // 双单位才有重量
                mRelativeInstanceWeight.setVisibility(View.VISIBLE);
            } else {
                mRelativeInstanceWeight.setVisibility(View.GONE);
            }

            if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
                getActivity().setTitle(getString(R.string.cancel_instance));
                mTextCancelInstanceNumberUnit.setText(String.format(getString(R.string.cancel_instance_number), instance.getUnit()));
                mTextCancelInstanceWeightUnit.setText(String.format(getString(R.string.cancel_instance_weight), instance.getAccountUnit()));
                mTextReasonTitle.setText(getString(R.string.cancel_instance_reason));
                mTextGivePs.setVisibility(View.GONE);
            } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
                getActivity().setTitle(getString(R.string.give_instance));
                mTextCancelInstanceNumberUnit.setText(String.format(getString(R.string.give_instance_number), instance.getUnit()));
                mTextCancelInstanceWeightUnit.setText(String.format(getString(R.string.give_instance_weight), instance.getAccountUnit()));
                mTextReasonTitle.setText(getString(R.string.give_instance_reason));
                mTextGivePs.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showReasonDialog(final List<Reason> list) {
        String title = "";
        if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
            title = getString(R.string.cancel_instance_reason);
        } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
            title = getString(R.string.give_instance_reason);
        }
        if (mOptionsPickerView == null) {
            mOptionsPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), title);
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

    @Override
    public void setPresenter(CancelOrGiveInstanceContract.Presenter presenter) {
        mPresenter = (CancelOrGiveInstancePresenter) presenter;
    }

    @Override
    public void cancelInstanceSuccess(CancelOrGiveInstance cancelOrGiveInstance) {
        showToast(getString(R.string.cancel_success));
        // 打印退菜单
        CcdPrintHelper.printInstance(getActivity()
                , PrintData.TYPE_ORDER
                , PrintData.BIZ_TYPE_PRINT_CANCEL_INSTANCE
                , Arrays.asList(mInstance.getId()));
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
        getActivity().finish();
    }

    @Override
    public void giveInstanceSuccess(CancelOrGiveInstance cancelOrGiveInstance) {
        showToast(getString(R.string.give_success));
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
        getActivity().finish();
    }

    @Override
    public void getReasonListSuccess(List<Reason> list) {
        if (mIsSubmitOk) { // 点击ok
            showReasonForOK(list);
        } else { // 选择原因
            showReasonForSelect(list);
        }
    }

    private void showReasonForOK(List<Reason> list) {
        if (list != null && list.size() > 0) {
            if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
                showToast(getString(R.string.please_select_cancel_instance_reason_prompt));
            } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
                showToast(getString(R.string.please_select_give_instance_reason_prompt));
            }
        } else {
            submitOk();
        }
    }

    private void showReasonForSelect(List<Reason> list) {
        if (list != null && list.size() > 0) {
            showReasonDialog(list);
        } else {
            if (CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE.equals(mForm)) {
                showToast(getString(R.string.cancel_instance_reason_prompt));
            } else if (CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE.equals(mForm)) {
                showToast(getString(R.string.give_instance_reason_prompt));
            }
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }
}
