package com.zmsoft.ccd.module.mistake;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Created by jihuo on 2016/12/26.
 *
 *异常消息接口页
 */

public class MistakeMsgContract {

    interface View extends BaseView<Presenter>{
        void getMistakeMsgSuccess();
    }

    interface Presenter extends BasePresenter{
        void getMistakeMsg();
    }
}
