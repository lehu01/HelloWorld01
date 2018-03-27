package com.zmsoft.ccd.business;

import android.content.Context;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.business.IQuickOpenOrderRouter;
import com.zmsoft.ccd.data.business.IQuickOpenOrderCallBack;
import com.zmsoft.ccd.data.business.IQuickOpenOrderSource;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.order.IOrderSource;
import com.zmsoft.ccd.data.source.order.OrderSource;
import com.zmsoft.ccd.data.source.seat.ISeatSource;
import com.zmsoft.ccd.data.source.seat.SeatSource;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.RetailOrderHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.DialogUtilAction;
import com.zmsoft.ccd.lib.widget.dialogutil.listener.SingleButtonCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/10/31 17:06
 *     desc  : 快速开单
 * </pre>
 */
@Route(path = IQuickOpenOrderRouter.QuickOpenOrder.QUICK_OPEN_ORDER)
public class QuickOpenOrder implements IQuickOpenOrderSource {

    private final IOrderSource mIOrderSource = new OrderSource();
    private final ISeatSource mISeatSource = new SeatSource();
    private final ICommonSource mICommonSource = new CommonRemoteSource();
    private IQuickOpenOrderCallBack iQuickOpenOrderCallBack;
    private OrderParam mOrderParam;
    private Context mContext;

    @Override
    public void doQuickOpenOrder(Context context, OrderParam orderParam) {
        if (orderParam != null) {
            doQuickOpenOrder(context, orderParam, null);
        }
    }

    @Override
    public void doQuickOpenOrder(Context context, OrderParam orderParam, IQuickOpenOrderCallBack iQuickOpenOrderCallBack) {
        if (orderParam != null) {
            this.mContext = context;
            this.mOrderParam = orderParam;
            this.iQuickOpenOrderCallBack = iQuickOpenOrderCallBack;
            if (ConfigHelper.hasOpenCashClean(GlobalVars.context)) { // 现金收款
                getCashLimit();
            } else {
                if (mOrderParam.getSeatCode().contains(RetailOrderHelper.SEAT_CODE_BY_RETAIL)) {
                    mOrderParam.setOriginNumber(1);
                    mOrderParam.setMemo("");
                    mOrderParam.setWait(false);
                    openOrderBuShopCar();
                } else {
                    getSeatStatusBySeatCode();
                }
            }
        }
    }

    //====================================================================================
    //执行接口
    //====================================================================================
    private void getCashLimit() {
        mICommonSource.getCashLimit(UserHelper.getEntityId(), UserHelper.getUserId())
                .retryWhen(new RetryWithDelay(3, 500))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CashLimit>() {
                    @Override
                    public void call(CashLimit cashLimit) {
                        if (cashLimit != null && cashLimit.hasExceedCashLimit()) {
                            String content = GlobalVars.context.getString(R.string.dialog_content_reach_cash_limit,
                                    NumberUtils.trimPointIfZero(cashLimit.getCollectLimit()),
                                    NumberUtils.trimPointIfZero(cashLimit.getCurrAmount()));
                            showNoticeDialog(content);
                        } else {
                            if (!StringUtils.isEmpty(mOrderParam.getSeatName())) {
                                getSeatStatusBySeatCode();
                            } else {
                                openOrderBuShopCar();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (throwable instanceof ServerException) {
                            ServerException serverException = (ServerException) throwable;
                            showToast(serverException.getMessage());
                        }
                    }
                });
    }

    private void getSeatStatusBySeatCode() {
        mISeatSource.getSeatStatusBySeatCode(UserHelper.getEntityId()
                , mOrderParam.getSeatCode()
                , new Callback<SeatStatus>() {
                    @Override
                    public void onSuccess(SeatStatus data) {
                        // 没有开桌
                        if (data == null || data.getStatus() == SeatStatus.NO_OPEN_ORDER) {
                            openOrderBuShopCar();
                        } else {
                            showDialogByCreate();
                        }
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        showToast(body.getMessage());
                    }
                });
    }

    private void openOrderBuShopCar() {
        mIOrderSource.createOrder(UserHelper.getEntityId()
                , UserHelper.getMemberId()
                , mOrderParam.getSeatCode()
                , mOrderParam.getOriginNumber()
                , mOrderParam.getMemo()
                , mOrderParam.isWait(), new Callback<OpenOrderVo>() {
                    @Override
                    public void onSuccess(OpenOrderVo data) {
                        if (data != null) {
                            if (data.getStatus() == OpenOrderVo.STATUS_SUCCESS) {
                                gotoMenuListActivity();
                            } else if (data.getStatus() == OpenOrderVo.STATUS_MORE_THAN_50) {
                                showToast(data.getMessage());
                                gotoMenuListActivity();
                            } else if (data.getStatus() == OpenOrderVo.STATUS_MORE_THAN_100) {
                                // 是否有关注的零售单
                                if (BaseSpHelper.isWatchedRetail(GlobalVars.context)) {
                                    showRetailOrderDialog(data.getMessage());
                                } else {
                                    showNoticeDialog(GlobalVars.context.getString(R.string.prompt_no_watched_retail));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailed(ErrorBody body) {
                        showToast(body.getMessage());
                    }
                });
    }

    //====================================================================================
    // 弹窗提示
    //====================================================================================
    private void showNoticeDialog(String content) {
        final DialogUtil dialogUtil = new DialogUtil(mContext);
        dialogUtil.showNoticeDialog(R.string.dialog_title, content, R.string.dialog_positive_reach_cash_limit, true, null);
    }

    private void showDialogByCreate() {
        final DialogUtil dialogUtil = new DialogUtil(mContext);
        dialogUtil.showDialog(R.string.prompt
                , R.string.open_car_prompt
                , R.string.my_add_instance
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            gotoMenuListActivity();
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    private void showRetailOrderDialog(String message) {
        final DialogUtil dialogUtil = new DialogUtil(mContext);
        dialogUtil.showDialog(R.string.prompt
                , message
                , R.string.go_deal_with
                , R.string.cancel
                , true, new SingleButtonCallback() {
                    @Override
                    public void onClick(DialogUtilAction which) {
                        if (which == DialogUtilAction.POSITIVE) {
                            EventBusHelper.post(BaseEvents.CommonEvent.EVENT_SWITCH_WATCHED_RETAIL);
                            MRouter.getInstance().build(RouterPathConstant.PATH_MAIN_ACTIVITY).navigation(mContext);
                        } else if (which == DialogUtilAction.NEGATIVE) {
                            dialogUtil.dismissDialog();
                        }
                    }
                });
    }

    //====================================================================================
    // go to other activity
    //====================================================================================
    private void gotoMenuListActivity() {
        if (mOrderParam != null) {
            MRouter.getInstance().build(RouterPathConstant.MenuList.PATH)
                    .putSerializable(RouterPathConstant.MenuList.EXTRA_CREATE_ORDER_PARAM, mOrderParam)
                    .navigation(mContext);
            if (iQuickOpenOrderCallBack != null) {
                iQuickOpenOrderCallBack.quickOpenOrderSuccess(mOrderParam);
            }
        }
    }

    private void showToast(String message) {
        ToastUtils.showShortToastSafe(GlobalVars.context, message);
    }
}
