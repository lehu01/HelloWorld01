package com.zmsoft.ccd.module.main.order.search;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.order.OrderSourceRepository;
import com.zmsoft.ccd.data.source.seat.SeatSourceRepository;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.Order;
import com.zmsoft.ccd.lib.bean.order.OrderConstants;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 19:46.
 */

public class OrderSeatSearchPresenter implements OrderSeatSearchContract.Presenter {

    private OrderSeatSearchContract.View mView;
    private final SeatSourceRepository mSeatSourceRepository;
    private final OrderSourceRepository mOrderSourceRepository;

    //================================================================================
    // inject
    //================================================================================
    @Inject
    public OrderSeatSearchPresenter(OrderSeatSearchContract.View mView, SeatSourceRepository seatSourceRepository, OrderSourceRepository orderSourceRepository) {
        this.mView = mView;
        this.mSeatSourceRepository = seatSourceRepository;
        this.mOrderSourceRepository = orderSourceRepository;
    }

    @Inject
    void setOrderSeatSearchPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // OrderSeatSearchContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void orderSearch(String keyWord) {
        String orderType = OrderConstants.OrderType.ORDER_TYPE_BY_RETAIL;
        String orderStatus = "";
        Boolean checkout = false;
        Boolean isCheckout = true;
        String code = keyWord;

        mOrderSourceRepository.getOrderList(UserHelper.getEntityId(), orderType, orderStatus, checkout, isCheckout, 1, 20, code, new Callback<OrderListResult>() {
            @Override
            public void onSuccess(OrderListResult orderListResult) {
                if (mView == null) {
                    return;
                }
                mView.loadSearchDataSuccess(calculateGridItemList(orderListResult));
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadSearchDataError(body.getMessage(), true);
            }
        });
    }

    @Override
    public void seatSearch(String keyWord) {
        String seatName = keyWord;
        mSeatSourceRepository.getSeatBySeatName(UserHelper.getEntityId(), seatName, new Callback<Seat>() {
            @Override
            public void onSuccess(Seat seat) {
                if (mView == null) {
                    return;
                }
                List<OrderFindItem> orderFindItemList = new ArrayList<>();
                if (null != seat) {
                    orderFindItemList.add(new OrderFindItem(seat));
                }
                mView.loadSearchDataSuccess(orderFindItemList);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.loadSearchDataError(body.getMessage(), true);
            }
        });
    }

    //================================================================================
    // retail order
    //================================================================================
    private List<OrderFindItem> calculateGridItemList(OrderListResult orderListResult) {
        List<OrderFindItem> retailGridItemList = new ArrayList<>();
        if (orderListResult == null) {
            return retailGridItemList;
        }
        List<Order> orderList = orderListResult.getBasicOrderVos();
        if (orderList == null) {
            return retailGridItemList;
        }
        for (Order order : orderList) {
            String areaId = order.getAreaId();
            if (areaId == null) {
                retailGridItemList.add(new OrderFindItem(order));
            }
        }
        return retailGridItemList;
    }
}
