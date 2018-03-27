package com.zmsoft.ccd.lib.base.constant;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 16:16
 */
public class SystemDirCodeConstant {

    // 系统类型
    public static final int TYPE_SYSTEM = 13;
    //排序的原因接口需要orderBy
    public static final String ORDER_BY = "sort_code ,create_time ASC";
    // 反结账原因列表字典编码
    public static final String ACCOUNT_REASON = "ACCOUNT_REASON";
    // 打折原因列表字典编码
    public static final String RATIO_REASON = "RATIO_REASON";
    // 撤单原因列表字典编码
    public static final String CZ_REASON = "CZ_REASON";
    // 退菜原因列表字典编码
    public static final String TC_REASON = "TC_REASON";
    // 赠菜原因列表字典编码
    public static final String PRESENT_REASON = "PRESENT_REASON";
    // 服务费开关编码code
    public static final String IS_SET_MINCONSUMEMODE = "IS_SET_MINCONSUMEMODE";
    // 客单备注列表
    public static final String SERVICE_CUSTOMER = "SERVICE_CUSTOMER";
    // 是否自动打印财务联
    public static final String ACCOUNT_BILL = "ACCOUNT_BILL";
    // 是否自动打印点菜单
    public static final String IS_PRINT_ORDER = "IS_PRINT_ORDER";
    // 是否使用本地收银
    public static final String IS_USE_LOCAL_CASH = "IS_USE_LOCAL_CASH";
    // 是否开启云收银
    public static final String TURN_ON_CLOUD_CASH = "TURN_ON_CLOUD_CASH";
    // 云收银监听手机来电
    public static final String CLOUD_CASH_CALL = "CLOUD_CASH_CALL";
    // 已结账单需现金清账
    public static final String CASH_CLEAN = "CASH_CLEAN";
    // 是否限时用餐
    public static final String IS_LIMIT_TIME = "IS_LIMIT_TIME";
    // 限时用餐时间
    public static final String LIMIT_TIME_END = "LIMIT_TIME_END";
    // 货币符号
    public static final String DEFAULT_CURRENCY_SYMBOL = "DEFAULT_CURRENCY_SYMBOL";

    /**
     * 开关filecode
     */
    public static class FunctionFielCode {
        // 自动打印标签打印开关
        public static final String PRINT_LABEL = "0046";
        // 双单位修改提醒开关
        public static final String DOUBLE_UPDATE_PROMPT = "0039";
        // 是否自动打印客户联
        public static final String SELF_PRINT_ACCOUNT_ORDER = "0022";
        // 开单时必须选桌
        public static final String OPEN_ORDER_MUST_SEAT = "0029";
        // 快速开单
        public static final String QUICK_OPEN_ORDER = "0020";
        // 转桌、并单、催菜时打印单据
        public static final String TRANS_SEAT_OR_PUSH_INSTANCE = "0036";
        // 退菜时打印退菜单
        public static final String PRINT_CANCEL_INSTANCE = "0037";
        // 当有足够的付款金额时是否自动结账完毕
        public static final String ENOUGH_MONEY_END_AFTER_ORDER = "0032";

    }

}
