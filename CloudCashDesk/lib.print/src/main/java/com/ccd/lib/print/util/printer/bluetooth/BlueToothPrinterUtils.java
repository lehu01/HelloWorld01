package com.ccd.lib.print.util.printer.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import com.ccd.lib.print.R;
import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.helper.PrintChooseHelper;
import com.ccd.lib.print.manager.BlueToothSocketManager;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.ccd.lib.print.util.ConvertUtils;
import com.ccd.lib.print.util.printer.PrinterUtils;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/2 11:01
 *     desc  : 蓝牙打印机工具类
 * </pre>
 */
public class BlueToothPrinterUtils {

    /**
     * 蓝牙打印机类型：已知
     */
    private static final int BLUE_PRINT_TYPE_1536 = 1536;
    private static final int BLUE_PRINT_TYPE_7936 = 7936;
    private static final int BLUE_PRINT_TYPE_1024 = 1024;
    private static final int BLUE_PRINT_TYPE_0 = 0;
    /**
     * 商米虚拟蓝牙
     */
    private static final String SHOP_DEFAULT_MAC_ADDRESS = "00:11:22:33:44:55";
    private static final String SHOP_DEFAULT_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    /**
     * 通联虚拟蓝牙
     */
    private static final String ALL_IN_MAC_ADDRESS = "00:01:02:03:0A:0B";
    /**
     * 慧银虚拟蓝牙
     */
    private static final String DISCOUNT_CASH_MAC_ADDRESS = "99:87:65:43:21:00";

    /**
     * 蓝牙打印
     */
    public static boolean printByBluetooth(byte[] content, SmallTicketPrinterConfig info) {
        try {
            if (info.getUuid() == null) {
                if (!PrintChooseHelper.getNoPrompt()) {
                    PrinterUtils.showGuideDialog();
                } else {
                    ToastUtils.showToastInWorkThread(context, context.getString(R.string.please_set_printer));
                }
                return false;
            }
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (!bluetoothAdapter.isEnabled()) {
                ToastUtils.showToastInWorkThread(context, context.getString(R.string.blue_tooth_close));
                return false;
            }
            for (BluetoothDevice device : pairedDevices) {
                int blueToothType = ConvertUtils.toInteger(device.getBluetoothClass().getMajorDeviceClass(), -1);
                if (isSupportBlueToothType(blueToothType)) {
                    // 虚拟蓝牙
                    ParcelUuid[] uuids = getResultUUids(device.getUuids(), blueToothType, device.getAddress());
                    // 蓝牙连接
                    if (uuids != null && uuids.length > 0) {
                        if (device.getAddress().equals(info.getMac()) && uuids[0].getUuid().equals(info.getUuid())) {
                            if (BlueToothSocketManager.getInstance().getSocket(info.getMac()) == null) {
                                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(info.getUuid());
                                socket.connect();
                                BlueToothSocketManager.getInstance().addSocket(info.getMac(), socket);
                                EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_REFRESH_BLUE_BOOTH);
                            }
                            OutputStream printerOut = BlueToothSocketManager.getInstance().getSocket(info.getMac()).getOutputStream();
                            printerOut.write(getFormatByte());
                            printerOut.write(content);
                            printerOut.flush();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (BlueToothSocketManager.getInstance().getSocket(info.getMac()) != null) {
                try {
                    BlueToothSocketManager.getInstance().getSocket(info.getMac()).close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        ToastUtils.showToastInWorkThread(context, context.getString(R.string.printer_bluetooth_send_error));
        return false;
    }

    /**
     * 支持的蓝牙打印机类型
     *
     * @param type 类型
     * @return 结果
     */
    public static boolean isSupportBlueToothType(int type) {
        return type == BLUE_PRINT_TYPE_1536
                || type == BLUE_PRINT_TYPE_7936
                || type == BLUE_PRINT_TYPE_0
                || type == BLUE_PRINT_TYPE_1024;
    }

    /**
     * 打印机恢复正常指令
     */
    private static byte[] getFormatByte() {
        return "1b40".getBytes();
    }

    /**
     * 获取蓝牙连接的uuids
     *
     * @param uuids         原生uuids
     * @param blueToothType 蓝牙类型
     * @param address       蓝牙mac地址
     * @return uuids
     */
    public static ParcelUuid[] getResultUUids(ParcelUuid[] uuids, int blueToothType, String address) {
        ParcelUuid[] result = null;
        if (blueToothType == BLUE_PRINT_TYPE_0) {
            if (SHOP_DEFAULT_MAC_ADDRESS.equals(address) || ALL_IN_MAC_ADDRESS.equals(address) || DISCOUNT_CASH_MAC_ADDRESS.equals(address)) {
                result = new ParcelUuid[1];
                result[0] = new ParcelUuid(UUID.fromString(SHOP_DEFAULT_UUID));
            }
        }
        return result == null ? uuids : result;
    }

    /**
     * 特殊蓝牙处理，二维码打印间断
     *
     * @param mac 蓝牙mac
     * @return 类型
     */
    public static int getBlueToothType(String mac) {
        if (SHOP_DEFAULT_MAC_ADDRESS.equals(mac)) {
            return PrintConfigConstant.BlueToothType.TYPE_SUN_MI;
        } else if (ALL_IN_MAC_ADDRESS.equals(mac)) {
            return PrintConfigConstant.BlueToothType.TYPE_ALL_IN;
        }
        return PrintConfigConstant.BlueToothType.TYPE_DEFAULT;
    }
}
