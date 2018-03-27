package com.zmsoft.ccd.module.printconfig.smallticket.fragment;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccd.lib.print.bean.SmallTicketPrinterConfig;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.manager.BlueToothSocketManager;
import com.ccd.lib.print.manager.SmallTicketPrinterManager;
import com.ccd.lib.print.util.ByteUtils;
import com.ccd.lib.print.util.ConvertUtils;
import com.ccd.lib.print.util.printer.LocalPrinterUtils;
import com.ccd.lib.print.util.printer.bluetooth.BlueToothPrinterUtils;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.PrintHelper;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.print.PrintRowCount;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.printconfig.BasePrinterConfigFragment;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Description：小票打印
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/1 14:34
 */
public class SmallTicketPrintConfigFragment extends BasePrinterConfigFragment implements SmallTicketPrintConfigContract.View {

    @BindView(R.id.text_print_type)
    TextView mTextPrintType;
    @BindView(R.id.text_usb_prompt)
    TextView mTextUsbPrompt;
    @BindView(R.id.linear_print_type)
    LinearLayout mLinearPrintType;
    @BindView(R.id.image_blue_tooth)
    ImageView mImageBlueTooth;
    @BindView(R.id.text_blue_booth_name)
    TextView mTextBlueBoothName;
    @BindView(R.id.linear_set_blue_tooth)
    LinearLayout mLinearSetBlueTooth;
    @BindView(R.id.text_print_ip_display)
    TextView mTextPrintIpDisplay;
    @BindView(R.id.edit_input_ip)
    EditText mEditInputIp;
    @BindView(R.id.switch_local)
    SwitchCompat mSwitchLocal;
    @BindView(R.id.linear_use_local_print)
    LinearLayout mLinearUseLocalPrint;
    @BindView(R.id.linear_print_ip)
    LinearLayout mLinearPrintIp;
    @BindView(R.id.text_byte_count)
    TextView mTextByteCount;
    @BindView(R.id.relative_byte_count)
    RelativeLayout mRelativeByteCount;
    @BindView(R.id.button_ok)
    Button mButtonOk;
    @BindView(R.id.content)
    LinearLayout mContent;

    private SmallTicketPrintConfigPresenter mPresenter;
    private int mPrintType = PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH;
    private SmallTicketPrinterConfig mPrintSetting;

    public static SmallTicketPrintConfigFragment newInstance() {
        SmallTicketPrintConfigFragment fragment = new SmallTicketPrintConfigFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_small_ticket_print_config;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        init();
    }

    @Override
    protected void initListener() {

    }

    private void init() {
        mContent.setVisibility(View.GONE);
        initPrint();
    }

