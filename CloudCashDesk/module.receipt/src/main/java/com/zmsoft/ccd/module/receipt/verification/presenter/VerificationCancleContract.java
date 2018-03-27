package com.zmsoft.ccd.module.receipt.verification.presenter;


import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.receipt.bean.VerificationResponse;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface VerificationCancleContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 云收银收款成功
         */
        void successCollectPay(VerificationResponse verificationResponse);

        /**
         * 云收银收款失败
         */
        void failCollectPay(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        /**
         * 云收银收款
         */
        void collectPay(String orderId, String promotionCode);
    }
}
