package com.zmsoft.ccd.module.takeout.order.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.Permission;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.view.JagVO;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.module.takeout.DaggerCommentManager;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.DeliveryActivity;
import com.zmsoft.ccd.module.takeout.delivery.PendingDeliveryListActivity;
import com.zmsoft.ccd.module.takeout.order.adapter.TakeoutListAdapter;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.DeliveryHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderFoodHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderInfoHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderMainHolderVO;
import com.zmsoft.ccd.module.takeout.order.adapter.vo.OrderPayInfoHolderVO;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListFragmentContract;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutListFragmentPresenter;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.DaggerTakeoutListFragmentComponent;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.TakeoutListFragmentPresenterModule;
import com.zmsoft.ccd.module.takeout.order.utils.TakeoutUtils;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderRequest;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderResponse;
import com.zmsoft.ccd.takeout.bean.DeliveryInfo;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoResponse;
import com.zmsoft.ccd.takeout.bean.OperateOrderRequest;
import com.zmsoft.ccd.takeout.bean.OperateOrderResponse;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderListResponse;
import com.zmsoft.ccd.takeout.bean.SearchOrderRequest;
import com.zmsoft.ccd.takeout.bean.Takeout;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.createGroupHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.createMainHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.getDeliveryHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.getOrderDescHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.getOrderFoodHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.getOrderInfoHolderVO;
import static com.zmsoft.ccd.module.takeout.order.adapter.vo.TakeoutVOCreator.getOrderPayInfoHolderVO;
import static com.zmsoft.ccd.module.takeout.order.utils.TakeoutUtils.operateTakeoutTip;


/**
 * Description：在线外卖列表
 * <br/>
 * Created by kumu on 2017/8/14.
 */

