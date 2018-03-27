package com.zmsoft.ccd.helper;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/22 18:31
 */
public class OrderHelper {

    /**
     * 订单详情状态
     */
    public class OrderDetail {
        public class Order { // 内层的order
            public static final int NOMAL = 1; // 正常状态
            public static final int CANCEL = 3; // 撤销状态
            public static final int END_PAY = 4; // 结账状态
        }

        public class Status { // 外层的status
            public static final int NO_PAY = 0; // 未付款
            public static final int NO_PAY_ALL = 1; // 未付清
            public static final int PAY_ALL = 2; // 已付清
        }
    }

    /**
     * 订单来源
     */
    public class OrderFrom {
        public static final int FROM_APP = 2; // app
        public static final int FROM_WAITER = 3; // 服务生
        public static final int FROM_ANDROID_BAG = 20; // android 卡包
        public static final int FROM_IOS_BAG = 21; // 表示ios 卡包
        public static final int FROM_ANDROID_WAITER = 30; // android服务生
        public static final int FROM_IOS_WAITER = 31; // ios服务生
        public static final int FROM_WEIXIN = 40; // 微信
        public static final int FROM_ALIPAY = 41; // 支付宝
        public static final int FROM_CASH = 50; // 收银
        public static final int FROM_CASH_0 = 0; // 本地收银
        public static final int FROM_BAIDU_TAKEAWAY = 100; // 百度外卖
        public static final int FROM_MEITUAN_TAKEAWAY = 101; // 美团外卖
        public static final int FROM_BAIDU_TAKEAWAY_DISCOUNT = 1100; // 百度外卖优惠
        public static final int FROM_MEITUAN_TAKEAWAY_DISCOUNT = 1101; // 美团外卖优惠
        public static final int FROM_ELE_ME = 102; // 饿了么
        public static final int FROM_DIAN_ME = 103; // 点我吧
        public static final int FROM_KOU_BEI = 107; // 口碑
        public static final int FROM_SMALL_TWO_TAKEAWAY = 112; // 小二外卖
        public static final int FROM_CLOUD_CASH = 60; // 云收银
        public static final int FROM_WEIDIAN = 70; //微店
    }

    /**
     * 订单列表支付状态
     */
    public class OrderList {
        public class Status {
            public static final int END_PAY = 8; // 已结账
        }

        public class PayStatus {
            public static final int NO_PAY = 1; // 未付款
            public static final int NO_PAY_ALL = 3; // 未付清
            public static final int PAY_ALL = 2; // 已付清
        }
    }

