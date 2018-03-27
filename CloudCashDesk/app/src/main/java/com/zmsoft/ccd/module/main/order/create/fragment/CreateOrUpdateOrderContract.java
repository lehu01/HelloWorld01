package com.zmsoft.ccd.module.main.order.create.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.OpenOrderVo;
import com.zmsoft.ccd.lib.bean.order.op.ChangeOrderByTrade;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.table.SeatStatus;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/1 15:18
 */
public class CreateOrUpdateOrderContract {

    public interface Presenter extends BasePresenter {

        /**
         * 现金收款限额
         *
         * @param entityId
         * @param userId
         * @return
         */
        void getCashLimit(String entityId, String userId);

        /**
         * 改单[订单中心改单]
         *
         * @param entityId    实体id
         * @param memo        备注
         * @param peopleCount 就餐人数
         * @param newSeatCode 新的的座位code
         * @param opUserId    操作员id
         * @param orderId     订单id
         * @param isWait      是否待上菜
         * @param opType      操作类型
         * @param modifyTime  修改时间
         */
        void changeOrderByTrade(String entityId, String memo, int peopleCount, String newSeatCode, String opUserId, String orderId, int isWait, int opType, long modifyTime);

        /**
         * 改单[小二改单]
         *
         * @param entityId    实体id
         * @param userId      用户id/操作
         * @param oldSeatCode 老的seatCode
         * @param newSeatCode 新的seatCode
         * @param peopleCount 就餐人数
         * @param memo        备注
         * @param isWait      是否带菜
         */
        void changeOrderByShopCar(String entityId, String userId, String oldSeatCode, String newSeatCode, int peopleCount, String memo, boolean isWait);

        /**
         * 根据座位code获取座位状态
         *
         * @param entityId 实体id
         * @param seatCode 座位code
         */
        void getSeatStatusBySeatCode(String entityId, String seatCode);

        /**
         * 开单
         *
         * @param entityId    实体id
         * @param userId      用户id
         * @param seatCode    座位code
         * @param peopleCount 就餐人数
         * @param memo        备注
         * @param isWait      是否待菜
         */
        void createOrder(String entityId, String userId, String seatCode, int peopleCount, String memo, boolean isWait);
    }

    public interface View extends BaseView<Presenter> {

        // 获取座位名称
        String getSeatName();

        // 获取现金收款金额失败
        void getCashLimitFailed(String errorCode, String errorMsg);

        // 获取现金收款金额
        void getCashLimitSuccess(CashLimit cashLimit);

        // 获取就餐人数
        String getPeople();

        // 网络失败提示
        void loadDataError(String errorMessage);

        // 改单成功
        void changeOrderSuccess(ChangeOrderByTrade changeOrder);

        // 改单成功 - 购物车
        void changeOrderSuccessByShopCar();

        // 检测是否开单
        void getSeatStatusSuccess(SeatStatus seatStatus);

        // 开单成功
        void createOrderSuccess(OpenOrderVo data);

    }

}
