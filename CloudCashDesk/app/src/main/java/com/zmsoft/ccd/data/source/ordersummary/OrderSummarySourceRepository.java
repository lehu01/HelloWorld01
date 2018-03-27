package com.zmsoft.ccd.data.source.ordersummary;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:55.
 */
@Singleton
public class OrderSummarySourceRepository implements IOrderSummarySource {

    private final IOrderSummarySource mIOrderSummarySource;

    @Inject
    public OrderSummarySourceRepository() {
        this.mIOrderSummarySource = new OrderSummarySource();
    }

    @Override
    public void getBillSummaryDays(String entityId, String startDate, String endDate, Callback<List<BillSummaryVo>> callback) {
        mIOrderSummarySource.getBillSummaryDays(entityId, startDate, endDate, callback);
    }
}
