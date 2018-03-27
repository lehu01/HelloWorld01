package com.zmsoft.ccd.data.source.ordercomplete;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:40.
 */
@Singleton
public class OrderCompleteSourceRepository implements IOrderComplete {
    private final IOrderComplete mIOrderComplete;

    @Inject
    public OrderCompleteSourceRepository() {
        this.mIOrderComplete = new OrderCompleteSource();
    }

    @Override
    public void getCompleteBillByDate(String entityId, String startDate, String endDate, Callback<List<CompleteBillVo>> callback) {
        mIOrderComplete.getCompleteBillByDate(entityId, startDate, endDate, callback);
    }
}
