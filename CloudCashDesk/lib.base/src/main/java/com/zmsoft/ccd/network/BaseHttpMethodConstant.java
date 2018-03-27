package com.zmsoft.ccd.network;

/**
 * @author DangGui
 * @create 2017/5/4.
 */

public class BaseHttpMethodConstant {

    /**
     * 权限
     */
    public static class CheckPermission {
        // 权限校验
        public static final String METHOD_CHECK_PERMISSION = "com.dfire.bps.hasPermissionByActionCode";
        // 批量权限校验
        public static final String METHOD_BATCH_CHECK_PERMISSION = "com.dfire.bps.hasPermissionByActionCodeList";
        //参数
        public static final String PARA_SYSTEM_TYPE = "system_type";
        public static final String PARA_ACTION_CODE = "action_code";
        //批量权限校验
        public static final String PARA_BATCH_ACTION_CODE = "action_code_list";
    }

    /**
     * 二维码相关
     */
    public static class QRcode {
        // 二维码获取seat
        public static final String METHOD_QR_SCAN_SEAT = "com.dfire.qrcode.getScanBean";
        // 二维码获取菜肴
        public static final String METHOD_QR_SCAN_MENU = "com.dfire.qrcode.getMenuWithShortUrl";
        // 参数
        public static final String PARA_STRING = "param_string"; //param_string
    }

    /**
     * 打印相关
     */
    public static class Print {
        // 打印点菜单[财务联/点菜单/客户联]
        public static final String METHOD_PRINT_ORDER = "com.dfire.soa.cloudcash.printOrder";
        // 订单纬度：包含区域打印等逻辑，迁移服务端
        public static final String METHOD_PRINT_ORDER_SELF = "com.dfire.soa.cloudcash.printOrderIncludePrintLogicJudge";
        // 打印点菜单[加菜]
        public static final String METHOD_PRINT_INSTANCE = "com.dfire.soa.cloudcash.printInstance";
        // 菜肴纬度：包含区域打印等逻辑，迁移服务端
        public static final String METHOD_PRINT_INSTANCE_SELF = "com.dfire.soa.cloudcash.printInstanceIncludePrintLogicJudge";
        // 测试打印
        public static final String METHOD_PRINT_TEST = "com.dfire.soa.cloudcash.testPrint";
        // 根据seatCode获取区域打印信息
        public static final String METHOD_PRINT_BY_AREA_ID = "com.dfire.cashconfig.getPrintBySeatCode";
        // 参数
        public static final String DOCUMENT_TYPE = "document_type"; // document_type
        public static final String ENTITY_ID = "entity_id"; // entity_id
        public static final String ORDER_ID = "order_id"; // order_id
        public static final String USER_ID = "user_id"; // user_id
        public static final String INSTANCE_ID_LIST = "instance_id_list"; // instance_id_list
        public static final String SEAT_CODE = "seat_code"; // seat_code
        public static final String ORDER_KIND = "order_kind"; // order_kind

    }

    /**
     * 原因相关
     */
    public static class Reason {
        // 获取原因列表
        public static final String METHOD_REASON_LIST = "com.dfire.turtle.IDicItemService.queryList";
        // 参数
        public static final String ENTITY_ID = "entity_id"; // entity_id
        public static final String DIC_CODE = "dic_code"; // dic_code 字典编码
        public static final String SYSTEM_TYPE = "system_type"; // system_type 系统类型
    }

    /**
     * 原因相关
     */
    public static class ReasonSorted {
        // 获取原因列表
        public static final String METHOD_REASON_LIST = "com.dfire.turtle.getDicItemByDicItemQuery";
        // 参数
        public static final String ENTITY_ID = "entityId"; // entity_id
        public static final String DIC_CODE = "dicCode"; // dic_code 字典编码
        public static final String SYSTEM_TYPE = "systemType"; // system_type 系统类型
        public static final String ORDER_BY = "orderBy"; // orderBy 这个参数是固定的 值是  "sort_code ,create_time ASC"
    }

    /**
     * 订单相关
     */
    public static class Order {
        // 结账完毕
        public static final String METHOD_ORDER_AFTER_END_PAY = "com.dfire.tp.client.service.ITradePlatformService.checkoutOrder";
        //结账完毕（零售）
        public static final String METHOD_ORDER_AFTER_END_PAY_FOR_RETAIL = "com.dfire.tp.client.service.ITradePlatformService.checkoutRetailOrder";

        // 参数
        public static final String ENTITY_ID = "entityId";
        public static final String MODIFY_TIME = "modifyTime";
        public static final String OPERATOR_ID = "opUserId"; // 参数：operatorId 操作员id
        public static final String ORDER_ID = "orderId"; // 参数：orderId 订单id
        public static final String PARA_PARAM = "param"; // 参数：param，对象
    }

    /**
     * 收款模块
     */
    public static class Receipt {
        // 云收银清空折扣
        public static final String METHOD_EMPTY_DISCOUNT = "com.dfire.tp.client.service.ITradePlatformService.emptyDiscount";
        // 云收银退款
        public static final String METHOD_EMPTY_REFUND = "com.dfire.soa.cloudcash.client.service.ICloudTradePlateFormService.refund3";
        // 云收银电子支付退款
        public static final String METHOD_E_PAY_REFUND = "com.dfire.tp.client.service.ITradePlatformService.eRefund";

        public static class Paras {
            public static final String PARAS_PARAMS = "param";
        }
    }

    /**
     * 配置
     */
    public static class Config {
        // 保存配置开关Function
        public static final String METHOD_SAVE_CONFIG_FUNCATION_FIEL_CODE_LIST = "com.dfire.cashconfig.IFunctionClientService.save";
        // 根据配置获取开关
        public static final String METHOD_CONFIG_FUNCATION_FIEL_CODE = "com.dfire.cashconfig.queryValueByFunctionFielCode";
        // 获取批量开关
        public static final String METHOD_CONFIG_FUNCATION_FIEL_CODE_LIST = "com.dfire.cashconfig.IFunctionClientService.queryValueMapByCodeList";
        // 获取批量开关
        public static final String METHOD_CONFIG_VAL_BY_LIST_CODE = "com.dfire.turtle.queryConfigValueListByCodeList";
        // 获取单个开关
        public static final String METHOD_CONFIG_VAL_BY_CODE = "com.dfire.turtle.getConfigValByCode";
    }

    /**
     * 现金清算限额
     */
    public interface CashLimit {
        String METHOD = "com.dfire.state.getUserLimit";
        String ENTITY_ID = "entity_id";
        String USER_ID = "user_id";
    }

    public interface ChannelInfo {
        String METHOD_SAVE_APP_INFO = "com.dfire.cash.service.appinfo.appSaveAppInfo";
    }
}
