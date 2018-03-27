package com.zmsoft.ccd.module.takeout.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.module.takeout.DaggerCommentManager;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.delivery.dagger.DaggerDeliveryComponent;
import com.zmsoft.ccd.module.takeout.delivery.dagger.DeliveryPresenterModule;
import com.zmsoft.ccd.module.takeout.delivery.helper.DeliveryHelper;
import com.zmsoft.ccd.module.takeout.delivery.presenter.DeliveryPresenter;
import com.zmsoft.ccd.takeout.bean.Takeout;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 配送页
 *
 * @author DangGui
 * @create 2017/8/18.
 */
public class DeliveryActivity extends ToolBarActivity {
    @Inject
    DeliveryPresenter mPresenter;
    /**
     * 外卖订单信息（单独配送）
     */
    private Takeout mTakeout;
    /**
     * 待配送订单（批量配送）
     */
    private ArrayList<String> mOrderCodes;
    /**
     * 待配送订单（批量配送）
     */
    private ArrayList<String> mOrderIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framelayout);
        DeliveryFragment fragment = (DeliveryFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        if (fragment == null) {
            mTakeout = getIntent().getParcelableExtra(DeliveryHelper.ExtraParams.PARAM_TAKE_OUT);
            mOrderCodes = getIntent().getStringArrayListExtra(DeliveryHelper.ExtraParams.PARAM_ORDER_CODES);
            mOrderIds = getIntent().getStringArrayListExtra(DeliveryHelper.ExtraParams.PARAM_ORDER_IDS);
            fragment = DeliveryFragment.newInstance(mTakeout, mOrderCodes, mOrderIds);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.content);
        }
        DaggerDeliveryComponent.builder()
                .deliveryPresenterModule(new DeliveryPresenterModule(fragment))
                .takeoutSourceComponent(DaggerCommentManager.get().getTakeoutSourceComponent())
                .build()
                .inject(this);
    }

    public static void launchActivity(Fragment fragment, Takeout takeout, ArrayList<String> orderCodes
            , ArrayList<String> orderIds, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), DeliveryActivity.class);
        intent.putExtra(DeliveryHelper.ExtraParams.PARAM_TAKE_OUT, takeout);
        intent.putStringArrayListExtra(DeliveryHelper.ExtraParams.PARAM_ORDER_CODES, orderCodes);
        intent.putStringArrayListExtra(DeliveryHelper.ExtraParams.PARAM_ORDER_IDS, orderIds);
        fragment.startActivityForResult(intent, requestCode);
    }
}
