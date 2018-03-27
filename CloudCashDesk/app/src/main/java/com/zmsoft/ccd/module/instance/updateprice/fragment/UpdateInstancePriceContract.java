package com.zmsoft.ccd.module.instance.updateprice.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.instance.op.updateprice.UpdateInstancePrice;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/11 15:51
 */
public class UpdateInstancePriceContract {

    public interface Presenter extends BasePresenter {
        /**
         * 更新菜肴价格
         *
         * @param entityId   实体id
         * @param instanceId 菜肴id
         * @param opUserId   用户id
         * @param modifyTime 修改时间
         * @param fee        修改价格
         */
        void updateInstancePrice(String entityId, String instanceId, String opUserId, long modifyTime, int fee);
    }

    public interface View extends BaseView<Presenter> {

        void updateInstancePriceSuccess(UpdateInstancePrice updateInstancePrice);

        void loadDataError(String errorMessage);

    }

}
