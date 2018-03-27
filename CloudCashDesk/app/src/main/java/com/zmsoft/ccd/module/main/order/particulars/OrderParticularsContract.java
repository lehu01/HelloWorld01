package com.zmsoft.ccd.module.main.order.particulars;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsItem;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 16:03.
 */

public class OrderParticularsContract {

    public interface Presenter extends BasePresenter {
        void loadOrderParticularsData(String orderCode, String sourceCode, String cashierCode, String dateCode, Integer pageIndex);
    }

    public interface View extends BaseView<Presenter> {
        void loadOrderFindDataSuccess(List<OrderParticularsItem> orderParticularsItemList, boolean isFirstPage);
        void loadOrderFindDataError(String errorMessage);
    }
}
