package com.zmsoft.ccd.constant;

/**
 * 应用内所有的网络请求method<br />
 * <b>根据模块进行划分</b>
 *
 * @author DangGui
 * @create 2017/1/16.
 */

public class HttpMethodConstants {


    /**
     * 选店模块
     */
    public static class CheckShop {
        // 获取绑定店铺列表
        public static final String METHOD_GET_BIND_SHOP_LIST = "com.dfire.cashsoa.queryBindEntityListByAppKey";//"com.dfire.cashsoa.IShopBindService.queryBindEntityList";//
        // 获取绑定列表：掌柜
        public static final String METHOD_GET_BIND_SHOP_LIST1 = "com.dfire.soa.boss.center.login.service.IUnifiedLoginClientService.queryBindEntityList";
        // 绑定店铺F
        public static final String METHOD_BIND_SHOP = "com.dfire.soa.cloudcash.ILoginClientService.switchShopByAppKey"; //"com.dfire.cashsoa.switchShopByAppKey"; //"com.dfire.cashsoa.switchShop";//
        // 区分应用，绑定店铺
        public static final String METHOD_BIND_SHOP1 = "com.dfire.boss.center.soa.login.service.IUnifiedLoginClientService.loginShop";
    }

    /**
     * 座位相关接口方法
     */
    public static class Seat {
        // 根据区域获获取关注桌位的列表
        public static final String METHOD_WATCHED_SEAT_LIST_BY_AREA = "com.dfire.soa.cloudcash.getWatchedSeatStatusListByArea";
        // 获取主页关注桌位的列表
        public static final String METHOD_WATCHED_SEAT_LIST = "com.dfire.cloudcash.getWatchedSeatStatusList";
        // 获取列表座位的二维码
        public static final String METHOD_WATCHED_SEAT_LIST_CODE = "com.dfire.qrcode.getZwShortUrlList";
        // 根据区域获桌位列表 <右侧筛选> 跟关注的桌位同级
        public static final String METHOD_SEAT_LIST_BY_AREA = "com.dfire.soa.cloudcash.getSeatStatusListByArea";
        // 根据桌号查找桌位
        public static final String METHOD_FIND_SEAT_BY_SEAT_NAME = "com.dfire.cloudcash.getSeatStatusBySeatName";
        // 根据桌code查找桌位
        public static final String METHOD_FIND_SEAT_BY_SEAT_CODE = "com.dfire.turtle.querySeatByCode";
        // 根据座位code查找座位
        public static final String METHOD_GET_SEAT_STATUS_BY_SEAT_CODE = "com.dfire.msstate.getSeatStatusBySeatCode";
    }

    public static class MENU_BALANCE {
        //获得店铺商品沽清列表
        public static final String GET_MENU_BALANCE_LIST = "com.dfire.msstate.getMenuBalanceVoListByQuery";
        //清除菜品沽清信息
        public static final String CLEAR_MENU_BALANCE = "com.dfire.msstate.clearMenuBalanceFromCloudCash";
        //获得店铺菜品列表
        public static final String GET_MENU_LIST = "com.dfire.soa.cloudcash.getMenuVoList";
        //重置沽清
        public static final String RESET_MENU_BALANCE = "com.dfire.msstate.saveOrUpdateMenuBalance";
    }

    public static class MSG_SETTINGS {
        //获取设置项
        public static final String GET_MSG_GROUP_SETTINGS = "com.dfire.soa.cloudcash.getMessageGroupSettingVOListByDeviceId";
        //上传设置
        public static final String UPLOAD_SETTINGS = "com.dfire.soa.cloudcash.uploadSettings";
    }

    /**
     * “关注的桌位”模块
     */
    public static class ATTENTION_DESK {
        // 绑定选中的桌位列表
        public static final String METHOD_BIND_SEAT = "com.dfire.soa.cloudcash.bindSeat";
        // 解绑选中的桌位列表
        public static final String METHOD_UNBIND_SEAT = "com.dfire.soa.cloudcash.unbindSeat";
        // 获取所有桌位列表(增加了桌位是否绑定的状态)
        public static final String METHOD_QUERY_ALL_SEATS = "com.dfire.soa.cloudcash.queryAllSeatsIncludeTakeOutSeat";
        // 获取关注的区域
        public static final String METHOD_QUERY_WATCH_AREA = "com.dfire.soa.cloudcash.queryTabMenuByUserId";
    }


