package com.zmsoft.ccd.module.main;


import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.manager.LabelPrintManager;
import com.ccd.lib.print.manager.SmallTicketPrinterManager;
import com.dfire.mobile.cashupdate.bean.CashUpdateInfoDO;
import com.dfire.mobile.network.service.NetworkService;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.data.source.carryout.ITakeoutRemoteSource;
import com.zmsoft.ccd.data.source.carryout.TakeoutRemoteSource;
import com.zmsoft.ccd.data.source.home.HomeRemoteSource;
import com.zmsoft.ccd.data.source.home.IHomeSource;
import com.zmsoft.ccd.data.source.print.IPrintConfigSource;
import com.zmsoft.ccd.data.source.print.PrintConfigSource;
import com.zmsoft.ccd.data.source.system.AppSystemSource;
import com.zmsoft.ccd.data.source.system.IAppSystemSource;
import com.zmsoft.ccd.data.source.user.UserDataSource;
import com.zmsoft.ccd.data.source.user.UserRemoteSource;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.BatchPermissionHelper;
import com.zmsoft.ccd.lib.base.helper.ConfigHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.carryout.TakeoutMobile;
import com.zmsoft.ccd.lib.bean.shop.ShopLimitVo;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 15:42
 */
public class RetailMainPresenter implements RetailMainContract.Presenter {

    private RetailMainContract.View mMainView;
    private ICommonSource mBaseSource;
    private IAppSystemSource iAppSystemSource;
    private IPrintConfigSource iPrintConfigSource;
    private final ITakeoutRemoteSource mCarryoutRemoteSource;
    private final UserDataSource mUserRemoteSource;
    private final IHomeSource iHomeSource;

    @Inject
    RetailMainPresenter(RetailMainContract.View view) {
        mMainView = view;
        mBaseSource = new CommonRemoteSource();
        mCarryoutRemoteSource = new TakeoutRemoteSource();
        iAppSystemSource = new AppSystemSource();
        iPrintConfigSource = new PrintConfigSource();
        mUserRemoteSource = new UserRemoteSource(NetworkService.getDefault());
        iHomeSource = new HomeRemoteSource();
    }

    @Inject
    void setPresenter() {
        mMainView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mMainView = null;
    }

    @Override
    public void getShopLimitDay(String entityId) {
        iHomeSource.getShopLimitDay(entityId, new Callback<ShopLimitVo>() {
            @Override
            public void onSuccess(ShopLimitVo data) {
                if (mMainView == null) {
                    return;
                }
                mMainView.getShopLimitDaySuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
                mMainView.getShopLimitDayFailure();
            }
        });
    }

