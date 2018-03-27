package com.zmsoft.ccd.data.source.print;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.zmsoft.ccd.data.Callback;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:35
 */
public interface IPrintConfigSource {

    /**
     * 保存标签打印配置信息
     *
     * @param entityId    实体id
     * @param userId      用户id
     * @param printerType 打印类型
     * @param ip          ip地址
     * @param callback
     */
    void saveLabelPrintConfig(String entityId, String userId, int printerType, String ip, Callback<LabelPrinterConfig> callback);

    /**
     * 获取标签打印配置信息
     *
     * @param entityId 实体id
     * @param userId   用户id
     * @param callback
     */
    void getLabelPrintConfig(String entityId, String userId, Callback<LabelPrinterConfig> callback);

    /**
     * 获取小票打印配置信息
     *
     * @param entityId 实体id
     * @param userId   用户id
     * @param callback
     */
    void getPrintConfig(String entityId, String userId, Callback<SmallTicketPrinterConfig> callback);

    /**
     * 保存小票打印配置信息
     *
     * @param entityId      实体id
     * @param userId        用户id
     * @param printerType   类型：蓝牙/网口
     * @param ip            ip地址
     * @param rowCharMaxMun 每行字符数
     * @param callback
     */
    void savePrintConfig(String entityId, String userId, int printerType, String ip, int rowCharMaxMun, short isLocalCashPrint, short type, Callback<SmallTicketPrinterConfig> callback);

}
