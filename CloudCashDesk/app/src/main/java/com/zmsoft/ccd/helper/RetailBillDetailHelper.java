package com.zmsoft.ccd.helper;

import android.content.Context;
import android.util.LruCache;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * Created by huaixi on 2017/11/14.
 */

public class RetailBillDetailHelper {
    /**
     * OrderFromMap数据管理
     */
    private static LruCache<Integer, String> mRetailOrderFromMap = new LruCache<>(100);
    /**
     * 订单来源
     */
    public class OrderFrom {
        public static final int FROM_CASH_0 = 0;             // 本地收银
        public static final int FROM_APP = 2;                // app
        public static final int FROM_WAITER = 3;             // 服务生
        public static final int FROM_ANDROID_BAG = 20;       // android 卡包
        public static final int FROM_IOS_BAG = 21;           // 表示ios 卡包
        public static final int FROM_ANDROID_WAITER = 30;    // android服务生
        public static final int FROM_IOS_WAITER = 31;        // ios服务生
        public static final int FROM_WEIXIN = 40;            // 微信
        public static final int FROM_ALIPAY = 41;            // 支付宝
        public static final int FROM_CASH = 50;              // 收银
        public static final int FROM_CLOUD_CASH = 60;        // 云收银
        public static final int FROM_WEI_SHOP = 70;          //微店
        public static final int FROM_BAIDU_TAKEAWAY = 100;   // 百度外卖
        public static final int FROM_MEITUAN_TAKEAWAY = 101; // 美团外卖
        public static final int FROM_ELE_ME = 102;           // 饿了么
        public static final int FROM_DIAN_ME = 103;          // 点我吧
        public static final int FROM_KOU_BEI = 107;          // 口碑
        public static final int FROM_SMALL_TWO_TAKEAWAY = 112;         // 小二外卖
        public static final int FROM_BAIDU_TAKEAWAY_DISCOUNT = 1100;   // 百度外卖优惠
        public static final int FROM_MEITUAN_TAKEAWAY_DISCOUNT = 1101; // 美团外卖优惠
    }

    /**
     * 存储数据
     *
     * @param key   code
     * @param value desc
     */
    public static void addRetailOrderFromMap(int key, String value) {
        if (mRetailOrderFromMap != null) {
            String result = mRetailOrderFromMap.get(key);
            if (StringUtils.isEmpty(result)) {
                mRetailOrderFromMap.put(key, value);
            }
        }
    }

    public static String getRetailOrderFrom(Context context, int code) {
        String desc = mRetailOrderFromMap.get(code);
        if (StringUtils.isEmpty(desc)) {
            return getDefaultOrderFrom(context, code); //若服务器没有获取到对应的订单来源，则获取默认的
        }
        return desc;
    }

    public static String getDefaultOrderFrom(Context context, int code) {
        switch (code) {
            case OrderFrom.FROM_WEIXIN:
                return context.getResources().getString(R.string.order_from_weixin);
            case OrderFrom.FROM_ALIPAY:
                return context.getResources().getString(R.string.order_from_alipay);
            case OrderFrom.FROM_WAITER:
            case OrderFrom.FROM_ANDROID_WAITER:
            case OrderFrom.FROM_IOS_WAITER:
                return context.getResources().getString(R.string.order_from_waiter);
            case OrderFrom.FROM_CASH:
            case OrderFrom.FROM_CASH_0:
                return context.getResources().getString(R.string.order_from_cash);
            case OrderFrom.FROM_BAIDU_TAKEAWAY:
            case OrderFrom.FROM_BAIDU_TAKEAWAY_DISCOUNT:
                return context.getResources().getString(R.string.retail_order_from_baidu_takeaway);
            case OrderFrom.FROM_MEITUAN_TAKEAWAY:
            case OrderFrom.FROM_MEITUAN_TAKEAWAY_DISCOUNT:
                return context.getResources().getString(R.string.retail_order_from_meituan_takeaway);
            case OrderFrom.FROM_ELE_ME:
                return context.getResources().getString(R.string.order_from_el_me);
            case OrderFrom.FROM_DIAN_ME:
                return context.getResources().getString(R.string.order_from_dian_me);
            case OrderFrom.FROM_KOU_BEI:
                return context.getResources().getString(R.string.order_from_kou_bei);
            case OrderFrom.FROM_SMALL_TWO_TAKEAWAY:
                return context.getResources().getString(R.string.order_from_small_two);
            case OrderFrom.FROM_CLOUD_CASH:
                return context.getResources().getString(R.string.retail_cloud_cash);
            case OrderFrom.FROM_WEI_SHOP:
                return context.getResources().getString(R.string.retail_order_from_wei_shop);
            default:
                return context.getResources().getString(R.string.order_from_other);
        }
    }
}