    private void initPrint() {
        mPrintSetting = SmallTicketPrinterManager.getPrinterSetting();
        if (mPrintSetting == null) {
            mPrintSetting = new SmallTicketPrinterConfig();
        } else {
            mPrintType = mPrintSetting.getPrinterType();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        showLoadingView();
        getPrintSettingConfig();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getPrintSettingConfig();
    }

    /**
     * 获取配置小票打印机信息
     */
    private void getPrintSettingConfig() {
        mPresenter.getPrintConfig(UserHelper.getEntityId(), UserHelper.getUserId());
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(SmallTicketPrintConfigContract.Presenter presenter) {
        mPresenter = (SmallTicketPrintConfigPresenter) presenter;
    }

    @Override
    public void getPrintConfigSuccess(SmallTicketPrinterConfig printerSetting) {
        showContentView();
        mContent.setVisibility(View.VISIBLE);
        if (printerSetting != null) {
            mPrintSetting.setIsLocalCashPrint(printerSetting.getIsLocalCashPrint());
            mPrintSetting.setIp(printerSetting.getIp());
            mPrintSetting.setRowCharMaxNum(printerSetting.getRowCharMaxNum());
            if (printerSetting.getRowCharMaxNum() != 0) {
                mPrintSetting.setByteCount(StringUtils.appendStr(printerSetting.getRowCharMaxNum(), "mm"));
            }
            // 如果是非凡打印机
            if (printerSetting.getPrinterType() == PrintConfigConstant.PrinterType.PRINT_TYPE_LOCAL) {
                if (LocalPrinterUtils.isSupportLocalPrinter()) {
                    mPrintType = printerSetting.getPrinterType();
                    mPrintSetting.setPrinterType(printerSetting.getPrinterType());
                } else {
                    mPrintType = PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH;
                    mPrintSetting.setPrinterType(mPrintType);
                }
            } else {
                mPrintType = printerSetting.getPrinterType();
                mPrintSetting.setPrinterType(printerSetting.getPrinterType());
            }
        }
        SmallTicketPrinterManager.setPrinterSetting(mPrintSetting);
        SmallTicketPrinterManager.saveToPref(getActivity());
        updateViewByPrintType();
        updateViewByPrintData();
    }

    @Override
    public void showKnowDialog(String message) {
        getDialogUtil().showNoticeDialog(R.string.dialog_title, message, R.string.know, true, null);
    }

    @Override
    public void savePrintConfigSuccess(SmallTicketPrinterConfig printerSetting) {
        showToast(getString(R.string.save_success));
        savePrinterSettingConfig();
        PrintHelper.saveLocalCashPrint(mPrintType, mSwitchLocal.isChecked());
        getActivity().finish();
    }

    @Override
    public void showLoadDataErrorView(String errorMessage) {
        mContent.setVisibility(View.GONE);
        showErrorView(errorMessage);
    }

    @Override
    public String getBlueToothName() {
        return mTextBlueBoothName.getText().toString().trim();
    }

    @Override
    public String getPrintByteCount() {
        return mTextByteCount.getText().toString().trim();
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    @Override
    public String getIp() {
        return mEditInputIp.getText().toString().trim();
    }

    @OnClick({R.id.linear_print_type, R.id.linear_set_blue_tooth,
            R.id.relative_byte_count, R.id.button_ok})
    void processClick(View view) {
        switch (view.getId()) {
            case R.id.linear_print_type:
                showPrintTypeDialog();
                break;
            case R.id.linear_set_blue_tooth:
                gotoSetBlueBoothActivity();
                break;
            case R.id.relative_byte_count:
                selectByteCount(getPrintByteCount());
                break;
            case R.id.button_ok:
                printLocalTestPage();
                break;
        }
    }

    @Override
    protected void updateBlueTooth(BluetoothDevice device) {
        updateAboutAllBlueTooth(device);
    }

    @Override
    protected void updateSelectByte(PrintRowCount printRowCount) {
        updateSelectByteCount(printRowCount);
    }

    @Override
    protected void updatePrinterDialog(int type) {
        mPrintType = type;
        mPrintSetting.setPrinterType(mPrintType);
        updateViewByPrintType();
    }

    @Override
    protected void updateBlueToothIcon() {
        setBlueToothIcon();
        updateBlueToothName();
    }

    /**
     * 更新选择字符数
     *
     * @param printRowCount
     */
    private void updateSelectByteCount(PrintRowCount printRowCount) {
        mPrintSetting.setRowCharMaxNum(printRowCount.getValue());
        mPrintSetting.setByteCount(printRowCount.getDisplayName());
        mTextByteCount.setText(printRowCount.getDisplayName());
    }

    /**
     * 更新蓝牙相关所有操作
     *
     * @param device
     */
    private void updateAboutAllBlueTooth(BluetoothDevice device) {
        try {
            SystemClock.sleep(600);
            setBlueIcon();
            mTextBlueBoothName.setText(device.getName());
            bindBlueTooth(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置蓝牙图标
     */
    private void setBlueIcon() {
        mImageBlueTooth.setImageResource(R.drawable.icon_blue_tooth_select);
    }

    /**
     * 打印测试本地页面
     */
    private void printLocalTestPage() {
        if (mPresenter.check(mPrintType, getIp(), getBlueToothName(), getPrintByteCount())) {
            try {
                AssetManager assetManager = getActivity().getAssets();
                InputStream in;
                in = assetManager.open("printerTest.temp");
                byte[] content = ByteUtils.readByStream(in);
                CcdPrintHelper.printSmallTicketTest(getActivity()
                        , getIp()
                        , content
                        , Base.SHORT_TRUE.equals(isLocalCashPrint())
                        , mPrintType
                        , mPrintSetting.getRowCharMaxNum()
                        , mPrintSetting);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存小票本地数据
     */
    private void savePrinterSettingConfig() {
        mPrintSetting.setEntityId(UserHelper.getEntityId());
        mPrintSetting.setIp(getIp());
        mPrintSetting.setIsLocalCashPrint(mSwitchLocal.isChecked() ? Base.SHORT_TRUE : Base.SHORT_FALSE);
        SmallTicketPrinterManager.setPrinterSetting(mPrintSetting);
        SmallTicketPrinterManager.saveToPref(getActivity());
    }

    /**
     * update init view
     */
    private void updateViewByPrintData() {
        setBlueToothIcon();
        if (mPrintSetting != null) {
            mTextBlueBoothName.setText(StringUtils.notNull(mPrintSetting.getBluetoothName()));
            mTextByteCount.setText(StringUtils.notNull(mPrintSetting.getByteCount()));
            mEditInputIp.setText(StringUtils.notNull(mPrintSetting.getIp()));
            mEditInputIp.setSelection(StringUtils.notNull(mPrintSetting.getIp()).length());
            mSwitchLocal.setChecked(Base.SHORT_TRUE.equals(mPrintSetting.getIsLocalCashPrint()));
        }
    }


    /**
     * 更新打印机类型
     */
    private void updateViewByPrintType() {
        switch (mPrintType) {
            case PrintConfigConstant.PrinterType.PRINT_TYPE_NET:
                mTextPrintType.setText(getString(R.string.net_printer));
                mLinearSetBlueTooth.setVisibility(View.GONE);
                mLinearPrintIp.setVisibility(View.VISIBLE);
                mRelativeByteCount.setVisibility(View.VISIBLE);
                if (BaseSpHelper.isHybrid(getActivity())) {
                    mLinearUseLocalPrint.setVisibility(View.VISIBLE);
                } else {
                    mLinearUseLocalPrint.setVisibility(View.GONE);
                }
                mTextUsbPrompt.setVisibility(View.GONE);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH:
                mTextPrintType.setText(getString(R.string.blue_booth_printer));
                mLinearSetBlueTooth.setVisibility(View.VISIBLE);
                mLinearPrintIp.setVisibility(View.GONE);
                mRelativeByteCount.setVisibility(View.VISIBLE);
                mLinearUseLocalPrint.setVisibility(View.GONE);
                mTextUsbPrompt.setVisibility(View.GONE);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_LOCAL:
                mTextPrintType.setText(getString(R.string.local_printer));
                mLinearSetBlueTooth.setVisibility(View.GONE);
                mLinearPrintIp.setVisibility(View.GONE);
                mRelativeByteCount.setVisibility(View.VISIBLE);
                mLinearUseLocalPrint.setVisibility(View.GONE);
                mTextUsbPrompt.setVisibility(View.GONE);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_USB:
                mTextPrintType.setText(getString(R.string.usb_print));
                mLinearSetBlueTooth.setVisibility(View.GONE);
                mLinearPrintIp.setVisibility(View.GONE);
                mRelativeByteCount.setVisibility(View.GONE);
                mLinearUseLocalPrint.setVisibility(View.GONE);
                mTextUsbPrompt.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 保存
     */
    public void save() {
        if (mPresenter.check(mPrintType, getIp(), getBlueToothName(), getPrintByteCount())) {
            mPresenter.savePrintConfig(UserHelper.getEntityId()
                    , UserHelper.getUserId()
                    , mPrintType, getIp()
                    , mPrintSetting.getRowCharMaxNum()
                    , isLocalCashPrint()
                    , getBluePrintType());
        }
    }

    /**
     * 更新蓝牙名称
     */
    private void updateBlueToothName() {
        mTextBlueBoothName.setText(StringUtils.notNull(mPrintSetting.getBluetoothName()));
    }

    /**
     * 更新蓝牙连接状态icon
     */
    private void setBlueToothIcon() {
        boolean flag = BlueToothSocketManager.getInstance().getSocket(StringUtils.notNull(mPrintSetting.getMac())) != null;
        mImageBlueTooth.setImageResource(flag ? R.drawable.icon_blue_tooth_select : R.drawable.icon_blue_tooth_normal);
    }

    /**
     * bind blue tooth printer
     */
    private void bindBlueTooth(BluetoothDevice device) {
        try {
            int bluetoothType = ConvertUtils.toInteger(device.getBluetoothClass().getMajorDeviceClass(), -1);
            if (BlueToothPrinterUtils.isSupportBlueToothType(bluetoothType)) {
                // 虚拟蓝牙
                ParcelUuid[] uuids = BlueToothPrinterUtils.getResultUUids(device.getUuids(), bluetoothType, device.getAddress());
                // 蓝牙连接
                if (uuids != null && uuids.length > 0) {
                    mPrintSetting.setPrinterType(PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH);
                    mPrintSetting.setUuid(uuids[0].getUuid().toString());
                    mPrintSetting.setBluetoothName(device.getName());
                    // 小票socket
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(mPrintSetting.getUuid());
                    socket.connect();
                    BlueToothSocketManager.getInstance().addSocket(device.getAddress(), socket);
                    // 存储数据
                    mPrintSetting.setMac(device.getAddress());
                    mPrintSetting.setBlueType(PrintConfigConstant.TicketType.TYPE_SMALL_TICKET);
                    SmallTicketPrinterManager.setPrinterSetting(mPrintSetting);
                    SmallTicketPrinterManager.saveToPref(getActivity());
                    Logger.d("蓝牙打印机适配成功，请按返回键回到云收银");
                    Toast.makeText(getActivity(), getString(R.string.bluetooth_adapter_success), Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否使用本地收银打印
     * <p>
     * 混合模式下
     * 1.本地收银打印开关开启
     * 2.usb打印
     *
     * @return short
     */
    private short isLocalCashPrint() {
        if (mPrintType == PrintConfigConstant.PrinterType.PRINT_TYPE_USB) {
            return Base.SHORT_TRUE;
        }
        if (mPrintType == PrintConfigConstant.PrinterType.PRINT_TYPE_NET && mSwitchLocal.isChecked()) {
            return Base.SHORT_TRUE;
        }
        return Base.SHORT_FALSE;
    }

    /**
     * 获取蓝牙打印类型
     * 0.普通默认
     * 1.商米
     * 2.暂无
     *
     * @return
     */
    private short getBluePrintType() {
        if (mPrintType == PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH) {
            return (short) BlueToothPrinterUtils.getBlueToothType(mPrintSetting.getMac());
        }
        return PrintConfigConstant.BlueToothType.TYPE_DEFAULT;
    }
}
