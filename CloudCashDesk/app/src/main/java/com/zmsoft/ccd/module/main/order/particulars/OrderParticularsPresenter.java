package com.zmsoft.ccd.module.main.order.particulars;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.orderparticulars.OrderParticularsSourceRepository;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.order.particulars.DateOrderParticularsListResult;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;
import com.zmsoft.ccd.lib.bean.user.User;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsAdapter;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsItem;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 15:03.
 */

public class OrderParticularsPresenter implements OrderParticularsContract.Presenter{

    private OrderParticularsContract.View mView;
    private final OrderParticularsSourceRepository mOrderParticularsSourceRepository;

    @Inject
    public OrderParticularsPresenter(OrderParticularsContract.View mView, OrderParticularsSourceRepository mOrderParticularsSourceRepository) {
        this.mView = mView;
        this.mOrderParticularsSourceRepository = mOrderParticularsSourceRepository;
    }

    @Inject
    void setOrderParticularsPresenter() {
        mView.setPresenter(this);
    }

    //================================================================================
    // OrderParticularsContract.Presenter
    //================================================================================
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    //================================================================================
    // load
    //================================================================================
    public void loadOrderParticularsData(String orderCode, String sourceCode, String cashierCode, String dateCode, final Integer pageIndex) {
        User user = UserLocalPrefsCacheSource.getUser();
        String entityId = user.getEntityId();
        String opUserId = user.getUserId();
        String opUserName = user.getUserName();
        int pageSize = OrderParticularsAdapter.PAGE_SIZE;
        // 筛选栏数据
        Integer orderFrom = null;
        if (!OrderRightFilterItem.CodeSource.CODE_ALL.equals(sourceCode)) {
            orderFrom = Integer.valueOf(sourceCode);
        }
        String cashier = null;
        if (OrderRightFilterItem.CodeCashier.CODE_CURRENT.equals(cashierCode)) {
            cashier = opUserId;
        }
        Integer date = Integer.valueOf(dateCode);

        mOrderParticularsSourceRepository.getOrderParticularsList(entityId, opUserId, opUserName, orderCode, orderFrom, cashier, date, pageIndex, pageSize, new Callback<DateOrderParticularsListResult>() {
            @Override
            public void onSuccess(DateOrderParticularsListResult data) {
                if(mView == null) {
                    return;
                }
                List<OrderParticularsItem> orderParticularsItemList = OrderParticularsItem.transformResponse(data);
                mView.loadOrderFindDataSuccess(orderParticularsItemList, OrderParticularsAdapter.PAGE_INDEX_INITIAL == pageIndex);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if(mView == null) {
                    return;
                }
                mView.loadOrderFindDataError(body.getMessage());
            }
        });
    }
}
