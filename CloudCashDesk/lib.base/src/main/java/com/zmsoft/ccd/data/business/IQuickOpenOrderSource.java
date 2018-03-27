package com.zmsoft.ccd.data.business;

import android.content.Context;

import com.chiclaim.modularization.router.IProvider;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/1 15:09
 *     desc  : 快速开单对外暴露的接口
 * </pre>
 */
public interface IQuickOpenOrderSource extends IProvider {

    void doQuickOpenOrder(Context context, OrderParam orderParam);

    void doQuickOpenOrder(Context context, OrderParam orderParam,IQuickOpenOrderCallBack iQuickOpenOrderCallBack);

}
