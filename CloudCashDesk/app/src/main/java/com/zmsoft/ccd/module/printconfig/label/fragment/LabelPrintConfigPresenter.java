package com.zmsoft.ccd.module.printconfig.label.fragment;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.print.PrintConfigSourceRepository;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.SwitchRequest;
import com.zmsoft.ccd.lib.utils.IpUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 10:45
 *     desc  :
 * </pre>
 */
public class LabelPrintConfigPresenter implements LabelPrintConfigContract.Presenter {

    private LabelPrintConfigContract.View mView;
    private final PrintConfigSourceRepository mRepository;
    private final ICommonSource mICommonSource;

    @Inject
    public LabelPrintConfigPresenter(LabelPrintConfigContract.View view, PrintConfigSourceRepository repository) {
        this.mView = view;
        this.mRepository = repository;
        mICommonSource = new CommonRemoteSource();
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
    public void saveFunctionSwitchList(String entityId, List<SwitchRequest> codeList) {
        mView.showLoading(false);
        mICommonSource.saveFunctionSwitchList(entityId, codeList)
                .retryWhen(new RetryWithDelay(3, 500))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (mView == null) {
                            return;
                        }
                        mView.hideLoading();
                        if (aBoolean) {
                            mView.saveLabelSwitchSuccess();
                        } else {
                            mView.showErrorToast(GlobalVars.context.getString(R.string.save_label_switch_failure));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (null == mView) {
                            return;
                        }
                        mView.hideLoading();
                        if (throwable instanceof ServerException) {
                            ServerException serverException = (ServerException) throwable;
                            mView.showErrorToast(serverException.getMessage());
                        }
                    }
                });
    }

    @Override
    public void saveLabelPrintConfig(String entityId, String userId, int printerType, String ip) {
        mView.showLoading(false);
        mRepository.saveLabelPrintConfig(entityId, userId, printerType, ip, new Callback<LabelPrinterConfig>() {
            @Override
            public void onSuccess(LabelPrinterConfig data) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.saveLabelPrintConfigSuccess();
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.hideLoading();
                mView.showErrorToast(body.getMessage());
            }
        });
    }

    @Override
    public boolean check(int type, String ip, String blueToothName) {
        if (type == PrintConfigConstant.PrinterType.PRINT_TYPE_NET) {
            if (StringUtils.isEmpty(ip)) {
                mView.showErrorToast(GlobalVars.context.getString(R.string.print_ip_is_null));
                return false;
            }
            if (!IpUtils.ipCheck(ip)) {
                mView.showErrorToast(GlobalVars.context.getString(R.string.print_ip_is_not_ok));
                return false;
            }
        } else if (type == PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH) {
            String blueBooth = blueToothName;
            if (StringUtils.isEmpty(blueBooth) || GlobalVars.context.getString(R.string.please_select).equals(blueBooth)) {
                mView.showErrorToast(GlobalVars.context.getString(R.string.please_select_bluetooth_print));
                return false;
            }
        }
        return true;
    }

    @Override
    public void getLabelPrintConfig(String entityId, String userId) {
        mRepository.getLabelPrintConfig(entityId, userId, new Callback<LabelPrinterConfig>() {
            @Override
            public void onSuccess(LabelPrinterConfig data) {
                if (mView == null) {
                    return;
                }
                mView.getLabelPrintConfigSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mView == null) {
                    return;
                }
                mView.showErrorLoadView(body.getMessage());
            }
        });
    }
}
