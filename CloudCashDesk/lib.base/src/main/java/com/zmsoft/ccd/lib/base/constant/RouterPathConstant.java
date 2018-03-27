package com.zmsoft.ccd.lib.base.constant;

/**
 * Arouter path配置，统一管理
 *
 * @author DangGui
 * @create 2017/4/10.
 */

public interface RouterPathConstant {
    String PATH_MAIN_ACTIVITY = "/main/mainActivity";
    String PATH_RETAIL_MAIN_ACTIVITY = "/main/retail/mainActivity";

    /**
     * 购物车
     */
    String PATH_CART = "/menu/module/cart";

    /**
     * 购物车详情
     */
    String PATH_CART_DETAIL = "/menu/module/cartdetail";



    interface MenuScan{
        /**
         * 菜单列表扫码
         */
        String PATH_MENU_SCAN = "/menu/module/scan";
    }




    interface RetailMenuScan {
        String PATH_MENU_SCAN = "/menu/module/retail/scan";
    }

    /**
     * 零售购物车列表
     */
    interface RetailCart {
        String PATH_CART = "/menu/module/retail/cart";
        //上级页面传参，订单信息
        String EXTRA_CREATE_ORDER_PARAM = "createOrderParam";
        //上级页面传参，标识上级页面的来源
        String EXTRA_FROM = "from";
        //购物车信息
        String EXTRA_DINNING_VO = "dinningTableVo";
        //来自挂单列表
        int EXTRA_FROM_HANG_UP_ORDER = 1;
        //继续点菜时，购物车跳转到菜单列表的requestCode
        int REQUEST_CODE_TO_MENU_LIST = 100;
    }

    /**
     * 零售购物车详情
     */
    interface RetailCartDetail {
        String PATH_CART_DETAIL = "/menu/module/retail/cartdetail";
    }

    /**
     * 零售登录
     */
    interface RetailLogin {
        String PATH_LOGIN_ACTIVITY = "/main/retail/login";
        String EXTRA_CODE = "extraCode";
    }

    /**
     * 主页面
     */
    interface Main {
        //切换主页Tab
        String EXTRA_SWITCH_TAB_PARAM = "switchTabParam";
        //切换到主页找单Tab
        int SWITCH_TO_FIND_ORDER = 2;
    }

    /**
     * 菜单列表
     */
    interface MenuList {
        String PATH = "/menu/module/list";
        String GROUP = "menu";
        String EXTRA_CREATE_ORDER_PARAM = "createOrderParam";
    }

    /**
     * 零售列表
     */
    interface RetailMenuList {
        String PATH = "/menu/retail/module/list";
        String GROUP = "menu";
        String EXTRA_CREATE_ORDER_PARAM = "createOrderParam";
        String FROM = "from";
        int FROM_COMPLETE_RECEIPT = 2;
    }

    interface SuitNote {
        String PATH = "/menu/module/suit/note";
        String PARAM_SUITMENOVO = "suitMenuVO";
    }

    /**
     * 套餐详情
     */
    interface SuitDetail {
        String PATH = "/menu/module/suit/detail";
        String PARAM_SUIT_BASE_MENU_VO = "suit_base_menu_vo";
        String PARAM_SUIT_MENU_ID = "suit_menu_id";
        String PARAM_SUIT_MENU_NAME = "suit_menu_name";
        String PARAM_CREATE_ORDER_PARAM = "createOrderParam";
        String RESULT_MENU_CHILD = "resultMenuChild";
        String PARAM_ITEMVO = "itemVO";
        String PARAM_INSTANCE_ID = "instanceId";
    }

    /**
     * 菜肴详情
     */
    interface MenuDetail {
        String PATH = "/menu/module/suit/detail";
        String EXTRA_MENU_ID = "menuId";
    }

    /**
     * 自定义商品
     */
    interface CustomFood {
        String PATH = "/menu/module/custom/food";
        String EXTRA_CREATE_ORDER_PARAM = MenuList.EXTRA_CREATE_ORDER_PARAM;
        String EXTRA_CUSTOM_FOOD_ITEMVO = "item_vo";
    }

    /**
     * 自定义商品
     */
    interface RetailCustomFood {
        String PATH = "/menu/module/retail/custom/food";
        String EXTRA_CREATE_ORDER_PARAM = MenuList.EXTRA_CREATE_ORDER_PARAM;
        String EXTRA_CUSTOM_FOOD_ITEMVO = "item_vo";
    }

