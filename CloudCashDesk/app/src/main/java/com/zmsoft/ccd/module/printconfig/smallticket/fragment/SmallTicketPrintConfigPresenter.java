package com.zmsoft.ccd.module.printconfig.smallticket.fragment;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.source.print.PrintConfigSourceRepository;
import com.zmsoft.ccd.lib.utils.IpUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.network.ErrorBizHttpCode;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
public class SmallTicketPrintConfigPresenter implements SmallTicketPrintConfigContract.Presenter {

    private SmallTicketPrintConfigContract.View mView;
    private final PrintConfigSourceRepository mRepository;

    @Inject
    public SmallTicketPrintConfigPresenter(SmallTicketPrintConfigContract.View view, PrintConfigSourceRepository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Inject
    void setPresenter() {
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
    public void savePrintConfig(String entityId, String userId, int printerType, String ip, int rowCharMaxMun, Short isLocalCashPrint, Short type) {
        mView.showLoading(GlobalVars.context.getString(R.string.saving), true);
        mRepository.savePrintConfig(entityId, userId, printerType, ip, rowCharMaxMun, isLocalCashPrint, type, new Callback<SmallTicketPrinterConfig>() {
            @Override
            public void onSuccess(SmallTicketPrinterConfig data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.savePrintConfigSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                if (ErrorBizHttpCode.CLOUDCASH_1061.equals(body.getErrorCode())) {
                    mView.showKnowDialog(body.getMessage());
                } else {
                    mView.loadDataError(body.getMessage());
                }
            }
        });
    }

    @Override
    public void getPrintConfig(String entityId, String userId) {
        mRepository.getPrintConfig(entityId, userId, new Callback<SmallTicketPrinterConfig>() {
            @Override
            public void onSuccess(SmallTicketPrinterConfig data) {
                if (mView == null) {
                    return;
                }
                mView.getPrintConfigSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.showLoadDataErrorView(body.getMessage());
            }
        });
    }

    @Override
    public boolean check(int type, String ip, String blueToothName, String byteCount) {
        if (type == PrintConfigConstant.PrinterType.PRINT_TYPE_NET) {
            if (StringUtils.isEmpty(ip)) {
                mView.loadDataError(GlobalVars.context.getString(R.string.print_ip_is_null));
                return false;
            }
            if (!IpUtils.ipCheck(ip)) {
                mView.loadDataError(GlobalVars.context.getString(R.string.print_ip_is_not_ok));
                return false;
            }
        } else if (type == PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH) {
            String blueBooth = blueToothName;
            if (StringUtils.isEmpty(blueBooth) || GlobalVars.context.getString(R.string.please_select).equals(blueBooth)) {
                mView.loadDataError(GlobalVars.context.getString(R.string.please_select_bluetooth_print));
                return false;
            }
        }
        if (StringUtils.isEmpty(byteCount)) {
            mView.loadDataError(GlobalVars.context.getString(R.string.please_set_print_byte_count));
            return false;
        }
        return true;
    }
}
