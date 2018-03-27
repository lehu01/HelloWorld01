package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.menubalance.MenuBalanceVO;

import java.util.List;

/**
 * Created by jihuo on 2016/12/22.
 */

public interface MenuBalanceContract {

    interface View extends BaseView<Presenter> {

        void getMenuBalanceListSuccess(List<MenuBalanceVO> mList);

        void getMenuBalanceListFailure(String errorMsg);

        void clearAllMenuBalanceSuccess(int effectCount);

        void clearAllMenuBalanceFailure(String errorMsg);

        void checkPermissionSuccess(String actionCode, boolean hasPermission, int requestSource);

        void noPermission(String errorMsg);
    }

    interface Presenter extends BasePresenter {

        void getMenuBalanceList(String entityId, Integer pageSize, Integer pageIndex);

        void clearAllMenuBalance(String entityId, List<String> menuIdList, String opUserId);

    }
}
