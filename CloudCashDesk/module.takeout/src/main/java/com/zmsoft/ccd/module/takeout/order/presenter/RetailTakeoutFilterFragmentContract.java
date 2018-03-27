package com.zmsoft.ccd.module.takeout.order.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.takeout.bean.FilterConditionResponse;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/22.
 */

public interface RetailTakeoutFilterFragmentContract {

    public interface View extends BaseView {
        public void getFilterConfigsSuccess(FilterConditionResponse response);

        public void getFilterConfigsFailed(String errorCode, String errorMsg);
    }


    public interface Presenter extends BasePresenter {
        public void getFilterConfigs(OrderStatusRequest requestOrderStatus);
    }


}
