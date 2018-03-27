package com.zmsoft.ccd.module.shortcutreceipt;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.EditFoodNumberView;
import com.zmsoft.ccd.lib.widget.softkeyboard.CustomSoftKeyboardView;

import butterknife.BindView;

/**
 * 快捷收款
 *
 * @author DangGui
 * @create 2017/8/2.
 */

public class ShortCutReceiptFragment extends BaseFragment implements ShortCutReceiptContract.View, CustomSoftKeyboardView.OnKeyboardClickListener {

    @BindView(R.id.edit_actual_received)
    EditFoodNumberView mEditActualReceived;
    @BindView(R.id.customsoftkeyboardview)
    CustomSoftKeyboardView mCustomSoftKeyboardView;
    @BindView(R.id.text_actual_received)
    TextView mActualTextView;

    private ShortCutReceiptContract.Presenter mPresenter;

    public static ShortCutReceiptFragment newInstance() {
        Bundle args = new Bundle();
        ShortCutReceiptFragment fragment = new ShortCutReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setPresenter(ShortCutReceiptContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shortcut_receipt_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mCustomSoftKeyboardView.init(getActivity(), mEditActualReceived.getEditText());
        mCustomSoftKeyboardView.setOnKeyboardClickListener(this);
        mActualTextView.setText(UserHelper.getCurrencySymbol());
    }

    public void initListener() {
        mEditActualReceived.setOnInputExceedListener(new EditFoodNumberView.OnInputExceedListener() {
            @Override
            public void OnInputExceed(double maxValue) {
                showToast(R.string.shortcut_receipt_actual_receive_exceed_error_alert);
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void onConfimClicked() {
        shortCutReceipt();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successReceipt(ShortCutReceiptResponse shortCutReceiptResponse) {
        if (shortCutReceiptResponse.isNumOutOfLimit() && !TextUtils.isEmpty(shortCutReceiptResponse.getNumOutOfLimitTip())) {
            showToast(shortCutReceiptResponse.getNumOutOfLimitTip());
        }
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
        MRouter.getInstance().build(RouterPathConstant.Receipt.PATH)
                .putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, shortCutReceiptResponse.getOrderId())
                .navigation(getActivity());
        getActivity().finish();
    }

    @Override
    public void failReceipt(String errorMessage) {

    }

    @Override
    public void showFailReceiptDialog(String errorMessage) {
        // 是否有关注的零售单
        if (BaseSpHelper.isWatchedRetail(getActivity())) {
            showRetailOrderDialog(errorMessage);
        } else {
            showRetailOrderDialogByNoWatched();
        }
    }

    private void shortCutReceipt() {
        double actualReceiveFee = mEditActualReceived.getNumber();
        if (actualReceiveFee <= 0) {
            ToastUtils.showShortToast(getActivity(), R.string.shortcut_receipt_actual_receive_error_alert);
            return;
        }
        int fee = (int) Double.parseDouble(FeeHelper.getDecimalFeeByYuan(actualReceiveFee));
        mPresenter.shortCutReceipt(fee);
    }

    /**
     * 零售单弹窗提示：没有关注
     */
    private void showRetailOrderDialogByNoWatched() {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , getString(R.string.prompt_no_watched_retail)
                , R.string.dialog_hint_know
                , false
                , new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                        }
                    }
                });
    }

    /**
     * 零售单弹窗提示
     */
    private void showRetailOrderDialog(String message) {
        getDialogUtil().showDialog(R.string.prompt
                , message
                , R.string.go_deal_with
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            EventBusHelper.post(BaseEvents.CommonEvent.EVENT_SWITCH_WATCHED_RETAIL);
                            getActivity().finish();
                        }
                    }
                });
    }
}
