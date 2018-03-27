package com.zmsoft.ccd.module.main.order.cancel.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.op.CancelOrder;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/17 16:03
 */
public class RetailCancelOrderContract {

    public interface Presenter extends BasePresenter {

        /**
         * 撤单
         *
         * @param entityId   实体id
         * @param orderId    订单id
         * @param opUserId   操作员id，用户id
         * @param modifyTime 修改时间
         * @param reason     撤单原因
         */
        void cancelOrder(String entityId, String orderId, String opUserId, long modifyTime, String reason);


        /**
         * 获取撤单原因
         *
         * @param entityId   实体id
         * @param dicCode    字典
         * @param systemType 系统类型
         */
        void getReasonList(String entityId, String dicCode, int systemType);
    }

    public interface View extends BaseView<Presenter> {

        // 网络失败提示
        void loadDataError(String errorMessage);

        // 改单成功
        void cancelOrderSuccess(CancelOrder cancelOrder);

        // 撤单原因列表
        void cancelOrderReasonListSuccess(List<Reason> list);

    }
}
