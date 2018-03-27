package com.zmsoft.ccd.module.main.order.bill;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListRequest;
import com.zmsoft.ccd.lib.bean.order.GetBillDetailListResponse;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromRequest;
import com.zmsoft.ccd.lib.bean.order.RetailOrderFromResponse;

/**
 * Created by huaixi on 2017/11/01.
 */

public class RetailBillDetailContract {

    public interface Presenter extends BasePresenter {

        void getRetailOrderFrom(RetailOrderFromRequest retailOrderFromRequest);

        void getBillDetailList(GetBillDetailListRequest request);
    }

    public interface View extends BaseView<RetailBillDetailContract.Presenter> {

        void loadRetailOrderFromSuccess(RetailOrderFromResponse retailOrderFromResponse);

        void loadBillDetailListSuccess(GetBillDetailListResponse response);

        void loadBillDetailListError(String errorMessage);

        void loadDataError(String errorMessage);
    }
}
