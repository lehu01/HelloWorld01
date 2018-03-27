package com.zmsoft.ccd.module.receipt.receipt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.allinpay.usdk.core.data.ResponseData;
import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.ccd.lib.print.util.printer.feifan.FeiFanPrintUtils;
import com.chiclaim.modularization.router.MRouter;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.receipt.R;
import com.zmsoft.ccd.module.receipt.complete.view.CompleteReceiptActivity;
import com.zmsoft.ccd.module.receipt.discount.view.DiscountReceiptActivity;
import com.zmsoft.ccd.module.receipt.events.BaseEvents;
import com.zmsoft.ccd.module.receipt.receipt.adapter.ReceiptAdapter;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptMethodRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptRecyclerItem;
import com.zmsoft.ccd.module.receipt.receipt.helper.DataMapLayer;
import com.zmsoft.ccd.module.receipt.receipt.helper.ReceiptHelper;
import com.zmsoft.ccd.module.receipt.receipt.presenter.ReceiptContract;
import com.zmsoft.ccd.module.receipt.receiptway.coupon.view.CouponReceiptActivity;
import com.zmsoft.ccd.module.receipt.receiptway.normal.view.NormalReceiptActivity;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.view.OnAccountReceiptActivity;
import com.zmsoft.ccd.module.receipt.receiptway.scan.view.ScanReceiptActivity;
import com.zmsoft.ccd.module.receipt.verification.view.VerificationCancleActivity;
import com.zmsoft.ccd.module.receipt.vipcard.input.InputVipCardActivity;
import com.zmsoft.ccd.receipt.bean.CloudCashBill;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.KindPay;
import com.zmsoft.ccd.receipt.bean.Pay;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.shop.bean.IndustryType;
import com.zmsoft.lib.pos.common.PosCallBack;
import com.zmsoft.lib.pos.common.bean.PosCancelPay;
import com.zmsoft.lib.pos.common.bean.PosPay;
import com.zmsoft.lib.pos.common.helper.PosReceiveDataHelper;
import com.zmsoft.lib.pos.common.PosTransCallBack;
import com.zmsoft.lib.pos.common.helper.PosTransHelper;
import com.zmsoft.lib.pos.allin.helper.AllInDataHelper;
import com.zmsoft.lib.pos.newland.bean.NewLandResponse;
import com.zmsoft.lib.pos.newland.helper.NewLandDataHelper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptFragment extends BaseListFragment implements ReceiptContract.View, BaseListAdapter.AdapterClick {
    private GridLayoutManager mGridLayoutManager;
    private ReceiptContract.Presenter mPresenter;
    private List<ReceiptRecyclerItem> mRecyclerItems;
    private ReceiptAdapter mReceiptAdapter;

    /**
     * 订单ID
     */
    private String mOrderId;
    /**
     * 订单编号
     */
    private int mOrderCode;
    /**
     * 桌位编码
     */
    private String mSeatCode;
    /**
     * 桌位名称
     */
    private String mSeatName;
    /**
     * 是否是第三方外卖
     *
     * @see com.zmsoft.ccd.lib.base.constant.RouterPathConstant.Receipt
     */
    private boolean mThirdTakeout;
    /**
     * 云收银账单
     */
    private GetCloudCashBillResponse mGetCloudCashBillResponse;
    /**
     * 云收银账单金额详情
     */
    private CloudCashBill mCloudCashBill;

    /**
     * 是否进行了收款操作，如果是true，关闭收款模块时需要通知找单页刷新数据
     */
    private boolean mIsModifyCash = false;
    /**
     * 待退款（通联支付退款的情况下，需要先调用通联SDK进行退款，之后才调用自己的退款接口，所以需要保存一个临时变量供自身退款接口使用）
     */
    private List mAllInRefundList;

    public static ReceiptFragment newInstance(String orderId, String seatCode, String seatName, int orderCode
            , boolean thirdTakeout) {
        Bundle args = new Bundle();
        args.putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, orderId);
        args.putString(RouterPathConstant.Receipt.EXTRA_SEAT_CODE, seatCode);
        args.putString(RouterPathConstant.Receipt.EXTRA_SEAT_NAME, seatName);
        args.putInt(RouterPathConstant.Receipt.EXTRA_CODE, orderCode);
        args.putBoolean(RouterPathConstant.Receipt.EXTRA_THIRD_TAKEOUT, thirdTakeout);
        ReceiptFragment fragment = new ReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_receipt_receipt_fragment_layout;
    }

    @Override
    public void initRecyclerLayoutManager() {
        getRecyclerView().setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        getRecyclerView().setLayoutManager(mGridLayoutManager);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
//        disableRefresh();
        Bundle bundle = getArguments();
        if (null != bundle) {
            mOrderId = bundle.getString(RouterPathConstant.Receipt.EXTRA_ORDER_ID);
            mOrderCode = bundle.getInt(RouterPathConstant.Receipt.EXTRA_CODE);
            mSeatCode = bundle.getString(RouterPathConstant.Receipt.EXTRA_SEAT_CODE);
            mSeatName = bundle.getString(RouterPathConstant.Receipt.EXTRA_SEAT_NAME);
            mThirdTakeout = bundle.getBoolean(RouterPathConstant.Receipt.EXTRA_THIRD_TAKEOUT, false);
        }
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? mGridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mReceiptAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (position > 0 && position < mRecyclerItems.size() && data instanceof ReceiptRecyclerItem) {
                    ReceiptMethodRecyclerItem mMethodRecyclerItem = ((ReceiptRecyclerItem) data).getMethodRecyclerItem();
                    double fee = 0; //应收金额
                    double needFee = 0; //还需收款
                    if (null != mCloudCashBill) {
                        //将应收金额转换为元
                        fee = Double.parseDouble(FeeHelper.getDecimalFeeByFen(mCloudCashBill.getFee()));
                        //将还需收款金额转换为元
                        needFee = Double.parseDouble(FeeHelper.getDecimalFeeByFen(mCloudCashBill.getNeedFee()));
                    }
                    if (null != mMethodRecyclerItem) {
                        switch (mMethodRecyclerItem.getMethod()) {
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_CASH:
                                FeiFanPrintUtils.openMoneyBox();
                                if (ConfigHelper.hasOpenCashClean(getActivity())) {
                                    mPresenter.getCashLimit(UserHelper.getEntityId(), UserHelper.getUserId()
                                            , mMethodRecyclerItem, fee, needFee);
                                } else {
                                    goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                            , mMethodRecyclerItem.getKindPayId());
                                }
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_WEIXIN:
                                goToScanReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                        , mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALIPAY:
                                goToScanReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                        , mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP:
                                gotoVipCardActivity(mMethodRecyclerItem.getKindPayId(), mMethodRecyclerItem.getName(), fee);
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_SIGN_BILL:
                                goToOnAccountReceipt(fee, needFee, mMethodRecyclerItem.getName(), mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_BANK:
                                goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                        , mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_FREE_BILL:
                                if (BatchPermissionHelper.getPermission(Permission.FreeOrder.ACTION_CODE)) {
                                    goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                            , mMethodRecyclerItem.getKindPayId());
                                } else {
                                    showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                                            , getResources().getString(R.string.module_receipt_free_charge)));
                                }
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_COUPON:
                                goToCouponReceipt(fee, needFee, mMethodRecyclerItem.getName(), mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_PART_PAY:
                                goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                        , mMethodRecyclerItem.getKindPayId());
                                break;
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VOUCHER:
                                goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                                        , mMethodRecyclerItem.getKindPayId());
                                break;
                            // 通联
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_ALIPAY:
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_WEICHAT:
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_ALLIN_BANK:
                                // 新大陆
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_ALIPAY:
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_WEICHAT:
                            case BusinessHelper.ReceiptMethod.RECEIPT_METHOD_NEW_LAND_BANK:
                                PosPay posPay = new PosPay();
                                posPay.setPayType(mMethodRecyclerItem.getMethod());
                                posPay.setPayMoney((int) (needFee * 100));
                                PosTransHelper.gotoPosPayActivity(ReceiptFragment.this, posPay);
                                break;
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    protected void loadListData() {
        if (null == mRecyclerItems) {
            mRecyclerItems = new ArrayList<>();
        } else {
            mRecyclerItems.clear();
        }
        if (!TextUtils.isEmpty(mOrderId)) {
            mPresenter.getCloudCash(mOrderId);
            getBatchPermission();
        }
    }

    @Override
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mReceiptAdapter = new ReceiptAdapter(getActivity(), null, this);
        return mReceiptAdapter;
    }

    @Override
    public void setPresenter(ReceiptContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void successShowCloudCash(final GetCloudCashBillResponse getCloudCashBillResponse) {
        getRecyclerView().setVisibility(View.VISIBLE);
        mGetCloudCashBillResponse = getCloudCashBillResponse;
        if (mGetCloudCashBillResponse != null) {
            showContentView();
            Subscription subscription = RxUtils.fromCallable(new Callable<List<ReceiptRecyclerItem>>() {
                @Override
                public List<ReceiptRecyclerItem> call() {
                    mRecyclerItems = DataMapLayer.getReceiptInfo(mGetCloudCashBillResponse, mThirdTakeout);
                    return mRecyclerItems;
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .onTerminateDetach()
                    .subscribe(new Action1<List<ReceiptRecyclerItem>>() {
                        @Override
                        public void call(List<ReceiptRecyclerItem> cartRecyclerItems) {
                            cleanAll();
                            renderListData(cartRecyclerItems);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
            addRxSubscription(subscription);
            //如果“还需收款”的金额是0 (应收金额 > 0)，代表订单状态为“已付清”
            mCloudCashBill = mGetCloudCashBillResponse.getCloudCashBill();
            if (null != mCloudCashBill) {
                if (UserHelper.getIndustry() == IndustryType.RETAIL) {

                } else {
                    if (mCloudCashBill.getFee() > 0 && mCloudCashBill.getNeedFee() <= 0) {
                        getDialogUtil().showDialog(R.string.material_dialog_title,
                                R.string.module_receipt_order_clear_alert,
                                R.string.module_receipt_order_clear_alert_sure,
                                R.string.material_dialog_cancel,
                                false, new SingleButtonCallback() {
                                    @Override
                                    public void onClick(DialogUtilAction which) {
                                        if (which == DialogUtilAction.POSITIVE) {
                                            mPresenter.afterEndPay(UserHelper.getEntityId(), UserHelper.getUserId()
                                                    , mOrderId, mCloudCashBill.getModifyTime());
                                        }
                                    }
                                });
                    }
                }
            }
        } else {
            showErrorView(getString(R.string.http_net_error));
        }
    }

    @Override
    public void failShowCloudCash(ErrorBody errorBody) {
        //列表无数据时才显示errorView
        getRecyclerView().setVisibility(View.GONE);
        showErrorView(errorBody.getMessage());
    }

    @Override
    public void successCompletePay() {
        mIsModifyCash = true;
        notifyFindOrder();
        CcdPrintHelper.manualPrintOrder(getActivity()
                , PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER
                , mOrderId);
        //跳转到找单页面
        MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY).navigation(getActivity());
    }

    @Override
    public void getCashLimitSuccess(CashLimit cashLimit, ReceiptMethodRecyclerItem mMethodRecyclerItem
            , double fee, double needFee) {
        if (cashLimit == null) {
            return;
        }
        Logger.d(cashLimit.getCurrAmount() + ":" + cashLimit.getCollectLimit());
        if (cashLimit.hasExceedCashLimit()) {
            String content = getString(R.string.dialog_content_reach_cash_limit,
                    NumberUtils.trimPointIfZero(cashLimit.getCollectLimit()),
                    NumberUtils.trimPointIfZero(cashLimit.getCurrAmount()));
            getDialogUtil().showNoticeDialog(R.string.dialog_title, content, R.string.dialog_positive_reach_cash_limit, true, null);
        } else {
            goToNormalReceipt(mMethodRecyclerItem.getMethod(), fee, needFee, mMethodRecyclerItem.getName()
                    , mMethodRecyclerItem.getKindPayId());
        }
    }

    @Override
    public void getCashLimitFailed(String errorCode, String errorMsg) {
        Logger.d(errorCode + ',' + errorMsg);
        showToast(errorMsg);
    }

    @Override
    public void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse) {
        if (null == cloudCashCollectPayResponse) {
            failCollectPay(getString(R.string.module_receipt_fail));
        } else {
            if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.SUCCESS) {
                CompleteReceiptActivity.launchActivity(getActivity(), mOrderId
                        , cloudCashCollectPayResponse.getModifyTime(), 0);
            } else if (cloudCashCollectPayResponse.getStatus() == ReceiptHelper.PayStatus.FAILURE) {
                failCollectPay(getString(R.string.module_receipt_fail));
            }
        }
    }

    @Override
    public void failCollectPay(String errorMessage) {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , errorMessage, R.string.dialog_hint_know, false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                        }
                    }
                });
    }


    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    @Subscribe
    public void onReceiveEvent(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.RECEIPT_CLEAR_DISCOUNT_EVENT) {
            mPresenter.emptyDiscount(mOrderId);
            mIsModifyCash = true;
        } else if (event == BaseEvents.CommonEvent.RECEIPT_DELETE_PAY_EVENT) {
            if (null != event.getObject()) {
                List refundList = (List) event.getObject();
                if (null != refundList && !refundList.isEmpty()) {
                    handleRefund(refundList);
                }
            }
        }
    }

    private void handleRefund(List refundList) {
        Refund refund = (Refund) refundList.get(0);
        if (PosTransHelper.isPosPay(refund.getType())) {
            mAllInRefundList = refundList;
            gotoAllInRefund(refundList);
        } else {
            mPresenter.refund(mOrderId, refundList);
            mIsModifyCash = true;
        }
    }

    private void goToNormalReceipt(int type, double fee, double needFee, String title, String kindPayId) {
        NormalReceiptActivity.launchActivity(this, mOrderId, fee, needFee
                , type, title, kindPayId, title, mSeatCode, mSeatName, mOrderCode);
    }

    private void goToCouponReceipt(double fee, double needFee, String title, String kindPayId) {
        CouponReceiptActivity.launchActivity(this, fee, needFee
                , title, kindPayId, mOrderId);
    }

    private void goToOnAccountReceipt(double fee, double needFee, String title, String kindPayId) {
        if (BatchPermissionHelper.getPermission(Permission.OnAccount.ACTION_CODE)) {
            OnAccountReceiptActivity.launchActivity(this, fee, needFee
                    , title, kindPayId, mOrderId);
        } else {
            showToast(String.format(getResources().getString(R.string.alert_permission_deny)
                    , getResources().getString(R.string.module_receipt_onaccount)));
        }
    }

    private void goToScanReceipt(int type, double fee, double needFee, String title, String kindPayId) {
        ScanReceiptActivity.launchActivity(getActivity(), mOrderId, fee, needFee
                , type, title, kindPayId);
    }

    private void goToDiscountReceipt() {
        DiscountReceiptActivity.launchActivity(this, mOrderId);
    }

    private void goToVerificationReceipt() {
        VerificationCancleActivity.launchActivity(this, mOrderId);
    }

    private void gotoVipCardActivity(String kindPayId, String name, double fee) {
        InputVipCardActivity.launchActivity(this, mOrderId, kindPayId, name, fee);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PosReceiveDataHelper.getInstance().doReceive(requestCode, resultCode, data, new PosCallBack() {
            @Override
            public void onSuccessPayByNewLand(NewLandResponse response) {
                if (response != null) {
                    collectByAllIn(NewLandDataHelper.getPayType(response)
                            , NewLandDataHelper.getPayMoney(response)
                            , response.getSysTraceNo());
                }
            }

            @Override
            public void onSuccessCancelPayByNewLand() {
                refundByAllIn();
            }

            @Override
            public void onSuccessPayByAllIn(ResponseData responseData) {
                if (null != responseData) {
                    int money = AllInDataHelper.getPayMoney(responseData);
                    short payType = AllInDataHelper.getPayType(responseData);
                    String outTradeNo = AllInDataHelper.getTraceNo(responseData);
                    collectByAllIn(payType, money, outTradeNo);
                }
            }

            @Override
            public void onSuccessCancelPayByAllIn(ResponseData responseData) {
                refundByAllIn();
            }

            @Override
            public void onErrorMessage(String message) {
                showToast(message);
            }
        });
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ReceiptHelper.ACTIVITY_REQUEST_CODE.CODE_RECEIPT_WAY:
                    //支付方式界面支付成功，但是未付清，回到收款界面，刷新一次
                    mIsModifyCash = true;
                    loadListData();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAdapterClick(int type, View view, Object data) {
        switch (type) {
            case ReceiptHelper.RecyclerViewHolderClickType.DISCOUNT:
                goToDiscountReceipt();
                break;
            case ReceiptHelper.RecyclerViewHolderClickType.VERIFICATION:
                goToVerificationReceipt();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        notifyFindOrder();
        return super.onBackPressed();
    }

    /**
     * 通知找单页
     */
    private void notifyFindOrder() {
        if (mIsModifyCash) {
            EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
            EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL);
        }
    }

    /**
     * 批量获取应用内所需权限
     */
    private void getBatchPermission() {
        mPresenter.batchCheckPermisson(Permission.EmptyDiscount.SYSTEM_TYPE, BatchPermissionHelper.getAllPermissionCode());
    }

    /**
     * 通联支付收款
     */
    private void collectByAllIn(short payType, int actualReceiveFee, String outTradeNo) {
        if (null != mGetCloudCashBillResponse) {
            List<Fund> fundList = new ArrayList<>(1);
            Fund fund = new Fund();
            fund.setType(payType);
            List<KindPay> kindPayList = mGetCloudCashBillResponse.getKindPays();
            for (int i = 0; i < kindPayList.size(); i++) {
                KindPay kindPay = kindPayList.get(i);
                if (kindPay.getType() == payType) {
                    fund.setKindPayId(kindPay.getId());
                    break;
                }
            }
            fund.setClassName(ReceiptHelper.ReceiptFund.CLASS_NORMAL_FUND);
            fund.setFee(actualReceiveFee);
            fund.setOutTradeNo(outTradeNo);
            fundList.add(fund);
            mPresenter.collectPay(mOrderId, fundList);
        }
    }

    /**
     * 跳转到通联支付POS
     *
     * @param refundList
     */
    private void gotoAllInRefund(List refundList) {
        if (null != mGetCloudCashBillResponse && null != mGetCloudCashBillResponse.getPays()
                && !mGetCloudCashBillResponse.getPays().isEmpty() && null != refundList && !refundList.isEmpty()) {
            Refund refund = (Refund) refundList.get(0);
            List<Pay> payList = mGetCloudCashBillResponse.getPays();
            String payId = refund.getPayId();
            if (!TextUtils.isEmpty(payId)) {
                for (int i = 0; i < payList.size(); i++) {
                    Pay pay = payList.get(i);
                    if (pay.getId().equals(payId)) {
                        PosCancelPay cancelPay = new PosCancelPay();
                        cancelPay.setPayMoney(pay.getFee());
                        cancelPay.setPayTransNo(pay.getCode());
                        cancelPay.setPayType(pay.getType());
                        PosTransHelper.gotoPosCancelPayActivity(ReceiptFragment.this, cancelPay, new PosTransCallBack() {
                            @Override
                            public void transFailure() {
                                getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                                        , getString(R.string.all_in_cancel_pay_failure), R.string.dialog_hint_know
                                        , false, new SingleButtonCallback() {
                                            @Override
                                            public void onClick(DialogUtilAction which) {
                                            }
                                        });
                            }
                        });
                        break;
                    }
                }
            }
        }
    }

    /**
     * 通联支付退款
     */
    private void refundByAllIn() {
        if (null != mAllInRefundList) {
            mPresenter.refund(mOrderId, mAllInRefundList);
        }
    }
}
