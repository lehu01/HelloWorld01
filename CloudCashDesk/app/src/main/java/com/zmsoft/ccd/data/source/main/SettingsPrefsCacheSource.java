package com.zmsoft.ccd.data.source.main;


import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.utils.SPUtils;

/**
 * 用户配置信息 eg.用户最后一次查看消息中心的消息类型等
 *
 * @author DangGui
 * @create 2017/2/24.
 */
public class SettingsPrefsCacheSource {
    private static final String SETTING_PREF = "setting_pref";
    private static final String SETTING_KEY_MSG_CENTER_CATEGORY = "setting_key_msg_center_category";

    /**
     * 将选中的消息类型保存起来
     *
     * @param category 消息类型
     *                 public static final String MENU_ITEM_MESSAGE_ALL = "MENU_ITEM_MESSAGE_ALL"; // 全部消息
     *                 public static final String MENU_ITEM_MESSAGE_USER = "MENU_ITEM_MESSAGE_USER"; // 顾客新消息
     *                 public static final String MENU_ITEM_MESSAGE_DEAL_WITH = "MENU_ITEM_MESSAGE_DEAL_WITH"; // 已处理消息
     */
    public static void saveMsgCenterCategory(String category) {
//        SPUtils.getmInstance(GlobalVars.mContext, SETTING_PREF).putString(SETTING_KEY_MSG_CENTER_CATEGORY, category);
    }

    public static String getMsgCenterCategory() {
        return SPUtils.getInstance(GlobalVars.context, SETTING_PREF).getString(SETTING_KEY_MSG_CENTER_CATEGORY);
    }
}
