package com.zmsoft.ccd.module.shortcutreceipt;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;

/**
 * @author DangGui
 * @create 2017/8/2.
 */

public interface RetailShortCutReceiptContract {
    interface View extends BaseView<Presenter> {
        /**
         * 网络加载失败，显示失败页，利于用户体验 eg. 服务端异常、网络连接失败等
         *
         * @param errorMessage
         */
        void loadDataError(String errorMessage);

        /**
         * 成功收款
         */
        void successReceipt(ShortCutReceiptResponse shortCutReceiptResponse);

        /**
         * 收款失败
         */
        void failReceipt(String errorMessage);

        /**
         * 开单数量超过100单，弹窗提示
         */
        void showFailReceiptDialog(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void shortCutReceipt(int fee);
    }
}
