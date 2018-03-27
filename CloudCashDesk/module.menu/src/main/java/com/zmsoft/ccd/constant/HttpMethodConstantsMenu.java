package com.zmsoft.ccd.constant;

/**
 * 应用内所有的网络请求method<br />
 * <b>根据模块进行划分</b>
 *
 * @author DangGui
 * @create 2017/4/15.
 */

public class HttpMethodConstantsMenu {


    /**
     * 购物车模块
     */
    public static class Cart {
        // 查询购物车
        public static final String METHOD_QUERY_CART = "com.dfire.consumer.queryCart"; //"com.dfire.soa.cloudcash.ICartBaseClientService.queryCart"; //
        // 加入购物车
        public static final String METHOD_JOIN_CART = "com.dfire.consumer.joinTable"; //"com.dfire.soa.cloudcash.ICartBaseClientService.joinTable";//
        // 清理购物车
        public static final String METHOD_CLEAR_CART = "com.dfire.consumer.clearCart"; //"com.dfire.soa.cloudcash.ICartBaseClientService.clearCart";//
        //修改购物车
        public static final String METHOD_MODIFY_CART = "com.dfire.consumer.modifyCartItem";//"com.dfire.soa.cloudcash.ICartBaseClientService.modifyCartItem";//
        //购物车确认下单
        public static final String METHOD_SUBMIT_ORDER = "com.dfire.tp.client.service.ITradePlatformService.submitOrder";
        //购物车挂单
        public static final String METHOD_HANG_UP_ORDER = "com.dfire.soa.cloudcash.retainOrder";

        public static class Paras {
            public static final String PARAS_CART_QUERY_REQUEST = "cartQueryRequest";
            public static final String PARAS_CART_ITEM_MODIFY_REQUEST = "CartItemModifyRequest";
            public static final String PARAS_CART_CLEAR_REQUEST = "cartClearRequest";
            public static final String PARAS_CART_JOIN_REQUEST = "cartJoinTableRequest";
            public static final String PARAS_ENTIYID = "entityId";
            public static final String PARAS_PARAMS = "param";
            public static final String PARAS_CUSTOMER_ID = "customerRegisterId";
            public static final String PARAS_OP_USER_ID = "opUserId";
            public static final String PARAS_SEAT_CODE = "seatCode";
            public static final String PARAS_ORDER_ID = "orderId";
            public static final String PARAS_CART_ITEMS = "cartItems";
            public static final String PARAS_CART_TIME = "cartTime";
            public static final String PARAS_CART_MODIFY_TIME = "modifyTime";
            public static final String PARAS_RETAIN_ENTIY_ID = "entity_id";
            public static final String PARAS_RETAIN_USER_ID = "user_id";
            public static final String PARAS_RETAIN_SEAT_CODE = "seat_code";
        }
    }

    /**
     * 商品详情
     */
    public static class FoodDetail {
        // 查询商品详情
        public static final String QUERY_DETAIL = "com.dfire.soa.cloudcash.getBaseMenuVoByMenuId";

        public static class Paras {
            public static final String MENU_ID = "menu_id";
        }
    }

}
