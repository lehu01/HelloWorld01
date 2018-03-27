package com.zmsoft.ccd.helper;

/**
 * Created by jihuo on 2017/2/28.
 */

public final class MsgSettingType {
    public static final String IS_SHOW_NEW_MESSAGE = "IS_SHOW_NEW_MESSAGE"; //消息中心是否提示新消息
    public static final String SEAT_SERVICE_BELL = "seat_service_bell"; // 服务铃
    public static final String TAKE_OUT_SEAT_CODE = "TAKE_OUT_SEAT_CODE"; //是否接收外卖单消息(0关1开)
    public static final String OUR_TAKEOUT_SET = "OUR_TAKEOUT_SET"; //自动审核本店外卖
    public static final String MESSAGE_SOUND = "MESSAGE_SOUND"; //不同消息使用不同语音
    public static final String TANGSHI_SOUND = "TANGSHI_SOUND"; //堂食订单语音提示
    public static final String WAIMAI_SOUND = "WAIMAI_SOUND"; //外卖订单语音提示
    public static final String PAY_SOUND = "PAY_SOUND"; //支付消息语音提示
    public static final String THIRD_TAKEOUT_SET = "IS_ELEME_REVIEW"; //自动审核第三方外卖
}