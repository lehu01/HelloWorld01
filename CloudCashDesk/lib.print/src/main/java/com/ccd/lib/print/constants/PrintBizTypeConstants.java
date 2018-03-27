package com.ccd.lib.print.constants;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/12/14 15:26
 *     desc  : 打印业务管理
 * </pre>
 */
public interface PrintBizTypeConstants {

    interface OrderSummaryType {
        // 1.今日
        int BILL_TYPE_TODAY = 1;
        // 2.昨天
        int BILL_TYPE_YESTERDAY = 2;
        // 3.两日内
        int BILL_TYPE_TOW_DAYS = 3;
    }

    interface PrintInstance {
        // 催菜
        int TYPE_PUSH_INSTANCE = 8;
        // 退菜
        int TYPE_CANCEL_INSTANCE = 6;
        // 加菜
        int TYPE_ADD_INSTANCE = 1;
    }

    interface PrintOrder {
        // 点菜单
        int BIZ_TYPE_PRINT_DISHES_ORDER = 1;
        // 客户联
        int BIZ_TYPE_PRINT_ACCOUNT_ORDER = 2;
        // 财务联
        int BIZ_TYPE_PRINT_FINANCE_ORDER = 3;
        // 催单
        int BIZ_TYPE_PRINT_PUSH_ORDER = 9;
    }

}
