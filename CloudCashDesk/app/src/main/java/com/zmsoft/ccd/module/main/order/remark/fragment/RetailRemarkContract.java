package com.zmsoft.ccd.module.main.order.remark.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.remark.Memo;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:33
 */
public class RetailRemarkContract {

    public interface Presenter extends BasePresenter {

        /**
         * 获取备注列表
         *
         * @param entityId   实体id
         * @param dicCode    字典
         * @param systemType 系统类型
         */
        void getRemarkList(String entityId, String dicCode, int systemType);

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
    }

    public interface View extends BaseView<Presenter> {
        void showStateErrorView(String message);

        void refreshRemarkList(List<Memo> list);

        void loadDataError(String errorMessage);

        // 改单成功 - 购物车
        void changeOrderSuccessByShopCar();
    }
}
