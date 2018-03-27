package com.zmsoft.ccd.module.takeout.delivery.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.takeout.bean.DeliverTakeoutResponse;
import com.zmsoft.ccd.takeout.bean.GetDeliveryTypeResponse;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/8/18.
 */
public class DeliveryContract {

    public interface Presenter extends BasePresenter {
        void getCourierList(List<String> orderIds);

        void deliveryTakeout(List<String> orderIds, short type, String platformCode, String carrierName, String carrierPhone
                , String expressCode);
    }

    public interface View extends BaseView<Presenter> {

        /**
         * 获取配送方式成功
         */
        void successGetDeliveryType(GetDeliveryTypeResponse response);

        /**
         * 获取配送方式失败
         */
        void faileGetDeliveryType(String errorMsg);

        /**
         * 配送外卖单请求成功
         */
        void successDeliveryTakeout(DeliverTakeoutResponse response);

        /**
         * 配送外卖单请求失败
         */
        void faileDeliveryTakeout(String errorMsg);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

}
