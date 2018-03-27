package com.zmsoft.ccd.module.electronic.detail;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentResponse;
import com.zmsoft.ccd.receipt.bean.Refund;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 16:18
 */
public class ElectronicDetailContract {

    public interface Presenter extends BasePresenter {
        void getElePaymentDetail(String payId);

        /**
         * 云收银退款
         *
         * @param orderId
         * @param refundList
         */
        void refund(String payId, String orderId, List<Refund> refundList);
    }

    public interface View extends BaseView<ElectronicDetailContract.Presenter> {
        /**
         * 数据请求成功
         */
        void successGetElePayment(GetElePaymentResponse getElePaymentResponse);

        /**
         * 数据请求失败
         */
        void failGetElePayment(String errorMsg);

        /**
         * 退款成功
         */
        void successRefund();

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

}
