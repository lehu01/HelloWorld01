package com.zmsoft.ccd.module.main.message.takeout;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.message.AuditOrderResponse;
import com.zmsoft.ccd.lib.bean.message.TakeoutMsgDetailResponse;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public interface RetailTakeoutMsgDetailContract {
    interface View extends BaseView<Presenter> {
        void successGetMsgDetail(TakeoutMsgDetailResponse takeoutMsgDetailResponse);

        void failGetMsgDetail(String errorMsg);

        /**
         * 展示底部操作按钮 同意/拒绝
         */
        void showBottomView();

        /**
         * 关闭页面
         */
        void closeView();

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 打印小票
         *
         * @param auditOrderResponse
         */
        void printInstance(AuditOrderResponse auditOrderResponse, TakeoutMsgDetailResponse takeoutMsgDetailResponse);
    }

    interface Presenter extends BasePresenter {
        void loadMsgDetail(String msgId);

        void handleMessage(String messageId, TakeoutMsgDetailResponse takeoutMsgDetailResponse, int position, boolean isAgree);

        /**
         * 处理消息
         *
         * @param isAgree 同意 or 拒绝
         */
        void refreshMessage(TakeoutMsgDetailResponse takeoutMsgDetailResponse, int position, boolean isAgree, String messageId);
    }
}
