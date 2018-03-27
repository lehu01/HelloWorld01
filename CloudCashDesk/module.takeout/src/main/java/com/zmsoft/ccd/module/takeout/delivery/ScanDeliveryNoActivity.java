package com.zmsoft.ccd.module.takeout.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.helper.DeliveryHelper;
import com.zmsoft.scan.lib.scan.BaseScanActivity;

/**
 * 扫码运单号
 *
 * @author DangGui
 * @create 2017/11/16.
 */

public class ScanDeliveryNoActivity extends BaseScanActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(DEFAULT_LAYOUT);
    }

    @Override
    protected void onScanSuccess(String result) {
        mCodeScanView.pauseScan();
        mCodeScanView.hiddenScanRect();
        mTextScanPrompt.setVisibility(View.INVISIBLE);
        Intent intent = new Intent();
        intent.putExtra(DeliveryHelper.DELIVERY_RESULT_KEY, result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void init() {
        mTextScanPrompt.setText(getString(R.string.module_takeout_delivery_no_please_scan));
    }

    public static void luanchActivity(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), ScanDeliveryNoActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }
}
