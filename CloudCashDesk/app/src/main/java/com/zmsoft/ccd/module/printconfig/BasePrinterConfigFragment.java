package com.zmsoft.ccd.module.printconfig;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.ccd.lib.print.constants.PrintConfigConstant;
import com.ccd.lib.print.util.ConvertUtils;
import com.ccd.lib.print.util.printer.bluetooth.BlueToothPrinterUtils;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.spdata.PrintTypeDataManager;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.helper.PrintHelper;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.print.PrintRowCount;
import com.zmsoft.ccd.lib.widget.pickerview.OptionsPickerView;
import com.zmsoft.ccd.lib.widget.pickerview.PickerViewOptionsHelper;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/19 15:07
 *     desc  : 配置打印机父类
 * </pre>
 */
public abstract class BasePrinterConfigFragment extends BaseFragment {

    public static final int TYPE_SMALL_TICKET = 1;
    public static final int TYPE_LABEL = 2;

    protected OptionsPickerView mByteCountPickerView;
    protected List<PrintRowCount> mByteCountList = PrintHelper.getPrintByteList();
    protected BottomDialog mPrintTypeBottomDialog;
    private String[] mDialogItems;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initBroadcast();
    }

    @Override
    protected void initListener() {

    }

    private void initBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mBlueTooThReceiver, filter);
    }

    private BroadcastReceiver mBlueTooThReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null) {
                    int state = device.getBondState();
                    switch (state) {
                        case BluetoothDevice.BOND_BONDED:
                            try {
                                int bluetoothType = ConvertUtils.toInteger(device.getBluetoothClass().getMajorDeviceClass(), -1);
                                if (BlueToothPrinterUtils.isSupportBlueToothType(bluetoothType)) {
//                                    bindBlueToothConnect(device);
//                                    SystemClock.sleep(600);
                                    updateBlueTooth(device);
                                } else {
                                    showToast(getString(R.string.bluetooth_not_support));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        }
    };

    protected void gotoSetBlueBoothActivity() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        getActivity().startActivity(intent);
    }

    protected void showPrintTypeDialog() {
        showPrintTypeDialog(TYPE_SMALL_TICKET);
    }

    protected void showPrintTypeDialog(int type) {
        if (mPrintTypeBottomDialog == null) {
            mPrintTypeBottomDialog = new BottomDialog(getActivity());
        }
        switch (type) {
            case TYPE_LABEL:
                mDialogItems = PrintTypeDataManager.getLabelPrintType();
                mPrintTypeBottomDialog.setItemText(mDialogItems);
                break;
            case TYPE_SMALL_TICKET:
                mDialogItems = PrintTypeDataManager.getSmallTicketPrintType();
                mPrintTypeBottomDialog.setItemText(mDialogItems);
                break;
        }
        mPrintTypeBottomDialog.setItemClickListener(new BottomDialog.PopupWindowItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mDialogItems != null && position < mDialogItems.length) {
                    String str = mDialogItems[position];
                    int printType = 0;
                    if (getString(R.string.net_printer).equals(str)) {
                        printType = PrintConfigConstant.PrinterType.PRINT_TYPE_NET;
                    } else if (getString(R.string.blue_booth_printer).equals(str)) {
                        printType = PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH;
                    } else if (getString(R.string.local_printer).equals(str)) {
                        printType = PrintConfigConstant.PrinterType.PRINT_TYPE_LOCAL;
                    } else if (getString(R.string.usb_print).equals(str)) {
                        printType = PrintConfigConstant.PrinterType.PRINT_TYPE_USB;
                    }
                    updatePrinterDialog(printType);
                }
                mPrintTypeBottomDialog.dismiss();
            }
        });
        if (!mPrintTypeBottomDialog.isShowing()) {
            mPrintTypeBottomDialog.showPopupWindow();
        }
    }

    protected void selectByteCount(String indexStr) {
        if (mByteCountPickerView == null) {
            mByteCountPickerView = PickerViewOptionsHelper.createDefaultPrickerView(getActivity(), R.string.set_print_byte_count);
            mByteCountPickerView.setOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (options1 > mByteCountList.size() - 1) {
                        return;
                    }
                    updateSelectByte(mByteCountList.get(options1));
                }
            });
        }
        mByteCountPickerView.setSelectOptions(PrintHelper.getCheckIndex(indexStr, mByteCountList));
        mByteCountPickerView.setPicker(mByteCountList);
        mByteCountPickerView.show();
    }

    @Subscribe
    public void refreshBlueToothIcon(RouterBaseEvent.CommonEvent event) {
        if (event == RouterBaseEvent.CommonEvent.EVENT_REFRESH_BLUE_BOOTH) {
            updateBlueToothIcon();
        }
    }

    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mBlueTooThReceiver);
        super.onDestroy();
    }

    protected abstract void updateBlueTooth(BluetoothDevice device);

    protected void updateSelectByte(PrintRowCount printRowCount) {

    }

    protected abstract void updatePrinterDialog(int type);

    protected abstract void updateBlueToothIcon();
}
