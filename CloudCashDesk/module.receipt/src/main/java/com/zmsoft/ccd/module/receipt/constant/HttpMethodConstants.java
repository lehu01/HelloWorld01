package com.zmsoft.ccd.module.receipt.constant;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/12 14:50
 */
public class HttpMethodConstants {

    /**
     * 会员卡模块
     */
    public class VipCard {
        // 获取会员卡列表
        public static final String METHOD_GET_VIP_CARD_LIST = "com.dfire.tp.client.service.ITradePlatformService.getCardInfoList";
        // 获取会员卡详情
        public static final String METHOD_GET_VIP_CARD_DETAIL = "com.dfire.tp.client.service.ITradePlatformService.getCardInfoDetail";

        public class Param {
            public static final String ENTITYID = "entityId";
            public static final String KEYWORD = "keyword";
            public static final String PARAM = "param";
            public static final String CARDID = "cardId";
            public static final String ORDERID = "orderId";
        }
    }

    /**
     * 收款模块
     */
    public static class Receipt {
        // 云收银获取账单
        public static final String METHOD_GET_CLOUDCASHBILL = "com.dfire.tp.client.service.ITradePlatformService.getCloudCashBill";
        // 云收银收款
        public static final String METHOD_EMPTY_COLLECT_PAY = "com.dfire.soa.cloudcash.collectPay";
        // 云收银收款（零售）
        public static final String METHOD_EMPTY_COLLECT_PAY_FOR_RETAIL = "com.dfire.soa.cloudcash.collectPayForRetailIndustry";
        // 获取代金券面额列表
        public static final String METHOD_GET_VOUCHER_INFO = "com.dfire.tp.client.service.ITradePlatformService.getVoucherInfo";
        // 获取签字员工列表
        public static final String METHOD_GET_SIGN_BILL_SINGER = "com.dfire.tp.client.service.ITradePlatformService.getSignBillSinger";
        // 获取挂账单位（人）列表
        public static final String METHOD_GET_SIGN_UNIT = "com.dfire.tp.client.service.ITradePlatformService.getSignBillUnit";
        // 账单优惠（整单打折，卡优惠）
        public static final String METHOD_PROMOTE_BILL = "com.dfire.tp.client.service.ITradePlatformService.promoteBill";
        //获取支付选项信息
        public static final String METHOD_GET_KINDDETAIL_INFO = "com.dfire.tp.client.service.ITradePlatformService.getKindDetailInfo";
        //获取支付状态
        public static final String METHOD_GET_PAY_STATUS = "com.dfire.tp.client.service.ITradePlatformService.getPayStatus";
        //优惠券核销
        public static final String METHOD_VERIFICATION = "com.dfire.tp.client.service.ITradePlatformService.verifyMcPromotion";
        //获取订单的全部支付列表
        public static final String METHOD_ORDER_PAYLIST = "com.dfire.tp.client.service.ITradePlatformService.getOrderPayList";

        public static class Paras {
            public static final String PARAS_PARAMS = "param";
        }
    }
}
