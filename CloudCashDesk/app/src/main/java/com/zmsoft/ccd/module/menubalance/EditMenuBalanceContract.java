package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Created by jihuo on 2016/12/23.
 */

public interface EditMenuBalanceContract {

    interface View extends BaseView<Present>{
        void cancelMenuBalanceSuccess();

        void cancelMenuBalanceFailure();

        void updateMenuBalanceSuccess();

        void updateMenuBalanceFailure();
    }

    interface Present extends BasePresenter{
        void cancelMenuBalance(String entityId, String menuId, String opUserId);

        void updateMenuBalance(String entityId, String menuId, double num, String opUser);
    }
}
