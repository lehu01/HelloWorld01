package com.zmsoft.ccd.module.work.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/28 11:14
 */
public class GotoWorkContract {

    public interface View extends BaseView<Presenter> {

        // toast
        void showToastMessage(String message);

    }

    interface Presenter extends BasePresenter {


    }
}
