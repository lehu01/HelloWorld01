package com.ccd.lib.print.service;

import android.app.IntentService;
import android.content.Intent;

import com.ccd.lib.print.R;
import com.ccd.lib.print.bean.UploadPrintNum;
import com.ccd.lib.print.constants.PrintBizTypeConstants;
import com.ccd.lib.print.data.IPrintSource;
import com.ccd.lib.print.data.PrintSource;
import com.ccd.lib.print.model.PrintData;
import com.ccd.lib.print.type.PrintConstants;
import com.ccd.lib.print.util.ByteUtils;
import com.ccd.lib.print.util.PrintFileUtils;
import com.ccd.lib.print.util.printer.PrinterUtils;
import com.ccd.lib.print.util.printer.port.PortUtils;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.print.PrintOrderVo;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description：打印服务
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/13 19:27
 */
public class CcdPrintService extends IntentService {

    public static final String PRINT_DATA = "keyPrintData";
    public static final String PRINT_TAG = "CCD-Print***";
    public static final int SIZE = 3;
    public static final long TEMP_TIME = 1000;

    private ICommonSource mICommonSource = new CommonRemoteSource();
    private IPrintSource mIPrintSource = new PrintSource();
    private PrintData mPrintData;

    public CcdPrintService() {
        super(StringUtils.appendStr("ccdprintservice", System.currentTimeMillis()));
    }

