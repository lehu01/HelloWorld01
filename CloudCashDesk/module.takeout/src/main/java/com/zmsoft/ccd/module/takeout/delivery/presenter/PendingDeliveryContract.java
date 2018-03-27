package com.zmsoft.ccd.module.takeout.delivery.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.takeout.bean.GetDeliveryOrderListResponse;

/**
 * @author DangGui
 * @create 2017/8/18.
 */
public class PendingDeliveryContract {

    public interface Presenter extends BasePresenter {
        void getDeliveryOrderList(int pageIndex, int pageSize);
    }

    public interface View extends BaseView<Presenter> {
        /**
         * 数据请求成功
         */
        void successGetDeliveryOrderList(GetDeliveryOrderListResponse response);

        /**
         * 数据请求失败
         */
        void faileGetDeliveryOrderList(String errorMsg);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

}
