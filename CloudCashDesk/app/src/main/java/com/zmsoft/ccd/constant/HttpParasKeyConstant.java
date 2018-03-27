package com.zmsoft.ccd.constant;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/6 14:05
 */
public final class HttpParasKeyConstant {

    // 公共参数
    public static final String PARA_PAGE_INDEX = "page_index"; // 参数pageIndex：请求的页码
    public static final String PARA_PAGE_SIZE = "page_size"; // 参数pageSize：请求的数量

    // 登录接口参数
    public static final String PARA_METHOD = "method"; // 参数method：方法key
    public static final String PARA_ENTITY_ID = "entity_id"; // 参数entity_id：商铺id
    public static final String PARA_SIGN = "sign"; // 参数sign：签名
    public static final String PARA_TOKEN = "token"; // token
    public static final String PARA_USER_ID = "user_id"; //当前账户ID
    public static final String PAGE_INDEX = "page_index"; // 参数pageIndex：请求的页码
    public static final String PAGE_SIZE = "page_size"; // 参数pageSize：请求的数量

    //登录接口参数
    public static final String COUNTRY_CODE = "country_code"; // 国家区号
    public static final String MOBILE = "mobile"; // 参数mobile：电话号码
    public static final String PASSWORD = "password"; // 参数password：密码
    public static final String DEVICE_ID = "device_id"; // 参数deviceId：设备id
    public static final String APP_KEY = "app_key"; // 参数appKey：appKey
    public static final String CLIENT_TYPE = "client_type"; // 参数clientType："android", "ios", "winphone","pc"
    public static final String IP = "ip"; // 参数ip：ip地址，可传，可不传
    public static final String MAC = "mac"; // 参数ip： mac地址,可传，可不传
    public static final String BRAND = "brand"; // 参数ip：设备品牌,可传，可不传
    public static final String IS_SHOP_DIMENSION_KICK = "is_shop_dimension_kick"; // 参数ip：是否是店铺维度踢人,可传，可不传
    // 登录模块
    public static final String PARA_MOBILE = "mobile"; // 参数mobile：电话号码
    public static final String PARA_PASSWORD = "password"; // 参数password：密码
    public static final String PARA_DEVICE_ID = "device_id"; // 参数deviceId：设备id
    public static final String PARA_CLIENT_TYPE = "client_type"; // 参数clientType："android", "ios", "winphone","pc"
    public static final String PARA_IP = "ip"; // 参数ip：ip地址，可传，可不传
    public static final String PARA_MAC = "mac"; // 参数ip： mac地址,可传，可不传
    public static final String PARA_BRAND = "brand"; // 参数ip：设备品牌,可传，可不传
    public static final String IS_KICK = "isKick"; // 是否踢人
    public static final String PARA_IS_SHOP_DIMENSION_KICK = "is_shop_dimension_kick"; // 参数ip：是否是店铺维度踢人,可传，可不传

    // 切店
    public static final String PARA_MEMBER_ID = "member_id"; // 参数：memberId，会员id
    public static final String PARA_MEMBER_USER_ID = "member_user_id"; // 参数：member_user_id，会员id
    public static final String PARA_PARAM = "param"; // 参数：param，对象
    public static final String PARAM_APPKEY_STR = "app_key_str"; //appKey
    public static final String PARAM_ISKICK = "is_kick"; //是否踢人
    public static final String PARAM_ORIGINAL_USER_ID = "original_user_id"; //切换店铺之前的用户id
    //沽清模块
    public static final String PAGESIZE = "page_size";
    public static final String PAGEINDEX = "page_index";
    public static final String MENUIDLIST = "menu_id_list";
    public static final String MENUID = "menu_id";
    public static final String NUM = "num";
    public static final String OPUSER = "op_user";
    public static final String OPUSERID = "op_user_id";

    // 主界面-关注的桌位列表接口参数
    public static final String PARA_ORDER_BY = "order_by"; // 参数orderBy：排序字段，如：createTime，可传，可不传
    public static final String PARA_DESC = "desc"; // 参数desc：升序还是降序，可传，可不传

    // 获取桌位二维码
    public static final String PARA_SEAT_ID = "seat_id"; // 参数seatId：桌位id
    public static final String PARA_SEAT_CODE_LIST = "seat_code_list"; // 参数seatCodeList：seatCodeList
    public static final String PARA_AREA_ID = "area_id"; // 参数areaId：区域id
    public static final String PARA_SEAT_NAME = "seat_name"; // 参数seatNam：桌位名称
    public static final String PARA_CODE = "code"; // 参数：code，订单号
    public static final String PARA_CUSTOMER_ID = "customer_id"; //当前用户id
    public static final String PARA_ORDER_ID = "order_id"; // 订单id

    public static final String PARA_DIC_CODE = "dic_code"; // dic_code 字典编码
    public static final String PARA_SYSTEM_TYPE = "system_type"; // system_type 系统类型
    public static final String PARA_SEAT_CODE = "seat_code"; // seat_code 座位cod

