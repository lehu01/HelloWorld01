package com.zmsoft.ccd.data.source.ordersummary;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/15 10:53.
 */

public interface IOrderSummarySource {
    /**
     * 通过日期列表 获取账单汇总信息
     * @param entityId
     * @param startDate
     * @param endDate
     * @param callback
     */
    void getBillSummaryDays(String entityId, String startDate, String endDate, final Callback<List<BillSummaryVo>> callback);
}
