package com.zmsoft.ccd.data.source.msgcenter;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.bean.message.DeskMsgDetail;
import com.zmsoft.ccd.lib.bean.message.SendMessage;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 消息中心Model
 *
 * @author DangGui
 * @create 2017/3/4.
 */
@Singleton
public class MsgCenterRepository implements IMsgCenterSource {

    private final IMsgCenterSource remoteSource;

    @Inject
    MsgCenterRepository(@Remote IMsgCenterSource remoteSource) {
        this.remoteSource = remoteSource;
    }

    @Override
    public void getMessageList(int msgCategory, int pageIndex, Callback<List<DeskMessage>> callback) {
        remoteSource.getMessageList(msgCategory, pageIndex, callback);
    }

    @Override
    public void batchUpdateMessage(String messageIdListJson, int status, String resultMsg, Callback<Boolean> callback) {
        remoteSource.batchUpdateMessage(messageIdListJson, status, resultMsg, callback);
    }

    @Override
    public void loadMsgDetail(String messageId, Callback<DeskMsgDetail> callback) {
        remoteSource.loadMsgDetail(messageId, callback);
    }

    @Override
    public void handleMessage(String messageId, boolean isAgree, Callback<AuditOrderResponse> callback) {
        remoteSource.handleMessage(messageId, isAgree, callback);
    }

    @Override
    public void sendMessage(String entityId, String orderId, int type, String opUserId, Callback<SendMessage> callback) {
        remoteSource.sendMessage(entityId, orderId, type, opUserId, callback);
    }

    @Override
    public void printAccountOrder(String entityId, String orderId, String opUserId, Callback<CommonResult> callback) {
        remoteSource.printAccountOrder(entityId, orderId, opUserId, callback);
    }

    @Override
    public void loadTakeoutMsgDetail(String msgId, Callback<TakeoutMsgDetailResponse> callback) {
        remoteSource.loadTakeoutMsgDetail(msgId, callback);
    }
}
