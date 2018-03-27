package com.zmsoft.ccd.module.main.message;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 16:18
 */
public class MessageListContract {

    public interface Presenter extends BasePresenter {

        void getMessageList(int msgCategory, int pageIndex);

        /**
         * 获取所有需要处理的消息
         *
         * @param deskList 数据源
         * @param position 如果指定了position，则只获取该position相应的数据（针对处理单条消息）
         * @return
         */
        List<DeskMessage> getBatchUpdateMsgList(List<DeskMessage> deskList, int position);

        /**
         * 判断需不需要批量处理，还是只处理单条消息
         *
         * @param deskList
         * @param position
         * @return
         */
        boolean isNeedBatchUpdateMsg(List<DeskMessage> deskList, int position);

        /**
         * 根据需要处理的消息列表生成对应的json串给服务端
         *
         * @param deskList 需要处理的消息列表
         * @return
         */
        String getBatchUpdateMsgIdJson(List<DeskMessage> deskList);

        /**
         * 批量处理消息
         *
         * @param messageIdListJson json字符串
         * @param status            处理状态（已处理）
         * @param resultMsg         处理结果（已处理）
         */
        void batchUpdateMessage(List<DeskMessage> deskList, String messageIdListJson, int status, String resultMsg
                , int position);
    }

    public interface View extends BaseView<MessageListContract.Presenter> {

        void showDeskMsgList(List<DeskMessage> deskMessageList);

        /**
         * 如果 顾客新消息/所有新消息 获取为空，则需要通知主界面隐藏消息中心的小红点
         */
        void checkUnReadMsg();

        /**
         * 通知UI列表更新
         */
        void notifyDataChanged(int position);

        /**
         * 数据请求成功
         */
        void loadDataSuccess();

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

}
