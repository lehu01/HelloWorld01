package com.zmsoft.ccd.module.takeout.delivery.helper;

/**
 * @author DangGui
 * @create 2017/9/2.
 */

public class DeliveryHelper {
    public class ExtraParams {
        public static final String PARAM_TAKE_OUT = "takeOut";
        public static final String PARAM_ORDER_CODES = "orderCodes";
        public static final String PARAM_ORDER_IDS = "orderIds";
    }

    /**
     * 配送平台编码
     */
    public class PlatformCode {
        public static final String SELF = "P000"; //自配送
        public static final String SHUN_FENG = "P001"; //顺丰配送
        public static final String DADA = "dada"; //达达配送
    }

    /**
     * 配送 RecyclerAdapter中ViewHolder 子view点击事件类型
     */
    public static class RecyclerViewHolderClickType {
        /**
         * 配送方式
         */
        public static final int DELIVERY_METHOD = 1;
    }

    /**
     * 配送状态回调
     */
    public static class DeliveryStatus {
        /**
         * 失败
         */
        public static final int FAIL = 0;
        /**
         * 成功
         */
        public static final int SUCCESS = 1;
    }

    /**
     * 配送方式
     */
    public static class DeliveryType {
        /**
         * 物流配送
         */
        public static final int LOGISTICS = 0;
        /**
         * 第三方配送
         */
        public static final int THIRD = 1;
        /**
         * 店家配送
         */
        public static final int SHOP = 2;
    }

    /**
     * 配送，弹框类型
     */
    public static class DeliveryDialogType {
        /**
         * 配送方式
         */
        public static final int DELIVERYTYPE = 0;
        /**
         * 快递公司
         */
        public static final int DELIVERYCOMPANY = 1;
        /**
         * 配送平台
         */
        public static final int DELIVERYPLATFORM = 2;
        /**
         * 配送员
         */
        public static final int COURIER = 3;
    }

    /**
     * 运单号字符范围
     */
    public static final String DELIVERY_CODE_DIGISTS = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 扫码接口回传key
     */
    public static final String DELIVERY_RESULT_KEY = "scanResult";
}
