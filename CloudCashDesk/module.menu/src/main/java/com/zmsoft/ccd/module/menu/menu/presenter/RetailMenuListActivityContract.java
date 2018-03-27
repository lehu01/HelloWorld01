package com.zmsoft.ccd.module.menu.menu.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Description：MenuListActivityContract
 * <br/>
 * Created by kumu on 2017/5/5.
 */

public interface RetailMenuListActivityContract {


    interface Presenter extends BasePresenter {
        void checkCustomFoodPermission();
    }

    interface View extends BaseView<Presenter> {
        void checkCustomFoodPermissionSuccess(boolean has);

        void checkCustomFoodPermissionFailed(String errorCode, String errorMsg);
    }


}
