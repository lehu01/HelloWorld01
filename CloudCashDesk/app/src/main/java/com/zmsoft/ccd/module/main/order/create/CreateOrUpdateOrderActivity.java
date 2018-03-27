package com.zmsoft.ccd.module.main.order.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chiclaim.modularization.router.annotation.Autowired;
import com.chiclaim.modularization.router.annotation.Route;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.source.ordercreateorupdate.dagger.DaggerCreateOrUpdateSourceComponent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.constant.RouterPathConstant;
import com.zmsoft.ccd.lib.base.helper.ActivityHelper;
import com.zmsoft.ccd.lib.bean.order.create.OrderParam;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.module.main.order.create.fragment.CreateOrUpdateOrderFragment;
import com.zmsoft.ccd.module.main.order.create.fragment.CreateOrUpdateOrderPresenter;
import com.zmsoft.ccd.module.main.order.create.fragment.dagger.CreateOrUpdateOrderPresenterModule;
import com.zmsoft.ccd.module.main.order.create.fragment.dagger.DaggerCreateOrUpdateOrderPresenterComponent;
import com.zmsoft.ccd.module.main.order.memo.MemoListActivity;
import com.zmsoft.ccd.module.main.seat.selectseat.SelectSeatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/3/29 19:11
 */
@Route(path = RouterPathConstant.CreateOrUpdateOrder.PATH)
public class CreateOrUpdateOrderActivity extends ToolBarActivity {

    // Result code
    public static final int RESULT_REMARK_CODE = 3001; // 订单备注
    public static final int RESULT_SELECT_SEAT_CODE = 3002; // 选桌


    // Router
    @Autowired(name = RouterPathConstant.CreateOrUpdateOrder.EXTRA_FROM)
    String mFrom;
    OrderParam createOrderParam;

    private CreateOrUpdateOrderFragment fragment;

    @Inject
    CreateOrUpdateOrderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_order);
        ButterKnife.bind(this);

        // 获取数据
        Intent intent = getIntent();
        createOrderParam = (OrderParam) intent.getSerializableExtra(RouterPathConstant.CreateOrUpdateOrder.EXTRA_ORDER_PARAM);

        fragment = (CreateOrUpdateOrderFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_create_or_update_order_content);

        if (fragment == null) {
            fragment = CreateOrUpdateOrderFragment.newInstance(mFrom, createOrderParam);
            ActivityHelper.showFragment(getSupportFragmentManager(), fragment, R.id.frame_create_or_update_order_content);
        }

        // dagger
        DaggerCreateOrUpdateOrderPresenterComponent.builder()
                .createOrUpdateSourceComponent(DaggerCreateOrUpdateSourceComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .createOrUpdateOrderPresenterModule(new CreateOrUpdateOrderPresenterModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_REMARK_CODE) { // 订单备注
                String result = data.getStringExtra(MemoListActivity.EXTRA_MEMO);
                fragment.updateRemark(result);
            } else if (requestCode == RESULT_SELECT_SEAT_CODE) { // 座位列表选桌
                Seat seat = (Seat) data.getSerializableExtra(SelectSeatActivity.EXTRA_SEAT);
                if (seat != null) {
                    fragment.updateSeatName(seat);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            hideSoftInputMethod();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
