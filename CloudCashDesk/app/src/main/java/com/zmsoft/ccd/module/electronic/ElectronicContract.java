package com.zmsoft.ccd.module.electronic;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.electronic.GetElePaymentListResponse;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 16:18
 */
public class ElectronicContract {

    public interface Presenter extends BasePresenter {

        void getElePaymentList(int pageIndex, int pageSize);
    }

    public interface View extends BaseView<ElectronicContract.Presenter> {
        /**
         * 数据请求成功
         */
        void getElectronicListSuccess(GetElePaymentListResponse getElePaymentListResponse);

        void getElectronicListFail(String errorMsg);

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);
    }

}
