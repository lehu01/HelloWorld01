package com.zmsoft.ccd.module.takeout.order.presenter;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.takeout.bean.Config;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class TakeoutListActivityContract {


    public interface View extends BaseView {
        void getOrderStatusListSuccess(List<Config> configs);

        void getOrderStatusListFailed(String errorCode,String errorMsg);
    }


    public interface Presenter extends BasePresenter {
        void getOrderStatusList(OrderStatusRequest orderStatusRequest);
    }
}
