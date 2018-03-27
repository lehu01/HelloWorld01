package com.zmsoft.ccd.data.source.ordercomplete;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:27.
 */

public interface IOrderComplete {

    /**
     *
     * @param entityId
     * @param startDate          date 格式：yyyyMMdd
     * @param endDate
     * @param callback
     */
    void getCompleteBillByDate(String entityId, String startDate, String endDate, final Callback<List<CompleteBillVo>> callback);
}
