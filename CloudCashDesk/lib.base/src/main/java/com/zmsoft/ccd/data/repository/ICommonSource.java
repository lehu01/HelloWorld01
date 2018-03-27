package com.zmsoft.ccd.data.repository;

import com.chiclaim.modularization.router.IProvider;
import com.zmsoft.ccd.data.Callback;
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

import rx.Observable;

/**
 * @author DangGui
 * @create 2017/5/4.
 */

public interface ICommonSource extends IProvider {

    /**
     * 批量保存开关：Function
     *
     * @param entityId 实体id
     * @param codeList codeList
     * @return
     */
    Observable<Boolean> saveFunctionSwitchList(String entityId, List<SwitchRequest> codeList);

    /**
     * 获取批量开关
     *
     * @param entityId
     * @param codeList
     */
    Observable<Map<String, String>> getFielCodeByList(String entityId, List<String> codeList);

    /**
     * 权限校验
     *
     * @param callback
     */
    void checkPermission(int systemType, String actionCode, final Callback<Boolean> callback);

    /**
     * 批量权限校验
     *
     * @param callback
     */
    void batchCheckPermission(int systemType, List<String> actionCodeList, final Callback<HashMap<String, Boolean>> callback);

    /**
     * 下载文件
     *
     * @param loadFilePath
     * @return
     */
    Observable<File> loadFile(String loadFilePath, File outFile);

    /**
     * 获取反结账/赠菜/退菜/打折/撤单原因列表
     *
     * @param entityId   实体id
     * @param dicCode    字典类型
     * @param systemType 字典类型值
     * @param callback   回调
     */
    void getReasonList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback);

    /**
     * 获取<b>已排序</b>的反结账/赠菜/退菜/打折/撤单原因列表
     *
     * @param entityId   实体id
     * @param dicCode    字典类型
     * @param systemType 字典类型值
     * @param callback   回调
     */
    void getReasonSortedList(String entityId, String dicCode, int systemType, Callback<List<Reason>> callback);

    /**
     * 通过seatCode获取区域打印信息
     *
     * @param entityId
     * @param seatCode
     */
    Observable<PrintAreaVo> getPrintBySeatCode(String entityId, String seatCode, int orderKind);

    /**
     * 获取店铺配置开关
     *
     * @param entityId
     * @param fieldCode
     * @return
     */
    Observable<FunctionFieldValue> getFunctionFileSwitch(String entityId, String fieldCode);

    /**
     * 获取批量开关
     *
     * @param entityId
     * @param codeList
     * @param callback
     */
    void getSwitchByList(String entityId, List<String> codeList, Callback<Map<String, String>> callback);

    /**
     * 获取单个开关
     *
     * @param entityId
     * @param systemTypeId
     * @param code
     * @return
     */
    Observable<String> getConfigSwitchVal(String entityId, String systemTypeId, String code);

    /**
     * 现金收款限额
     *
     * @param entityId
     * @param userId
     * @return
     */
    Observable<CashLimit> getCashLimit(String entityId, String userId);

    Observable<List<Reason>> getReasonList(final String entityId, final String dicCode, final int systemType);

    /**
     * 上传渠道信息
     * @param channelInfoRequest
     */
    void uploadChannelInfo(ChannelInfoRequest channelInfoRequest);
}
