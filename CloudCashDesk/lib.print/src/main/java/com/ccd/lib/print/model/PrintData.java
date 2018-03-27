package com.ccd.lib.print.model;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/13 20:13
 */
public class PrintData extends Base {

    /**
     * 打印类型
     * 1.打印网络测试页面
     * 2.单独打印某一个订单
     * 3.自动打印多个订单
     */
    public static final int TYPE_TEST = 1;
    public static final int TYPE_ORDER = 2;
    public static final int TYPE_SELF_ORDER = 3;
    /**
     * 业务类型
     * 1.点菜单
     * 2.客户联
     * 3.财务联
     * 4.加菜
     * 5.标签打印：订单或者单个菜肴
     * 6.退菜
     * 7.账单汇总
     * 8.催菜
     */
    public static final int BIZ_TYPE_PRINT_DISHES_ORDER = 1;
    public static final int BIZ_TYPE_PRINT_ACCOUNT_ORDER = 2;
    public static final int BIZ_TYPE_PRINT_FINANCE_ORDER = 3;
    public static final int BIZ_TYPE_PRINT_ADD_INSTANCE = 4;
    public static final int BIZ_TYPE_PRINT_LABEL = 5;

    // 零售财务联
    public static final int BIZ_TYPE_PRINT_RETAIL_FINANCE_ORDER = 11;
    // 零售线上订单运送单
    public static final int BIZ_TYPE_PRINT_RETAIL_ORDER = 12;
    public static final int BIZ_TYPE_PRINT_CANCEL_INSTANCE = 6;
    public static final int BIZ_TYPE_PRINT_BILL = 7;
    public static final int BIZ_TYPE_PRINT_PUSH_INSTANCE = 8;
    public static final int BIZ_TYPE_PRINT_CHANGE_SEAT = 9;
    public static final int BIZ_TYPE_PRINT_PUSH_ORDER = 10;

    private int type; // 业务：测试或者订单
    private List<String> instanceIds; // 菜肴id
    private String orderId; // 订单id
    private String userId; // 操作员id
    private String entityId; // 实体id
    private int bizType; // 打印具体操作[点菜单，客户联，财务联]
    private String loadFilePath; // 打印文件下载地址
    // 判断是否走区域打印
    private String seatCode; // seatCode
    private int orderKind; // 订单类型
    // 本地测试打印
    private String ip; // 打印ip
    private byte[] rawData; // 可以直接打印的原始数据
    private boolean isLocalPrint; // 是否本地打印
    private int localType; // 本地打印type
    private int rowCount; // 打印字符数
    private SmallTicketPrinterConfig printerConfig;
    // 账单汇总
    private int billType;// 1.今日 2昨日 3两日内
    private String areaIp; // 区域ip地址
    private int reprint; // 是否是补打：1.是，0不是
    private String printTaskId; // 打印消息体里面的id，更新打印状态

    public PrintData() {
    }

    public PrintData(byte[] rawData) {
        this.rawData = rawData;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoadFilePath() {
        return loadFilePath;
    }

    public void setLoadFilePath(String loadFilePath) {
        this.loadFilePath = loadFilePath;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public int getOrderKind() {
        return orderKind;
    }

    public void setOrderKind(int orderKind) {
        this.orderKind = orderKind;
    }

    public boolean isLocalPrint() {
        return isLocalPrint;
    }

    public void setLocalPrint(boolean localPrint) {
        isLocalPrint = localPrint;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public int getLocalType() {
        return localType;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getAreaIp() {
        return areaIp;
    }

    public void setAreaIp(String areaIp) {
        this.areaIp = areaIp;
    }

    public int getReprint() {
        return reprint;
    }

    public void setReprint(int reprint) {
        this.reprint = reprint;
    }

    public void setPrinterConfig(SmallTicketPrinterConfig printerConfig) {
        this.printerConfig = printerConfig;
    }

    public SmallTicketPrinterConfig getPrinterConfig() {
        return printerConfig;
    }

    public String getPrintTaskId() {
        return printTaskId;
    }

    public void setPrintTaskId(String printTaskId) {
        this.printTaskId = printTaskId;
    }
}