    public CcdPrintService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mPrintData = (PrintData) intent.getSerializableExtra(PRINT_DATA);
            doPrint();
        }
    }

    /**
     * 开始打印操作
     */
    private void doPrint() {
        if (mPrintData != null) {
            // 1.有打印原始数据则直接传给打印机
            if (mPrintData.getRawData() != null && mPrintData.getRawData().length != 0) {
                if (mPrintData.isLocalPrint()) {
                    sendMessageToLocalCashPrintTest();
                } else {
                    PrinterUtils.print(StringUtils.notNull(mPrintData.getIp())
                            , mPrintData.getLocalType()
                            , mPrintData.getRawData()
                            , mPrintData.getPrinterConfig());
                }
                return;
            }
            // 2.服务器直接返回下载文件地址
            if (mPrintData != null && !StringUtils.isEmpty(mPrintData.getLoadFilePath())) {
                autoPrintByFile();
                return;
            }
            // 3.类型处理
            doPrintByType();
        } else {
            ToastUtils.showShortToastSafe(GlobalVars.context, GlobalVars.context.getString(R.string.empty_print_data));
        }
    }

    // ===========================Start==============处理分配打印任务=====================================
    // ===========================Start==============处理分配打印任务=====================================


    /**
     * type类型处理
     */
    // ===========================================================================================
    // 打印任务分配：自动和手动
    // ===========================================================================================
    private void doPrintByType() {
        int type = mPrintData.getType();
        switch (type) {
            case PrintData.TYPE_TEST: // 测试页面：网络
                break;
            case PrintData.TYPE_SELF_ORDER: // 自动打印客户联+点菜单/加菜单
                autoSelfPrintAccount();
                autoSelfPrintAddOrDishesInstanceOrder();
                break;
            case PrintData.TYPE_ORDER: // 手动打印触发
                manualPrintTicket();
                break;
            default:
                ToastUtils.showShortToastSafe(GlobalVars.context, GlobalVars.context.getString(R.string.empty_print_type));
                break;
        }
    }

    private void manualPrintTicket() {
        int bizType = mPrintData.getBizType();
        switch (bizType) {
            case PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE: // 加菜单
                printInstance(PrintBizTypeConstants.PrintInstance.TYPE_ADD_INSTANCE);
                break;
            case PrintData.BIZ_TYPE_PRINT_DISHES_ORDER: // 点菜单
            case PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER:  // 客户联
            case PrintData.BIZ_TYPE_PRINT_RETAIL_ORDER:  // 零售单
            case PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER: // 财务联
            case PrintData.BIZ_TYPE_PRINT_RETAIL_FINANCE_ORDER: // 零售单财务联
                printOrder();
                break;
            case PrintData.BIZ_TYPE_PRINT_CANCEL_INSTANCE: // 退菜
                printInstance(PrintBizTypeConstants.PrintInstance.TYPE_CANCEL_INSTANCE);
                break;
            case PrintData.BIZ_TYPE_PRINT_BILL: // 账单汇总
                printBill();
                break;
            case PrintData.BIZ_TYPE_PRINT_PUSH_INSTANCE: // 催菜
                printInstance(PrintBizTypeConstants.PrintInstance.TYPE_PUSH_INSTANCE);
                break;
            case PrintData.BIZ_TYPE_PRINT_CHANGE_SEAT: // 转桌
                printChangeSeat();
                break;
            case PrintData.BIZ_TYPE_PRINT_PUSH_ORDER: // 催单
                printOrder(PrintBizTypeConstants.PrintOrder.BIZ_TYPE_PRINT_PUSH_ORDER);
                break;
            default:
                ToastUtils.showShortToastSafe(GlobalVars.context, GlobalVars.context.getString(R.string.empty_print_type));
                break;
        }
    }


    // ===========================================================================================
    // 根据地址下载打印
    // ===========================================================================================
    private void autoPrintByFile() {
        loadPrintFile(mPrintData.getLoadFilePath())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        sendPrint(file, mPrintData.getBizType());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                            updatePrintStatusByMessage(PrintConstants.PrintTaskStatus.FAILURE, e.getMessage());
                            Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_download_print_file), e.getMessage()));
                        }
                    }
                });
    }

    // ===========================================================================================
    // 单独打印指定类型订单
    // ===========================================================================================
    private void printChangeSeat() {
        mIPrintSource.printChangeSeat(UserHelper.getEntityId()
                , mPrintData.getOrderId()
                , UserHelper.getUserId()
                , mPrintData.getSeatCode())
                .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                    @Override
                    public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                        if (printOrderVo == null) {
                            return null;
                        }
                        return loadPrintFile(printOrderVo.getFilePath());
                    }
                }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        sendPrint(file, mPrintData.getBizType());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                            Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_order_summary), e.getMessage()));
                        }
                    }
                });
    }

    private void printBill() {
        if (mPrintData != null) {
            mIPrintSource.printBillOrder(UserHelper.getEntityId()
                    , UserHelper.getUserId()
                    , mPrintData.getBillType())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            if (printOrderVo == null) {
                                return null;
                            }
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file, mPrintData.getBizType());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_order_summary), e.getMessage()));
                            }
                        }
                    });
        }
    }

    private void printOrder() {
        printOrder(mPrintData.getBizType());
    }

    private void printOrder(final int bizType) {
        if (mPrintData != null) {
            mIPrintSource.printOrder(bizType
                    , UserHelper.getEntityId()
                    , mPrintData.getOrderId()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            if (printOrderVo == null) {
                                return null;
                            }
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file, bizType);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_customer_receipt), e.getMessage()));
                            }
                        }
                    });
        }
    }

    private void printInstance(final int type) {
        if (mPrintData != null) {
            mIPrintSource.printInstance(type
                    , UserHelper.getEntityId()
                    , mPrintData.getInstanceIds()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            if (printOrderVo == null) {
                                return null;
                            }
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file, type);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_add_menu), e.getMessage()));
                            }
                        }
                    });
        }
    }

    // ===========================================================================================
    // 云收银下单，加菜，消息审核，自动审核（点菜单/加菜单 区域）
    // ===========================================================================================
    private void autoSelfPrintAccount() {
        printOrder(PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER);
    }

    private void autoSelfPrintAddOrDishesInstanceOrder() {
        if (mPrintData != null) {
            int bizType = mPrintData.getBizType();
            switch (bizType) {
                case PrintData.BIZ_TYPE_PRINT_DISHES_ORDER:
                    printAddOrDishesOrderByArea();
                    break;
                case PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE:
                    printInstanceByArea(PrintBizTypeConstants.PrintInstance.TYPE_ADD_INSTANCE);
                    break;
            }
        }
    }

    private void printAddOrDishesOrderByArea() {
        if (mPrintData != null) {
            mIPrintSource.printOrder(mPrintData.getBizType()
                    , UserHelper.getEntityId()
                    , mPrintData.getOrderId()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            if (printOrderVo == null) {
                                return null;
                            }
                            mPrintData.setAreaIp(printOrderVo.getIp());
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    })
                    .retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file, mPrintData.getBizType());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_file), e.getMessage()));
                            }
                        }
                    });
        }
    }

    private void printInstanceByArea(final int type) {
        if (mPrintData != null) {
            mIPrintSource.printInstance(type
                    , UserHelper.getEntityId()
                    , mPrintData.getInstanceIds()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            if (printOrderVo == null) {
                                return null;
                            }
                            mPrintData.setAreaIp(printOrderVo.getIp());
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file, type);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_get_the_path_of_add_menu), e.getMessage()));
                            }
                        }
                    });
        }
    }

    // ===========================================================================================
    // 下载打印文件，发送打印
    // ===========================================================================================
    private Observable<File> loadPrintFile(final String filePath) {
        if (!StringUtils.isEmpty(filePath)) {
            File createFile = null;
            try {
                createFile = PrintFileUtils.createFile("ccdprint", StringUtils.appendStr(System.currentTimeMillis(), ".txt"));
            } catch (IOException e) {
                e.printStackTrace();
                Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_create_file)));
                return Observable.error(new IOException(GlobalVars.context.getString(R.string.fail_mk_file)));
            }
            return mICommonSource.loadFile(filePath, createFile)
                    .retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io());
        } else {
            ToastUtils.showToastInWorkThread(GlobalVars.context, GlobalVars.context.getString(R.string.empty_load_file_path));
        }
        Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_download_file)));
        return Observable.error(new Exception(GlobalVars.context.getString(R.string.fail_print)));
    }

    private void sendPrint(File file, int printType) {
        if (file != null && file.exists()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                byte[] content = ByteUtils.readByStream(is);
                // 1.使用区域打印机打印
                // 2.使用云收银连接的打印机打印
                boolean isPrintSuccess;
                if (printType == PrintData.BIZ_TYPE_PRINT_DISHES_ORDER
                        || printType == PrintData.BIZ_TYPE_PRINT_ADD_INSTANCE) {
                    if (!StringUtils.isEmpty(mPrintData.getAreaIp())) {
                        isPrintSuccess = PortUtils.printByPort(mPrintData.getAreaIp(), PortUtils.PRINTER_PORT, content, 0, 3);
                    } else {
                        isPrintSuccess = PrinterUtils.print(content);
                    }
                } else {
                    isPrintSuccess = PrinterUtils.print(content);
                }
                // 更新打印标记
                uploadPrintNum(isPrintSuccess, printType);
                updatePrintStatusByMessage(isPrintSuccess ? PrintConstants.PrintTaskStatus.SUCCESS : PrintConstants.PrintTaskStatus.FAILURE
                        , isPrintSuccess ? GlobalVars.context.getString(R.string.print_success) : GlobalVars.context.getString(R.string.printer_send_error));
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtils.showToastInWorkThread(GlobalVars.context, GlobalVars.context.getString(R.string.empty_print_file_path));
        }
    }

    // ==================================================================================================
    // 更新打印财务联或者客户联次数
    // ==================================================================================================
    private void uploadPrintNum(boolean isPrintSuccess, int printType) {
        if (mPrintData != null) {
            if (!isPrintSuccess) {
                return;
            }
            // 更新特殊情况下财务联和客户联数量
            switch (printType) {
                case PrintData.BIZ_TYPE_PRINT_ACCOUNT_ORDER: // 更新客户联打印标记
                    uploadPrintNum(UploadPrintNum.PRINT_ACCOUNT);
                    break;
                case PrintData.BIZ_TYPE_PRINT_FINANCE_ORDER:// 更新财务联打印标记
                    uploadPrintNum(UploadPrintNum.PRINT_FINANCE);
                    break;
            }
        }
    }

    private void uploadPrintNum(int type) {
        mIPrintSource.uploadPrintNum(UserHelper.getEntityId(), mPrintData.getOrderId(), type)
                .retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<UploadPrintNum>() {
                    @Override
                    public void call(UploadPrintNum uploadPrintNum) {
                        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    //===============================================================================
    //发送消息给本地收银，本地收银打印
    //===============================================================================
    private void sendMessageToLocalCashPrintTest() {
        mIPrintSource.sendMessageToLocalCashPrintTest(UserHelper.getEntityId()
                , UserHelper.getUserId()
                , mPrintData.getIp()
                , mPrintData.getLocalType()
                , mPrintData.getRowCount())
                .subscribe(new Action1<CommonResult>() {
                    @Override
                    public void call(CommonResult commonResult) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                        }
                    }
                });
    }

    //===============================================================================
    // 更新打印状态，消息
    //===============================================================================
    private void updatePrintStatusByMessage(short status, String memo) {
        if (!StringUtils.isEmpty(mPrintData.getLoadFilePath())) {
            mIPrintSource.updateTaskStatus(UserHelper.getEntityId()
                    , UserHelper.getUserId()
                    , mPrintData.getPrintTaskId()
                    , status, memo)
                    .retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean data) {

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                            }
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
