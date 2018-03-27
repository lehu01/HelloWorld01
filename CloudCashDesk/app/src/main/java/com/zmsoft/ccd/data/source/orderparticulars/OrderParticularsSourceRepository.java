package com.zmsoft.ccd.data.source.orderparticulars;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.order.particulars.DateOrderParticularsListResult;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:28.
 */
@Singleton
public class OrderParticularsSourceRepository implements IOrderParticulars {

    private final IOrderParticulars mIOrderSource;

    @Inject
    public OrderParticularsSourceRepository(@Remote IOrderParticulars mIOrderSource) {
        this.mIOrderSource = mIOrderSource;
    }

    @Override
    public void getOrderParticularsList(String entityId, String opUserId, String opUserName, String orderCode, Integer orderFrom, String cashier, Integer date, int pageIndex, int pageSize, Callback<DateOrderParticularsListResult> callback) {
        mIOrderSource.getOrderParticularsList(entityId, opUserId, opUserName, orderCode, orderFrom, cashier, date, pageIndex, pageSize, callback);
    }
}
