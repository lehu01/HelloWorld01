package com.zmsoft.ccd.lib.bean.order.rightfilter;

import java.io.Serializable;



public class OrderRightFilterItem implements Serializable {

    public interface Type {
        int TYPE_SOURCE = 0;            // 订单来源
        int TYPE_CASHIER = 1;           // 收银员
        int TYPE_DATE = 2;              // 日期
    }

    // 订单来源，不填表示全部，0表示座位单，1表示零售单，112表示小二外卖，100百度外卖，101表示美团外卖，102表示饿了吗外卖
    public interface CodeSource {
        String CODE_ALL = "";               // 全部
        String CODE_DESK = "0";             // 桌位单
        String CODE_RETAIL = "1";           // 零售单
        String CODE_WAITER_TAKEOUT = "112"; // 小二外卖
        String CODE_MEITUAN = "101";        // 美团
        String CODE_ELME = "102";           // 饿了么
        String CODE_BAIDU = "100";          // 百度
    }

    public interface CodeCashier {
        String CODE_ALL = "0";       // 全部收银员
        String CODE_CURRENT = "1";   // 当前收银员
    }

    // 日期，0表示今天，1表示昨天，2表示两日内
    public interface CodeDate {
        String CODE_TODAY = "0";                // 今日
        String CODE_YESTERDAY = "1";            // 昨日
        String CODE_WITHIN_TWO_DAYS = "2";      // 两日内
    }

    private boolean isCheck;    // 是否选中
    private String name;        // 名称
    private int type;           // 类型
    private String code;        // code

    public OrderRightFilterItem(boolean isCheck, String name, int type, String code) {
        this.isCheck = isCheck;
        this.name = name;
        this.type = type;
        this.code = code;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
