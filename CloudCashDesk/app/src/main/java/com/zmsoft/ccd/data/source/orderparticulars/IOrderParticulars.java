package com.zmsoft.ccd.data.source.orderparticulars;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.order.particulars.DateOrderParticularsListResult;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 14:21.
 */

public interface IOrderParticulars {


    /**
     * 账单详情
     * @param entityId
     * @param opUserId
     * @param opUserName
     * @param orderCode 订单编号，搜索时使用
     * @param orderFrom 订单来源，不填表示全部，0表示座位单，1表示零售单，112表示小二外卖，100百度外卖，101表示美团外卖，102表示饿了吗外卖
     * @param cashier   收银员id
     * @param date      日期，0表示今天，1表示昨天，2表示两日内
     * @param pageIndex
     * @param pageSize
     * @param callback
     */
    void getOrderParticularsList(String entityId, String opUserId, String opUserName, String orderCode, Integer orderFrom, String cashier, Integer date, int pageIndex, int pageSize, final Callback<DateOrderParticularsListResult> callback);
}
