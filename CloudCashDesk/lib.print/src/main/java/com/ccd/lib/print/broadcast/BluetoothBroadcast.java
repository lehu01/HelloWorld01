package com.ccd.lib.print.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ccd.lib.print.R;
import com.ccd.lib.print.manager.BlueToothSocketManager;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.ToastUtils;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/15 19:27
 */
public class BluetoothBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (device != null) {
                String mac = device.getAddress();
                if (!StringUtils.isEmpty(mac) && BlueToothSocketManager.getInstance().getSocket(mac) != null) {
                    BlueToothSocketManager.getInstance().removeSocket(mac);
                    ToastUtils.showShortToast(GlobalVars.context, GlobalVars.context.getString(R.string.bluetooth_disconnect));
                    EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_REFRESH_BLUE_BOOTH);
                }
            }
        }

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    ToastUtils.showShortToast(GlobalVars.context, GlobalVars.context.getString(R.string.bluetooth_closed));
                    break;
            }
        }

        if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
            if (state == BluetoothDevice.BOND_NONE) {
                if (device != null) {
                    String mac = device.getAddress();
                    if (!StringUtils.isEmpty(mac) && BlueToothSocketManager.getInstance().getSocket(mac) != null) {
                        ToastUtils.showShortToast(GlobalVars.context, GlobalVars.context.getString(R.string.bluetooth_adapter_cancled));
                        BlueToothSocketManager.getInstance().removeSocket(mac);
                        EventBusHelper.post(RouterBaseEvent.CommonEvent.EVENT_REFRESH_BLUE_BOOTH);
                    }
                }
            }

        }
    }
}