    /**
     * </p>
     * 开单/改单
     */
    interface CreateOrUpdateOrder {
        String PATH = "/main/createOrUpdateOrder"; // 开单/改单Router地址
        String EXTRA_FROM = "from"; // 来源
        String EXTRA_FROM_CREATE = "create"; // 开单（扫码/座位）
        String EXTRA_FROM_UPDATE_BY_SHOP_CAR = "update_by_shop_car"; // 改单（购物车）
        String EXTRA_FROM_UPDATE_BY_ORDER_DETAIL = "update_by_order_detail"; // 改单（订单详情）
        String EXTRA_ORDER_PARAM = "orderParam"; // 开单param
    }

    /**
     * </p>
     * 订单详情
     */
    interface OrderDetail {
        String PATH = "/main/orderDetail";
        String EXTRA_FROM = "from"; // 来源，key
        String EXTRA_ORDER_DETAIL = "orderDetail"; // 订单order，key
        String EXTRA_ORDER_ID = "orderId";// 订单id，key
        String EXTRA_AREA_ID = "areaId";// 区域id，key
        int EXTRA_FROM_SEAT_LIST = 1;  // 座位列表
        int EXTRA_FROM_ORDER_LIST = 2; // 订单列表
        int EXTRA_FROM_FIND_ORDER = 3; // 扫码找单
    }

    /**
     * </p>
     * 零售 订单详情
     */
    interface RetailOrderDetail {
        String PATH = "/main/retail/order/detail";
        String EXTRA_FROM = "from"; // 来源，key
        String EXTRA_ORDER_DETAIL = "orderDetail"; // 订单order，key
        String EXTRA_ORDER_ID = "orderId";// 订单id，key
        String EXTRA_AREA_ID = "areaId";// 区域id，key
        int EXTRA_FROM_SEAT_LIST = 1;  // 座位列表
        int EXTRA_FROM_ORDER_LIST = 2; // 订单列表
        int EXTRA_FROM_FIND_ORDER = 3; // 扫码找单
    }
    /**
     * 购物车
     */
    interface Cart {
        //上级页面传参，订单信息
        String EXTRA_CREATE_ORDER_PARAM = "createOrderParam";
        //上级页面传参，标识上级页面的来源
        String EXTRA_FROM = "from";
        //购物车信息
        String EXTRA_DINNING_VO = "dinningTableVo";
        //来自挂单列表
        int EXTRA_FROM_HANG_UP_ORDER = 1;
        //继续点菜时，购物车跳转到菜单列表的requestCode
        int REQUEST_CODE_TO_MENU_LIST = 100;
    }

    /**
     * </p>
     * 菜肴详情
     */
    interface CartDetail {
        String EXTRA_SEATCODE = "seatCode";
        String EXTRA_ORDERID = "orderId";
        String EXTRA_SPECID = "specId"; //套餐子菜详情 掌柜上设置的默认规格ID
        String EXTRA_SUITGROUPVO = "SuitGroupVO"; //套餐子菜详情 需要判断子菜所在分组相关信息，比如分组点菜数量上限等
        String EXTRA_FOOD_NUM = "foodNum"; //套餐必选子菜，需要传参“已点份数”
        String EXTRA_FOOD_LIMITNUM = "foodLimitNum"; //套餐子菜，菜本身数量限制
        String PARAM_SUIT_SUB_MENU = "paramSuitSubMenu"; //套餐子菜为必选，把相关参数传给商品详情
        String PARAM_SUIT_SUB_MENU_NAME = "paramSuitSubMenuName"; //套餐子菜名称
        String PARAM_SUIT_SUB_MENU_POSITION = "paramSuitSubMenuPosition"; //所在list的位置
    }

    /**
     * </p>
     * 工作模式
     */
    interface WorkModel {
        String PATH = "/main/workModel";
        String FROM = "from";
        int FROM_LOGIN = 1;
        int FROM_MAIN = 2;
    }

    /**
     * 收款首页
     */
    interface Receipt {
        String PATH = "/receipt/module/cash";
        String EXTRA_ORDER_ID = "orderId";// 订单id
        String EXTRA_SEAT_NAME = "seatName";// 座位名称
        String EXTRA_SEAT_CODE = "seatCode";// 座位code
        String EXTRA_CODE = "code";// 单号
        String EXTRA_PAY_NAME = "payName";// 付款方式名称
        String EXTRA_THIRD_TAKEOUT = "thirdTakeout";// 是否是第三方外卖
    }

