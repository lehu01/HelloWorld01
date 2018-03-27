package com.ccd.lib.print.bean;

import android.text.TextUtils;

import com.ccd.lib.print.constants.PrintConfigConstant;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description：打印存储的配置
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/3 11:21
 */
public class SmallTicketPrinterConfig implements Serializable {

    private String id; // id
    private String ip; // ip地址
    private String entityId; // 店铺entityId
    private int rowCharMaxNum; // 每行显示数据
    private String byteCount; // 每行显示数据
    private int printerType = PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH; // 类型
    private Short isLocalCashPrint; // 是否本地打印
    /**
     * 蓝牙打印机信息：本地添加
     */
    private String uuid; // 蓝牙uuid
    private String bluetoothName; // 蓝牙名称
    private String mac; // mac地址
    private int blueType; // 蓝牙类型配置

    public UUID getUuid() {
        if (!TextUtils.isEmpty(uuid)) {
            return UUID.fromString(uuid);
        }
        return null;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPrinterType() {
        return printerType;
    }

    public void setPrinterType(int printerType) {
        this.printerType = printerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getRowCharMaxNum() {
        return rowCharMaxNum;
    }

    public void setRowCharMaxNum(int rowCharMaxNum) {
        this.rowCharMaxNum = rowCharMaxNum;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public String getByteCount() {
        return byteCount;
    }

    public void setByteCount(String byteCount) {
        this.byteCount = byteCount;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getBlueType() {
        return blueType;
    }

    public void setBlueType(int blueType) {
        this.blueType = blueType;
    }


    public Short getIsLocalCashPrint() {
        return isLocalCashPrint;
    }

    public void setIsLocalCashPrint(Short isLocalCashPrint) {
        this.isLocalCashPrint = isLocalCashPrint;
    }
}
