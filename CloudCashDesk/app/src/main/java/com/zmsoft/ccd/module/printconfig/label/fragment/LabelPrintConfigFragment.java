package com.zmsoft.ccd.module.printconfig.label.fragment;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccd.lib.print.bean.LabelPrinterConfig;
import com.ccd.lib.print.constants.PrintConfigConstant;
import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.manager.BlueToothSocketManager;
import com.ccd.lib.print.manager.LabelPrintManager;
import com.ccd.lib.print.util.ByteUtils;
import com.ccd.lib.print.util.ConvertUtils;
import com.ccd.lib.print.util.printer.bluetooth.BlueToothPrinterUtils;
import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.constant.SystemDirCodeConstant;
import com.zmsoft.ccd.lib.base.helper.BaseSpHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.base.helper.UserLocalPrefsCacheSource;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.SwitchRequest;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.printconfig.BasePrinterConfigFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/18 14:56
 *     desc  : 配置标签打印机
 * </pre>
 */
public class LabelPrintConfigFragment extends BasePrinterConfigFragment implements LabelPrintConfigContract.View {


    @BindView(R.id.switch_label)
    SwitchCompat mSwitchLabel;
    @BindView(R.id.text_print_type)
    TextView mTextPrintType;
    @BindView(R.id.relative_print_type)
    RelativeLayout mRelativePrintType;
    @BindView(R.id.image_blue_tooth)
    ImageView mImageBlueTooth;
    @BindView(R.id.text_blue_booth_name)
    TextView mTextBlueBoothName;
    @BindView(R.id.relative_blue_tooth)
    RelativeLayout mRelativeBlueTooth;
    @BindView(R.id.text_print_ip_display)
    TextView mTextPrintIpDisplay;
    @BindView(R.id.edit_input_ip)
    EditText mEditInputIp;
    @BindView(R.id.relative_net)
    RelativeLayout mRelativeNet;
    @BindView(R.id.text_label_byte)
    TextView mTextLabelByte;
    @BindView(R.id.linear_label_model_number)
    LinearLayout mLinearLabelModelNumber;
    @BindView(R.id.textView3)
    TextView mTextView3;
    @BindView(R.id.button_ok)
    Button mButtonOk;
    @BindView(R.id.content)
    LinearLayout mContent;

    private LabelPrintConfigPresenter mPresenter;
    private LabelPrinterConfig mPrintSetting;
    private int mPrintType = PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH;
    private boolean mLabelCheck;

    public static LabelPrintConfigFragment newInstance() {
        LabelPrintConfigFragment fragment = new LabelPrintConfigFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_label_config;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mContent.setVisibility(View.GONE);
        showLoadingView();
        getLabelPrintConfig();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        getLabelPrintConfig();
    }