    /**
     * </p>
     * 输入会员卡
     */
    interface InputVipCar {
        String PATH = "/receipt/module/inputVipCar";
        String EXTRA_KIND_PAY_ID = "kindPayId";
    }

    /**
     * </p>
     * 会员卡详情
     */
    interface VipCardDetail {
        String PATH = "/receipt/module/vipCardDetail";
        String CARD_ID = "cardId";
        String ORDER_ID = "orderId";
        String EXTRA_KIND_PAY_ID = "kindPayId";
    }

    /**
     * </p>
     * 配置打印机
     */
    interface PrintConfig {
        String PATH = "/main/printConfig";
    }

    interface Login {
        String PATH_LOGIN_ACTIVITY = "/main/login/login";
        String EXTRA_CODE = "extraCode";
        int REQUEST_CODE_LOGIN_TO_MOBILE_AREA = 1001;
    }


    /**
     * </p>
     * 注册
     */
    interface Register {
        String PATH = "/main/register";
        String FROM = "from";
        String PHONE = "phone";
        String COUNTRY_CODE = "countryCode";
        String WECHAT_UNION_ID = "wechatUnionId";

        public interface FROM_VALUE {
            int REGISTER = 1;
            int FORGET_PASSWORD = 2;
            int VERIFY_CODE_LOGIN = 3;
            int BIND_MOBILE = 4;
        }

        int REQUEST_CODE_REGISTER_TO_MOBILE_AREA = 1002;
    }

    interface MobileArea {
        String PATH = "/main/login/mobileArea";
    }

    /**
     * 微信登录页面
     */
    interface WxEntry {
        String PATH = "/main/wxentry";
    }


    /**
     * 快捷收款
     */
    interface ShortCutReceipt {
        String PATH = "/main/shortcut";
    }

    /**
     * 零售快速收款
     */
    interface RetailShortCutReceipt {
        String PATH = "/main/retail/shortcut";
    }

    /**
     * 选店
     */
    interface CheckShop {
        String PATH_CHECK_SHOP_ACTIVITY = "/main/checkShop";
        String FROM = "from";
        int FROM_LOGIN = 1;
        int FROM_MAIN = 2;
    }

    /**
     * 零售选店
     */
    interface RetailCheckShop {
        String PATH_CHECK_SHOP_ACTIVITY = "/main/retail/checkShop";
        String FROM = "from";
        int FROM_LOGIN = 1;
        int FROM_MAIN = 2;
    }

    /**
     * 外卖列表
     */
    interface TakeoutList {
        String PATH = "/takeout/module/takeout/list";

    }

    /**
     * 零售在线订单
     */
    interface RetailTakeoutList {
        String PATH = "/takeout/module/retail/takeout/list";

    }

    /**
     * 挂单列表
     */
    interface HangUpOrderList {
        String PATH = "/main/hanguporderlist";
    }

    /**
     * 零售挂单列表
     */
    interface RetailHangUpOrderList {
        String PATH = "/main/retail/hanguporderlist";
    }

    /**
     * 外卖消息详情
     */
    interface TakeOutMsgDetail {
        String EXTRA_ORDER_ID = "orderId";// 订单id
        String EXTRA_MSG_ID = "msgId";// 消息id
        String EXTRA_POSITION = "position";// 在消息中心列表中的position
        String EXTRA_MSG_TYPE = "msgType";// 消息类型
    }

    /**
     * </p>
     * 绑定手机号码
     */
    interface BindMobile {
        String PATH = "/main/bindMobile";
        String FROM = "from";
        int FROM_WECHAT = 1;
        int FROM_BIND_MOBILE = 2;
    }


    /**
     *零售撤单
     */
    interface RetailCancelOrder {
        String PATH = "/main/retail/order/cancel";
        String EXTRA_ORDER_ID = "orderId";
        String EXTRA_SEAT_NAME = "seatName";
        String EXTRA_ORDER_SERIAL_NUMBER = "serialNumber";
        String EXTRA_ORDER_NUMBER = "orderNumber";
        String EXTRA_MODIFY_TIME = "modifyTime";
    }

    /**
     * 零售备注
     */
    interface RetailRemark {
        String PATH_REMARK = "/main/retail/remark";

        String EXTRA_REMARK = "remark";
        String EXTRA_SEAT = "seat";
    }

}
