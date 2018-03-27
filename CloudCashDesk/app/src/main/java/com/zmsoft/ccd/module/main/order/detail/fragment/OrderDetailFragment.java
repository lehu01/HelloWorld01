package com.zmsoft.ccd.module.main.order.detail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allinpay.usdk.core.data.ResponseData;
import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.OrderDetailManager;
import com.zmsoft.ccd.data.local.OpInstanceVo;
import com.zmsoft.ccd.data.spdata.InstanceDataManager;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.event.orderdetail.ClearDiscountEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.InstanceHelper;
import com.zmsoft.ccd.helper.OpOrderTypeHelper;
import com.zmsoft.ccd.helper.OrderHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.instance.PersonInstance;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderVo;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;
import com.zmsoft.ccd.lib.bean.order.feeplan.UpdateFeePlan;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.pay.Pay;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.module.instance.cancel.CancelOrGiveInstanceActivity;
import com.zmsoft.ccd.module.instance.updateprice.UpdateInstancePriceIActivity;
import com.zmsoft.ccd.module.instance.updateweight.UpdateInstanceWeightActivity;
import com.zmsoft.ccd.module.main.order.cancel.CancelOrderActivity;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailActivity;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailConstant;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailContract;
import com.zmsoft.ccd.module.main.order.detail.OrderDetailPresenter;
import com.zmsoft.ccd.module.main.order.detail.adapter.OrderDetailAdapter;
import com.zmsoft.ccd.receipt.bean.Refund;
import com.zmsoft.ccd.receipt.bean.RefundResponse;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;
import com.zmsoft.ccd.widget.orderop.OrderOpView;
import com.zmsoft.lib.pos.common.PosCallBack;
import com.zmsoft.lib.pos.common.PosTransCallBack;
import com.zmsoft.lib.pos.common.bean.PosCancelPay;
import com.zmsoft.lib.pos.common.helper.PosReceiveDataHelper;
import com.zmsoft.lib.pos.common.helper.PosTransHelper;
import com.zmsoft.lib.pos.newland.bean.NewLandResponse;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/17 10:58
 */
public class OrderDetailFragment extends BaseListFragment implements OrderDetailContract.View {

    @BindView(R.id.text_add_instance)
    TextView mTextAddInstance;
    @BindView(R.id.text_order_op)
    TextView mTextOrderOp;
    @BindView(R.id.text_pay_state)
    TextView mTextPayState;
    @BindView(R.id.linear_order_manager)
    LinearLayout mLinearOrderManager;
    @BindView(R.id.linear_op_order)
    LinearLayout mLinearOpOrder;
    @BindView(R.id.text_print_account_order)
    TextView mTextPrintAccountOrder;
    @BindView(R.id.text_print_finance_order)
    TextView mTextPrintFinanceOrder;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private List<OrderDetailItem> mOrderDetailList = new ArrayList<>();
    private OrderDetailAdapter mOrderDetailAdapter;
    private OrderDetailPresenter mPresenter;

    private int mOrderType = OrderDetailConstant.OrderType.ORDER_CHECKOUT;
    private int mFrom;
    private String mOrderId;

    private OptionsPickerView mReverseCheckoutPickerView;
    private OptionsPickerView mFeePlanPickerView;
    private OrderDetail mOrderDetail;

    // 底部弹窗
    private BottomDialog mBottomDialog;
    private Instance mInstance;
    private long mModifyTime; // 订单详情返回
    private OrderOpView mOrderOpView; // 订单操作view
    private Pay mClearPay;
    private String[] mDialogItems;
    private boolean mInitData = true;

