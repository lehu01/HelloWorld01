package com.zmsoft.ccd.module.scan.findseat;

import android.content.Intent;
import android.os.Bundle;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.scan.dagger.DaggerScanSourceComponent;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.scan.findseat.dagger.DaggerScanFindSeatComponent;
import com.zmsoft.ccd.module.scan.findseat.dagger.ScanFindSeatModule;
import com.zmsoft.scan.lib.scan.BaseQrScanActivity;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.scan.findseat.ScanFindSeatActivity.PATH;

/**
 * Description：扫码选座
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:36
 */
@Route(path = PATH)
public class ScanFindSeatActivity extends BaseQrScanActivity implements ScanFindSeatContract.View {

    public static final String PATH = "/main/scanFindSeat";
    public static final String EXTRA_SEAT = "seat";                 // 本次扫码选中的桌位
    public static final String EXTRA_FROM = "scan_find_seat_extra_from";

    public interface EXTRA_FROM_VALUE {
        String OPEN_SEAT = "open_seat";     // 开单
        String CHANGE_SEAT = "change_seat"; // 改单
    }

    @Autowired(name = EXTRA_FROM)
    String mFrom;

    @Inject
    ScanFindSeatPresenter mPresenter;

    private ScanSeat mScanSeat;             // 本次扫码选中的桌位


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(DEFAULT_LAYOUT);
    }

    @Override
    protected void qrScanResult(Object result) {
        if (result instanceof ScanSeat) {
            mScanSeat = (ScanSeat) result;
        }
        if (mScanSeat == null) {
            mCodeScanView.restartScan();
            showToast(getString(R.string.toast_please_scan_seat));
            return;
        }
        String seatCode = mScanSeat.getSeatCode();
        if (!StringUtils.isEmpty(seatCode)) {
            mPresenter.getSeatBySeatCode(UserHelper.getEntityId(), seatCode);
        } else {
            mCodeScanView.restartScan();
            showToast(getString(R.string.toast_please_scan_seat));
        }
    }

    @Override
    protected void init() {
        initDagger();
        initHitTxt();
        initTitleText();
    }

    @Override
    protected int bizType() {
        return TYPE_SCAN_SEAT;
    }

    //================================================================================
    // init
    //================================================================================
    private void initHitTxt() {
        mTextScanPrompt.setText(getString(R.string.please_qr_seat));
    }

    private void initDagger() {
        DaggerScanFindSeatComponent.builder()
                .scanSourceComponent(DaggerScanSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .scanFindSeatModule(new ScanFindSeatModule(this))
                .build()
                .inject(this);
    }

    private void initTitleText() {
        if (null == mFrom) {
            return;
        }
        if (EXTRA_FROM_VALUE.OPEN_SEAT.equals(mFrom)) {
            setTitle(getString(R.string.qr_open_seat));
        } else if (EXTRA_FROM_VALUE.CHANGE_SEAT.equals(mFrom)) {
            setTitle(getString(R.string.qr_change_seat));
        }
    }

    //================================================================================
    // ScanFindSeatContract.View
    //================================================================================
    @Override
    public void setPresenter(ScanFindSeatContract.Presenter presenter) {
        mPresenter = (ScanFindSeatPresenter) presenter;
    }

    @Override
    public void loadDataError(String errorMessage) {
        mCodeScanView.restartScan();
        showToast(errorMessage);
    }

    @Override
    public void loadSeatBySeatCodeSuccess(Seat seat) {
        com.zmsoft.ccd.lib.bean.table.Seat newSeat = new com.zmsoft.ccd.lib.bean.table.Seat();
        newSeat.setSeatName(seat.getName());
        newSeat.setSeatCode(seat.getCode());
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SEAT, newSeat);
        setResult(RESULT_OK, intent);
        finish();
    }
}
