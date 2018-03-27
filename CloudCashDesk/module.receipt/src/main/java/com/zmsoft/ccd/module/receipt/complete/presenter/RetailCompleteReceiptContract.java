package com.zmsoft.ccd.module.receipt.complete.presenter;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.receipt.bean.OrderPayListResponse;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface RetailCompleteReceiptContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        void successGetPayList(OrderPayListResponse orderPayListResponse);

        void failGetPayList(String failMessage);

        /**
         * 结账完毕成功
         */
        void successCompletePay();
    }

    interface Presenter extends BasePresenter {
        /**
         * 结账完毕
         *
         * @param modifyTime
         */
        void afterEndPayForRetail(String entityId, String operatorId, String orderId, long modifyTime);

        void getOrderPayList(String orderId);
    }
}
