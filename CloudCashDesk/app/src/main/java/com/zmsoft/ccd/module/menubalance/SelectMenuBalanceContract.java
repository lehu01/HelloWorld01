package com.zmsoft.ccd.module.menubalance;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.menubalance.MenuVO;

import java.util.List;

/**
 * Created by jihuo on 2016/12/23.
 */

public interface SelectMenuBalanceContract {

    interface View extends BaseView<Presenter>{
        void getAllMenuSuccess(List<MenuVO> menuVOList);

        void getAllMenuFailure(String errorMsg);
    }

    interface Presenter extends BasePresenter{
        void getAllMenu(String entityId);
    }
}