    /**
     * 右侧筛选模块
     */
    public static class Filter {
        // 找单界面筛选（只返回的有关注区域和关注对象的数据）
        public static final String METHOD_FILTER_FIND_ORDER = "com.dfire.soa.cloudcash.querySystemMenuByUserId";
    }

    /**
     * “消息中心”模块
     */
    public static class MESSAGE_CENTER {
        // 获取消息列表
        public static final String METHOD_GET_MESSAGE_LIST = "com.dfire.soa.cloudcash.getMessageListV2";
        // 批量处理消息
        public static final String METHOD_BATCH_UPDATE_MESSAGE = "com.dfire.soa.cloudcash.batchUpdateMessage";
        // 获取消息详情
        public static final String METHOD_MESSAGE_DETAIL = "com.dfire.soa.cloudcash.getMessage";
        // 消息详情—审核消息
        public static final String METHOD_MESSAGE_AUDIT = "com.dfire.tp.client.service.ITradePlatformService.auditOrder";
        // 发送消息[打印客户联/打印点菜联]
        public static final String METHOD_SEND_MESSAGE = "com.dfire.soa.cloudcash.sendMessage";
        // 打印客户联
        public static final String METHOD_PRINT_ACCOUNT = "com.dfire.soa.cloudcash.sendCustomerPageMessage";
        // 获取外卖消息详情
        public static final String METHOD_TAKE_OUT_MESSAGE_DETAIL = "com.dfire.soa.cloudcash.getTakeoutMessage";
    }

    /**
     * 订单相关接口方法
     */
    public static class Order {
        // 挂单列表
        public static final String METHOD_ORDER_HANG_UP_ORDER_LIST = "com.dfire.soa.cloudcash.retainOrderList";
        // 按单号查询桌位订单
        public static final String METHOD_ORDER_LIST_BY_CODE = "com.dfire.order.service.ICloudOrderService.queryOrderByCode";
        // 获取订单列表
        public static final String METHOD_ORDER_LIST_QUERY = "com.dfire.tp.client.service.ITradePlatformService.queryFocusOrder";
        // 获取订单详情
        public static final String METHOD_ORDER_DETAIL = "com.dfire.cloudcash.getOrderDetail";
        // 获取订单详情by seatCode
        public static final String METHOD_ORDER_DETAIL_BY_SEAT_CODE = "com.dfire.cloudcash.getOrderDetailBySeatCode";
        // 反结账
        public static final String METHOD_ORDER_REVERSE_CHECK_OUT = "com.dfire.tp.client.service.ITradePlatformService.reverseCheckout";
        // 结账完毕
        public static final String METHOD_ORDER_AFTER_END_PAY = "com.dfire.tp.client.service.ITradePlatformService.checkoutOrder";
        // 获取服务费列表
        public static final String METHOD_GET_FEE_PLAN_LIST_BY_AREA_ID = "com.dfire.trade.conf.service.getAreaFeePlans";
        // 修改订单服务费方案
        public static final String METHOD_ORDER_ORDER_FEE_PLAN = "com.dfire.tp.client.service.ITradePlatformService.modifyOrderFeePlan";
        // 催单
        public static final String METHOD_ORDER_PUSH_ORDER = "com.dfire.soa.cloudcash.remindOrder";
        // 撤单
        public static final String METHOD_ORDER_CANCEL_ORDER = "com.dfire.tp.client.service.ITradePlatformService.cancelOrder";
        // 改单[交易中心]
        public static final String METHOD_CHANGE_ORDER_BY_TRADE = "com.dfire.tp.client.service.ITradePlatformService.modifyOrder";
        // 改单[购物车]
        public static final String METHOD_CHANGE_ORDER_BY_SHOP = "com.dfire.soa.cloudcash.changeOrder";
        // 获取备注列表
        public static final String METHOD_GET_REMARK_LIST = "com.dfire.item.IGetTasteService.queryKindAndTasteList";
        // 开单
        public static final String METHOD_CREATE_ORDER = "com.dfire.soa.cloudcash.openOrderIncludeRetail";
        // 开单（零售）
        public static final String METHOD_CREATE_ORDER_FOR_RETAIL = "com.dfire.soa.cloudcash.openOrderForRetailIndustry";
        //获取零售单订单来源
        public static final String METHOD_GET_RETAIL_ORDER_FROM = "com.dfire.tp.client.service.ITradePlatformService.getRetailOrderFrom";
        //获取已结账单明细
        public static final String METHOD_GET_BILL_DETAIL_LIST = "com.dfire.tp.client.service.ITradePlatformService.getBillDetailList";
        // 账单明细
        public static final String METHOD_ORDER_PARTICULARS = "com.dfire.tp.client.service.ITradePlatformService.getBillDetailList";
        // 账单汇总信息
        public static final String METHOD_ORDER_SUMMARY = "com.dfire.soa.cloudcash.getBillSummaryDays";
        // 已结账单信息
        public static final String METHOD_ORDER_COMPLETE = "com.dfire.soa.cloudcash.getCompleteBillByDate";
    }