public class TakeoutListFragment extends BaseListFragment implements TakeoutListFragmentContract.View
        , BaseListAdapter.AdapterClick {

    private static final int REQUEST_CODE_DELIVERY = 1;

    @Inject
    TakeoutListFragmentPresenter mPresenter;


    private OrderListRequest mRequest;
    private short mStatus = -1;
    private String mStatusName;

    private boolean mIsFromSearch;
    private String mSearchCondition;
    private String mCursorMark;

    private String mSelectedCancelReason;
    private Takeout mClickedTakeout;
    private short mOperationType;

    private TakeoutListener mTakeoutListener;

    public static TakeoutListFragment create(int status, String statusName) {
        TakeoutListFragment fragment = new TakeoutListFragment();
        Bundle bundle = new Bundle();
        bundle.putShort("status", (short) status);
        bundle.putString("statusName", statusName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static TakeoutListFragment createForSearch() {
        TakeoutListFragment fragment = new TakeoutListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromSearch", true);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initParameters() {
        super.initParameters();
        initParamsFromBundle();
        //setPageCount(TakeoutConstants.PAGE_COUNT);
    }

    public void initParamsFromBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mStatus = bundle.getShort("status", (short) -1);
            mStatusName = bundle.getString("statusName");
            mIsFromSearch = bundle.getBoolean("fromSearch", false);
        }
    }

    public short getStatus() {
        return mStatus;
    }

    public void updateParams(int status, String statusName) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundle.putShort("status", (short) status);
            bundle.putString("statusName", statusName);
        }
        mStatus = (short) status;
        mStatusName = statusName;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_takeout_fragment_takeout_list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DaggerTakeoutListFragmentComponent.builder()
                .takeoutListFragmentPresenterModule(new TakeoutListFragmentPresenterModule(this))
                .takeoutSourceComponent(DaggerCommentManager.get().getTakeoutSourceComponent())
                .build()
                .inject(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void unBindPresenterFromView() {
        if (this.mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    protected boolean refreshThreshold() {
        return true;
    }

    @Override
    protected void loadListData() {
        if (mIsFromSearch) {
            loadListBySearch(false);
        } else {
            loadListNormal();
        }
    }


    @Override
    public boolean canRefresh() {
        if (mIsFromSearch) {
            return mSearchCondition != null && mSearchCondition.trim().length() > 0;
        }
        return true;
    }


    /**
     * 打印点菜联
     */
    private void printOrder(String orderId) {
        CcdPrintHelper.reprintOrder(getContext()
                , PrintData.BIZ_TYPE_PRINT_DISHES_ORDER
                , orderId);
    }

    /**
     * 打印财务联
     */
    private void printCash(String orderId) {
        CcdPrintHelper.manualPrintOrder(getContext()
                , PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER
                , orderId);
    }


    private void loadListNormal() {
        if (mRequest == null) {
            mRequest = OrderListRequest.create();
        }
        mRequest.setEntityId(UserHelper.getEntityId());
        mRequest.setStatus(mStatus);
        mRequest.setPageSize(getPageCount());
        mRequest.setPageIndex(getPageIndex());

        if (getActivity() instanceof TakeoutListActivity) {
            TakeoutListActivity activity = (TakeoutListActivity) getActivity();
            TakeoutUtils.copyFilterToRequest(activity.getFilterParams(), mRequest);
        }
        mPresenter.getOrderList(mRequest);
    }

    public String getCurrentCondition() {
        return mSearchCondition;
    }

    public void setParams(String condition, String cursorMark) {
        mSearchCondition = condition;
        mCursorMark = cursorMark;
    }


    public void loadListBySearch(boolean showLoading) {
        if (mSearchCondition == null || mSearchCondition.trim().length() == 0) {
            return;
        }
        SearchOrderRequest request = new SearchOrderRequest();
        request.setCursorMark(mCursorMark);
        request.setPageSize(getPageCount());
        request.setEntityId(UserHelper.getEntityId());
        request.setCondition(mSearchCondition);

        if (showLoading) {
            showLoading(false);
        }
        mPresenter.searchOrder(request);
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new TakeoutListAdapter(getActivity(), this, R.layout.module_takeout_order_empty, this);
    }


    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void getOrderListSuccess(final OrderListResponse response) {

        showContentView();

        if (isRefreshing() && mOrderGroupDays != null) {
            mOrderGroupDays.clear();
        }

        if (getActivity() instanceof TakeoutListActivity) {
            TakeoutListActivity activity = (TakeoutListActivity) getActivity();
            if (response != null) {
                activity.setWaitNum(response.getWaitNum());
            } else {
                activity.setWaitNum(0);
            }
        }
        final boolean resultEmpty = (response == null || response.getTakeoutOrderVos() == null
                || response.getTakeoutOrderVos().isEmpty());
        renderData(resultEmpty ? null : response);
        if (mStatus == TakeoutConstants.OrderStatus.WAITING_DISPATCH && null != mTakeoutListener) {
            getRecyclerView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTakeoutListener.showBatchDelivery(resultEmpty);
                }
            }, 500);
        }
    }

    private void renderData(final OrderListResponse response) {
        if (response == null || response.getTakeoutOrderVos() == null || response.getTakeoutOrderVos().isEmpty()) {
            renderListData(null);
            return;
        }
        RxUtils.fromCallable(new Callable<List<Object>>() {
            @Override
            public List<Object> call() throws Exception {
                return assembleTakeoutHolderVO(getContext(), response,
                        mStatus == TakeoutConstants.OrderStatus.WAITING_DISPATCH);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Object>>() {
            @Override
            public void call(List<Object> objects) {
                renderListData(objects, response.getTakeoutOrderVos().size());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
                renderListData(null);
            }
        });
    }

    @Override
    public void searchOrderSuccess(OrderListResponse response) {
        hideLoading();

        showContentView();

        if (response != null) {
            mCursorMark = response.getCursorMark();
        }
        boolean resultEmpty = (response == null || response.getTakeoutOrderVos() == null
                || response.getTakeoutOrderVos().isEmpty());

        renderData(resultEmpty ? null : response);
    }

    @Override
    public void searchOrderFailed(String errorCode, String errorMsg) {
        hideLoading();
        if (getAdapter() != null && getAdapter().getListCount() == 0) {
            getAdapter().hideEmpty();
            showErrorView(errorMsg);
        } else {
            ToastUtils.showShortToast(getContext(), errorMsg);
        }
    }

    @Override
    public void getCancelReasonSuccess(List<Reason> reasons) {
        hideLoading();
        showCancelReasons(reasons);
    }

    @Override
    public void getCancelReasonFailed(String errorCode, String errorMsg) {
        hideLoading();
        showToast(errorMsg);
    }


    private HashSet<String> mOrderGroupDays = new HashSet<>();

    /**
     * 组装界面需要展示的数据
     */
    public List<Object> assembleTakeoutHolderVO(Context context, OrderListResponse response, boolean open) {
        List<Takeout> list = response.getTakeoutOrderVos();
        List<Object> container = new ArrayList<>();
        for (Takeout takeout : list) {
            if (!mIsFromSearch) {
                String date = TakeoutUtils.getGroupIdentify(takeout);
                if (!TextUtils.isEmpty(date) && !mOrderGroupDays.contains(date)) {
                    String groupNum = TakeoutUtils.getGroupTitle(context, takeout);
                    mOrderGroupDays.add(date);
                    container.add(createGroupHolderVO(groupNum));
                }
            }

            container.add(new JagVO(true));
            OrderMainHolderVO mainHolderVO = createMainHolderVO(context, takeout);
            mainHolderVO.setOpen(open);
            container.add(mainHolderVO);
            List<Object> children = new ArrayList<>();
            mainHolderVO.setChildNodes(children);

            DeliveryHolderVO deliveryHolderVO = getDeliveryHolderVO(context, takeout);
            if (deliveryHolderVO != null) {
                children.add(deliveryHolderVO);
            }
            children.add(getOrderDescHolderVO(context, takeout));
            List<OrderFoodHolderVO> foodHolderVOs = getOrderFoodHolderVO(context, takeout);
            if (foodHolderVOs != null && !foodHolderVOs.isEmpty()) {
                for (OrderFoodHolderVO foodHolderVO : foodHolderVOs) {
                    children.add(foodHolderVO);
                    if (foodHolderVO.getChildren() != null && !foodHolderVO.getChildren().isEmpty()) {
                        foodHolderVO.getChildren().get(foodHolderVO.getChildren().size() - 1).setShowDivider(true);
                        children.addAll(foodHolderVO.getChildren());
                    }
                }
            }
            OrderPayInfoHolderVO payInfoHolderVO = getOrderPayInfoHolderVO(context, takeout);
            if (payInfoHolderVO != null) {
                children.add(payInfoHolderVO);
            }

            OrderInfoHolderVO orderInfoHolderVO = getOrderInfoHolderVO(context, takeout);
            if (orderInfoHolderVO != null) {
                children.add(orderInfoHolderVO);
            }

            if (mainHolderVO.isOpen() && !children.isEmpty()) {
                container.addAll(children);
            }

            container.add(new JagVO(false));
        }
        return container;
    }


    @Override
    public void getOrderListFailed(String errorCode, String errorMsg) {
        Log.e("TakeoutListFragment", errorCode + "-" + errorMsg);
        loadListFailed();
        //  如果数据为空，展示stateView
        if (getAdapter() != null && getAdapter().getListCount() == 0) {
            getAdapter().hideEmpty();
            showErrorView(errorMsg);
        } else {
            ToastUtils.showShortToast(getContext(), errorMsg);
        }
    }

    @Override
    public void cancelOrderSuccess(CancelTakeoutOrderResponse response) {
        hideLoading();

        startRefresh();

        if (response == null) {
            return;
        }
        if (response.isShowDeliveryType()) {
            getDialogUtil().showDialog(R.string.module_takeout_dialog_title, response.getDeliveryTypeTip(), true, new SingleButtonCallback() {
                @Override
                public void onClick(DialogUtilAction which) {
                    switch (which) {
                        case POSITIVE:
                            requestCancelOrder(mClickedTakeout, mSelectedCancelReason, false);
                            break;
                    }
                }
            });
        } else if (response.getNeedCancelAgain() == 1) {
            getDialogUtil().showDialog(R.string.module_takeout_dialog_title, response.getMessage(),
                    R.string.module_takeout_continue_cancel_positive, R.string.module_takeout_continue_cancel_negative, true, new SingleButtonCallback() {
                        @Override
                        public void onClick(DialogUtilAction which) {
                            switch (which) {
                                case POSITIVE:
                                    requestCancelOrder(mClickedTakeout, mSelectedCancelReason, false);
                                    break;
                            }
                        }
                    });
        } else {
            ToastUtils.showShortToast(getContext(), response.getMessage());
        }

        // 退菜单
        if (response.getInstanceIds() != null) {
            CcdPrintHelper.printInstance(getActivity()
                    , PrintData.TYPE_ORDER
                    , PrintData.BIZ_TYPE_PRINT_CANCEL_INSTANCE
                    , response.getInstanceIds()
                    , ""
                    , "");
        }
    }

    @Override
    public void cancelOrderFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }

    @Override
    public void changeOrderStatusSuccess(OperateOrderResponse response) {
        hideLoading();
        startRefresh();
        if (response == null) {
            return;
        }
        //取消配送可能会返回违约金信息
        if (!TextUtils.isEmpty(response.getMsg())) {
            ToastUtils.showShortToast(getContext(), response.getMsg());
        } else if (mClickedTakeout != null) {
            ToastUtils.showShortToast(getContext(), operateTakeoutTip(mClickedTakeout));
        }

        switch (mOperationType) {
            //如果是下单到厨房，则打印菜单小票
            case TakeoutConstants.OperationType.ORDER_TO_KITCHEN:
                printOrder(response.getOrderId());
                break;
            case TakeoutConstants.OperationType.ORDER_CHECK_OUT:
                printCash(response.getOrderId());
                break;
            case TakeoutConstants.OperationType.ORDER_ARRIVED://已送达
            case TakeoutConstants.OperationType.ORDER_SELF_TAKE://已自取
                //已送达、已自取，如果自动结账完毕成功(resultCode为空)，打印财务联
                if (TextUtils.isEmpty(response.getResultCode())) {
                    printCash(response.getOrderId());
                }
                break;
        }
    }

    @Override
    public void changeOrderStatusFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);

        if (mClickedTakeout == null) {
            return;
        }

        if (TakeoutConstants.TakeoutErrorCode.ACTUAL_PAY_GREATER_NEED_PAY.equals(errorCode)) {
            ToastUtils.showShortToast(getContext(), R.string.module_takeout_checkout_failed_actual_greater_need);
        } else if (TakeoutConstants.TakeoutErrorCode.ACTUAL_PAY_LESS_NEED_PAY.equals(errorCode)) {
            MRouter.getInstance().build(RouterPathConstant.Receipt.PATH)
                    .putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, mClickedTakeout.getOrderId())
                    .putBoolean(RouterPathConstant.Receipt.EXTRA_THIRD_TAKEOUT, TakeoutUtils.isThirdTakeout(mClickedTakeout))
                    .navigation(getActivity());
        }

    }

    @Override
    public void getDeliveryInfoSuccess(DeliveryInfoResponse response) {
        hideLoading();
        if (response == null || response.getTakeoutDeliveryInfoVos() == null
                || response.getTakeoutDeliveryInfoVos().isEmpty()) {
            ToastUtils.showShortToast(getContext(), R.string.module_takeout_delivery_empty);
            return;
        }
        showDeliveryDialog(response.getTakeoutDeliveryInfoVos());
    }

    @Override
    public void getDeliveryInfoFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
    }


    public void refreshByConditions(OrderListRequest request) {
        TakeoutUtils.copyFilterToRequest(request, mRequest);
        startRefresh();
    }


    private OptionsPickerView mOptionsPickerView;

    /**
     * 弹出撤单原因
     *
     * @param list List<Reason>
     */
    private void showCancelReasons(final List<Reason> list) {
        if (list == null || list.isEmpty()) {
            mSelectedCancelReason = null;
            cancelOrderDialog(null);
            return;
        }
        if (mOptionsPickerView == null) {
            mOptionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (options1 > list.size() - 1) {
                        return;
                    }
                    mSelectedCancelReason = list.get(options1).getName();
                    cancelOrderDialog(mSelectedCancelReason);
                }
            }).setTitleText(getString(R.string.module_takeout_cancel_order_reason))
                    .setTitleSize(18)
                    .setContentTextSize(18)//设置滚轮文字大小
                    .setDividerColor(Color.GRAY)//设置分割线的颜色
                    .setBgColor(Color.WHITE)
                    .setTitleBgColor(Color.WHITE)
                    .setTitleColor(Color.BLACK)
                    .setCancelColor(Color.BLUE)
                    .setSubmitColor(Color.BLUE)
                    .setTextColorCenter(Color.LTGRAY)
                    .isCenterLabel(false)
                    .setOutSideCancelable(true)
                    .build();
            mOptionsPickerView.setPicker(list);
        }
        mOptionsPickerView.show();
    }

    /**
     * 取消外卖单前弹窗提醒
     */
    private void cancelOrderDialog(final String cancelReason) {
        getDialogUtil().showDialog(R.string.module_takeout_dialog_title, R.string.module_takeout_dialog_content_cancel_order,
                true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        switch (which) {
                            case POSITIVE:
                                if (mClickedTakeout == null) {
                                    return;
                                }
                                requestCancelOrder(mClickedTakeout, cancelReason, mClickedTakeout.getNeedCancelAgain() == 0);
                                break;
                        }
                    }
                });
    }

    /**
     * 请求取消外卖单接口
     *
     * @param cancelReason      撤单原因
     * @param checkDeliveryType 取消外卖单前是否检查配送方式（第一次撤单需要传true）
     */
    private void requestCancelOrder(Takeout takeout, String cancelReason, boolean checkDeliveryType) {
        showLoading(false);
        CancelTakeoutOrderRequest request = new CancelTakeoutOrderRequest();
        request.setEntityId(UserHelper.getEntityId());
        request.setOpUserId(UserHelper.getUserId());
        request.setModifyTime(takeout.getModifyTime());
        request.setOrderId(takeout.getOrderId());
        request.setCheckDeliveryType(checkDeliveryType);
        request.setReason(cancelReason);
        mPresenter.cancelOrder(request);

        Log.e("TakeoutListFragment", takeout.getCode() + "=" + takeout.getOrderId() + "-" + cancelReason);
    }

    private void requestOperationTakeout(Takeout takeout) {
        showLoading(false);
        OperateOrderRequest operateOrderRequest = new OperateOrderRequest();
        operateOrderRequest.setOrderId(takeout.getOrderId());
        operateOrderRequest.setModifyTime(takeout.getModifyTime());
        operateOrderRequest.setOpUserId(UserHelper.getUserId());
        operateOrderRequest.setEntityId(UserHelper.getEntityId());
        operateOrderRequest.setOperateType(TakeoutUtils.getOperationType(takeout));
        if (takeout.getTakeoutOrderDetailVo() != null) {
            operateOrderRequest.setDeliveryPlatformCode(takeout.getTakeoutOrderDetailVo().getDeliveryPlatformCode());
        }
        mPresenter.changeOrderStatus(operateOrderRequest);
    }

    @Override
    public void onAdapterClick(int type, View view, final Object data) {
        //如果正在刷新，则不能点击按钮，可能状态不一致
        if (isRefreshing()) {
            return;
        }
        switch (type) {
            case TakeoutListAdapter.CLICK_TYPE_CANCEL_ORDER://撤单
                //  检查撤单权限
                if (!BatchPermissionHelper.getPermission(Permission.CancelOrder.ACTION_CODE)) {
                    ToastUtils.showShortToast(getContext(), getString(R.string.alert_permission_deny,
                            getString(R.string.module_takeout_permission_action_cancel_order)));
                    return;
                }
                if (!(data instanceof Takeout)) {
                    return;
                }
                mClickedTakeout = (Takeout) data;
                // 底部弹出撤单原因
                showLoading(false);
                mPresenter.getCancelReason();
                break;
            case TakeoutListAdapter.CLICK_TYPE_CHANGE_STATUS:
                if (!(data instanceof Takeout)) {
                    return;
                }
                mClickedTakeout = (Takeout) data;
                mOperationType = TakeoutUtils.getOperationType(mClickedTakeout);
                switch (mOperationType) {
                    case TakeoutConstants.OperationType.ORDER_DISPATCH:
                        DeliveryActivity.launchActivity(this, mClickedTakeout, null, null, REQUEST_CODE_DELIVERY);
                        break;
                    case TakeoutConstants.OperationType.ORDER_CANCEL_DISPATCH:
                        int messageId = TakeoutConstants.DeliveryPlatformCode.SHUNFENG_DELIVERY
                                .equals(mClickedTakeout.getTakeoutOrderDetailVo().getDeliveryPlatformCode()) ?
                                R.string.module_takeout_dialog_cancel_delivery_shunfeng :
                                R.string.module_takeout_dialog_cancel_delivery;

                        getDialogUtil().showDialog(R.string.dialog_title, messageId,
                                R.string.module_takeout_dialog_cancel_delivery_positive,
                                R.string.module_takeout_dialog_cancel_delivery_negative, true, new SingleButtonCallback() {
                                    @Override
                                    public void onClick(DialogUtilAction which) {
                                        switch (which) {
                                            case POSITIVE:
                                                requestOperationTakeout(mClickedTakeout);
                                                break;
                                        }
                                    }
                                });
                        break;
                    default:
                        requestOperationTakeout(mClickedTakeout);
                }
                break;
            case TakeoutListAdapter.CLICK_TYPE_PRINT:
                if (!(data instanceof Takeout)) {
                    return;
                }
                Takeout takeout1 = (Takeout) data;
                printOrder(takeout1.getOrderId());
                ToastUtils.showShortToast(getContext(), R.string.module_takeout_print_waiting);
                break;
            case TakeoutListAdapter.CLICK_TYPE_SHOW_PHONE_TIP:
                if (data == null) {
                    return;
                }
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data));
                startActivity(dialIntent);
                break;

            case TakeoutListAdapter.CLICK_TYPE_SHOW_DELIVERY_INFO:
                if (!(data instanceof Takeout)) {
                    return;
                }
                Takeout takeout2 = (Takeout) data;
                if (takeout2.getTakeoutOrderDetailVo().getTakeoutDeliveryInfoVos() == null
                        || takeout2.getTakeoutOrderDetailVo().getTakeoutDeliveryInfoVos().isEmpty()) {
                    ToastUtils.showShortToast(getContext(), R.string.module_takeout_delivery_empty);
                    return;
                }
                showDeliveryDialog(takeout2.getTakeoutOrderDetailVo().getTakeoutDeliveryInfoVos());
