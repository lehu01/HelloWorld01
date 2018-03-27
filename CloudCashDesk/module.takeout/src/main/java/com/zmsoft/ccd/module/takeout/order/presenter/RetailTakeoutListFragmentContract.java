package com.zmsoft.ccd.module.takeout.order.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderRequest;
import com.zmsoft.ccd.takeout.bean.CancelTakeoutOrderResponse;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoRequest;
import com.zmsoft.ccd.takeout.bean.DeliveryInfoResponse;
import com.zmsoft.ccd.takeout.bean.OperateOrderRequest;
import com.zmsoft.ccd.takeout.bean.OperateOrderResponse;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderListResponse;
import com.zmsoft.ccd.takeout.bean.SearchOrderRequest;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class RetailTakeoutListFragmentContract {


    public interface View extends BaseView {
        void getOrderListSuccess(OrderListResponse response);

        void getOrderListFailed(String errorCode, String errorMsg);

        void cancelOrderSuccess(CancelTakeoutOrderResponse response);

        void cancelOrderFailed(String errorCode, String errorMsg);

        void changeOrderStatusSuccess(OperateOrderResponse response);

        void changeOrderStatusFailed(String errorCode, String errorMsg);

        void getDeliveryInfoSuccess(DeliveryInfoResponse response);

        void getDeliveryInfoFailed(String errorCode, String errorMsg);

        void searchOrderSuccess(OrderListResponse response);

        void searchOrderFailed(String errorCode, String errorMsg);


        void getCancelReasonSuccess(List<Reason> reasons);

        void getCancelReasonFailed(String errorCode, String errorMsg);




    }


    public interface Presenter extends BasePresenter {
        void getOrderList(OrderListRequest orderStatusRequest);

        void getCancelReason();

        void cancelOrder(CancelTakeoutOrderRequest request);

        void changeOrderStatus(OperateOrderRequest request);

        void getDeliveryInfo(DeliveryInfoRequest request);

        void searchOrder(SearchOrderRequest searchOrderRequest);



    }
}
