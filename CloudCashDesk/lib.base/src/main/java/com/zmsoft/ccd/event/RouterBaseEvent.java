package com.zmsoft.ccd.event;

/**
 * Description：事件通知EventButs
 * <br/>
 * 需要注意的每次使用的必须设置data，没有数据setData(null)，如下所示：
 * <br/>
 * <p>
 * <pre>
 * RouterBaseEvent.CommonEvent event = RouterBaseEvent.CommonEvent.EVENT_NOTIFY_MENU_LIST_CART;
 * event.setObject(data);
 * //event.setObject(null);
 * EventBusHelper.post(event);
 * </pre>
 * Create by danshen@2dfire.com
 * Time on 2017/4/24 11:50
 */
public interface RouterBaseEvent {

    void setObject(Object obj);

    Object getObject();

    enum CommonEvent implements RouterBaseEvent {
        // 自动退出登录(被踢)
        EVENT_LOG_OUT,
        // 刷新购物车
        EVENT_REFRESH_SHOP_CAR,
        /**
         * 通知购物车有新消息
         */
        EVENT_NOTIFY_CART_NEW,

        /**
         * 购物车数据发生变化，通知菜单列表刷新
         * <br/>
         * 如果有购物车列表数据，直接setData设置进来，就不用请求网络了，如果没有发个通知就可以
         */
        EVENT_NOTIFY_MENU_LIST_CART,
        /**
         * 通知蓝牙打印连接成功
         */
        EVENT_REFRESH_BLUE_BOOTH,
        /**
         * 订单变化：推送，撤单，退菜，加菜，赠菜，打印等
         */
        EVENT_RELOAD_SEAT_LIST,
        /**
         * 订单详情刷新
         */
        EVENT_RELOAD_ORDER_DETAIL,
        /**
         * 进入套餐子菜详情， 选择相关属性，返回套餐详情（兼容已有不规范代码^v^）
         */
        EVENT_SELECT_SUIT_CHILE_MENU,
        /**
         * 称重页面返回
         */
        EVENT_NOTIFY_MENU_LIST_FOR_WEIGHT,
        /**
         * 外卖列表
         */
        EVENT_NOTIFY_TAKEOUT_LIST;

        private Object obj;

        @Override
        public void setObject(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object getObject() {
            return obj;
        }
    }
}
