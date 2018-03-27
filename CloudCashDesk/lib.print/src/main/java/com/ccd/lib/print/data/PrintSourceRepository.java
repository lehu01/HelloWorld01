package com.ccd.lib.print.data;

import com.ccd.lib.print.bean.UploadPrintNum;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.CommonResult;
import com.zmsoft.ccd.lib.bean.print.PrintAreaVo;
import com.zmsoft.ccd.lib.bean.print.PrintOrderVo;
import com.zmsoft.ccd.lib.bean.print.PrintTestVo;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/12 10:27
 */
public class PrintSourceRepository implements IPrintSource {

    private final IPrintSource mIPrintSource;

    @Inject
    PrintSourceRepository(@Remote IPrintSource printSource) {
        this.mIPrintSource = printSource;
    }

    @Override
    public Observable<Boolean> updateTaskStatus(String entityId, String userId, String id, short status, String memo) {
        return mIPrintSource.updateTaskStatus(entityId, userId, id, status, memo);
    }

    @Override
    public Observable<Boolean> lockPrintTask(String entityId, String userId, String taskId) {
        return mIPrintSource.lockPrintTask(entityId, userId, taskId);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintBill(String entityId, int type, String opUserId) {
        return mIPrintSource.sendMessageToLocalCashPrintBill(entityId, type, opUserId);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintFinanceOrder(String entityId, String orderId, String opUserId) {
        return mIPrintSource.sendMessageToLocalCashPrintFinanceOrder(entityId, orderId, opUserId);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintTest(String entityId, String opUserId, String ip, int type, int rowCount) {
        return mIPrintSource.sendMessageToLocalCashPrintTest(entityId, opUserId, ip, type, rowCount);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintDishesOrder(String entityId, String orderId, int type, String opUserId) {
        return mIPrintSource.sendMessageToLocalCashPrintDishesOrder(entityId, orderId, type, opUserId);
    }

    @Override
    public Observable<CommonResult> sendMessageToLocalCashPrintAccountOrder(String entityId, String orderId, String opUserId) {
        return mIPrintSource.sendMessageToLocalCashPrintAccountOrder(entityId, orderId, opUserId);
    }

    @Override
    public Observable<PrintOrderVo> printBillOrder(String entityId, String userId, int billType) {
        return mIPrintSource.printBillOrder(entityId, userId, billType);
    }

    @Override
    public Observable<UploadPrintNum> uploadPrintNum(String entityId, String orderId, int type) {
        return mIPrintSource.uploadPrintNum(entityId, orderId, type);
    }

    @Override
    public Observable<PrintOrderVo> printOrder(int type, String entityId, String orderId, String userId, int reprint) {
        return mIPrintSource.printOrder(type, entityId, orderId, userId, reprint);
    }

    @Override
    public Observable<PrintOrderVo> printInstance(int type, String entityId, List<String> instanceIds, String userId, int reprint) {
        return mIPrintSource.printInstance(type, entityId, instanceIds, userId, reprint);
    }

    @Override
    public void printTest(String entityId, Callback<PrintTestVo> callback) {
        mIPrintSource.printTest(entityId, callback);
    }

    @Override
    public Observable<PrintAreaVo> getPrintBySeatCode(String entityId, String seatCode, int orderKind) {
        return mIPrintSource.getPrintBySeatCode(entityId, seatCode, orderKind);
    }

    @Override
    public Observable<PrintOrderVo> printChangeSeat(String entityId, String orderId, String userId, String oldSeatCode) {
        return mIPrintSource.printChangeSeat(entityId, orderId, userId, oldSeatCode);
    }
}
