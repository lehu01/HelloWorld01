package com.zmsoft.ccd.module.receipt.complete.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.data.business.IQuickOpenOrderCallBack;
import com.zmsoft.ccd.data.business.IQuickOpenOrderRouter;
import com.zmsoft.ccd.data.business.IQuickOpenOrderSource;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.complete.presenter.CompletReceiptContract;
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

public class CompletReceiptFragment extends BaseFragment implements CompletReceiptContract.View {

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

    private CompletReceiptContract.Presenter mPresenter;

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
    /**
     * 当有足够的付款金额时是否自动结账完毕
     */
    private boolean mAutoCheckout;

    @Autowired(name = IQuickOpenOrderRouter.QuickOpenOrder.QUICK_OPEN_ORDER)
    IQuickOpenOrderSource mIQuickOpenOrderSource;

    public static CompletReceiptFragment newInstance(String orderId, long modifyTime, double receiveableFee) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putLong(ExtraConstants.CompleteReceipt.EXTRA_MODIFY_TIME, modifyTime);
        args.putDouble(ExtraConstants.NormalReceipt.EXTRA_NEED_FEE, receiveableFee);
        CompletReceiptFragment fragment = new CompletReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_complete_layout;
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
                        MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY).navigation(getActivity());
                    }
                });
        RxView.clicks(mButtonOver).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mCollectMode == ReceiptHelper.CollectPayMode.NORMAL) {
                            if (mAutoCheckout) {
                                OrderParam param = new OrderParam();
                                param.setSeatCode(RetailOrderHelper.getDefaultRetailSeatCode());
                                if (BaseSpHelper.isQuickOpenOrder(getActivity())) {
                                    quickOpenOrder(param);
                                } else {
                                    gotoOpenOrder(param);
                                }
                                return;
                            }
                            showLoading(getString(R.string.dialog_waiting), false);
                            mPresenter.afterEndPay(UserHelper.getEntityId(), UserHelper.getUserId(), mOrderId, mModifyTime);
                        } else if (mCollectMode == ReceiptHelper.CollectPayMode.SHORTCUT) {
                            MRouter.getInstance().build(RouterPathConstant.ShortCutReceipt.PATH)
                                    .navigation(getActivity());
                            if (isHostActive()) {
                                getActivity().finish();
                            }
                        }
                    }
                });
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
    public void setPresenter(CompletReceiptContract.Presenter presenter) {
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
            mAutoCheckout = orderPayListResponse.isAutoCheckout();
            if (mCollectMode == ReceiptHelper.CollectPayMode.NORMAL) {
                if (mAutoCheckout) {
                    // 当有足够的付款金额时是否自动结账完毕
                    mButtonOver.setText(R.string.module_receipt_open_order_again);
                    mPresenter.afterEndPay(UserHelper.getEntityId(), UserHelper.getUserId(), mOrderId, mModifyTime);
                } else {
                    mButtonOver.setText(R.string.module_receipt_complete_over);
                }
            } else if (mCollectMode == ReceiptHelper.CollectPayMode.SHORTCUT) {
                mButtonOver.setText(R.string.module_receipt_goon);
                //快捷收款的订单已付清之后自动结账完毕
                mPresenter.afterEndPay(UserHelper.getEntityId(), UserHelper.getUserId(), mOrderId, mModifyTime);
            }
        }
    }

    @Override
    public void failGetPayList(String failMessage) {
        showErrorView(failMessage);
    }

    @Override
    public void successCompletePay() {
        CcdPrintHelper.manualPrintOrder(getActivity()
                , PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER
                , mOrderId);
        notifyFindOrder();
        if (mCollectMode != ReceiptHelper.CollectPayMode.SHORTCUT) {
            if (mAutoCheckout) {
                return;
            }
            // 跳转到找单页面
            MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY).navigation(getActivity());
        }
    }

    /**
     * 通知找单页
     */
    private void notifyFindOrder() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
    }

    private void quickOpenOrder(OrderParam orderParam) {
        if (orderParam != null) {
            mIQuickOpenOrderSource.doQuickOpenOrder(getActivity(), orderParam, new IQuickOpenOrderCallBack() {
                @Override
                public void quickOpenOrderSuccess(OrderParam orderParam) {
                    notFinishListActivity();
                }
            });
        }
    }

    private void gotoOpenOrder(OrderParam orderParam) {
        if (orderParam != null) {
            MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                    .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                    .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, orderParam)
                    .navigation(getActivity());
            notFinishListActivity();
        }
    }

    private void notFinishListActivity() {
        List<String> list = new ArrayList<>(3);
        list.add(RouterPathConstant.CreateOrUpdateOrder.PATH);
        list.add(RouterPathConstant.MenuList.PATH);
        list.add(RouterPathConstant.PATH_MAIN_ACTIVITY);
        RouterActivityManager.get().finishAllActivityExcept(list);
    }
}
