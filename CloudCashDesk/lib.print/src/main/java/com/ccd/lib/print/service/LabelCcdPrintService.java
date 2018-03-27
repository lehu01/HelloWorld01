package com.ccd.lib.print.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.ccd.lib.print.R;
import com.ccd.lib.print.data.IPrintSource;
import com.ccd.lib.print.data.PrintSource;
import com.ccd.lib.print.model.PrintData;
import com.ccd.lib.print.type.PrintConstants;
import com.ccd.lib.print.util.ByteUtils;
import com.ccd.lib.print.util.PrintFileUtils;
import com.ccd.lib.print.util.printer.PrinterUtils;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.repository.CommonRemoteSource;
import com.zmsoft.ccd.data.repository.ICommonSource;
import com.zmsoft.ccd.lib.base.exception.ServerException;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.rxjava.RetryWithDelay;
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

import static com.ccd.lib.print.util.printer.PrinterUtils.printByLabel;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/21 11:17
 *     desc  : 标签打印机服务
 * </pre>
 */
public class LabelCcdPrintService extends IntentService {

    public static final String PRINT_DATA = "keyLabelPrintData";
    public static final String PRINT_TAG = "CCD-LabelPrint***";
    public static final int SIZE = 3;
    public static final long TEMP_TIME = 1000;

    private PrintData mPrintData;
    private IPrintSource mIPrintSource = new PrintSource();
    private ICommonSource mICommonSource = new CommonRemoteSource();

    public LabelCcdPrintService() {
        super(StringUtils.appendStr("labelCcdPrintService", System.currentTimeMillis()));
    }

    public LabelCcdPrintService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mPrintData = (PrintData) intent.getSerializableExtra(PRINT_DATA);
        doPrint();
    }

    private void doPrint() {
        if (mPrintData != null) {
            // 1.打印测试页面
            if (mPrintData.getRawData() != null) {
                printByLabel(mPrintData.getIp()
                        , mPrintData.getLocalType()
                        , mPrintData.getRawData()
                        , mPrintData.getPrinterConfig());
                return;
            }
            // 2.没有打开标签打印开关
            if (!BaseSpHelper.isUseLabelPrinter(GlobalVars.context)) {
                return;
            }
            // 3.根据地址打印
            if (mPrintData.getLoadFilePath() != null) {
                printOrderByFile();
                return;
            }
            // 4.类型处理
            switch (mPrintData.getType()) {
                case PrintData.TYPE_SELF_ORDER: // 自动打印
                    printLabelOrder();
                    break;
                case PrintData.TYPE_ORDER: // 手动打印触发
                    printTicket();
                    break;
            }
        }
    }

    /**
     * 打印标签：手动触发
     */
    private void printTicket() {
        int bizType = mPrintData.getBizType();
        switch (bizType) {
            case PrintData.BIZ_TYPE_PRINT_LABEL:
                printLabelInstance();
                break;
        }
    }

    /**
     * 补打标签纸
     */
    private void printLabelInstance() {
        if (mPrintData != null) {
            mIPrintSource.printInstance(mPrintData.getBizType()
                    , UserHelper.getEntityId()
                    , mPrintData.getInstanceIds()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, rx.Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    })
                    .retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, "打印失败：获取打印“补打标签纸”文件下载地址失败，", e.getMessage()));
                            }
                        }
                    });
        }
    }

    /**
     * 打印标签：订单维度
     */
    private void printLabelOrder() {
        if (mPrintData != null) {
            mIPrintSource.printOrder(mPrintData.getBizType()
                    , UserHelper.getEntityId()
                    , mPrintData.getOrderId()
                    , mPrintData.getUserId()
                    , mPrintData.getReprint())
                    .flatMap(new Func1<PrintOrderVo, Observable<File>>() {
                        @Override
                        public rx.Observable<File> call(PrintOrderVo printOrderVo) {
                            return loadPrintFile(printOrderVo.getFilePath());
                        }
                    }).retryWhen(new RetryWithDelay(SIZE, TEMP_TIME))
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            sendPrint(file);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            if (throwable instanceof ServerException) {
                                ServerException e = (ServerException) throwable;
                                ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                                Logger.d(StringUtils.appendStr(PRINT_TAG, "打印失败：获取打印“标签菜肴列表”文件下载地址失败，", e.getMessage()));
                            }
                        }
                    });
        }
    }

    /**
     * 下载打印文件txt，返回file
     */
    private Observable<File> loadPrintFile(final String filePath) {
        if (!StringUtils.isEmpty(filePath)) {
            File createFile = null;
            try {
                createFile = PrintFileUtils.createFile("ccdlabelprint", StringUtils.appendStr(System.currentTimeMillis(), ".txt"));
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

    /**
     * 知道下载文件地址，直接下载打印
     */
    private void printOrderByFile() {
        loadPrintFile(mPrintData.getLoadFilePath())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        sendPrint(file);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ServerException) {
                            ServerException e = (ServerException) throwable;
                            ToastUtils.showToastInWorkThread(GlobalVars.context, e.getMessage());
                            updatePrintStatusByMessage(PrintConstants.PrintTaskStatus.FAILURE, e.getMessage());
                            Logger.d(StringUtils.appendStr(PRINT_TAG, GlobalVars.context.getString(R.string.fail_print_download_file), e.getMessage()));
                        }
                    }
                });
    }

    /**
     * @param file 文件下载的地址
     */
    private void sendPrint(File file) {
        if (file != null && file.exists()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                byte[] content = ByteUtils.readByStream(is);
                boolean isPrintSuccess = PrinterUtils.printByLabel(content);

                updatePrintStatusByMessage(isPrintSuccess ? PrintConstants.PrintTaskStatus.SUCCESS : PrintConstants.PrintTaskStatus.FAILURE
                        , isPrintSuccess ? GlobalVars.context.getString(R.string.print_success) : GlobalVars.context.getString(R.string.printer_send_error));
                if (file != null && file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ToastUtils.showToastInWorkThread(GlobalVars.context, GlobalVars.context.getString(R.string.empty_print_file_path));
        }
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
}
