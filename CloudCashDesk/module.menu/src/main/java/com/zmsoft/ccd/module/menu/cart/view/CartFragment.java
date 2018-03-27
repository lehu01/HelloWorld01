package com.zmsoft.ccd.module.menu.cart.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.model.PrintData;
import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.utils.RouterActivityManager;
import com.jakewharton.rxbinding.view.RxView;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.Callable;
import com.zmsoft.ccd.lib.base.rxjava.RxUtils;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.utils.dialogutil.listener.SingleButtonCallback;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.CartAdapter;
import com.zmsoft.ccd.module.menu.cart.adapter.cartlist.items.CartRecyclerItem;
import com.zmsoft.ccd.module.menu.cart.model.DinningTableVo;
import com.zmsoft.ccd.module.menu.cart.model.ItemVo;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderRequest;
import com.zmsoft.ccd.module.menu.cart.model.SubmitOrderVo;
import com.zmsoft.ccd.module.menu.cart.presenter.cartlist.CartContract;
import com.zmsoft.ccd.module.menu.events.BaseEvents;
import com.zmsoft.ccd.module.menu.events.model.ModifyCartParam;
import com.zmsoft.ccd.module.menu.helper.CartHelper;
import com.zmsoft.ccd.module.menu.helper.DataMapLayer;
import com.zmsoft.ccd.module.menu.menu.ui.MenuListActivity;
import com.zmsoft.ccd.network.ErrorBizHttpCode;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 购物车
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public class CartFragment extends BaseListFragment implements CartContract.View {
    @BindView(R2.id.text_sure_order)
    TextView mTextSureOrder;
    @BindView(R2.id.text_go_on)
    TextView mTextGoOn;
    @BindView(R2.id.text_hang_up)
    TextView mTextHangUp;
    @BindView(R2.id.image_unread)
    ImageView mImageUnread;
    @BindView(R2.id.image_refresh_notice)
    ImageView mImageRefreshNotice;
    @BindView(R2.id.layout_refresh_notice)
    RelativeLayout mLayoutRefreshNotice;
    @BindView(R2.id.layout_refresh)
    FrameLayout mLayoutRefresh;
    @BindView(R2.id.layout_go_on)
    FrameLayout mLayoutGoOn;
    @BindView(R2.id.layout_sure_order)
    FrameLayout mLayoutSureOrder;
    @BindView(R2.id.layout_hang_up)
    FrameLayout mLayoutHangUp;

    private CartContract.Presenter mPresenter;
    /**
     * 菜单页传递过来的参数
     */
    private OrderParam mCreateOrderParam;
    /**
     * 上级页面传参，标识上级页面的来源
     *
     * @see RouterPathConstant.Cart
     */
    private int mFrom;
    /**
     * 购物车数据
     */
    private DinningTableVo mDinningTableVo;
    /**
     * 是否是加菜（点击确认下单之后，可能该桌已开单，所以要调用加菜接口，加菜接口和开单是同一个接口，所以需要区分）
     */
    private boolean isToAddFood;

    private List<CartRecyclerItem> mCartRecyclerItemList;

    public static CartFragment newInstance(OrderParam createOrderParam, int from, DinningTableVo dinningTableVo) {
        Bundle args = new Bundle();
        args.putSerializable(RouterPathConstant.Cart.EXTRA_CREATE_ORDER_PARAM, createOrderParam);
        args.putSerializable(RouterPathConstant.Cart.EXTRA_DINNING_VO, dinningTableVo);
        args.putInt(RouterPathConstant.Cart.EXTRA_FROM, from);
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.module_menu_cart_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
        disableRefresh();
        Bundle bundle = getArguments();
        if (null != bundle) {
            Serializable serializable = bundle.getSerializable(RouterPathConstant.Cart.EXTRA_CREATE_ORDER_PARAM);
            if (null != serializable) {
                mCreateOrderParam = (OrderParam) serializable;
            }
            Serializable dinnigVo = bundle.getSerializable(RouterPathConstant.Cart.EXTRA_DINNING_VO);
            if (null != dinnigVo) {
                mDinningTableVo = (DinningTableVo) dinnigVo;
            }
            mFrom = bundle.getInt(RouterPathConstant.Cart.EXTRA_FROM);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        RxView.clicks(mTextSureOrder).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        submitOrder(null);
                    }
                });
        RxView.clicks(mTextHangUp).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.hangUpOrder(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode());
                    }
                });
        RxView.clicks(mTextGoOn).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(getActivity(), MenuListActivity.class);
                        intent.putExtra(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, mCreateOrderParam);
                        intent.putExtra(RouterPathConstant.Cart.EXTRA_FROM, RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER);
                        startActivityForResult(intent, RouterPathConstant.Cart.REQUEST_CODE_TO_MENU_LIST);
                    }
                });
        RxView.clicks(mLayoutRefresh).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        refreshCart();
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (null != mDinningTableVo && null != mDinningTableVo.getKindUserCarts()
                && !mDinningTableVo.getKindUserCarts().isEmpty()) {
            showCart(mDinningTableVo);
        } else {
            showLoadingView();
        }
    }

    @Override
    protected void loadListData() {
        mPresenter.queryCart(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode()
                , mCreateOrderParam == null ? null : mCreateOrderParam.getOrderId()
                , mFrom != RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER);
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
    protected BaseListAdapter createAdapter() {
        return new CartAdapter(getActivity(), null);
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(CartContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        if (event == BaseEvents.CommonEvent.CART_CLEAR_EVENT) {
            mPresenter.clearCart(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode()
                    , mCreateOrderParam == null ? null : mCreateOrderParam.getOrderId());
        } else if (event == BaseEvents.CommonEvent.CART_LIST_FOODNUM_MODIFY) {
            if (null != event.getObject()) {
                if (event.getObject() instanceof ItemVo) {
                    ItemVo itemVo = (ItemVo) event.getObject();
                    //区分修改的是自定义菜还是普通菜
                    if (itemVo.getKind() == CartHelper.CartFoodKind.KIND_CUSTOM_FOOD) {
                        mPresenter.saveCustomMenuToCart(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode()
                                , itemVo, mCreateOrderParam == null ? 0 : mCreateOrderParam.getNumber());
                    } else {
                        mPresenter.modifyCart(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode()
                                , mCreateOrderParam == null ? null : mCreateOrderParam.getOrderId(), itemVo);
                    }
                }
            }
        } else if (event == BaseEvents.CommonEvent.CART_TO_MODIFY_ORDER) {
            goToModifyOrder();
        }
    }

    @Subscribe
    public void onReceiveRefreshEvent(RouterBaseEvent.CommonEvent event) {
        if (event == RouterBaseEvent.CommonEvent.EVENT_REFRESH_SHOP_CAR) { //改单成功之后，需要刷新最新桌位
            if (null != event.getObject() && event.getObject() instanceof OrderParam) {
                mCreateOrderParam = (OrderParam) event.getObject();
            }
            refreshCart();
            //购物车修改，通知菜单列表
            RouterBaseEvent.CommonEvent modifyCartEvent = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
            ModifyCartParam modifyCartParam = new ModifyCartParam(null, mCreateOrderParam);
            modifyCartEvent.setObject(modifyCartParam);
            EventBusHelper.post(modifyCartEvent);
        } else if (event == RouterBaseEvent.CommonEvent.EVENT_NOTIFY_CART_NEW) {
            mImageUnread.setVisibility(View.VISIBLE);
            mImageRefreshNotice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCart(final DinningTableVo dinningTableVo) {
        enableRefresh();
        mDinningTableVo = dinningTableVo;
        if (dinningTableVo != null) {
            Subscription subscription = RxUtils.fromCallable(new Callable<List<CartRecyclerItem>>() {
                @Override
                public List<CartRecyclerItem> call() {
                    mCartRecyclerItemList = DataMapLayer.getCartList(getActivity(), dinningTableVo
                            , mCreateOrderParam);
                    return mCartRecyclerItemList;
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()
                    .subscribe(new Action1<List<CartRecyclerItem>>() {
                        @Override
                        public void call(List<CartRecyclerItem> cartRecyclerItems) {
                            cleanAll();
                            renderListData(cartRecyclerItems);
                            mLayoutSureOrder.setVisibility(View.VISIBLE);
                            mLayoutGoOn.setVisibility(mFrom == RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER ? View.VISIBLE : View.GONE);
                            if (mFrom != RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER
                                    && null != mCreateOrderParam
                                    && TextUtils.isEmpty(mCreateOrderParam.getOrderId())) {
                                if (TextUtils.isEmpty(mCreateOrderParam.getSeatCode())
                                        || mCreateOrderParam.getSeatCode().contains(RetailOrderHelper.SEAT_CODE_BY_RETAIL)) {
                                    mLayoutHangUp.setVisibility(View.VISIBLE);
                                } else {
                                    mLayoutHangUp.setVisibility(View.GONE);
                                }
                            }
                            mLayoutRefreshNotice.setVisibility(View.VISIBLE);
                            if (isCartEmpty()) {
                                showCartEmptyView();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.e(throwable.toString());
                        }
                    });
            addRxSubscription(subscription);
        } else {
            showCartEmptyView();
        }
    }

    @Override
    public void queryCartError(ErrorBody body) {
        cleanAll();
        mLayoutGoOn.setVisibility(View.GONE);
        mLayoutHangUp.setVisibility(View.GONE);
        mLayoutSureOrder.setVisibility(View.GONE);
        mLayoutRefreshNotice.setVisibility(View.GONE);
        //如果购物车已过期（从挂单列表进入购物车时会出现已过期的情况）,显示过期界面
        if (null != body && !TextUtils.isEmpty(body.getErrorCode()) && body.getErrorCode().equals(ErrorBizHttpCode.ERR_CART106)) {
            showContentView();
            enableRefresh();
            RxUtils.fromCallable(new Callable<List<CartRecyclerItem>>() {
                @Override
                public List<CartRecyclerItem> call() {
                    mCartRecyclerItemList = DataMapLayer.getExpiredCartList();
                    return mCartRecyclerItemList;
                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<CartRecyclerItem>>() {
                        @Override
                        public void call(List<CartRecyclerItem> cartRecyclerItems) {
                            cleanAll();
                            renderListData(cartRecyclerItems);
                            showCartEmptyView();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        } else {
            showErrorView(body.getMessage());
        }
    }

    @Override
    public void confimOrder(final SubmitOrderVo submitOrderVo) {
        if (null != submitOrderVo) {
            RetailOrderHelper.addRetailMap(submitOrderVo.getOrderId(), mCreateOrderParam.getSeatCode());
            if (isToAddFood) {
                //打印加菜小票
                if (null != submitOrderVo.getInstanceIds() && !submitOrderVo.getInstanceIds().isEmpty()) {
                    CcdPrintHelper.printInstance(getActivity()
                            , PrintData.TYPE_SELF_ORDER
                            , PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE
                            , submitOrderVo.getInstanceIds()
                            , mCreateOrderParam.getSeatCode()
                            , submitOrderVo.getOrderId());
                    // 打印标签
                    CcdPrintHelper.printLabelInstance(getActivity()
                            , PrintData.BIZ_TYPE_PRINT_LABEL
                            , submitOrderVo.getInstanceIds());
                }
                //通知首页刷新桌位
                EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
                //跳转到订单详情
                MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                        .putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID
                                , submitOrderVo.getOrderId())
                        .navigation(getActivity());
                clearOtherActivity();
            } else {
                if (submitOrderVo.isOpenTable()) {
                    getDialogUtil().showDialog(R.string.material_dialog_title
                            , R.string.module_menu_cartdetail_submit_order_alert,
                            R.string.module_menu_cartdetail_submit_order_positive
                            , R.string.module_menu_cartdetail_submit_order_negative
                            , true, new SingleButtonCallback() {
                                @Override
                                public void onClick(DialogUtilAction which) {
                                    if (which == DialogUtilAction.POSITIVE) {
                                        isToAddFood = true;
                                        submitOrder(submitOrderVo.getOrderId());
                                    } else if (which == DialogUtilAction.NEGATIVE) {
                                        goToModifyOrder();
                                    }
                                }
                            });
                } else {
                    //打印加菜小票
                    if (null != mCreateOrderParam && !TextUtils.isEmpty(mCreateOrderParam.getOrderId())) {
                        isToAddFood = true;
                        //如果是加菜消息，打印加菜单
                        if (null != submitOrderVo.getInstanceIds() && !submitOrderVo.getInstanceIds().isEmpty()) {
                            CcdPrintHelper.printInstance(getActivity()
                                    , PrintData.TYPE_SELF_ORDER
                                    , PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE
                                    , submitOrderVo.getInstanceIds()
                                    , mCreateOrderParam.getSeatCode()
                                    , submitOrderVo.getOrderId());
                            // 打印标签
                            CcdPrintHelper.printLabelInstance(getActivity()
                                    , PrintData.BIZ_TYPE_PRINT_LABEL
                                    , submitOrderVo.getInstanceIds());
                        }
                    } else {
                        //如果是新单，打印点菜单
                        CcdPrintHelper.printOrder(getActivity()
                                , PrintData.TYPE_SELF_ORDER
                                , PrintData.BIZ_TYPE_PRINT_DISHES_ORDER
                                , submitOrderVo.getOrderId()
                                , mCreateOrderParam.getSeatCode());
                        // 标签打印：点菜单
                        CcdPrintHelper.printLabelOrderInstance(getActivity()
                                , PrintData.BIZ_TYPE_PRINT_LABEL
                                , submitOrderVo.getOrderId());
                    }
                    //通知首页刷新桌位
                    EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
                    if (isToAddFood) {
                        //跳转到订单详情
                        MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                                .putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID
                                        , submitOrderVo.getOrderId())
                                .navigation(getActivity());
                        clearOtherActivity();
                    } else {
                        if (submitOrderVo.isTurnToFindOrder()) {
                            MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY)
                                    .putInt(RouterPathConstant.Main.EXTRA_SWITCH_TAB_PARAM, 2).navigation(getActivity());
                        } else if (submitOrderVo.isTurnToCheckout()) {
                            MRouter.getInstance().build(RouterPathConstant.Receipt.PATH)
                                    .putString(RouterPathConstant.Receipt.EXTRA_ORDER_ID, submitOrderVo.getOrderId())
                                    .navigation(getActivity());
                            clearOtherActivity();
                        } else {
                            //跳转到订单详情
                            MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                                    .putString(RouterPathConstant.OrderDetail.EXTRA_ORDER_ID
                                            , submitOrderVo.getOrderId())
                                    .navigation(getActivity());
                            clearOtherActivity();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RouterPathConstant.Cart.REQUEST_CODE_TO_MENU_LIST: //继续点菜时，从菜单列表返回到购物车，需要刷新购物车
                    refreshCart();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 刷新购物车
     */
    public void refreshCart() {
        mImageUnread.setVisibility(View.INVISIBLE);
        mImageRefreshNotice.setVisibility(View.GONE);
        startRefresh();
    }

    private void submitOrder(String orderId) {
        long cartime = 0;
        if (null != mDinningTableVo) {
            cartime = mDinningTableVo.getCartTime();
        }
        if (TextUtils.isEmpty(orderId)) {
            orderId = mCreateOrderParam == null ? null : mCreateOrderParam.getOrderId();
        }
        SubmitOrderRequest request = SubmitOrderRequest.createRequestForCatering(mCreateOrderParam == null ? null : mCreateOrderParam.getSeatCode()
                , orderId, UserHelper.getEntityId(), UserHelper.getUserId(), cartime, UserHelper.getMemberId());
        mPresenter.submitOrder(request, mCartRecyclerItemList);
    }

    /**
     * 跳转到改单页
     */
    private void goToModifyOrder() {
        mCreateOrderParam.setOriginSeatCode(mCreateOrderParam.getSeatCode());
        mCreateOrderParam.setNumber(mDinningTableVo.getPeople());
        mCreateOrderParam.setMemo(mDinningTableVo.getMemo());
        mCreateOrderParam.setWait(mDinningTableVo.isWait());
        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM
                        , RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_UPDATE_BY_SHOP_CAR)
                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, mCreateOrderParam)
                .navigation(getActivity());
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
        finishRefresh();
    }

    @Override
    public void alertSoldOut(String errorMessage) {
        getDialogUtil().showNoticeDialog(R.string.material_dialog_title
                , errorMessage,
                R.string.module_menu_cart_dialog_iknow
                , false, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            refreshCart();
                        }
                    }
                });
    }

    @Override
    public void successHangUpOrder() {
        showToast(R.string.module_menu_cart_hangup_order_success);
        MRouter.getInstance().build(RouterPathConstant.HangUpOrderList.PATH).navigation(getActivity());
        getActivity().setResult(Activity.RESULT_OK);
        clearOtherActivity();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadListData();
    }

    private boolean isCartEmpty() {
        if (null != mCartRecyclerItemList && !mCartRecyclerItemList.isEmpty()) {
            if (mCartRecyclerItemList.size() == 1 && mCartRecyclerItemList.get(0).getItemType() ==
                    CartRecyclerItem.ItemType.TYPE_ORDER_INFO) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private void showCartEmptyView() {
        mLayoutGoOn.setVisibility(View.GONE);
        mLayoutHangUp.setVisibility(View.GONE);
        mLayoutSureOrder.setVisibility(View.GONE);
        mLayoutRefreshNotice.setVisibility(View.VISIBLE);
    }

    /**
     * 下单成功，跳转到订单详情，关闭掉开单页、菜单列表页、购物车页面
     */
    private void clearOtherActivity() {
        RouterActivityManager.get().finishActivity(RouterPathConstant.CreateOrUpdateOrder.PATH);
        RouterActivityManager.get().finishActivity(MenuListActivity.class);
        //如果是从挂单列表打开购物车，则需要关闭之前的挂单列表界面
        if (mFrom == RouterPathConstant.Cart.EXTRA_FROM_HANG_UP_ORDER) {
            RouterActivityManager.get().finishActivity(RouterPathConstant.HangUpOrderList.PATH);
        }
        if (isHostActive()) {
            getActivity().finish();
        }
    }
}
