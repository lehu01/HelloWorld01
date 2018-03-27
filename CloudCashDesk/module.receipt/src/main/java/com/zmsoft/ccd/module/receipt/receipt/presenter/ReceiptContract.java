package com.zmsoft.ccd.module.receipt.receipt.presenter;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptMethodRecyclerItem;
import com.zmsoft.ccd.receipt.bean.CloudCashCollectPayResponse;
import com.zmsoft.ccd.receipt.bean.Fund;
import com.zmsoft.ccd.receipt.bean.GetCloudCashBillResponse;
import com.zmsoft.ccd.receipt.bean.Refund;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface ReceiptContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功刷新收款界面
         *
         * @param getCloudCashBillResponse
         */
        void successShowCloudCash(GetCloudCashBillResponse getCloudCashBillResponse);

        /**
         * 刷新收款界面失败
         *
         * @param errorBody
         */
        void failShowCloudCash(ErrorBody errorBody);

        /**
         * 结账完毕成功
         */
        void successCompletePay();

        void getCashLimitSuccess(CashLimit cashLimit, ReceiptMethodRecyclerItem mMethodRecyclerItem
                , double fee, double needFee);

        void getCashLimitFailed(String errorCode, String errorMsg);

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
         * 云收银获取账单
         *
         * @param orderId
         */
        void getCloudCash(String orderId);

        /**
         * 云收银清空折扣
         *
         * @param orderId
         */
        void emptyDiscount(String orderId);

        /**
         * 云收银退款
         *
         * @param orderId
         * @param refundList
         */
        void refund(String orderId, List<Refund> refundList);

        /**
         * 结账完毕
         *
         * @param modifyTime
         */
        void afterEndPay(String entityId, String operatorId, String orderId, long modifyTime);

        /**
         * 批量获取权限
         */
        void batchCheckPermisson(int systemType, List<String> actionCodeList);

        void getCashLimit(String entityId, String UserId, ReceiptMethodRecyclerItem mMethodRecyclerItem
                , double fee, double needFee);

        /**
         * 云收银收款
         */
        void collectPay(String orderId, List<Fund> fundList);
    }
}
