package com.zmsoft.ccd.module.main.order.search;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/28 19:40.
 */

public class OrderSeatSearchContract {

    public interface Presenter extends BasePresenter {
        void orderSearch(String keyWord);
        void seatSearch(String keyWord);
    }

    public interface View extends BaseView<OrderSeatSearchContract.Presenter> {
        void loadSearchDataSuccess(List<OrderFindItem> orderFindItems);
        void loadSearchDataError(String errorMessage, boolean showNetErrorView);
    }
}
