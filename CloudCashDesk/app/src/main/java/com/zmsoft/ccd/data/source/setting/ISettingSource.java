package com.zmsoft.ccd.data.source.setting;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.settings.MessageGroupSettingVO;

import java.util.List;

/**
 * Created by huaixi on 2017/10/24.
 */

public interface ISettingSource {

    /**
     * 消息设置状态获取
     * @param entity_id 实体id
     * @param device_id 用户id
     */
    void getSettings(String entity_id, String device_id, Callback<List<MessageGroupSettingVO>> callback);

    /**
     * 设置消息
     * @param entityId 实体id
     * @param device_id 用户id
     * @param message_group 消息组
     * @param is_valid 是否有效
     */
    void uploadSettings(String entityId, String device_id, final String message_group, final Integer is_valid, Callback<Integer> callback);
}