    @Override
    public void getLabelPrintConfig(String entityId, String userId) {
        iPrintConfigSource.getLabelPrintConfig(entityId, userId, new Callback<LabelPrinterConfig>() {
            @Override
            public void onSuccess(LabelPrinterConfig data) {
                if (mMainView == null) {
                    return;
                }
                if (data != null) {
                    LabelPrinterConfig printerSetting = LabelPrintManager.getPrinterSetting();
                    printerSetting.setIp(data.getIp());
                    printerSetting.setLabelType(data.getLabelType());
                    printerSetting.setPrinterType(data.getPrinterType());
                    LabelPrintManager.setPrinterSetting(printerSetting);
                    LabelPrintManager.saveToPref(GlobalVars.context);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
            }
        });
    }

    @Override
    public Observable<Map<String, String>> getFielCodeByList(String entityId, List<String> codeList) {
        mBaseSource.getFielCodeByList(entityId, codeList)
                .retryWhen(new RetryWithDelay(3, 500))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map<String, String>>() {
                    @Override
                    public void call(Map<String, String> data) {
                        if (mMainView == null) {
                            return;
                        }
                        if (data != null) {
                            // 自动打印客户联
                            BaseSpHelper.saveSelfPrintAccountOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
                            BaseSpHelper.saveSelfPrintAccountOrderSwitch(GlobalVars.context,
                                    data.get(SystemDirCodeConstant.FunctionFielCode.SELF_PRINT_ACCOUNT_ORDER));
                            // 保存标签打印开关
                            BaseSpHelper.saveUseLabelPrinter(GlobalVars.context,
                                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.PRINT_LABEL)));
                            // 保存双单位修改提醒
                            BaseSpHelper.saveDoubleUnitSwitch(GlobalVars.context,
                                    Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.FunctionFielCode.DOUBLE_UPDATE_PROMPT)));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mMainView == null) {
                            return;
                        }
                    }
                });
        return null;
    }

    @Override
    public void getPrintConfig(String entityId, String userId) {
        iPrintConfigSource.getPrintConfig(entityId, userId, new Callback<SmallTicketPrinterConfig>() {
            @Override
            public void onSuccess(SmallTicketPrinterConfig data) {
                if (mMainView == null) {
                    return;
                }
                if (data != null) {
                    SmallTicketPrinterConfig printerSetting = SmallTicketPrinterManager.getPrinterSetting();
                    printerSetting.setIp(data.getIp());
                    printerSetting.setRowCharMaxNum(data.getRowCharMaxNum());
                    if (data.getRowCharMaxNum() != 0) {
                        printerSetting.setByteCount(StringUtils.appendStr(data.getRowCharMaxNum(), "mm"));
                    }
                    printerSetting.setPrinterType(data.getPrinterType());
                    SmallTicketPrinterManager.setPrinterSetting(printerSetting);
                    SmallTicketPrinterManager.saveToPref(GlobalVars.context);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
            }
        });
    }

    @Override
    public void checkAppUpdate(String entityId, String appCode, int version) {
        iAppSystemSource.checkAppUpdate(entityId, appCode, version, new Callback<CashUpdateInfoDO>() {
            @Override
            public void onSuccess(CashUpdateInfoDO data) {
                if (mMainView == null) {
                    return;
                }
                mMainView.checkAppUpdateSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
            }
        });
    }

    @Override
    public void getSwitchByList(String entityId, List<String> codeList) {
        mBaseSource.getSwitchByList(entityId, codeList, new Callback<Map<String, String>>() {
            @Override
            public void onSuccess(Map<String, String> data) {
                if (mMainView == null) {
                    return;
                }
                if (data != null) {
                    // 财务联开关
                    BaseSpHelper.saveSelfPrintFinanceOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
                    BaseSpHelper.saveSelfPrintFinanceOrderSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.ACCOUNT_BILL));
                    // 点菜单开关
                    BaseSpHelper.saveSelfPrintDishesOrderSwitchTime(GlobalVars.context, System.currentTimeMillis());
                    BaseSpHelper.saveSelfPrintDishesOrderSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.IS_PRINT_ORDER));
                    // 是否使用本地收银
                    BaseSpHelper.saveWorkMode(GlobalVars.context, Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_USE_LOCAL_CASH)));
                    // 现金清账 外卖电话
                    BaseSpHelper.saveCashCleanSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.CASH_CLEAN));
                    BaseSpHelper.saveCarryOutSwitch(GlobalVars.context, data.get(SystemDirCodeConstant.CLOUD_CASH_CALL));
                    // 能否修改服务费开关
                    BaseSpHelper.saveUpdateServiceFee(GlobalVars.context,
                            Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_SET_MINCONSUMEMODE)));

                    // 是否设置限时用餐
                    BaseSpHelper.saveIsLimitTime(GlobalVars.context, Base.STRING_TRUE.equals(data.get(SystemDirCodeConstant.IS_LIMIT_TIME)));
                    // 用餐超时时间
                    BaseSpHelper.saveLimitTimeEnd(GlobalVars.context, data.get(SystemDirCodeConstant.LIMIT_TIME_END));
                }

                // 如果开启了外卖设置，然后查询该用户是否开启接受外卖电话
                if (ConfigHelper.hasOpenCarryoutPhoneCall(GlobalVars.context)) {
                    loadCarryoutPhoneList(UserHelper.getEntityId());
                    mMainView.loadConfigSuccess(true);
                } else {
                    mMainView.loadConfigSuccess(false);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
                mMainView.loadConfigFailed(false);
            }
        });
    }

    @Override
    public void batchCheckPermisson(int systemType, List<String> actionCodeList) {
        mBaseSource.batchCheckPermission(systemType, actionCodeList, new Callback<HashMap<String, Boolean>>() {
            @Override
            public void onSuccess(HashMap<String, Boolean> data) {
                if (null == mMainView) {
                    return;
                }
                BatchPermissionHelper.saveBatchPermission(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (null == mMainView) {
                    return;
                }
            }
        });
    }

    @Override
    public void loadCarryoutPhoneList(String entityId) {
        mCarryoutRemoteSource.getTakeoutMobileList(entityId)
                .retryWhen(new RetryWithDelay(3, 300))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TakeoutMobile>>() {
                    @Override
                    public void call(List<TakeoutMobile> carryoutMobiles) {
                        if (mMainView == null) {
                            return;
                        }
                        mMainView.loadCarryoutPhoneListSuccess(carryoutMobiles);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (mMainView == null) {
                            return;
                        }
                        ServerException e = ServerException.convertIfSame(throwable);
                        if (e != null) {
                            mMainView.loadCarryoutPhoneListFailed(e.getErrorCode(), e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void getWorkingStatus(String entityId, int type, String userId) {
        mUserRemoteSource.isWorking(entityId, type, userId, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                if (mMainView == null) {
                    return;
                }
                mMainView.loadWorkingStatusSuccess(data);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
                mMainView.loadWorkingStatusFailure();
            }
        });
    }

    @Override
    public void setWorkingStatus(String entityId, int type, String userId, final int status) {
        mMainView.showLoading(true);
        mUserRemoteSource.setWorking(entityId, type, userId, status, new Callback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                if (mMainView == null) {
                    return;
                }
                mMainView.hideLoading();
                mMainView.setWorkingSuccess(data, status);
            }

            @Override
            public void onFailed(ErrorBody body) {
                if (mMainView == null) {
                    return;
                }
                mMainView.hideLoading();
                mMainView.showToastMessage(body.getMessage());
            }
        });
    }

    @Override
    public void checkTakeOutBindSeat(String entityId, String userId) {
        mUserRemoteSource.checkTakeOutBindSeat(entityId, userId);
    }
}
