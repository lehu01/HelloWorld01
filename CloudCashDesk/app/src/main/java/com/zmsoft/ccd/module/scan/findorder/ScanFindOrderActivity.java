package com.zmsoft.ccd.module.scan.findorder;

import android.os.Bundle;

import com.chiclaim.modularization.router.MRouter;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.scan.dagger.DaggerScanSourceComponent;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetail;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.module.scan.findorder.dagger.DaggerScanFindOrderComponent;
import com.zmsoft.ccd.module.scan.findorder.dagger.ScanFindOrderModule;
import com.zmsoft.scan.lib.scan.BaseQrScanActivity;

import javax.inject.Inject;

import static com.zmsoft.ccd.module.scan.findorder.ScanFindOrderActivity.PATH;

/**
 * Description：扫码找单
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/25 10:32
 */
@Route(path = PATH)
public class ScanFindOrderActivity extends BaseQrScanActivity implements ScanFindOrderContract.View {

    public static final String PATH = "/main/scanFindOrder";

    @Inject
    ScanFindOrderPresenter mPresenter;

    private ScanSeat mScanSeat;

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
            mPresenter.getOrderDetailBySeatCode(UserHelper.getEntityId(), seatCode, UserHelper.getUserId());
        } else {
            mCodeScanView.restartScan();
            showToast(getString(R.string.toast_please_scan_seat));
        }
    }

    @Override
    protected void init() {
        initDagger();
        initHintText();
    }

    @Override
    protected int bizType() {
        return TYPE_SCAN_SEAT;
    }

    private void initDagger() {
        DaggerScanFindOrderComponent.builder()
                .scanSourceComponent(DaggerScanSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .scanFindOrderModule(new ScanFindOrderModule(this))
                .build()
                .inject(this);
    }

    private void initHintText() {
        mTextScanPrompt.setText(getString(R.string.please_seat_code));
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void setPresenter(ScanFindOrderContract.Presenter presenter) {
        mPresenter = (ScanFindOrderPresenter) presenter;
    }

    @Override
    public void loadOrderDetailSuccess(OrderDetail orderDetail) {
        if (orderDetail == null) {
            String seatCode = mScanSeat.getSeatCode();
            if (!StringUtils.isEmpty(seatCode)) {
                mPresenter.getSeatBySeatCode(UserHelper.getEntityId(), seatCode);
            }
        } else {
            gotoOrderDetailActivity(orderDetail);
        }
    }

    @Override
    public void loadDataError(String errorMessage) {
        mCodeScanView.restartScan();
        showToast(errorMessage);
    }

    @Override
    public void loadSeatBySeatCodeSuccess(Seat seat) {
        if (seat != null) {
            gotoCreateOrUpdateOrderActivity(seat);
        } else {
            mCodeScanView.restartScan();
            showToast(getString(R.string.qr_seat_failure));
        }
    }

    /**
     * 跳转到详情界面
     */
    private void gotoOrderDetailActivity(OrderDetail orderDetail) {
        MRouter.getInstance().build(RouterPathConstant.OrderDetail.PATH)
                .putInt(RouterPathConstant.OrderDetail.EXTRA_FROM, RouterPathConstant.OrderDetail.EXTRA_FROM_FIND_ORDER)
                .putSerializable(RouterPathConstant.OrderDetail.EXTRA_ORDER_DETAIL, orderDetail)
                .navigation(this);
        finish();
    }

    /**
     * 跳转到开单界面
     */
    private void gotoCreateOrUpdateOrderActivity(Seat seat) {
        OrderParam param = new OrderParam();
        param.setSeatName(seat.getName());
        param.setSeatCode(seat.getCode());

        MRouter.getInstance().build(RouterPathConstant.CreateOrUpdateOrder.PATH)
                .putString(RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM, RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM_CREATE)
                .putSerializable(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM, param)
                .navigation(this);
        finish();
    }
}
