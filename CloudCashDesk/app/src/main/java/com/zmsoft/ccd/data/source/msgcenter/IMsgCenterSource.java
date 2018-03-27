package com.zmsoft.ccd.data.source.msgcenter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.lib.bean.message.SendMessage;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;

import java.util.List;

/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */

public interface IMsgCenterSource {
    /**
     * 获取消息列表
     *
     * @param msgCategory 消息类型
     * @param pageIndex   分页index
     * @param callback
     */
    void getMessageList(int msgCategory, final int pageIndex, final Callback<List<DeskMessage>> callback);

    /**
     * 批量处理消息
     *
     * @param messageIdListJson json字符串
     * @param status            处理状态（已处理）
     * @param resultMsg         处理结果（已处理）
     */
    void batchUpdateMessage(String messageIdListJson, int status, String resultMsg, Callback<Boolean> callback);

    /**
     * 获取消息详情
     *
     * @param messageId
     * @param callback
     */
    void loadMsgDetail(String messageId, Callback<DeskMsgDetail> callback);

    /**
     * 审核消息
     *
     * @param messageId
     * @param isAgree
     * @param callback
     */
    void handleMessage(final String messageId, final boolean isAgree, Callback<AuditOrderResponse> callback);

    /**
     * 发送打印点菜/客户联消息[1031表示客户联；1032表示点菜单]
     *
     * @param entityId 实体id
     * @param orderId  订单id
     * @param type     类型
     */
    void sendMessage(String entityId, String orderId, int type, String opUserId, Callback<SendMessage> callback);

    /**
     * 发送打印客户联
     *
     * @param entityId 实体id
     * @param orderId  订单id
     * @param opUserId 操作员id
     * @param callback 回调
     */
    void printAccountOrder(String entityId, String orderId, String opUserId, Callback<CommonResult> callback);

    /**
     * 获取外卖消息详情
     *
     * @param callback
     */
    void loadTakeoutMsgDetail(String msgId, Callback<TakeoutMsgDetailResponse> callback);
}
