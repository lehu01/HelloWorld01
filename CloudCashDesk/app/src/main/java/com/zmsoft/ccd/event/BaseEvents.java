package com.zmsoft.ccd.event;

/**
 * 基本事件，主要是为了便于事件管理添加和维护。避免新建很多不必要的类
 *
 * @author DangGui
 * @create 2017/1/7.
 */

public interface BaseEvents {
    void setObject(Object obj);

    Object getObject();

    /**
     * 事件定义
     */
    enum CommonEvent implements BaseEvents {

        /*我关注的桌位*/
        /**
         * section header被 选中/反选 后，整个section的所有item应该相应做 选中/反选 处理
         */
        CHECKED_SECTION_ITEMS_EVENT,
        /**
         * section中的某个item被 选中/反选 后，应当检测同sectionId的item是否全部是选中状态，
         * 如果是section header也应该是选中状态，只要有一个不是选中状态，header就应该是 反选 状态
         */
        CHECK_IF_SECTION_ITEM_CHECKED,
        /**
         * 全选/全不选 所有的item
         */
        CHECK_ALL_ITEMS,
        /**
         * 我关注的桌位——绑定桌位成功,通知我关注的桌位列表、首页订单列表刷新数据
         */
        EVENT_ATTENTION_DESK_BIND_SUCCESS,
        /**
         * 我关注的桌位——解绑桌位
         */
        EVENT_ATTENTION_DESK_UNBIND,
        /**
         * 我关注的桌位——解绑桌位成功
         */
        EVENT_ATTENTION_DESK_UNBIND_SUCCESS,
        /**
         * 消息中心——我知道了
         */
        EVENT_MSG_CENTER_IKNOW,
        /**
         * 消息中心——刷新消息列表
         */
        EVENT_MSG_CENTER_REFRESH,
        /**
         * 显示服务费方案
         */
        EVENT_SHOW_FEE_PLAN_BOTTOM_DIALOG,
        /**
         * 切店
         */
        EVENT_CHECK_SHOP,
        /**
         * 消息中心——未读新消息
         */
        EVENT_MSG_CENTER_UNREAD,
        /**
         * 右侧栏——上下班状态切换
         */
        EVENT_WORK_STATE_SWITCH,
        /**
         * 通知主页自动切换到零售单
         */
        EVENT_SWITCH_WATCHED_RETAIL,
        /**
         * 通知沽清页面刷新
         */
        EVENT_BALANCE_REFRESH,
        /**
         * 主页——刷新未读消息
         */
        EVENT_HOME_REFRESH,
        /**
         * 主页——刷新工作模式
         */
        EVENT_REFRESH_WORK_MODE,
        /**
         * 消息中心——隐藏右侧筛选栏
         */
        EVENT_MSG_CENTER_HIDE_FILTER;
        /**
         * event传递的对象
         */
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