//                showLoading(false);
//                DeliveryInfoRequest deliveryInfoRequest = new DeliveryInfoRequest();
//                deliveryInfoRequest.setOrderId(takeout2.getOrderId());
//                deliveryInfoRequest.setEntityId(UserHelper.getEntityId());
//                deliveryInfoRequest.setOpUserId(UserHelper.getUserId());
//                mPresenter.getDeliveryInfo(deliveryInfoRequest);
                break;
        }
    }

    @Override
    public void onAdapterClickEdit(int type, View view, Object data) {

    }

    private void showDeliveryDialog(List<DeliveryInfo> list) {
        final Dialog dialog = new Dialog(getActivity(), R.style.Module_Takeout_Dialog);
        dialog.setContentView(R.layout.module_takeout_dialog_delivery_info);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.findViewById(R.id.text_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        fillDeliveryView(dialog, list, LayoutInflater.from(getContext()));
        dialog.show();
    }

    private void fillDeliveryView(Dialog dialog, List<DeliveryInfo> list, LayoutInflater inflater) {
        LinearLayout llDeliveryProgress = (LinearLayout) dialog.findViewById(R.id.ll_delivery_progress);
        LinearLayout llDeliveryInfo = (LinearLayout) dialog.findViewById(R.id.ll_delivery_info);
        for (int i = 0; i < list.size(); i++) {
            boolean addedHistory = false;
            if (i == 0) {
                View view = inflater.inflate(R.layout.module_takeout_view_delivery_current_status, llDeliveryProgress, false);
                llDeliveryProgress.addView(view);
            } else {
                View view = inflater.inflate(R.layout.module_takeout_view_delivery_history_status, llDeliveryProgress, false);
                llDeliveryProgress.addView(view);
                addedHistory = true;
            }

            if (i != list.size() - 1) {
                View view = inflater.inflate(R.layout.module_takeout_view_delivery_vertical_divider, llDeliveryProgress, false);
                llDeliveryProgress.addView(view);
                if (llDeliveryProgress.getChildCount() >= 3) {
                    view.getLayoutParams().height += getContext().getResources().getDimensionPixelOffset(R.dimen.module_takeout_delivery_divider_gap);
                }
            }
            if (!addedHistory && i == list.size() - 1 && list.size() != 1) {
                View view = inflater.inflate(R.layout.module_takeout_view_delivery_history_status, llDeliveryProgress, false);
                llDeliveryProgress.addView(view);
            }

            DeliveryInfo info = list.get(i);
            View view = inflater.inflate(R.layout.module_takeout_item_delivery_info, llDeliveryProgress, false);
            TextView textDesc = (TextView) view.findViewById(R.id.text_delivery_desc);
            TextView textTime = (TextView) view.findViewById(R.id.text_delivery_time);
            textDesc.setTextColor(i == 0 ? getResources().getColor(R.color.module_takeout_delivery_current)
                    : getResources().getColor(R.color.module_takeout_delivery_history));
            textTime.setTextColor(textDesc.getTextColors());
            textDesc.setText(info.getDesc());
            textTime.setText(info.getDate());
            llDeliveryInfo.addView(view);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_DELIVERY) {
            startRefresh();
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        if (mIsFromSearch) {
            loadListBySearch(false);
        } else {
            loadListNormal();
        }
    }

    public void batchDelivery() {
        PendingDeliveryListActivity.launchActivity(this, REQUEST_CODE_DELIVERY);
    }

    public void setTakeoutListener(TakeoutListener takeoutListener) {
        mTakeoutListener = takeoutListener;
    }

    public interface TakeoutListener {
        void showBatchDelivery(boolean isShow);
    }
}
