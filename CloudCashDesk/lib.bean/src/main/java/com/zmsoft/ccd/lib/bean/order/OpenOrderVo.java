package com.zmsoft.ccd.lib.bean.order;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/7/31 10:12
 *     desc  : 开购物车
 * </pre>
 */
public class OpenOrderVo extends Base {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_MORE_THAN_50 = 2;
    public static final int STATUS_MORE_THAN_100 = 3;
    /**
     * 座位单
     * status = 1
     * 零售单
     * status = 1 || 2 || 3
     * 1、未结账完毕的零售单数量小于50可以开单
     * 2、未结账完毕的零售单数量大于50，小于等于100
     * 3、未结账完毕的零售单数量已经超过100单，无法开零售单
     */
    private int status;
    /**
     * 返回信息
     */
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
