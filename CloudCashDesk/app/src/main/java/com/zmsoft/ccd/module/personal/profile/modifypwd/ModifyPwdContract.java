package com.zmsoft.ccd.module.personal.profile.modifypwd;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * @author DangGui
 * @create 2016/12/16.
 */

public interface ModifyPwdContract {
    interface View extends BaseView<Presenter> {
        void finishView();

        void modifySuccess(String successMessage);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void modifyPwdTask(String oldPwd, String newPwd);
    }
}
