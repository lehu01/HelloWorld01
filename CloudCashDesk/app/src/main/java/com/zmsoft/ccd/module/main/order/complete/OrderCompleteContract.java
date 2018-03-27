package com.zmsoft.ccd.module.main.order.complete;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/27 10:52.
 */

public class OrderCompleteContract {

    public interface Presenter extends BasePresenter {
        void loadOrderCompleteData(String date);
    }

    public interface View extends BaseView<Presenter> {
        void loadOrderCompleteDataSuccess(CompleteBillVo billSummaryVo);
        void loadOrderFindDataError(String errorMessage);
    }
}
