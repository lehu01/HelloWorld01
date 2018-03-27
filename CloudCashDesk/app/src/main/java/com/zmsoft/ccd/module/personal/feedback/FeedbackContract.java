package com.zmsoft.ccd.module.personal.feedback;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * @author DangGui
 * @create 2016/12/16.
 */

public interface FeedbackContract {
    interface View extends BaseView<Presenter> {
        void finishView();

        /**
         * 发送成功
         *
         * @param successMessage
         */
        void sendSuccess(String successMessage);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void feedBackTask(String feedBackContent, String email);
    }
}
