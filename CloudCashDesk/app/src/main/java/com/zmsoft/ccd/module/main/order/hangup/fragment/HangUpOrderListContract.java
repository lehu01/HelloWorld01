package com.zmsoft.ccd.module.main.order.hangup.fragment;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.order.hangup.HangUpOrder;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 11:24
 *     desc  : contract
 * </pre>
 */
public class HangUpOrderListContract {

    public interface Presenter extends BasePresenter {

        // 获取挂单列表
        void getHangUpOrderList(String entityId);

    }

    public interface View extends BaseView<Presenter> {

        // 获取挂单列表成功
        void getHangUpOrderListSuccess(List<HangUpOrder> data);

        // 网络加载失败
        void showLoadErrorView(String errorMessage);

    }

}