    public static OrderDetailFragment newInstance(int from, String orderId, OrderDetail orderDetail) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RouterPathConstant.OrderDetail.EXTRA_FROM, from);
        bundle.putSerializable(RouterPathConstant.OrderDetail.EXTRA_ORDER_DETAIL, orderDetail);
        bundle.putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID, orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        initBundleData();
        initView();
        disableAutoRefresh();
    }

    // ===========================================================\
    // 初始化操作
    // ===========================================================
    private void initBundleData() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt(RouterPathConstant.OrderDetail.EXTRA_FROM, -1);
        mOrderId = bundle.getString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID);
        mOrderDetail = (OrderDetail) bundle.getSerializable(RouterPathConstant.OrderDetail.EXTRA_ORDER_DETAIL);
    }

    private void initView() {
        mBottomDialog = new BottomDialog(getActivity());
        mOrderOpView = new OrderOpView(getActivity(), mLinearOpOrder);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        setContentView(false);
        showLoadingView();
    }

    @Override
    protected void loadListData() {
        initOrderDetail();
    }

    private void initOrderDetail() {
        if (mInitData) {
            if (mOrderDetail == null) {
                getOrderDetail();
            } else {
                updateOrderDetailView(mOrderDetail, "");
            }
            mInitData = false;
        } else {
            getOrderDetail();
        }
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mOrderDetailAdapter = new OrderDetailAdapter(getActivity(), null);
        return mOrderDetailAdapter;
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 列表点击事件
        mOrderDetailAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                // 未上班
                if (!UserHelper.getWorkStatus()) {
                    CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
                    return;
                }
                // 结账完毕：[菜肴不允许操作]
                if (OrderHelper.isEndPay(mOrderDetail.getOrder().getStatus())) {
                    return;
                }
                // 外卖单不能操作
                if (OrderHelper.isTakeoutOrder(mOrderDetail.getOrder().getOrderFrom())) {
                    return;
                }
                OrderDetailItem orderDetailItem = mOrderDetailList.get(position);
                switch (orderDetailItem.getType()) {
                    case OrderDetailItem.ITEM_TYPE_INSTANCE: // 普通菜
                    case OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE: // 套菜子菜
                    case OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE: // 套菜
                        showInstanceOpBottomDialog(orderDetailItem.getInstance(), orderDetailItem.getType());
                        break;
                    case OrderDetailItem.ITEM_TYPE_PAY_INFO: // 删除支付
                        mClearPay = orderDetailItem.getPay();
                        if (BatchPermissionHelper.getPermission(Permission.ClearPay.ACTION_CODE)) {
                            clearPay(mClearPay);
                        } else {
                            if (BusinessHelper.isElectronicPay(mClearPay.getPayType())) {
                                showToast(GlobalVars.context.getString(R.string.permission_clear_pay_by_electronic_payment));
                            } else {
                                showToast(GlobalVars.context.getString(R.string.permission_clear_pay));
                            }
                        }
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, Object data, int position) {
                return false;
            }
        });
        // 订单操作事件
        mOrderOpView.setOnItemClickListener(new OrderOpView.OnItemClickListener() {
            @Override
            public void printDishesOrder() {
                if (hasInstance()) {
                    reprintOrder(PrintData.BIZ_TYPE_PRINT_DISHES_ORDER);
                }
            }

            @Override
            public void printAccountOrder() {
                if (hasInstance()) {
                    if (!BatchPermissionHelper.getPermission(Permission.PrintAccount.ACTION_CODE)) {
                        showToast(getString(R.string.permission_print_account));
                        return;
                    }
                    if (BaseSpHelper.isDoubleUnitSwitch(getActivity()) &&
                            OrderDetailManager.isDoubleUnitInstanceAndNoUpdate(mOrderDetailList)) {
                        // 掌柜开启双单位商品未修改重量提示 && 菜肴里面又双单位菜肴 && 并且没有修改重量
                        getDialogUtil().showDialog(R.string.prompt
                                , R.string.print_account_prompt
                                , R.string.ok
                                , R.string.cancel
                                , true, new SingleButtonCallback() {
                                    @Override
                                    public void onClick(DialogUtilAction which) {
                                        if (which == DialogUtilAction.POSITIVE) {
                                            reprintOrder(PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER);
                                        } else if (which == DialogUtilAction.NEGATIVE) {
                                            getDialogUtil().dismissDialog();
                                        }

                                    }
                                });
                    } else {
                        reprintOrder(PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER);
                    }
                }
            }

            @Override
            public void changeOrder() {
                /**
                 * 改单：权限
                 */
                if (BatchPermissionHelper.getPermission(Permission.ChangeOrder.ACTION_CODE)) {
                    gotoChangeOrderActivity();
                } else {
                    showToast(getString(R.string.permission_change_order));
                }
            }

            @Override
            public void cancelOrder() {
                /**
                 * 撤单：权限
                 */
                if (BatchPermissionHelper.getPermission(Permission.CancelOrder.ACTION_CODE)) {
                    gotoCancelOrderActivity();
                } else {
                    showToast(getString(R.string.permission_cancel_order));
                }
            }

            @Override
            public void pushOrder() {
                if (!BatchPermissionHelper.getPermission(Permission.PushOrder.ACTION_CODE)) {
                    showToast(R.string.permission_push_order);
                    return;
                }
                pushOrders();
            }
        });
        // 底部弹窗
        mBottomDialog.setItemClickListener(new BottomDialog.PopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!UserHelper.getWorkStatus()) {
                    CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
                    return;
                }
                if (mDialogItems != null && position < mDialogItems.length) {
                    String str = mDialogItems[position];
                    if (getString(R.string.push_instance).equals(str)) {
                        if (!BatchPermissionHelper.getPermission(Permission.PushInstance.ACTION_CODE)) {
                            showToast(R.string.permission_push_instance);
                            return;
                        }
                        pushInstance();
                    } else if (getString(R.string.cancel_instance).equals(str)) {
                        if (!BatchPermissionHelper.getPermission(Permission.CancelInstance.ACTION_CODE)) {
                            showToast(getString(R.string.permission_cancel_instance));
                            return;
                        }
                        gotoCancelInstanceActivity();
                    } else if (getString(R.string.reprint_label_paper).equals(str)) {
                        CcdPrintHelper.printLabelInstance(getActivity()
                                , PrintData.BIZ_TYPE_PRINT_LABEL
                                , Arrays.asList(mInstance.getId()));
                    } else if (getString(R.string.update_weight).equals(str)) {
                        if (!BatchPermissionHelper.getPermission(Permission.UpdateInstanceWeight.ACTION_CODE)) {
                            showToast(getString(R.string.permission_update_instance_weight));
                            return;
                        }
                        gotoUpdateWeightActivity();
                    } else if (getString(R.string.update_price).equals(str)) {
                        if (!BatchPermissionHelper.getPermission(Permission.UpdateInstancePrice.ACTION_CODE)) {
                            showToast(getString(R.string.permission_update_instance_price));
                            return;
                        }
                        gotoUpdatePriceActivity();
                    } else if (getString(R.string.give_this_instance).equals(str)) {
                        if (!BatchPermissionHelper.getPermission(Permission.PresentFood.ACTION_CODE)) {
                            showToast(getString(R.string.permission_give_instance));
                            return;
                        }
                        gotoGiveInstanceActivity();
                    }
                }
                mBottomDialog.dismiss();
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void clearPayListSuccess(RefundResponse data) {
        if (data != null) {
            List<Refund> paySuccessList = data.getRefunds();
            for (Refund refund : paySuccessList) {
                if (refund.getStatus() == Base.INT_TRUE) {
                    notifyListRefresh();
                    getOrderDetail();
                } else {
                    showToast(refund.getMessage());
                }
            }
        }
    }

    @Override
    public void clearDiscountSuccess(Object obj) {
        notifyListRefresh();
        getOrderDetail();
    }

    @Override
    public void updateOrderDetailView(OrderDetail orderDetail, String errorMessage) {
        if (orderDetail != null) {
            showContentView();
            setContentView(true);
            mOrderDetail = orderDetail;
            mOrderId = orderDetail.getOrder().getId();
            mModifyTime = orderDetail.getOrder().getModifyTime();
            updateTitle(orderDetail);
            updateDetailView(orderDetail);
            updateButtonByOrderType(orderDetail);
            updateOpOrderView(orderDetail);
        } else {
            loadListFailed();
            setContentView(false);
            mOrderDetailList.clear();
            mOrderDetailAdapter.notifyDataSetChanged();
            showErrorView(errorMessage);
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getOrderDetail();
    }

    @Override
    public void showReasonDialog(List<Reason> list) {
        if (list != null && list.size() > 0) {
            showReverseCheckoutReasonBottomDialog(list);
        } else {
            mPresenter.reverseCheckOut(UserHelper.getEntityId()
                    , UserHelper.getUserId()
                    , mOrderId
                    , ""
                    , mModifyTime);
        }
    }


    /**
     * 检查订单是否有菜肴
     */
    private boolean hasInstance() {
        if (mOrderDetail != null) {
            List<PersonInstance> personInstances = mOrderDetail.getPersonInstanceVoList();
            if (personInstances == null || personInstances.size() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setPresenter(OrderDetailContract.Presenter presenter) {
        this.mPresenter = (OrderDetailPresenter) presenter;
    }

    @OnClick({R.id.text_pay_state, R.id.text_add_instance, R.id.text_order_op
            , R.id.text_print_account_order, R.id.text_print_finance_order})
    void doClick(View view) {
        if (!UserHelper.getWorkStatus()) {
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
            return;
        }
        switch (view.getId()) {
            case R.id.text_pay_state:
                switchButtonByOrderType();
                break;
            case R.id.text_add_instance:
                gotoAddInstanceActivity();
                break;
            case R.id.text_order_op:
                mOrderOpView.show();
                break;
            case R.id.text_print_account_order:
                if (!BatchPermissionHelper.getPermission(Permission.PrintAccount.ACTION_CODE)) {
                    showToast(getString(R.string.permission_print_account));
                    return;
                }
                reprintOrder(PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER);
                break;
            case R.id.text_print_finance_order:
                if (!BatchPermissionHelper.getPermission(Permission.AccountPrintFin.ACTION_CODE)) {
                    showToast(getString(R.string.permission_print_fin));
                    return;
                }
                reprintOrder(PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER);
                break;
        }
    }

    @Override
    public void reverseCheckOut() {
        showToast(getString(R.string.reverser_check_out_success));
        notifyListRefresh();
        getActivity().finish();
    }

    @Override
    public void afterEndPay() {
        CcdPrintHelper.manualPrintOrder(getActivity()
                , PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER
                , mOrderId);

        showToast(getString(R.string.after_end_pay_success));
        notifyListRefresh();
        getActivity().finish();
    }

    @Override
    public void opOrderFailure(String failureMessage) {
        if (mOrderDetail != null) {
            showToast(failureMessage);
        }
    }

    /**
     * 通知列表刷新
     */
    public void notifyListRefresh() {
        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
    }

    //======================================================================
    // 更新view操作
    //======================================================================
    private void updateButtonByOrderType(OrderDetail orderDetail) {
        if (OrderHelper.OrderDetail.Order.END_PAY == orderDetail.getOrder().getStatus()) {
            // 反结账 = 结账完毕
            mOrderType = OrderDetailConstant.OrderType.ORDER_RESERVE_CHECKOUT;
            mTextPayState.setText(getString(R.string.reverser_check_out));
        } else if (OrderHelper.OrderDetail.Status.PAY_ALL == orderDetail.getStatus()
                && OrderHelper.OrderDetail.Order.END_PAY != orderDetail.getOrder().getStatus()) {
            // 结账完毕 = 已付清 + !结账完毕状态
            mOrderType = OrderDetailConstant.OrderType.ORDER_END_PAY;
            mTextPayState.setText(getString(R.string.after_end_pay));
        } else {
            // 结账
            mOrderType = OrderDetailConstant.OrderType.ORDER_CHECKOUT;
            mTextPayState.setText(getString(R.string.check_out));
        }
    }

    private void updateOpOrderView(OrderDetail orderDetail) {
        // 结账完毕
        if (OrderHelper.isEndPay(orderDetail.getOrder().getStatus())) {
            mTextAddInstance.setVisibility(View.GONE);
            mTextOrderOp.setVisibility(View.GONE);
            mTextPrintAccountOrder.setVisibility(View.VISIBLE);
            mTextPrintFinanceOrder.setVisibility(View.VISIBLE);
        } else {
            mTextPrintAccountOrder.setVisibility(View.GONE);
            mTextPrintFinanceOrder.setVisibility(View.GONE);
            // 外卖单不能操作
            if (OrderHelper.isTakeoutOrder(mOrderDetail.getOrder().getOrderFrom())) {
                mTextAddInstance.setVisibility(View.GONE);
                mTextOrderOp.setVisibility(View.GONE);
                mTextPayState.setVisibility(View.GONE);
            } else {
                mTextAddInstance.setVisibility(View.VISIBLE);
                mTextOrderOp.setVisibility(View.VISIBLE);
            }
        }
    }

    private void switchButtonByOrderType() {
        switch (mOrderType) {
            case OrderDetailConstant.OrderType.ORDER_CHECKOUT: // 结账
                if (!BatchPermissionHelper.getPermission(Permission.CheckOut.ACTION_CODE)) {
                    showToast(getString(R.string.permission_checkout));
                    return;
                }
                collectPayMoney();
                break;
            case OrderDetailConstant.OrderType.ORDER_RESERVE_CHECKOUT: // 反结账
                mPresenter.getReverseReasonList(UserHelper.getEntityId(), SystemDirCodeConstant.ACCOUNT_REASON, SystemDirCodeConstant.TYPE_SYSTEM);
                break;
            case OrderDetailConstant.OrderType.ORDER_END_PAY: // 结账完毕
                mPresenter.afterEndPay(UserHelper.getEntityId(), UserHelper.getUserId(), mOrderId, mModifyTime);
                break;
        }
    }

    private void updateTitle(OrderDetail orderDetail) {
        String seatName = orderDetail.getSeatName();
        String result;
        if (StringUtils.isEmpty(seatName)) {
            result = StringUtils.appendStr(getString(R.string.order_number), orderDetail.getOrder().getCode());
        } else {
            result = StringUtils.appendStr(getString(R.string.seat_number), seatName);
        }
        getActivity().setTitle(result);
    }

    private void updateDetailView(OrderDetail orderDetail) {
        List<OrderDetailItem> list = OrderDetailManager.getResultOrderDetail(orderDetail
                , BaseSpHelper.isUpdateServiceFee(getActivity())
                , BaseSpHelper.isDoubleUnitSwitch(getActivity()));
        if (list != null) {
            mOrderDetailList.clear();
            mOrderDetailList.addAll(list);
        }
        cleanAll();
        renderListData(mOrderDetailList);
    }

    private void setContentView(boolean isShow) {
        mLinearOpOrder.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLinearOrderManager.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRefreshLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    //======================================================================
    // 弹窗操作
    //======================================================================
    private void showNoticeDialog(String message) {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title, message, R.string.dialog_hint_know, false, null);
    }

    private void showReverseCheckoutReasonBottomDialog(final List<Reason> list) {
        if (mReverseCheckoutPickerView == null) {
            mReverseCheckoutPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), R.string.reverse_reason);
            mReverseCheckoutPickerView.setOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    if (options1 > list.size() - 1) {
                        return;
                    }
                    mPresenter.reverseCheckOut(UserHelper.getEntityId()
                            , UserHelper.getUserId()
                            , mOrderId
                            , list.get(options1).getName()
                            , mModifyTime);
                }
            });
        }
        mReverseCheckoutPickerView.setPicker(list);
        mReverseCheckoutPickerView.show();
    }

    private void showFeePlanBottomDialog(final List<FeePlan> list) {
        if (mFeePlanPickerView == null) {
            mFeePlanPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), R.string.update_fee_plan);
            mFeePlanPickerView.setOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    if (options1 > list.size() - 1) {
                        return;
                    }
                    mPresenter.updateFeePlan(UserHelper.getEntityId()
                            , mOrderId
                            , list.get(options1).getId()
                            , UserHelper.getUserId()
                            , OpOrderTypeHelper.OP_ORDER_CHANGE_FEE_PLAN
                            , mModifyTime);
                }
            });
        }
        mFeePlanPickerView.setSelectOptions(mPresenter.getCheckFeePlanIndex(list, mOrderDetail.getOrder().getFeePlanId()));
        mFeePlanPickerView.setPicker(list);
        mFeePlanPickerView.show();
    }

    private void showInstanceOpBottomDialog(Instance instance, int instanceTyp) {
        if (instance != null) {
            if (InstanceHelper.isCanOpInstance(instance.getStatus())) {
                mInstance = instance;
                switch (instanceTyp) {
                    case OrderDetailItem.ITEM_TYPE_INSTANCE: // 普通菜
                        showNormalInstanceDialog();
                        break;
                    case OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE: // 套菜
                        showSuitInstanceDialog();
                        break;
                    case OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE:// 套菜子菜
                        showSuitChildInstanceDialog();
                        break;
                }
            }
        }
    }

    private void showSuitChildInstanceDialog() {
        List<OpInstanceVo> list = InstanceDataManager.getDialogByOpSuitChildInstance(mInstance);
        List<String> resultList = InstanceDataManager.getDialogByOpInstance(list);
        mDialogItems = resultList.toArray(new String[resultList.size()]);
        mBottomDialog.setItems(mDialogItems);
        mBottomDialog.setTitle(mInstance.getName());
        mBottomDialog.showPopupWindow();
    }

    private void showSuitInstanceDialog() {
        mDialogItems = new String[]{getString(R.string.push_instance), getString(R.string.cancel_instance)};
        mBottomDialog.setItems(mDialogItems);
        mBottomDialog.setTitle(mInstance.getName());
        mBottomDialog.showPopupWindow();
    }

    private void showNormalInstanceDialog() {
        List<OpInstanceVo> list = InstanceDataManager.getDialogByOpNormalInstance(mInstance);
        List<String> resultList = InstanceDataManager.getDialogByOpInstance(list);
        mDialogItems = resultList.toArray(new String[resultList.size()]);
        mBottomDialog.setItems(mDialogItems);
        mBottomDialog.setTitle(mInstance.getName());
        mBottomDialog.showPopupWindow();
    }

    //======================================================================
    // 打印操作
    //======================================================================
    private void printOrderBySmallTicket(int bizType) {
        CcdPrintHelper.manualPrintOrder(getActivity()
                , bizType
                , mOrderId);
    }

    private void reprintOrder(int bizType) {
        CcdPrintHelper.reprintOrder(getActivity()
                , bizType
                , mOrderId);
    }

    //======================================================================
    // 催菜，催单，默认直接成功，然后直接打印催菜
    //======================================================================
    private void pushInstance() {
        // 催菜
        if (mInstance != null) {
            // 打印催菜单
            CcdPrintHelper.printInstance(getActivity()
                    , PrintData.TYPE_ORDER
                    , PrintData.BIZ_TYPE_PRINT_PUSH_INSTANCE
                    , Arrays.asList(mInstance.getId()));
        }
        showToast(StringUtils.appendStr(mInstance.getName()
                , getString(R.string.comma)
                , getString(R.string.push_instance_success)));
    }

    private void pushOrders() {
        printOrderBySmallTicket(PrintData.BIZ_TYPE_PRINT_PUSH_ORDER);
        showToast(StringUtils.appendStr("No.", mOrderDetail.getOrder().getCode()
                , " "
                , StringUtils.notNull(mOrderDetail.getSeatName())
                , getString(R.string.comma)
                , getString(R.string.push_order_success)));
    }

    //======================================================================
    // Go to other activity
    //======================================================================
    private void gotoReceiptActivity() {
        int code = 0;
        String seatCode = "";
        OrderVo orderVo = mOrderDetail.getOrder();
        if (orderVo != null) {
            code = orderVo.getCode();
            seatCode = orderVo.getSeatCode();
        }
        MRouter.getInstance().build(RouterPathConstant.Receipt.PATH)
                .putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, mOrderId)
                .putString(RouterPathConstant.Receipt.EXTRA_SEAT_NAME, mOrderDetail.getSeatName())
                .putString(RouterPathConstant.Receipt.EXTRA_SEAT_CODE, seatCode)
                .putInt(RouterPathConstant.Receipt.EXTRA_CODE, code)
                .navigation(getActivity());
    }

    private void gotoAddInstanceActivity() {
        OrderParam createOrderParam = new OrderParam();
        if (mOrderDetail != null) {
            createOrderParam.setSeatName(mOrderDetail.getSeatName());
            OrderVo orderVo = mOrderDetail.getOrder();
            if (orderVo != null) {
                // 如果seatCode为空，那么是零售单，传递规则的零售单seatCode
                createOrderParam.setSeatCode(StringUtils.isEmpty(orderVo.getSeatCode()) ? RetailOrderHelper.getRetailSeatCode(mOrderId) : orderVo.getSeatCode());
                createOrderParam.setMemo(orderVo.getMemo());
                createOrderParam.setNumber(orderVo.getPeopleCount());
                createOrderParam.setWait(Base.SHORT_TRUE.equals(orderVo.getIsWait()));
            }
            createOrderParam.setOrderId(mOrderId);
            createOrderParam.setModifyTime(mModifyTime);
        }
        MRouter.getInstance().build(RouterPathConstant.MenuList.PATH)
                .putSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, createOrderParam)
                .navigation(getActivity());
    }

    private void gotoCancelInstanceActivity() {
        MRouter.getInstance().build(CancelOrGiveInstanceActivity.PATH_CANCEL_INSTANCE_ACTIVITY)
                .putString(CancelOrGiveInstanceActivity.EXTRA_FROM, CancelOrGiveInstanceActivity.EXTRA_FROM_CANCEL_INSTANCE)
                .putSerializable(CancelOrGiveInstanceActivity.EXTRA_INSTANCE, mInstance)
                .navigation(getActivity());
    }

    private void gotoGiveInstanceActivity() {
        MRouter.getInstance().build(CancelOrGiveInstanceActivity.PATH_CANCEL_INSTANCE_ACTIVITY)
                .putString(CancelOrGiveInstanceActivity.EXTRA_FROM, CancelOrGiveInstanceActivity.EXTRA_FROM_GIVE_INSTANCE)
                .putSerializable(CancelOrGiveInstanceActivity.EXTRA_INSTANCE, mInstance)
                .navigation(getActivity());
    }

    private void gotoUpdatePriceActivity() {
        MRouter.getInstance().build(UpdateInstancePriceIActivity.PATH_UPDATE_PRICE_ACTIVITY)
                .putSerializable(UpdateInstancePriceIActivity.EXTRA_INSTANCE, mInstance)
                .navigation(getActivity());
    }

    private void gotoUpdateWeightActivity() {
        MRouter.getInstance().build(UpdateInstanceWeightActivity.PATH_UPDATE_INSTANCE_WEIGHT)
                .putSerializable(UpdateInstanceWeightActivity.EXTRA_INSTANCE, mInstance)
                .navigation(getActivity());
    }

    private void gotoCancelOrderActivity() {
        MRouter.getInstance().build(CancelOrderActivity.PATH)
                .putString(CancelOrderActivity.EXTRA_ORDER_ID, mOrderId)
                .putString(CancelOrderActivity.EXTRA_SEAT_NAME, mOrderDetail.getSeatName())
                .putInt(CancelOrderActivity.EXTRA_ORDER_NUMBER, mOrderDetail.getOrder().getCode())
                .putLong(CancelOrderActivity.EXTRA_MODIFY_TIME, mModifyTime)
                .navigation(getActivity(), OrderDetailActivity.RESULT_CANCEL_ORDER);
    }

    private void gotoChangeOrderActivity() {
        boolean isMust = mPresenter.isMustInstance(mOrderDetail.getPersonInstanceVoList());
        OrderParam param = new OrderParam();
        param.setOrderId(mOrderId);
        param.setModifyTime(mModifyTime);
        OrderVo order = mOrderDetail.getOrder();
        if (order != null) {
            param.setSeatCode(order.getSeatCode());
            param.setOriginSeatCode(order.getSeatCode());
            param.setMemo(order.getMemo());
            param.setNumber(order.getPeopleCount());
            param.setOriginNumber(order.getPeopleCount());
            param.setWait(Base.SHORT_TRUE.equals(order.getIsWait()));
        }
        param.setSeatName(mOrderDetail.getSeatName());
        param.setMustMenu(isMust);

        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_ORDER_DETAIL)
                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, param)
                .navigation(getActivity(), OrderDetailActivity.RESULT_UPDATE_ORDER);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public void showFeePlanDialog(List<FeePlan> list) {
        if (list != null) {
            showFeePlanBottomDialog(list);
        }
    }

    @Override
    public void reload() {
        notifyListRefresh();
        getOrderDetail();
    }

    @Override
    public void updateFeePlanSuccess(UpdateFeePlan updateFeePlan) {
        if (updateFeePlan == null) {
            showToast(getString(R.string.update_failure));
            return;
        }
        mOrderId = updateFeePlan.getOrderId();
        mModifyTime = updateFeePlan.getModifyTime();
        getOrderDetail();
    }

    @Override
    public void pushInstanceSuccess() {
        if (mInstance != null) {
            // 打印催菜单
            CcdPrintHelper.printInstance(getActivity()
                    , PrintData.TYPE_ORDER
                    , PrintData.BIZ_TYPE_PRINT_PUSH_INSTANCE
                    , Arrays.asList(mInstance.getId()));
            showToast(StringUtils.appendStr(mInstance.getName(), getString(R.string.comma), getString(R.string.push_instance_success)));
        }
    }

    @Override
    public void pushOrderSuccess() {
        printOrderBySmallTicket(PrintData.BIZ_TYPE_PRINT_PUSH_ORDER);
        showToast(StringUtils.appendStr("No.", mOrderDetail.getOrder().getCode(), " ", StringUtils.notNull(mOrderDetail.getSeatName()), getString(R.string.comma), getString(R.string.push_order_success)));
    }


    //======================================================================
    // 请求操作
    //======================================================================
    private void getOrderDetail() {
        mPresenter.getOrderDetail(UserHelper.getEntityId()
                , mOrderId
                , UserHelper.getUserId());
    }

    public void getOrderDetailByChangeOrder(String orderId) {
        if (!StringUtils.isEmpty(orderId)) {
            mOrderId = orderId;
            reload();
        }
    }

    private void collectPayMoney() {
        // 掌柜开启双单位商品未修改重量提示 && 菜肴里面又双单位菜肴 && 并且没有修改重量
        if (BaseSpHelper.isDoubleUnitSwitch(getActivity()) && OrderDetailManager.isDoubleUnitInstanceAndNoUpdate(mOrderDetailList)) {
            getDialogUtil().showDialog(R.string.prompt
                    , R.string.print_account_prompt
                    , R.string.ok
                    , R.string.cancel
                    , true, new SingleButtonCallback() {
                        @Override
                        public void onClick(DialogUtilAction which) {
                            if (which == DialogUtilAction.POSITIVE) {
                                gotoReceiptActivity();
                            } else if (which == DialogUtilAction.NEGATIVE) {
                                getDialogUtil().dismissDialog();
                            }
                        }
                    });
        } else {
            gotoReceiptActivity();
        }
    }

    private void clearPay(final Pay pay) {
        if (pay == null) {
            return;
        }
        String content = BusinessHelper.alertDeletePayItem(getActivity(), (short) pay.getPayType());
        if (StringUtils.isEmpty(content)) {
            content = getString(R.string.clear_pay_prompt);
        }
        getDialogUtil().showDialog(R.string.prompt, content
                , R.string.ok
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            // pos支付统一处理
                            if (PosTransHelper.isPosPay(mClearPay.getPayType())) {
                                PosCancelPay cancelPay = new PosCancelPay();
                                cancelPay.setPayMoney((int) (mClearPay.getPayAmount() * 100));
                                cancelPay.setPayTransNo(mClearPay.getPayNo());
                                cancelPay.setPayType((short) mClearPay.getPayType());
                                PosTransHelper.gotoPosCancelPayActivity(OrderDetailFragment.this, cancelPay, new PosTransCallBack() {
                                    @Override
                                    public void transFailure() {
                                        showNoticeDialog(getString(R.string.all_in_cancel_pay_failure));
                                    }
                                });
                            } else {
                                doCancelPayMoney();
                            }
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            getDialogUtil().dismissDialog();
                        }
                    }
                });
    }

    private void doCancelPayMoney() {
        Refund refund = new Refund();
        refund.setType((short) mClearPay.getPayType());
        refund.setPayId(mClearPay.getId());
        mPresenter.clearPay(mOrderId, mClearPay, Arrays.asList(refund));
    }

    //======================================================================
    // Event Bus
    //======================================================================
    @Subscribe
    public void showFeePlan(BaseEvents.CommonEvent event) { // 修改服务费方案
        if (event == BaseEvents.CommonEvent.EVENT_SHOW_FEE_PLAN_BOTTOM_DIALOG) {
            OrderVo orderVo = mOrderDetail.getOrder();
            if (orderVo != null) {
                mPresenter.getFeePlanListByAreaId(UserHelper.getEntityId(), orderVo.getAreaId());
            }
        }
    }

    @Subscribe
    public void refreshOrderDetail(RouterBaseEvent.CommonEvent event) { // 刷新订单详情：订单id不变
        if (event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_ORDER_DETAIL) {
            reload();
        }
    }

    @Subscribe
    public void clearDiscount(ClearDiscountEvent event) {
        // 未上班
        if (!UserHelper.getWorkStatus()) {
            CommonHelper.showOffWorkDialog(getActivity(), getDialogUtil());
            return;
        }
        // 结账完毕订单
        if (OrderHelper.isEndPay(mOrderDetail.getOrder().getStatus())) {
            return;
        }
        // 外卖单不能操作
        if (OrderHelper.isTakeoutOrder(mOrderDetail.getOrder().getOrderFrom())) {
            return;
        }

        // 权限
        if (!BatchPermissionHelper.getPermission(Permission.EmptyDiscount.ACTION_CODE)) {
            showToast(getString(R.string.permission_clear_discount));
            return;
        }
        getDialogUtil().showDialog(R.string.prompt, R.string.clear_discount_prompt
                , R.string.ok
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            mPresenter.clearDiscount(mOrderId);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            getDialogUtil().dismissDialog();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PosReceiveDataHelper.getInstance().doReceive(requestCode, resultCode, data, new PosCallBack() {
            @Override
            public void onSuccessPayByNewLand(NewLandResponse response) {

            }

            @Override
            public void onSuccessCancelPayByNewLand() {
                doCancelPayMoney();
            }

            @Override
            public void onSuccessPayByAllIn(ResponseData responseData) {

            }

            @Override
            public void onSuccessCancelPayByAllIn(ResponseData responseData) {
                doCancelPayMoney();
            }

            @Override
            public void onErrorMessage(String message) {
                showToast(message);
            }
        });
    }
}
