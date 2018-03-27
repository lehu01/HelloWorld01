package com.zmsoft.ccd.module.receipt.events;

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
         * 收款界面，清空优惠
         */
        RECEIPT_CLEAR_DISCOUNT_EVENT,
        /**
         * 收款界面，删除付款ITEM
         */
        RECEIPT_DELETE_PAY_EVENT,
        /**
         * 选择挂账单位（人）界面，选中某条ITEM
         */
        RECEIPT_UNIT_CHECKED_ITEM_EVENT;
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
