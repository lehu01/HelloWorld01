package com.zmsoft.ccd.module.receipt.receiptway.coupon.presenter;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.GetVoucherInfoResponse;
import com.zmsoft.ccd.receipt.bean.VoucherFund;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface CouponReceiptContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功获取面额列表
         *
         * @param getVoucherInfoResponse
         */
        void successGetVoucherInfo(GetVoucherInfoResponse getVoucherInfoResponse);

        /**
         * 调用接口获取数据失败
         */
        void failGetData(ErrorBody errorBody);

        /**
         * 支付结果
         *
         * @param cloudCashCollectPayResponse
         */
        void successCollectPay(CloudCashCollectPayResponse cloudCashCollectPayResponse);

        /**
         * 云收银收款失败
         */
        void failCollectPay(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取代金券面额列表
         *
         * @param kindPayId
         */
        void getVoucherInfo(String kindPayId);

        /**
         * 云收银收款
         */
        void collectPay(String orderId, List<VoucherFund> fundList);
    }
}
