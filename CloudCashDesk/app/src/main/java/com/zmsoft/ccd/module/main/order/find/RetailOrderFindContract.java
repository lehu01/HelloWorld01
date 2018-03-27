package com.zmsoft.ccd.module.main.order.find;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.FocusOrderRequest;
import com.zmsoft.ccd.lib.bean.order.OrderListResult;

/**
 * Created by huaixi on 2017/10/26.
 */

public class RetailOrderFindContract {

    final static public long NETWORK_LAG_THRESHOLD_MILLISECOND = 1000L;

    public interface Presenter extends BasePresenter {
        void loadOrderSource(FocusOrderRequest focusOrderRequest);
    }

    public interface View extends BaseView<RetailOrderFindContract.Presenter> {
        void loadOrderFindDataSuccess(OrderListResult orderListResult);  // 获取所有数据成功
        void loadOrderFindDataError(String errorMessage, boolean showNetErrorView);
    }
}
