package com.zmsoft.ccd.module.main.order.find;


import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.desk.DeskRepository;
import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.lib.bean.order.OrderConstants;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindAdapter;
import com.zmsoft.ccd.module.main.order.find.viewmodel.OrderFindViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * 1.获取关注的区域                com.dfire.soa.cloudcash.queryTabMenuByUserId
 * 2.切换tabLayout后，先显示原有数据，然后根据areaId加载
 * 2.1.按区域获取关注桌位的订单       com.dfire.soa.cloudcash.getWatchedSeatStatusListByArea
 * 2.2.获取零售单信息                com.dfire.tp.client.service.ITradePlatformService.queryFocusOrder
 *
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 15:43.
 */

public class OrderFindPresenter implements OrderFindContract.Presenter {

    private OrderFindContract.View mView;
    private final DeskRepository mDeskRepository;
    private final SeatSourceRepository mSeatSourceRepository;
    private final OrderSourceRepository mOrderSourceRepository;

    private final OrderFindViewModel mOrderFindViewModel;


    //================================================================================
    // inject
    //================================================================================
    @Inject
    public OrderFindPresenter(OrderFindContract.View mView, DeskRepository mDeskRepository, SeatSourceRepository seatSourceRepository, OrderSourceRepository orderSourceRepository) {
        this.mView = mView;
        this.mDeskRepository = mDeskRepository;
        this.mSeatSourceRepository = seatSourceRepository;
        this.mOrderSourceRepository = orderSourceRepository;
        this.mOrderFindViewModel = new OrderFindViewModel();
    }

    @Inject
    void setOrderFindPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // OrderFindContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void loadWatchedArea() {
        String entityId = UserHelper.getEntityId();
        String userId = UserHelper.getUserId();
        mDeskRepository.loadWatchAreaList(entityId, userId)
                .subscribe(new Action1<List<TabMenuVO>>() {
                    @Override
                    public void call(List<TabMenuVO> tabMenuVOs) {
                        if (null == mView) {
                            return;
                        }
                        mOrderFindViewModel.initWatchArea(tabMenuVOs);
                        mView.loadWatchAreaSuccess(mOrderFindViewModel);
                        BaseSpHelper.saveWatchedRetail(GlobalVars.context, mOrderFindViewModel.isContainRetailOrder());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null == mView) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        mView.loadOrderFindDataError(null == e ? "" : e.getMessage(), true);
                    }
                });
    }

    @Override
    public void onTabSelected(String areaName) {
        mOrderFindViewModel.setAreaInfo(areaName);
    }

    @Override
    public List<OrderFindItem> getSelectedData() {
        return mOrderFindViewModel.getSelectedDeskGridItemList();
    }

    @Override
    public void loadSelectedAreaData(int pageIndex) {
        String areaId = mOrderFindViewModel.getSelectedAreaId();
        // 零售单
        if (null == areaId) {
            loadRetailOrder(pageIndex);
        } else {         // 桌位单
            loadSeatOrder(pageIndex);
        }
    }

    @Override
    public void updateEatTime(int timeOutMinutes) {
        this.mOrderFindViewModel.updateEatTime(timeOutMinutes);
    }

    @Override
    public void handleShowRetailArea() {
        if (null == mView) {
            return;
        }
        // 当前显示的就是零售区域
        if (null == mOrderFindViewModel.getSelectedAreaId()) {
            return;
        }
        // 当前未关注零售区域
        if (!mOrderFindViewModel.isContainRetailOrder()) {
            return;
        }
        mView.performShowRetailAreaChecked();
    }

    //================================================================================
    // 获取订单信息
    //================================================================================
    // 2.1.按区域获取关注桌位的订单
    private void loadSeatOrder(final int pageIndex) {
        String entityId = UserHelper.getEntityId();
        String userId = UserHelper.getUserId();
        final String areaId = mOrderFindViewModel.getSelectedAreaId();
        final int pageSize = OrderFindAdapter.PAGE_SIZE;

        mSeatSourceRepository.getWatchedSeatList(entityId, userId, areaId, pageIndex, pageSize, new Callback<List<Seat>>() {
            @Override
            public void onSuccess(List<Seat> seatList) {
                if (mView == null) {
                    return;
                }
                mOrderFindViewModel.fillSeatOrderData(areaId, seatList, OrderFindAdapter.PAGE_INDEX_INITIAL == pageIndex, pageSize);
                mView.loadOrderFindDataSuccess(mOrderFindViewModel);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadOrderFindDataError(body.getMessage(), true);
            }
        });
    }

    // 2.2.获取零售单信息
    private void loadRetailOrder(final int pageIndex) {
        String orderType = OrderConstants.OrderType.ORDER_TYPE_BY_RETAIL;
        String orderStatus = "";
        Boolean checkout = false;
        Boolean isCheckout = true;
        final int pageSize = OrderFindAdapter.PAGE_SIZE;
        String code = "";

        mOrderSourceRepository.getOrderList(UserHelper.getEntityId(), orderType, orderStatus, checkout, isCheckout, pageIndex, pageSize, code, new Callback<OrderListResult>() {
            @Override
            public void onSuccess(OrderListResult orderListResult) {
                if (mView == null) {
                    return;
                }
                if (orderListResult != null) {
                    mOrderFindViewModel.fillRetailOrderData(orderListResult.getBasicOrderVos(), OrderFindAdapter.PAGE_INDEX_INITIAL == pageIndex, pageSize);
                }
                mView.loadOrderFindDataSuccess(mOrderFindViewModel);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadOrderFindDataError(body.getMessage(), true);
            }
        });
    }
}
