package com.zmsoft.ccd.data.repository;

import com.zmsoft.ccd.app.ModelScoped;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.SwitchRequest;
import com.zmsoft.ccd.lib.bean.order.reversecheckout.Reason;
import com.zmsoft.ccd.lib.bean.pay.CashLimit;
import com.zmsoft.ccd.lib.bean.print.PrintAreaVo;
import com.zmsoft.ccd.lib.bean.shop.FunctionFieldValue;
import com.zmsoft.ccd.lib.bean.user.ChannelInfoRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * 公共数据仓库
 *
 * @author DangGui
 * @create 2017/5/4.
 */
@ModelScoped
public class CommonRepository implements ICommonSource {

    private final ICommonSource mBaseRemoteSource;

    @Inject
    CommonRepository(@Remote ICommonSource remoteSource) {
        this.mBaseRemoteSource = remoteSource;
    }

    @Override
    public Observable<Boolean> saveFunctionSwitchList(String entityId, List<SwitchRequest> codeList) {
        return mBaseRemoteSource.saveFunctionSwitchList(entityId, codeList);
    }

    @Override
    public Observable<Map<String, String>> getFielCodeByList(String entityId, List<String> codeList) {
        return mBaseRemoteSource.getFielCodeByList(entityId, codeList);
    }

    @Override
    public void checkPermission(int systemType, String actionCode, Callback<Boolean> callback) {
        mBaseRemoteSource.checkPermission(systemType, actionCode, callback);
    }

    @Override
    public void batchCheckPermission(int systemType, List<String> actionCodeList, Callback<HashMap<String, Boolean>> callback) {
        mBaseRemoteSource.batchCheckPermission(systemType, actionCodeList, callback);
    }

    @Override
    public Observable<File> loadFile(String loadFilePath, File outFile) {
        return mBaseRemoteSource.loadFile(loadFilePath, outFile);
    }

    @Override
    public void getReasonList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback) {
        mBaseRemoteSource.getReasonList(entityId, dicCode, systemType, callback);
    }

    @Override
    public void getReasonSortedList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback) {
        mBaseRemoteSource.getReasonSortedList(entityId, dicCode, systemType, callback);
    }

    @Override
    public Observable<PrintAreaVo> getPrintBySeatCode(String entityId, String seatCode, int orderKind) {
        return mBaseRemoteSource.getPrintBySeatCode(entityId, seatCode, orderKind);
    }

    @Override
    public Observable<FunctionFieldValue> getFunctionFileSwitch(String entityId, String fieldCode) {
        return mBaseRemoteSource.getFunctionFileSwitch(entityId, fieldCode);
    }

    @Override
    public void getSwitchByList(String entityId, List<String> codeList, Callback<Map<String, String>> callback) {
        mBaseRemoteSource.getSwitchByList(entityId, codeList, callback);
    }

    @Override
    public Observable<String> getConfigSwitchVal(String entityId, String systemTypeId, String code) {
        return mBaseRemoteSource.getConfigSwitchVal(entityId, systemTypeId, code);
    }

    @Override
    public Observable<CashLimit> getCashLimit(String entityId, String userId) {
        return mBaseRemoteSource.getCashLimit(entityId, userId);
    }

    @Override
    public Observable<List<Reason>> getReasonList(String entityId, String dicCode, int systemType) {
        return mBaseRemoteSource.getReasonList(entityId, dicCode, systemType);
    }

    @Override
    public void uploadChannelInfo(ChannelInfoRequest channelInfoRequest) {
        mBaseRemoteSource.uploadChannelInfo(channelInfoRequest);
    }
}