    public static final String PARA_OLD_SEAT_CODE = "old_seat_code";
    public static final String PARA_NEW_SEAT_CODE = "new_seat_code";
    public static final String PARA_PEOPLE_COUNT = "people_count";
    public static final String PARA_MEMO = "memo";
    public static final String PARA_IS_WAIT = "is_wait";

    //消息设置
    public static final String MESSAGE_GROUP = "message_group";
    public static final String IS_VALID = "is_valid";
    public static final String PARA_TYPE = "type";
    public static final String PARA_CUSTOMER_REGISTER_ID = "customer_register_id";
    public static final String PARA_INSTANCE_ID_LIST = "instance_id_lst";
    public static final String PARA_SYSTEM_TYPE_ID = "system_type_id";
    public static final String PARA_OPERATE_USER_ID = "operate_user_id";
    public static final String PARA_OPERATE_TYPE = "operate_type";
    public static final String PARA_FIELD_CODE = "field_code";
    public static final String PARA_VALUE = "value";

    //检查更新
    public static final String APP_CODE = "app_code";
    public static final String VERSION = "version";

    public static class Hump { // 驼峰命名
        public static final String PAGE_INDEX = "pageIndex";
        public static final String PAGE_SIZE = "pageSize";
        public static final String ENTITY_ID = "entityId";
        public static final String BILL_STATUS = "billStatus"; // 参数：billStatus 订单状态
        public static final String ORDER_CATEGORY = "orderCategory"; // 参数：orderCategory 订单类型
        public static final String OPERATOR_ID = "operatorId"; // 参数：operatorId 操作员id
        public static final String ORDER_ID = "orderId"; // 参数：orderId 订单id
        public static final String REASON = "reason"; // 参数：reason 反结账原因
        public static final String CHECKOUT = "checkout"; // 参数：checkout 结账，未结账
        public static final String NEED_PAGE = "needPage"; // 参数：是否分页
        public static final String FEE_PLAN_ID = "feePlanId";
        public static final String OP_USER_ID = "opUserId";
        public static final String OP_USER_NAME = "opUserName";
        public static final String OP_TYPE = "opType";
        public static final String MODIFY_TIME = "modifyTime";
        public static final String MEMO = "memo";
        public static final String PEOPLE_COUNT = "peopleCount";
        public static final String NEW_SEAT_CODE = "newSeatCode";
        public static final String IS_WAIT = "isWait";
        public static final String INSTANCE_ID = "instanceId";
        public static final String FEE = "fee";
        public static final String WEIGHT = "weight";
        public static final String NUM = "num";
        public static final String ACCOUNT_NUM = "accountNum";
        public static final String AREA_ID = "areaId";
    }

    /**
     * “关注的桌位”模块
     */
    public static class ATTENTION_DESK_MESSAGE_CENTER {
        public static final String PARA_SEAT_ID_LIST = "seat_id_list_string"; //桌位idJson列表
    }

    /**
     * “消息中心”模块
     */
    public static class messageCenter {
        /*
        消息状态：
            0.来自关注的桌的新消息
            2.已处理的消息，包括我关注的已处理消息和不是我关注的但是我处理的消息
            7.本店铺下所有新消息
         */
        public static final String PARA_STATUS = "status";
        //批量处理消息的JSON字符串
        public static final String PARA_MESSAGE_ID_LIST = "message_id_list";
        //批量处理消息的结果（eg 已处理）
        public static final String PARA_RESULT_MESSAGE = "result_message";
        // 消息id
        public static final String PARA_MESSAGE_ID = "message_id";
        /*
        是否通过 true同意 false拒绝
         */
        public static final String PARA_IS_PASS = "is_pass";
    }

    /**
     * “个人中心”模块
     */
    public static class personalCenter {
        // 旧密码
        public static final String PARA_OLD_PWD = "old_pwd";
        //新密码
        public static final String PARA_NEW_PWD = "new_pwd";
        //用户名
        public static final String PARA_USER_NAME = "user_name";
        //反馈内容
        public static final String PARA_SUGGEST = "suggest";
        //邮箱头
        public static final String PARA_EMAIL_TITLE = "email_title";
        //邮箱
        public static final String PARA_USER_EMAIL = "user_email";
    }

    public interface orderParticulars {
        String ORDER_CODE = "orderCode";
        String ORDER_FROM = "orderFrom";    // 订单来源，不填表示全部，0表示座位单，1表示零售单，112表示小二外卖，100百度外卖，101表示美团外卖，102表示饿了吗外卖
        String CASHIER = "cashier";         // 收银员id
        String DATE = "date";               // 日期，0表示今天，1表示昨天，2表示两日内
    }

    public interface orderSummary {
        String ENTITY_ID = "entity_id";
        String START_DATE = "start_date";       // yyyy-MM-dd
        String END_DATE = "end_date";           // yyyy-MM-dd
    }

    public interface orderComplete {
        String ENTITY_ID = "entity_id";
        String START_DATE = "start_date";                   // yyyyMMdd
        String END_DATE = "end_date";
    }

    public interface QUERY_BIND_ENTITY_LIST {
        String APP_KEY_STR = "app_key_str";
    }
}
