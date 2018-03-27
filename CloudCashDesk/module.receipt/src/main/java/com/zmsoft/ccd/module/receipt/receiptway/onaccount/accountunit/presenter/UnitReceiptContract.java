package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.presenter;


import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.receipt.bean.GetSignBillSingerResponse;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;

import java.util.List;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public interface UnitReceiptContract {
    interface View extends BaseView<Presenter> {
        /**
         * 刷新界面
         */
        void notifyDataChange();

        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功获取挂账单位列表
         *
         * @param getSignBillSingerResponse
         */
        void successGetSignInfo(GetSignBillSingerResponse getSignBillSingerResponse);

        /**
         * 调用接口获取数据失败
         */
        void failGetData(ErrorBody errorBody);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取挂账单位（人）列表
         *
         * @param kindPayId
         */
        void getSignUnit(String kindPayId, String keyword, int pageSize, int pageIndex);

        void checkUnitItem(List<UnitRecyclerItem> unitRecyclerItemList, UnitRecyclerItem checkedUnitItem);
    }
}