    /**
     * 设置订单来源
     */
    public static void setOrderFromText(int fromType, TextView textView, Context context) {
        switch (fromType) {
            case OrderFrom.FROM_WEIXIN:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_weixin)));
                break;
            case OrderFrom.FROM_ALIPAY:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_alipay)));
                break;
            case OrderFrom.FROM_WAITER:
            case OrderFrom.FROM_ANDROID_WAITER:
            case OrderFrom.FROM_IOS_WAITER:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_waiter)));
                break;
            case OrderFrom.FROM_CASH:
            case OrderFrom.FROM_CASH_0:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_cash)));
                break;
            case OrderFrom.FROM_BAIDU_TAKEAWAY:
            case OrderFrom.FROM_BAIDU_TAKEAWAY_DISCOUNT:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_baidu_takeaway)));
                break;
            case OrderFrom.FROM_MEITUAN_TAKEAWAY:
            case OrderFrom.FROM_MEITUAN_TAKEAWAY_DISCOUNT:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_meituan_takeaway)));
                break;
            case OrderFrom.FROM_ELE_ME:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_el_me)));
                break;
            case OrderFrom.FROM_DIAN_ME:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_dian_me)));
                break;
            case OrderFrom.FROM_KOU_BEI:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_kou_bei)));
                break;
            case OrderFrom.FROM_SMALL_TWO_TAKEAWAY:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_small_two_takeaway)));
                break;
            case OrderFrom.FROM_CLOUD_CASH:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.cloud_cash)));
                break;
            default:
                textView.setText(String.format(context.getResources().getString(R.string.order_from), context.getResources().getString(R.string.order_from_other)));
                break;
        }
    }

    /**
     * 设置订单详情支付状态
     */
    public static void setOrderDetailPayImage(int orderStatus, int orderDetailStatus, ImageView imageView) {
        if (orderStatus == OrderDetail.Order.END_PAY) {
            imageView.setImageResource(R.drawable.icon_order_payed);
        } else if (orderDetailStatus == OrderDetail.Status.NO_PAY) {
            imageView.setImageResource(R.drawable.icon_order_no_pay);
        } else if (orderDetailStatus == OrderDetail.Status.NO_PAY_ALL) {
            imageView.setImageResource(R.drawable.icon_order_no_pay_all);
        } else if (orderDetailStatus == OrderDetail.Status.PAY_ALL) {
            imageView.setImageResource(R.drawable.icon_order_pay_all);
        }
    }

    /**
     * 获取订单状态image
     */
    public static int getOrderStatusImageByList(int status, int payStatus) {
        int resourceId = R.drawable.icon_seat_and_order_list_no_pay;
        if (status == OrderList.Status.END_PAY) {
            resourceId = R.drawable.icon_seat_and_order_list_end_pay;
        } else if (payStatus == OrderList.PayStatus.PAY_ALL) {
            resourceId = R.drawable.icon_seat_and_order_list_pay_all;
        } else if (payStatus == OrderList.PayStatus.NO_PAY) {
            resourceId = R.drawable.icon_seat_and_order_list_no_pay;
        } else if (payStatus == OrderList.PayStatus.NO_PAY_ALL) {
            resourceId = R.drawable.icon_seat_and_order_list_no_pay_all;
        }
        return resourceId;
    }

    /**
     * 获取订单状态text
     */
    public static int getOrderStatusTextByList(int status, int payStatus) {
        int textId = R.string.no_pay;
        if (status == OrderList.Status.END_PAY) {
            textId = R.string.after_end_pay;
        } else if (payStatus == OrderList.PayStatus.PAY_ALL) {
            textId = R.string.pay_all;
        } else if (payStatus == OrderList.PayStatus.NO_PAY) {
            textId = R.string.no_pay;
        } else if (payStatus == OrderList.PayStatus.NO_PAY_ALL) {
            textId = R.string.no_pay_all;
        }
        return textId;
    }

    /**
     * 获取订单状态text
     */
    public static int getOrderStatusColorByList(int status, int payStatus) {
        int colorId = R.color.order_list_red;
        if (status == OrderList.Status.END_PAY) {
            colorId = R.color.secondaryTextColor;
        } else if (payStatus == OrderList.PayStatus.PAY_ALL) {
            colorId = R.color.order_list_green;
        } else if (payStatus == OrderList.PayStatus.NO_PAY) {
            colorId = R.color.order_list_red;
        } else if (payStatus == OrderList.PayStatus.NO_PAY_ALL) {
            colorId = R.color.order_list_orange;
        }
        return colorId;
    }

    /**
     * 是否结账完毕
     */
    public static boolean isEndPay(int status) {
        if (status == OrderDetail.Order.END_PAY) {
            return true;
        }
        return false;
    }

    /**
     * 是否是外卖单
     */
    public static boolean isTakeoutOrder(int orderFrom) {
        if (orderFrom == OrderFrom.FROM_BAIDU_TAKEAWAY
                || orderFrom == OrderFrom.FROM_MEITUAN_TAKEAWAY
                || orderFrom == OrderFrom.FROM_ELE_ME
                || orderFrom == OrderFrom.FROM_SMALL_TWO_TAKEAWAY
                || orderFrom == OrderFrom.FROM_WEIDIAN) {
            return true;
        }
        return false;
    }
}
