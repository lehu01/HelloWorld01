package com.zmsoft.ccd.module.scan.findorder;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;

/**
 * Description：扫码找单
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:39
 */
public class ScanFindOrderContract {

    /**
     * Presenter提供
     */
    public interface Presenter extends BasePresenter {

        /**
         * 获取订单详情（根据座位编码）
         *
         * @param entityId   实体id
         * @param seatCode   座位code
         * @param customerId 用户id
         */
        void getOrderDetailBySeatCode(String entityId, String seatCode, String customerId);

        /**
         * 根据座位code获取座位
         */
        void getSeatBySeatCode(String entityId, String seatCode);
    }

    /**
     * View操作
     */
    public interface View extends BaseView<Presenter> {

        // 获取订单详情成功
        void loadOrderDetailSuccess(OrderDetail orderDetail);

        // 获取数据失败
        void loadDataError(String errorMessage);

        // 获取桌位
        void loadSeatBySeatCodeSuccess(com.zmsoft.ccd.lib.bean.desk.Seat seat);

    }
}