    private void getLabelPrintConfig() {
        mPresenter.getLabelPrintConfig(UserHelper.getEntityId(), UserHelper.getUserId());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        init();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSwitchLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setViewGonOrVisibility(isChecked);
            }
        });
    }

    private void init() {
        mLabelCheck = BaseSpHelper.isUseLabelPrinter(getActivity());
        initPrint();
    }

    private void initPrint() {
        mPrintSetting = LabelPrintManager.getPrinterSetting();
        if (mPrintSetting == null) {
            mPrintSetting = new LabelPrinterConfig();
        } else {
            mPrintType = mPrintSetting.getPrinterType();
        }
    }

    @Override
    protected void updateBlueTooth(BluetoothDevice device) {
        updateAboutAllBlueTooth(device);
    }

    @Override
    protected void updatePrinterDialog(int type) {
        mPrintType = type;
        mPrintSetting.setPrinterType(mPrintType);
        updateViewByPrintType();
    }

    @Override
    protected void updateBlueToothIcon() {
        updateBlueToothName();
        setBlueToothIcon();
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }

    /**
     * 保存
     */
    public void save() {
        if (mSwitchLabel.isChecked()) {
            if (mPresenter.check(mPrintType, getIp(), getBlueToothName())) {
                saveFunctionSwitchList();
            }
        } else {
            saveFunctionSwitchList();
        }
    }

    /**
     * 保存标签开关
     */
    private void saveFunctionSwitchList() {
        SwitchRequest request = new SwitchRequest();
        request.setCode(SystemDirCodeConstant.FunctionFielCode.PRINT_LABEL);
        request.setValue(mSwitchLabel.isChecked() ? Base.STRING_TRUE : Base.STRING_FALSE);
        mPresenter.saveFunctionSwitchList(UserHelper.getEntityId(), Arrays.asList(request));
    }

    /**
     * 保存标签打印信息
     */
    private void saveLabelPrintConfig() {
        mPresenter.saveLabelPrintConfig(UserHelper.getEntityId(), UserHelper.getUserId(), mPrintType, getIp());
    }

    /**
     * 更新打印机类型
     */
    private void updateViewByPrintType() {
        switch (mPrintType) {
            case PrintConfigConstant.PrinterType.PRINT_TYPE_NET:
                mTextPrintType.setText(getString(R.string.net_printer));
                mRelativeNet.setVisibility(View.VISIBLE);
                mRelativeBlueTooth.setVisibility(View.GONE);
                break;
            case PrintConfigConstant.PrinterType.PRINT_TYPE_BLUE_TOOTH:
                mTextPrintType.setText(getString(R.string.blue_booth_printer));
                mRelativeBlueTooth.setVisibility(View.VISIBLE);
                mRelativeNet.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * update init view
     */
    private void updateViewByPrintData() {
        setBlueToothIcon();
        if (mPrintSetting != null) {
            mTextLabelByte.setText(StringUtils.notNull(mPrintSetting.getLabelType()));
            mTextBlueBoothName.setText(StringUtils.notNull(mPrintSetting.getBluetoothName()));
            mEditInputIp.setText(StringUtils.notNull(mPrintSetting.getIp()));
            mEditInputIp.setSelection(StringUtils.notNull(mPrintSetting.getIp()).length());
        }
    }

    /**
     * 更新蓝牙相关所有操作
     *
     * @param device
     */
    private void updateAboutAllBlueTooth(BluetoothDevice device) {
        try {
            SystemClock.sleep(600);
            bindBlueTooth(device);
            mTextBlueBoothName.setText(device.getName());
            setBlueIcon();
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


    @Override
    public void setPresenter(LabelPrintConfigContract.Presenter presenter) {
        mPresenter = (LabelPrintConfigPresenter) presenter;
    }

    @Override
    public void saveLabelSwitchSuccess() {
        if (mSwitchLabel.isChecked()) {
            saveLabelPrintConfig();
        } else {
            showToast(getString(R.string.save_success));
            BaseSpHelper.saveUseLabelPrinter(getActivity(), mSwitchLabel.isChecked());
            getActivity().finish();
        }
    }

    @Override
    public void saveLabelPrintConfigSuccess() {
        showToast(getString(R.string.save_success));
        BaseSpHelper.saveUseLabelPrinter(getActivity(), mSwitchLabel.isChecked());
        savePrinterSettingConfig();
        getActivity().finish();
    }

    @Override
    public String getIp() {
        return mEditInputIp.getText().toString().trim();
    }

    @Override
    public String getBlueToothName() {
        return mTextBlueBoothName.getText().toString().trim();
    }

    @Override
    public void getLabelPrintConfigSuccess(LabelPrinterConfig data) {
        mContent.setVisibility(View.VISIBLE);
        showContentView();
        if (data != null) {
            mPrintType = data.getPrinterType();
            mPrintSetting.setPrinterType(mPrintType);
            mPrintSetting.setIp(StringUtils.notNull(data.getIp()));
            mPrintSetting.setLabelType(StringUtils.notNull(data.getLabelType()));
        }
        updateViewByPrintType();
        updateViewByPrintData();
        mSwitchLabel.setChecked(mLabelCheck);
        setViewGonOrVisibility(mLabelCheck);
    }

    @Override
    public void showErrorLoadView(String errorMessage) {
        mContent.setVisibility(View.GONE);
        showErrorView(errorMessage);
    }

    @Override
    public void showErrorToast(String errorMessage) {
        showToast(errorMessage);
    }

    @OnClick({R.id.relative_print_type, R.id.relative_blue_tooth, R.id.button_ok})
    void doClick(View view) {
        switch (view.getId()) {
            case R.id.relative_print_type:
                showPrintTypeDialog(TYPE_LABEL);
                break;
            case R.id.relative_blue_tooth:
                gotoSetBlueBoothActivity();
                break;
            case R.id.button_ok:
                printTestLabel();
                break;
        }
    }

    /**
     * 打印测试页
     */
    private void printTestLabel() {
        if (mPresenter.check(mPrintType, getIp(), getBlueToothName())) {
            try {
                AssetManager assetManager = getActivity().getAssets();
                InputStream in = assetManager.open("printerLabelTest.temp");
                byte[] content = ByteUtils.readByStream(in);
                CcdPrintHelper.printLabelTest(getActivity(), getIp(), content, mPrintType, mPrintSetting);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存标签本地数据
     */
    private void savePrinterSettingConfig() {
        mPrintSetting.setEntityId(UserLocalPrefsCacheSource.getEntityId(getActivity()));
        mPrintSetting.setIp(getIp());
        LabelPrintManager.setPrinterSetting(mPrintSetting);
        LabelPrintManager.saveToPref(getActivity());
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
                    // 标签socket
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(mPrintSetting.getUuid());
                    socket.connect();
                    BlueToothSocketManager.getInstance().addSocket(device.getAddress(), socket);
                    // 存储数据
                    mPrintSetting.setMac(device.getAddress());
                    mPrintSetting.setBlueType(PrintConfigConstant.TicketType.TYPE_LABEL_TICKET);
                    LabelPrintManager.setPrinterSetting(mPrintSetting);
                    LabelPrintManager.saveToPref(getActivity());
                    Logger.d("蓝牙打印机适配成功，请按返回键回到云收银");
                    Toast.makeText(getActivity(), getString(R.string.bluetooth_adapter_success), Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置view的显示和不显示
     *
     * @param isCheck true:false
     */
    private void setViewGonOrVisibility(boolean isCheck) {
        int disPlay = isCheck ? View.VISIBLE : View.GONE;
        mRelativeBlueTooth.setVisibility(disPlay);
        mRelativeNet.setVisibility(disPlay);
        mRelativePrintType.setVisibility(disPlay);
        mLinearLabelModelNumber.setVisibility(disPlay);
        mTextView3.setVisibility(disPlay);
        mButtonOk.setVisibility(disPlay);
        if (isCheck) {
            updateViewByPrintType();
        }
    }
}
