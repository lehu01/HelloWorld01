package com.zmsoft.ccd.module.main.order.detail;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/12/21 11:14
 *     desc  :
 * </pre>
 */
public interface OrderDetailConstant {

    interface OrderType {
        // 结账
        int ORDER_CHECKOUT = 1;
        // 反结账
        int ORDER_RESERVE_CHECKOUT = 2;
        //结账完毕
        int ORDER_END_PAY = 3;
    }
}
