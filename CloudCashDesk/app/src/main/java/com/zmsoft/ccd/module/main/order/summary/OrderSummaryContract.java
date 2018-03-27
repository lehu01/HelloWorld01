package com.zmsoft.ccd.module.main.order.summary;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;

import java.util.List;


/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 09:55.
 */

public class OrderSummaryContract {

    public interface Presenter extends BasePresenter {
        void loadOrderSummaryData(String startDate, String endDate);
    }

    public interface View extends BaseView<Presenter> {
        void loadOrderCompleteDataSuccess(List<BillSummaryVo> billSummaryVo);
        void loadOrderCompleteDataError(String errorMessage);
    }
}
