package com.zmsoft.ccd.module.instance.cancel.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.instance.op.cancelorgiveinstance.CancelOrGiveInstance;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 19:21
 */
public class CancelOrGiveInstanceContract {

    public interface Presenter extends BasePresenter {
        /**
         * 退菜
         *
         * @param entityId   实体id
         * @param opUserId   操作用户id
         * @param instanceId 菜肴id
         * @param reason     退菜原因
         * @param modifyTime 操作时间opTime:Instance内时间
         * @param num        退菜数量
         * @param accountNum 退菜数量（非双单位，值和num一样）
         */
        void cancelInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime, double num, double accountNum);

        /**
         * 赠送菜肴
         *
         * @param entityId   实体id
         * @param opUserId   操作用户id
         * @param instanceId 菜肴id
         * @param reason     赠菜原因
         * @param modifyTime 操作时间opTime:Instance内时间
         * @param num        退菜数量
         * @param accountNum 退菜数量（非双单位，值和num一样）
         */
        void giveInstance(String entityId, String opUserId, String instanceId, String reason, long modifyTime, double num, double accountNum);

        /**
         * 获取反结账/赠菜/退菜/打折/撤单原因列表
         *
         * @param entityId   实体id
         * @param dicCode    字典类型
         * @param systemType 字典类型值
         */
        void getReasonList(String entityId, String dicCode, int systemType);
    }

    public interface View extends BaseView<Presenter> {

        void cancelInstanceSuccess(CancelOrGiveInstance cancelOrGiveInstance);

        void giveInstanceSuccess(CancelOrGiveInstance cancelOrGiveInstance);

        void getReasonListSuccess(List<Reason> list);

        void loadDataError(String errorMessage);

    }
}