    /**
     * “个人中心”模块
     */
    public static class PERSONAL_CENTER {
        // 修改密码
        public static final String METHOD_CHANGE_PWD = "com.dfire.soa.cloudcash.changePwd";
        // 意见反馈
        public static final String METHOD_SEND_SUGGEST = "com.dfire.soa.cloudcash.sendSuggest";
    }

    /**
     * 菜肴
     */
    public static class Instance {
        // 修改菜肴价格
        public static final String METHOD_UPDATE_INSTANCE_PRICE = "com.dfire.tp.client.service.ITradePlatformService.modifyInstancePrice";
        // 修改菜肴重量
        public static final String METHOD_UPDATE_INSTANCE_WEIGHT = "com.dfire.tp.client.service.ITradePlatformService.modifyInstanceWeight";
        // 退菜
        public static final String METHOD_CANCEL_INSTANCE = "com.dfire.tp.client.service.ITradePlatformService.cancelInstance";
        // 赠菜
        public static final String METHOD_GIVE_INSTANCE = "com.dfire.tp.client.service.ITradePlatformService.freeInstance";
        // 催菜
        public static final String METHOD_PUSH_INSTANCE = "com.dfire.soa.cloudcash.remindDish";
    }

    /**
     * 工作模式
     */
    public static class WorkMode {
        // 保存工作模式
        public static final String METHOD_SAVE_WORK_MODEL_CONFIG = "com.dfire.soa.cloudcash.setCloudCashWorkMode";
        // 获取工作模式
        public static final String METHOD_GET_WORK_MODEL_CONFIG = "com.dfire.turtle.queryConfigValueListByCodeList";
    }

    /**
     * 检查更新
     */
    public static class CheckUpdate {
        // 检测更新
        public static final String CHECK_UPDATE = "com.dfire.cashsoa.checkAppUpdate";
        // 检测本地收银是否能使用
        public static final String CHECK_SUPPORT_CASH_VERSION = "com.dfire.soa.cloudcash.checkVersionAndOpenCloudCash";
    }

    /**
     * 打印
     */
    public static class Print {
        // 保存标签打印配置信息
        public static final String METHOD_SAVE_LABEL_PRINT_CONFIG = "com.dfire.soa.cloudcash.ILabelPrinterConfigClientService.save";
        // 获取标签打印配置信息
        public static final String METHOD_GET_LABEL_PRINT_CONFIG = "com.dfire.soa.cloudcash.ILabelPrinterConfigClientService.getVOByEntityIdAndUserId";
        // 获取打印配置
        public static final String METHOD_GET_PRINT_CONFIG = "com.dfire.soa.cloudcash.IPrinterConfigClientService.getPrinterConfigByUserId";
        // 保存打印配置
        public static final String METHOD_SAVE_PRINT_CONFIG = "com.dfire.soa.cloudcash.saveUserPrinterConfigV2";
    }

    /**
     * 快捷收款
     */
    public static class ShortCutReceipt {
        // 快捷收款
        public static final String METHOD_SHORT_CUT_RECEIPT = "com.dfire.tp.client.service.ITradePlatformService.quickPay";
    }

    /**
     * 电子支付明细
     */
    public static class ElePayment {
        // 获取电子支付明细列表
        public static final String METHOD_GET_ELEPAYMENT_LIST = "com.dfire.tp.client.service.ITradePlatformService.getElePaymentList";
        // 获取电子支付明细凭证
        public static final String METHOD_GET_ELEPAYMENT_DETAIL = "com.dfire.tp.client.service.ITradePlatformService.getElePayment";
    }

    /**
     * 首页
     */
    public static class Home {
        // 获取首页各项未读数量
        public static final String METHOD_GET_HOME_UNREAD_COUNT = "com.dfire.soa.cloudcash.homeCount";
        // 获取店铺正式使用情况
        public static final String METHOD_GET_SHOP_LIMIT_DAY = "com.dfire.soa.cloudcash.getShopVoByEntityId";
    }
}
