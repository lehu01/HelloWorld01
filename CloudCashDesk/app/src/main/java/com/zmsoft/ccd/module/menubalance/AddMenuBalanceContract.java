package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;

/**
 * Created by jihuo on 2016/12/23.
 */

public interface AddMenuBalanceContract {

    interface View extends BaseView<Presenter>{
        void addMenuBalanceSuccess();

        void addMenuBalanceFailure();
    }

    interface Presenter extends BasePresenter{
        void addMenuBalance(String entityId, String menuId, double num, String opUser);
    }
}
