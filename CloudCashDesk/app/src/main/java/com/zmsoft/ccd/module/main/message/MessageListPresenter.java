package com.zmsoft.ccd.module.main.message;

import com.dfire.mobile.util.JsonMapper;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.msgcenter.MsgCenterRepository;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2016/12/17.
 */
public class MessageListPresenter implements MessageListContract.Presenter {

    private MessageListContract.View mView;
    private MsgCenterRepository mMsgCenterRepository;

    @Inject
    public MessageListPresenter(MessageListContract.View view, MsgCenterRepository msgCenterRepository) {
        this.mView = view;
        this.mMsgCenterRepository = msgCenterRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void getMessageList(int msgCategory, final int pageIndex) {
        mMsgCenterRepository.getMessageList(msgCategory, pageIndex, new com.zmsoft.ccd.data.Callback<List<DeskMessage>>() {
            @Override
            public void onSuccess(List<DeskMessage> deskMessageList) {
                if (null == mView) {
                    return;
                }
                if (deskMessageList != null) {
                    mView.loadDataSuccess();
                    mView.showContentView();
                    mView.showDeskMsgList(deskMessageList);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public List<DeskMessage> getBatchUpdateMsgList(List deskList, int position) {
        if (null == deskList) {
            return null;
        }
        List<DeskMessage> deskMessageList = new ArrayList<>();
        /*
        如果position是被特意指定的，则只获取该position相应的数据（针对处理单条消息）
        否则，批量处理所有不需要审核的消息
         */
        if (isNeedBatchUpdateMsg(deskList, position)) {
            for (int i = 0; i < deskList.size(); i++) {
                DeskMessage deskMessage = (DeskMessage) deskList.get(i);
                int msgType = deskMessage.getTy();
                int msgStatus = deskMessage.getSu();
                boolean isUnhandled = (isNeedHandle(msgType) && msgStatus == MessageHelper.MsgState.STATE_UN_HANDLE);
                if (isUnhandled) {
                    deskMessageList.add(deskMessage);
                }
            }
        } else {
            DeskMessage deskMessage = (DeskMessage) deskList.get(position);
            int msgType = deskMessage.getTy();
            int msgStatus = deskMessage.getSu();
            boolean isUnhandled = (isNeedHandle(msgType) && msgStatus == MessageHelper.MsgState.STATE_UN_HANDLE);
            if (isUnhandled) {
                deskMessageList.add(deskMessage);
            }
        }
        return deskMessageList;
    }

    /**
     * 判断需不需要批量处理，还是只处理单条消息
     *
     * @param deskList
     * @param position
     * @return
     */
    @Override
    public boolean isNeedBatchUpdateMsg(List deskList, int position) {
        if (position < 0 || position >= deskList.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getBatchUpdateMsgIdJson(List deskList) {
        List<String> jsonList = new ArrayList<>();
        for (int i = 0; i < deskList.size(); i++) {
            jsonList.add(((DeskMessage) deskList.get(i)).getId());
        }
        if (jsonList.isEmpty()) {
            return null;
        } else {
            return JsonMapper.toJsonString(jsonList);
        }
    }

    @Override
    public void batchUpdateMessage(final List batchHandleList, String messageIdListJson
            , int status, String resultMsg, final int position) {
        mView.showLoading(context.getString(R.string.dialog_handling), false);
        mMsgCenterRepository.batchUpdateMessage(messageIdListJson, status, resultMsg, new com.zmsoft.ccd.data.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                updateBatchHandledView(batchHandleList, position);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.loadDataError(body.getMessage());
            }
        });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    /**
     * 更新列表
     *
     * @param batchHandleList 批量处理的列表
     * @param position        如果是只处理单条，position是合法的（>=0）,否则是-1,代表是批量处理
     */
    private void updateBatchHandledView(List batchHandleList, int position) {
        if (null != batchHandleList && !batchHandleList.isEmpty()) {
            boolean isContainPayTypeMsg = false;
            for (int i = 0; i < batchHandleList.size(); i++) {
                ((DeskMessage) batchHandleList.get(i)).setSu(MessageHelper.MsgState.STATE_HANDLED_SUCCESS);
                //如果处理“我知道了”的消息中包含支付类型消息，需要通知订单中心刷新数据
                if (((DeskMessage) batchHandleList.get(i)).getTy() == MessageHelper.MsgType.TYPE_PAY) {
                    isContainPayTypeMsg = true;
                }
            }
            mView.notifyDataChanged(position);
            if (isContainPayTypeMsg) {
                EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
            }
        }
    }

    /**
     * 判断某种类型是否可以点击“我知道了”
     *
     * @param msgType
     * @return
     */
    private boolean isNeedHandle(int msgType) {
        return !(msgType == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                || msgType == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                || msgType == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT
                || msgType == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                || msgType == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE);
    }
}
