package com.ccd.lib.print.bean;

import java.io.Serializable;
import java.util.UUID;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/26 15:24
 *     desc  : 蓝牙设备信息
 * </pre>
 */
public class BlueToothPrinterInfo implements Serializable {

    private UUID uuid;
    private String mac;
    private String bluetoothName;
    private int type;

    public BlueToothPrinterInfo(UUID uuid, String mac, String bluetoothName, int type) {
        this.uuid = uuid;
        this.mac = mac;
        this.bluetoothName = bluetoothName;
        this.type = type;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBluetoothName() {
        return bluetoothName;
    }

    public void setBluetoothName(String bluetoothName) {
        this.bluetoothName = bluetoothName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
