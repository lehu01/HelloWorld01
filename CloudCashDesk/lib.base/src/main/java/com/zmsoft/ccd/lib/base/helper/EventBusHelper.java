package com.zmsoft.ccd.lib.base.helper;

import org.greenrobot.eventbus.EventBus;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/13 16:44
 */
public class EventBusHelper {

//    public static void initEventBus() {
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
//    }

    public static void register(Object subscriber) {
        if (subscriber != null && !EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (subscriber != null && EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送粘滞事件
     *
     * @param event
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 为节省开销，应在相关接收事件的模块及时回收掉当前发送的黏滞事件
     *
     * @param event
     */
    public static void removeSticky(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }
}
