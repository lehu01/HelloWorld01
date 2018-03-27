package com.zmsoft.ccd.lib.base.helper;

import android.util.LruCache;

import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/9 16:06
 *     desc  : 零售单管理帮助类
 * </pre>
 */
public class RetailOrderHelper {

    /**
     * Map数据管理
     */
    private static LruCache<String, String> mRetailMap = new LruCache<>(1000);
    /**
     * 零售单seatCode规则
     */
    public static final String SEAT_CODE_BY_RETAIL = "CLOUD_CASH_RETAIL_SEAT_CODE@";


    /**
     * 存储数据
     *
     * @param key   orderId
     * @param value seatCode
     */
    public static void addRetailMap(String key, String value) {
        if (mRetailMap != null) {
            if (StringUtils.isEmpty(key)) {
                return;
            }
            if (value.contains(RetailOrderHelper.SEAT_CODE_BY_RETAIL)) {
                String result = mRetailMap.get(key);
                if (StringUtils.isEmpty(result)) {
                    mRetailMap.put(key, value);
                }
            }
        }
    }

    /**
     * 获取默认零售单seatCode
     */
    public static String getDefaultRetailSeatCode() {
        return StringUtils.appendStr(SEAT_CODE_BY_RETAIL, UserHelper.getEntityId(), System.currentTimeMillis());
    }

    /**
     * 获取零售单关联的seatCode
     *
     * @param key orderId
     * @return seatCode
     */
    public static String getRetailSeatCode(String key) {
        String seatCode = mRetailMap.get(key);
        if (StringUtils.isEmpty(seatCode)) {
            seatCode = getDefaultRetailSeatCode();
            addRetailMap(key, seatCode);
        }
        return seatCode;
    }

}
