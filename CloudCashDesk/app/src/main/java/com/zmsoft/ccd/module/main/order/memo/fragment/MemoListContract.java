package com.zmsoft.ccd.module.main.order.memo.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.remark.Memo;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:33
 */
public class MemoListContract {

    public interface Presenter extends BasePresenter {

        /**
         * 获取备注列表
         *
         * @param entityId   实体id
         * @param dicCode    字典
         * @param systemType 系统类型
         */
        void getMemoList(String entityId, String dicCode, int systemType);

    }

    public interface View extends BaseView<Presenter> {

        void showStateErrorView(String errorMessage);

        void refreshRemarkList(List<Memo> list);

        void loadDataError(String errorMessage);

    }
}
