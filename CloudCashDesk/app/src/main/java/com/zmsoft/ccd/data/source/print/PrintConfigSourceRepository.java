package com.zmsoft.ccd.data.source.print;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
@Singleton
public class PrintConfigSourceRepository implements IPrintConfigSource {

    private final IPrintConfigSource mIPrintConfigSource;

    @Inject
    public PrintConfigSourceRepository(@Remote IPrintConfigSource iPrintConfigSource) {
        this.mIPrintConfigSource = iPrintConfigSource;
    }

    @Override
    public void saveLabelPrintConfig(String entityId, String userId, int printerType, String ip, Callback<LabelPrinterConfig> callback) {
        mIPrintConfigSource.saveLabelPrintConfig(entityId, userId, printerType, ip, callback);
    }

    @Override
    public void getLabelPrintConfig(String entityId, String userId, Callback<LabelPrinterConfig> callback) {
        mIPrintConfigSource.getLabelPrintConfig(entityId, userId, callback);
    }

    @Override
    public void getPrintConfig(String entityId, String userId, Callback<SmallTicketPrinterConfig> callback) {
        mIPrintConfigSource.getPrintConfig(entityId, userId, callback);
    }

    @Override
    public void savePrintConfig(String entityId, String userId, int printerType, String ip, int rowCharMaxMun, short isLocalCashPrint, short type, Callback<SmallTicketPrinterConfig> callback) {
        mIPrintConfigSource.savePrintConfig(entityId, userId, printerType, ip, rowCharMaxMun, isLocalCashPrint, type, callback);
    }
}
