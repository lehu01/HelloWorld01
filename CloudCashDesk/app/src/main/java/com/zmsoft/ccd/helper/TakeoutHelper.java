package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/8 16:26
 *     desc  : 外卖帮助类
 * </pre>
 */
public class TakeoutHelper {

    /**
     * 第三方配送方式
     * 2.自取
     */
    public static final int DELIVERY_INVITE = 2;

    public static String getDeliveryTypeStr(int type) {
        if (type == DELIVERY_INVITE) {
            return GlobalVars.context.getString(R.string.goto_shop_get);
        }
        return GlobalVars.context.getString(R.string.send_goods_to_door);
    }

}
