package com.zmsoft.ccd.lib.base.constant;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/17 15:14
 *     desc  : 埋点统计事件key: 行为名称+行为分类
 * </pre>
 */
public interface AnswerEventConstant {

    interface Home {
        //首页头部按钮
        String ANSWER_EVENT_TYPE_HEADER = "home_page_header";
        //首页中间内容按钮
        String ANSWER_EVENT_TYPE_CONTENT = "home_page_content";
        //快速开单
        String ANSWER_EVENT_NAME_ORDER = "home_page_open_order";
        //快速收款
        String ANSWER_EVENT_NAME_QUICK_PAY = "home_page_quick_pay";
        //已结账单
        String ANSWER_EVENT_NAME_ACCOUNTED = "home_item_accounted";
        //商品估清
        String ANSWER_EVENT_NAME_FOODS_BALANCE = "home_item_balance";
    }

    interface Menu {
        String ANSWER_EVENT_MENU_LIST_SHOW_CATEGORY = "MENU_LIST_SHOW_CATEGORY";
    }

    interface Order {
        // 搜索订单
        String ANSWER_EVENT_NAME_SEARCH = "order_search";
        // 开单
        String ANSWER_EVENT_TYPE_OPEN_ORDER = "open_order";
        String ANSWER_EVENT_NAME_OPEN_ORDER_FROM_SEAT = "open_order_from_seat";
        String ANSWER_EVENT_NAME_OPEN_ORDER_FROM_RETAIL = "open_order_from_retail";
        // 开购物车
        String ANSWER_EVENT_OPEN_ORDER = "OPEN_ORDER";
        // 选桌
        String ANSWER_EVENT_SELECT_DESK_BY_LIST = "SELECT_DESK_BY_LIST";
        String ANSWER_EVENT_SELECT_DESK_BY_SCAN = "SELECT_DESK_BY_SCAN";
        String ANSWER_EVENT_FROM_CREATE_ORDER = "from create order";
        String ANSWER_EVENT_FROM_CHANGE_ORDER = "from change order";
    }

    interface MainTab {
        // MainTab
        String ANSWER_EVENT_TYPE_TAB_SWITCH = "home_page_tab_switch";
        String ANSWER_EVENT_NAME_MAIN = "name_tab_order";
        String ANSWER_EVENT_NAME_FIND = "name_tab_find";
        String ANSWER_EVENT_NAME_MSG = "name_tab_msg";
        String ANSWER_EVENT_NAME_SET = "name_tab_set";
    }
}
