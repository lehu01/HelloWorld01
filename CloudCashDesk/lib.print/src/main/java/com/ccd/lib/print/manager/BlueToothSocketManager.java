package com.ccd.lib.print.manager;

import android.bluetooth.BluetoothSocket;
import android.util.LruCache;

import com.zmsoft.ccd.lib.utils.StringUtils;

/**
 * Description：蓝牙连接管理器
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/6 20:05
 */
public class BlueToothSocketManager {

    private static BlueToothSocketManager mManager;
    private static LruCache<String, BluetoothSocket> mSocketMap = new LruCache<>(6);

    private BlueToothSocketManager() {

    }

    public static BlueToothSocketManager getInstance() {
        if (mManager == null) {
            synchronized (BlueToothSocketManager.class) {
                if (mManager == null) {
                    mManager = new BlueToothSocketManager();
                }
            }
        }
        return mManager;
    }

    public void addSocket(String mac, BluetoothSocket bluetoothSocket) {
        if (!StringUtils.isEmpty(mac) && bluetoothSocket != null) {
            mSocketMap.put(mac, bluetoothSocket);
        }
    }

    public BluetoothSocket getSocket(String mac) {
        if (!StringUtils.isEmpty(mac)) {
            return mSocketMap.get(mac);
        }
        return null;
    }

    public void removeSocket(String mac) {
        if (!StringUtils.isEmpty(mac)) {
            mSocketMap.remove(mac);
        }
    }
}
