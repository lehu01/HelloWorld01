package com.zmsoft.ccd.module.receipt.discount.presenter;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.receipt.bean.CashPromotionBillResponse;
import com.zmsoft.ccd.receipt.bean.Promotion;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface DiscountReceiptContract {
    interface View extends BaseView<Presenter> {

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功获取打折原因列表
         *
         * @param reasonList
         */
        void successGetReasonList(List<Reason> reasonList);

        /**
         * 获取打折原因列表失败
         *
         * @param errorBody
         */
        void failGetReasonList(ErrorBody errorBody);

        /**
         * 整单打折确认结果
         *
         * @param cashPromotionBillResponse
         */
        void successPromoteBill(CashPromotionBillResponse cashPromotionBillResponse);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取打折原因
         *
         * @param entityId
         * @param dicCode
         * @param systemType
         */
        void getReasonList(String entityId, String dicCode, int systemType);

        /**
         * 账单优惠（整单打折，卡优惠）
         */
        void promoteBill(String orderId, List<Promotion> promotionList);
    }
}
