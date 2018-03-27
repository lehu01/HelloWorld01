package com.zmsoft.ccd.constant;

/**
 * SharedPreferences 常量类，应用内所有用到的SharedPreferences Key值全部定义在该类，方便管理
 * <p>
 * <b>按功能类型区分填写</b>
 * </p>
 *
 * @author DangGui
 * @create 2016/12/26.
 */

public class SPConstants {
    /**
     * 样例模板
     */
    public static class FeatherDemoKey {
        public static final String FEATHER_DEMO_KEY_ONE = "feather_demo_key_one";
        public static final String FEATHER_DEMO_KEY_TWO = "feather_demo_key_two";
    }

    /**
     * 消息设置
     */
    public static class MsgSetting {
        public static final String SHOW_NEW_MSG = "show_new_msg";
        public static final String RECEIVE_MSG_FROM_WAITER_WECHAT = "receive_msg_from_waiter_wechat";
        public static final String RECEIVE_MSG_FROM_TAKEOUT = "receive_msg_from_waiter_takeout";
        public static final String AUTO_CHECK_TAKEOUT = "auto_check_takeout";
        public static final String AUTO_CHECK_THIRD_TAKEOUT = "auto_check_third_takeout";
        public static final String AUTO_CHECK_MSG_FROM_WAITER_WECHAT = "auto_check_msg_from_waiter_wechat";
        public static final String CUSTOM_TTS = "custom_tts";
        public static final String CUSTOM_TTS_SCAN_ORDER = "custom_tts_scan_order";
        public static final String CUSTOM_TTS_TAKE_OUT = "custom_tts_take_out";
        public static final String CUSTOM_TTS_PAY = "custom_tts_pay";

    }

    /**
     * 推送消息设置
     */
    public static class PushSetting {
        public static final String JPUSH_TAG = "jpush_tag";
        public static final String JPUSH_ALIAS = "jpush_alias";
    }

    /**
     * 上一次检查更新的时间
     */
    public static class CheckUpdate {
        public static final String LAST_TIME = "last_time_check";
    }

    public interface FirstTimeOpenApp {
        String FIRST_TIME = "first_time_open";
    }
}
