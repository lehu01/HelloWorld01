package com.zmsoft.ccd.lib.base.constant;

/**
 * Description：权限常量
 * <br/>
 * Created by kumu on 2017/5/5.
 */

public interface Permission {

    /**
     * 自定义菜
     */
    interface CustomFood {
        int SYSTEM_TYPE = 4;
        String ACTION_CODE = "SELF_INSTANCE";
    }

    /**
     * 赠送这个菜
     */
    interface PresentFood {
        int SYSTEM_TYPE = 4;
        String ACTION_CODE = "INSTANCEGIVE";
    }

    /**
     * 退菜
     */
    interface CancelInstance {
        String ACTION_CODE = "INSTANCEBACK";
    }

    /**
     * 撤单
     */
    interface CancelOrder {
        String ACTION_CODE = "ORDERCANCEL";
    }

    /**
     * 修改菜肴价格
     */
    interface UpdateInstancePrice {
        String ACTION_CODE = "INSTANCECHANGEPRICE";
    }

    /**
     * 修改菜肴重量
     */
    interface UpdateInstanceWeight {
        String ACTION_CODE = "INSTANCECHANGENUMBER";
    }

    /**
     * 改单[下单]购物车改单不需要
     */
    interface ChangeOrder {
        String ACTION_CODE = "ORDERCHANGE";
    }

    /**
     * 沽清添加
     */
    interface MenuBalance {
        String ACTION_CODE = "ABSENTS";
    }

    /**
     * 清空优惠
     */
    interface EmptyDiscount {
        int SYSTEM_TYPE = 4;
        String ACTION_CODE = "CLEAN_DISCOUNT";
    }

    /**
     * 挂帐
     */
    interface OnAccount {
        String ACTION_CODE = "ON_ACCOUNT";
    }

    /**
     * 免单
     */
    interface FreeOrder {
        String ACTION_CODE = "FREE_ORDER";
    }

    /**
     * 结账权限
     */
    interface CheckOut {
        String ACTION_CODE = "ACCOUNTGET";
    }

    /**
     * 清空支付
     */
    interface ClearPay {
        String ACTION_CODE = "CLEAR_PAYMENT";
    }

    /**
     * 电子支付退款
     */
    interface EPayRefund {
        String ACTION_CODE = "E_PAY_REFUND";
    }

    /**
     * 打印客户联权限
     */
    interface PrintAccount {
        String ACTION_CODE = "ACCOUNTPRINTCUS";
    }

    /**
     * 外卖
     */
    interface TakeOut {
        String ACTION_CODE = "TAKEAWAY";
    }

    /**
     * 补打小票
     */
    interface ReprintSmallTicket {
        int SYSTEM_TYPE = 4;
        String ACTION_CODE = "PRINT_BILL_HIS";
    }

    /**
    * 开单收款
    */
    interface OrderBegin {
        int SYSTEM_TYPE = 4;
        String ACTION_CODE = "ORDERBEGIN";
    }

    /**
     * 账单权限
     */
    interface AccountBills {
        String ACTION_CODE = "ACCOUNTBILLS";
    }

    /**
     * 打印财务联权限
     */
    interface AccountPrintFin {
        String ACTION_CODE = "ACCOUNTPRINTFIN";
    }

    /**
     * 催菜
     */
    interface PushInstance {
        String ACTION_CODE = "INSTANCEURGENCE";
    }

    /**
     * 催单
     */
    interface PushOrder {
        String ACTION_CODE = "INSTACCEURGENCEALL";
    }
}

