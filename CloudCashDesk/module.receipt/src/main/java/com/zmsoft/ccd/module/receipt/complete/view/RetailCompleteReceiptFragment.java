package com.zmsoft.ccd.module.receipt.complete.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.complete.presenter.RetailCompleteReceiptContract;
import com.zmsoft.ccd.module.receipt.constant.ExtraConstants;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;
import com.zmsoft.ccd.receipt.bean.Pay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 收款完成
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class RetailCompleteReceiptFragment extends BaseFragment implements RetailCompleteReceiptContract.View {

    @BindView(R2.id.button_back)
    Button mButtonBack;
    @BindView(R2.id.button_over)
    Button mButtonOver;
    @BindView(R2.id.rootView)
    LinearLayout mRootView;
    @BindView(R2.id.text_receivable)
    TextView mTextReceivable;
    @BindView(R2.id.text_actual_received)
    TextView mTextActualReceived;

    private RetailCompleteReceiptContract.Presenter mPresenter;

    /**
     * 订单ID
     */
    private String mOrderId;

    //应收金额(单位 元)
    private double mReceiveableFee;

    /**
     * 订单修改时间
     */
    private long mModifyTime;

    /**
     * 收款模式，普通收款/快捷收款
     */
    private short mCollectMode;

    private short mIsTakeout;

    public static RetailCompleteReceiptFragment newInstance(String orderId, long modifyTime, double receiveableFee) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putLong(ExtraConstants.CompleteReceipt.EXTRA_MODIFY_TIME, modifyTime);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, receiveableFee);
        RetailCompleteReceiptFragment fragment = new RetailCompleteReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_retail_complete_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
            mModifyTime = bundle.getLong(ExtraConstants.CompleteReceipt.EXTRA_MODIFY_TIME);
            mReceiveableFee = bundle.getDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE);
        }
    }

    @Override
    protected void initListener() {
        RxView.clicks(mButtonBack).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        notifyFindOrder();
                        //跳转到找单页面
                        MRouter.getInstance().build(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY).navigation(getActivity());
                    }
                });
        RxView.clicks(mButtonOver).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mIsTakeout == 0) {
                            if (mCollectMode == ReceiptHelper.CollectPayMode.NORMAL) {
                                gotoCreateOrderActivity();
                            } else if (mCollectMode == ReceiptHelper.CollectPayMode.SHORTCUT) {
                                MRouter.getInstance().build(RouterPathConstant.RetailShortCutReceipt.PATH)
                                        .navigation(getActivity());
                                if (isHostActive()) {
                                    getActivity().finish();
                                }
                            }
                        } else {
                            //通知线上订单列表刷新
                            EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_NOTIFY_TAKEOUT_LIST);
                            if (isHostActive()) {
                                getActivity().finish();
                                clearOtherActivity();
                            }
                        }
                    }
                });
    }

    private void gotoCreateOrderActivity() {
        OrderParam param = new OrderParam();
        param.setSeatCode(RetailOrderHelper.getDefaultRetailSeatCode());
        MRouter.getInstance().build(RouterPathConstant.RetailMenuList.PATH)
                .putInt(RouterPathConstant.RetailMenuList.FROM, RouterPathConstant.RetailMenuList.FROM_COMPLETE_RECEIPT)
                .putSerializable(RouterPathConstant.RetailMenuList.EXTRA_CREATE_ORDER_PARAM, param)
                .navigation(getActivity());

        if (isHostActive()) {
            getActivity().finish();
        }
    }

    /**
     * 跳转外卖界面
     */
    private void gotoTakeoutActivity() {
        MRouter.getInstance().build(RouterPathConstant.RetailTakeoutList.PATH)
                .navigation(getActivity());
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        loadData();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    protected void loadData() {
        mPresenter.getOrderPayList(mOrderId);
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadData();
    }

    @Override
    public void setPresenter(RetailCompleteReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successGetPayList(OrderPayListResponse orderPayListResponse) {
        mRootView.setVisibility(View.VISIBLE);
        if (mReceiveableFee < 0) {
            mReceiveableFee = 0;
        }
        if (null != orderPayListResponse) {
            mTextReceivable.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFeeByFen(orderPayListResponse.getNeedFee())));
            List<Pay> pays = orderPayListResponse.getPays();
            if (null != pays) {
                StringBuilder payBuilder = new StringBuilder();
                for (int i = 0; i < pays.size(); i++) {
                    Pay pay = pays.get(i);
                    if (null != pay) {
                        payBuilder.append(pay.getName());
                        payBuilder.append(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFeeByFen(pay.getFee())));
                        if (i != pays.size() - 1) {
                            payBuilder.append(getResources().getString(R.string.comma_separator));
                        }
                    }
                }
                mTextActualReceived.setText(payBuilder.toString());
            }
            mCollectMode = orderPayListResponse.getCollectPayMode();
            mIsTakeout = orderPayListResponse.getTakeout();
            if (mIsTakeout == 1) {
                mButtonBack.setVisibility(View.GONE);
                mButtonOver.setText(R.string.module_receipt_retail_complete);
            } else {
                if (mCollectMode == ReceiptHelper.CollectPayMode.NORMAL) {
                    mButtonOver.setText(R.string.module_receipt_retail_goon);
                } else if (mCollectMode == ReceiptHelper.CollectPayMode.SHORTCUT) {
                    mButtonOver.setText(R.string.module_receipt_goon);
                }
            }
            //快捷收款的订单已付清之后自动结账完毕
            mPresenter.afterEndPayForRetail(UserHelper.getEntityId(), UserHelper.getUserId(), mOrderId, mModifyTime);
        }
    }

    @Override
    public void failGetPayList(String failMessage) {
        showErrorView(failMessage);
    }

    @Override
    public void successCompletePay() {
        if (mIsTakeout == 0) {
            CcdPrintHelper.manualPrintOrder(getActivity()
                    , PrintData.BIZ_TYPE_PRINT_RETAIL_FINANCE_ORDER
                    , mOrderId);
        }
        notifyFindOrder();
    }

    /**
     * 通知找单页
     */
    private void notifyFindOrder() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
    }

    private void clearOtherActivity() {
        List<String> notCloseList = new ArrayList<>(2);
        notCloseList.add(RouterPathConstant.PATH_RETAIL_MAIN_ACTIVITY);
        notCloseList.add(RouterPathConstant.RetailTakeoutList.PATH);
        RouterActivityManager.get().finishAllActivityExcept(notCloseList);
    }
}
