package com.zmsoft.ccd.module.shortcutreceipt;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.shortcutreceipt.ShortCutReceiptRepository;
import com.zmsoft.ccd.lib.bean.shortcutreceipt.ShortCutReceiptResponse;

import javax.inject.Inject;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @author DangGui
 * @create 2017/8/2.
 */

public class RetailShortCutReceiptPresenter implements RetailShortCutReceiptContract.Presenter {
    /**
     * 零售单数量超过100单的错误码
     */
    public static final String ERROR_CODE_ORDER_NUM_LIMIT = "1701008";

    private RetailShortCutReceiptContract.View mView;
    private final ShortCutReceiptRepository mShortCutReceiptRepository;

    @Inject
    RetailShortCutReceiptPresenter(RetailShortCutReceiptContract.View view, ShortCutReceiptRepository shortCutReceiptRepository) {
        this.mView = view;
        this.mShortCutReceiptRepository = shortCutReceiptRepository;
    }

    /**
     * 加注解确保改方法在实例化之后调用
     */
    @Inject
    void setupPresenterForView() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void shortCutReceipt(int fee) {
        mView.showLoading(context.getString(R.string.dialog_waiting), false);
        mShortCutReceiptRepository.shortCutReceipt(fee, new Callback<ShortCutReceiptResponse>() {
            @Override
            public void onSuccess(ShortCutReceiptResponse data) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                if (null != data) {
                    mView.successReceipt(data);
                } else {
                    mView.failReceipt(context.getString(R.string.server_no_data));
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mView) {
                    return;
                }
                mView.hideLoading();
                mView.failReceipt(body.getMessage());
                if (body.getErrorCode().equals(ERROR_CODE_ORDER_NUM_LIMIT)) {
                    mView.showFailReceiptDialog(body.getMessage());
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }
}
