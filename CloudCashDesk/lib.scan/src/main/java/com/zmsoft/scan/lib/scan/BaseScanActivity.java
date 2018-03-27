package com.zmsoft.scan.lib.scan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.utils.PermissionUtils;
import com.zmsoft.component.codescanner.CodeScanView;

import java.util.Arrays;


/**
 * Description：扫码父类
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/28 11:20
 */
public abstract class BaseScanActivity extends ToolBarActivity implements CodeScanView.ScanResultCallback {

    public static final int DEFAULT_LAYOUT = -1;
    private static final int RC_CAMERA_PERM = 123;
    protected TextView mTextScanPrompt;
    protected CodeScanView mCodeScanView;
    private int mLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mLayoutId = layoutResID;
        if (layoutResID == DEFAULT_LAYOUT) {
            layoutResID = R.layout.activity_base_scan;
        }
        super.setContentView(layoutResID);
        initView();
        init();
    }

    private void initView() {
        mCodeScanView = (CodeScanView) findViewById(R.id.codescan_view);
        mCodeScanView.setScanResultCallback(this);
        mCodeScanView.setVisibility(View.GONE);
        if (mLayoutId == DEFAULT_LAYOUT) {
            mTextScanPrompt = (TextView) findViewById(R.id.text_prompt);
        }
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        onScanSuccess(result);
        vibrate();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        showToast(getString(R.string.open_camera));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PermissionUtils.hasPermissions(this, Manifest.permission.CAMERA)) {
            startScan();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldInitCamera()) {
            mCodeScanView.onViewResumed();
        }
    }

    public boolean shouldInitCamera() {
        return true;
    }

    @Override
    protected void onStop() {
        mCodeScanView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mCodeScanView.onDestroy();
        super.onDestroy();
    }

    @SuppressLint("MissingPermission")
    protected void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    protected void startSpot() {
        mCodeScanView.restartScan();
    }

    private void startScan() {
        mCodeScanView.setVisibility(View.VISIBLE);
        mCodeScanView.showScanRect();
        startSpot();
    }

    @TargetApi(23)
    void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, RC_CAMERA_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.d("grantResults = " + Arrays.toString(grantResults));
        if (requestCode == RC_CAMERA_PERM && grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startScan();
        } else {
            showCameraRationale();
        }
    }

    private void showCameraRationale() {
        showPermissionRationale(R.string.title_camera_permission_dialog);
    }

    /**
     * @param result 二维码连接地址
     */
    protected abstract void onScanSuccess(String result);

    /**
     * 初始化操作
     */
    protected abstract void init();

}
