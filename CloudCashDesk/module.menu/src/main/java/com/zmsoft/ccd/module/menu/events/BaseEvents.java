package com.zmsoft.ccd.module.menu.events;

/**
 * 基本事件，主要是为了便于事件管理添加和维护。避免新建很多不必要的类
 *
 * @author DangGui
 * @create 2017/4/17.
 */

public interface BaseEvents {
    void setObject(Object obj);

    Object getObject();

    /**
     * 事件定义
     */
    enum CommonEvent implements BaseEvents {
        /**
         * 购物车——跳转购物车详情
         */
        CART_SWITCH_TO_DETAIL_EVENT,
        /**
         * 购物车——清空购物车
         */
        CART_CLEAR_EVENT,
        /**
         * 购物车详情被修改
         */
        CART_DETAIL_MODIFY,
        /**
         * 购物车列表，菜数量被修改
         */
        CART_LIST_FOODNUM_MODIFY,
        /**
         * 购物车列表，跳转到改单页
         */
        CART_TO_MODIFY_ORDER,
        /**
         * 购物车被修改
         */
        CART_MODIFY,
        /**
         * 购物车详情开/关“赠送这个菜”
         */
        CART_DETAIL_SWITCH_PRESENT_FOOD;
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
