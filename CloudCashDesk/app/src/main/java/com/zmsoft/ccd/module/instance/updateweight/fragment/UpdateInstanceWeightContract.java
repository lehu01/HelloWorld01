package com.zmsoft.ccd.module.instance.updateweight.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.instance.op.updateweight.UpdateInstanceWeight;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/12 14:19
 */
public class UpdateInstanceWeightContract {

    public interface Presenter extends BasePresenter {
        /**
         * 更改订单内单个菜肴重量
         *
         * @param entityId   实体id
         * @param instanceId 菜肴id
         * @param opUserId   操作用户id
         * @param modifyTme  修改时间
         * @param weight     修改重量
         */
        void updateInstanceWeight(String entityId, String instanceId, String opUserId, long modifyTme, double weight);
    }

    public interface View extends BaseView<Presenter> {

        void updateInstanceWeightSuccess(UpdateInstanceWeight updateInstanceWeight);

        void loadDataError(String errorMessage);

    }

}
