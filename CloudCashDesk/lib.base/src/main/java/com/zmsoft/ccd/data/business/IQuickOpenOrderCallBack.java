package com.zmsoft.ccd.data.business;

import com.zmsoft.ccd.lib.bean.order.create.OrderParam;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/1 15:27
 *     desc  :
 * </pre>
 */
public interface IQuickOpenOrderCallBack {

    void quickOpenOrderSuccess(OrderParam orderParam);
}
