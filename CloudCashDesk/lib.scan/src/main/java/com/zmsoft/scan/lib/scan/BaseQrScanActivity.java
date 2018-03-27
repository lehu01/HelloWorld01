package com.zmsoft.scan.lib.scan;

import android.os.Bundle;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.ErrorBody;
import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.scan.lib.scan.data.IScanSource;
import com.zmsoft.scan.lib.scan.data.ScanSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description：扫码中间类：包含具体业务逻辑，针对于扫码找单，找桌，点菜公共处理
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/5 19:29
 */
public abstract class BaseQrScanActivity extends BaseScanActivity {

    private final IScanSource iScanSource = new ScanSource();

    public final static int TYPE_SCAN_NULL = 0;
    public final static int TYPE_SCAN_SEAT = 1;
    public final static int TYPE_SCAN_MENU = 2;

    private int mType; // 业务type
    private String mQrResult; // 二维码字符串
    private ScanSeat mScanSeat; // 座位seat对象
    private ScanMenu mScanMenu; // 菜肴menu对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = bizType();
    }

    @Override
    protected void onScanSuccess(String result) {
        if (StringUtils.isEmpty(result)) {
            return;
        }
        //识别成功一次后，等获取到结果后再次进行识别，防止短时间内多次扫码
        mCodeScanView.pauseScan();
        mQrResult = result;
        getQrBean(result);
    }

    private void getQrBean(String result) {
        switch (mType) {
            case TYPE_SCAN_SEAT:
                getScanBySeat(result);
                break;
            case TYPE_SCAN_MENU:
                if (isMenu(result)) {
                    getScanByMenu(result);
                } else {
                    getScanByCode(result);
                }
                break;
            case TYPE_SCAN_NULL:
                showToast(getString(R.string.setting_biz_source));
                mCodeScanView.restartScan();
                break;
            default:
                mCodeScanView.restartScan();
                break;
        }
    }

    private boolean isMenu(String mQrResult) {
        Pattern p = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(mQrResult);
        return matcher.find();
    }

    private void getScanBySeat(String result) {
        showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        iScanSource.getScanSeatByQr(result, new Callback<ScanSeat>() {
            @Override
            public void onSuccess(ScanSeat data) {
                hideLoading();
                if (data == null) {
                    mCodeScanView.restartScan();
                    showToast(getString(R.string.qr_code_error));
                    return;
                }
                if (checkMyShop(data.getEntityId())) {
                    setScanSeat(data);
                    qrScanResult(data);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                hideLoading();
                if (body.getErrorCode().equals("ERR_PUB500003")) {
                    showToast(getString(R.string.qr_code_error));
                } else {
                    showToast(body.getMessage());
                }
                mCodeScanView.restartScan();
            }
        });
    }

    private void getScanByMenu(String result) {
        showLoading(GlobalVars.context.getString(R.string.dialog_waiting), false);
        iScanSource.getScanMenuByQr(result, new Callback<ScanMenu>() {
            @Override
            public void onSuccess(ScanMenu data) {
                hideLoading();
                if (data == null) {
                    mCodeScanView.restartScan();
                    showToast(getString(R.string.qr_code_error));
                    return;
                }
                if (checkMyShop(data.getEntityId())) {
                    setScanMenu(data);
                    qrScanResult(data);
                }
            }

            @Override
            public void onFailed(ErrorBody body) {
                hideLoading();
                if (body.getErrorCode().equals("ERR_PUB500003")) {
                    showToast(getString(R.string.qr_code_error));
                } else {
                    showToast(body.getMessage());
                }
                mCodeScanView.restartScan();
            }
        });
    }

    private void getScanByCode(String result) {
        qrScanResult(result);
    }

    private boolean checkMyShop(String entityId) {
        if (!UserHelper.getEntityId().equals(entityId)) {
            showToast(getString(R.string.qr_code_error));
            vibrate();
            mCodeScanView.restartScan();
            mCodeScanView.pauseScan(1500);
            return false;
        }
        return true;
    }

    protected abstract void qrScanResult(Object object);

    protected abstract void init();

    protected abstract int bizType();

    public String getQrResult() {
        return mQrResult;
    }

    public void setQrResult(String mQrResult) {
        this.mQrResult = mQrResult;
    }

    public ScanSeat getScanSeat() {
        return mScanSeat;
    }

    public void setScanSeat(ScanSeat mScanSeat) {
        this.mScanSeat = mScanSeat;
    }

    public ScanMenu getScanMenu() {
        return mScanMenu;
    }

    public void setScanMenu(ScanMenu mScanMenu) {
        this.mScanMenu = mScanMenu;
    }
}
