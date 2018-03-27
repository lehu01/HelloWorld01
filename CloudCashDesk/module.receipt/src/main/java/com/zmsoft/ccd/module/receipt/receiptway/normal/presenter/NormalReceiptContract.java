package com.zmsoft.ccd.module.receipt.receiptway.normal.presenter;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetKindDetailInfoResponse;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface NormalReceiptContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功获取支付选项信息
         */
        void successGetKindDetailInfo(GetKindDetailInfoResponse getKindDetailInfoResponse);

        /**
         * 获取支付选项信息失败
         *
         * @param errorBody
         */
        void failGetKindDetailInfo(ErrorBody errorBody);


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
         * 获取支付选项信息
         */
        void getKindDetailInfo(String name, int mode, String kindPayId);

        /**
         * 云收银收款
         */
        void collectPay(String orderId, String payName, int orderCode, String seatCode, String seatName, List<Fund> fundList);
    }
}
