package com.zmsoft.ccd.module.main.order.complete.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.RouterBaseEvent;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.complete.CompleteBillVo;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.module.main.order.complete.OrderCompleteContract;
import com.zmsoft.ccd.module.main.order.complete.OrderCompletePresenter;
import com.zmsoft.ccd.module.main.order.particulars.OrderParticularsActivity;
import com.zmsoft.ccd.module.main.order.summary.OrderSummaryActivity;

import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/16 14:09.
 */

public class OrderCompleteFragment extends BaseFragment implements OrderCompleteContract.View {

    @BindView(R.id.refresh_layout_order_complete)
    SwipeRefreshLayout mRefreshLayoutOrderComplete;
    @BindView(R.id.text_order_complete_calendar)
    TextView mTextOrderCompleteCalender;
    @BindView(R.id.text_today_earnings)
    TextView mTextTodayEarnings;
    @BindView(R.id.text_order_total_number)
    TextView mTextOrderTotalNumber;
    @BindView(R.id.text_order_total_amount)
    TextView mTextOrderTotalAmount;
    @BindView(R.id.text_order_completed_number)
    TextView mTextOrderCompletedNumber;
    @BindView(R.id.text_order_uncompleted_number)
    TextView mTextOrderUncompletedNumber;
    @BindView(R.id.text_order_completed_amount)
    TextView mTextOrderCompletedAmount;
    @BindView(R.id.text_order_uncompleted_amount)
    TextView mTextOrderUncompletedAmount;


    @Inject
    public OrderCompletePresenter mPresenter;

    private boolean mNeedReloadData;
    private boolean mIsPausingFragment;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_complete;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mNeedReloadData = false;
        mIsPausingFragment = false;
        initFragmentView();

        showLoadingView();
        setContentViewVisible(false);
        loadNetWorkData();
    }

    @Override
    protected void initListener() {
        mRefreshLayoutOrderComplete.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNetWorkData();
            }
        });
    }

    @Override
    public void unBindPresenterFromView() {
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        loadNetWorkData();
    }

    //================================================================================
    // life cycle
    //================================================================================
    @Override
    public void onResume() {
        super.onResume();
        mIsPausingFragment = false;
        if (mNeedReloadData) {
            mNeedReloadData = false;
            loadNetWorkData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPausingFragment = true;
    }

    //================================================================================
    // init
    //================================================================================
    private void initFragmentView() {
        mRefreshLayoutOrderComplete.setColorSchemeResources(R.color.accentColor, R.color.accentColor, R.color.accentColor, R.color.accentColor);
    }

    //================================================================================
    // update view
    //================================================================================
    private void setContentViewVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mRefreshLayoutOrderComplete.setVisibility(visibility);
    }

    private void updateContentView(CompleteBillVo completeBillVo) {
        String day = TimeUtils.date2String(new Date(), TimeUtils.FORMAT_PATTERN_dd);
        day = day.replaceFirst("^0*", "");  // 去掉前面的0，“日期2017-10-06”只需要显示“6”
        mTextOrderCompleteCalender.setText(day);

        if (null == completeBillVo) {
            completeBillVo = new CompleteBillVo();
        }
        BigDecimal valueTodayEarnings = completeBillVo.getEarningAmount();
        Integer valueOrderTotalNumber = completeBillVo.getOrderTotalNum();
        BigDecimal valueOrderTotalAmount = completeBillVo.getOrderTotalAmount();
        Integer valueOrderCompletedNumber = completeBillVo.getCompleteOrderNum();
        Integer valueOrderUncompletedNumber = completeBillVo.getNotCompleteOrderNum();
        BigDecimal valueOrderCompletedAmount = completeBillVo.getCompleteOrderTotalAmount();
        BigDecimal valueOrderUncompletedAmount = completeBillVo.getNotCompleteOrderTotalAmount();

        String textTodayEarnings = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), getString(R.string.order_summary_value_format), null == valueTodayEarnings ? BigDecimal.ZERO: valueTodayEarnings);
        String textOrderTotalAmount = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), getString(R.string.order_summary_value_format), null == valueOrderTotalAmount ? BigDecimal.ZERO : valueOrderTotalAmount);
        String textOrderCompletedAmount = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), getString(R.string.order_summary_value_format), null == valueOrderCompletedAmount ? BigDecimal.ZERO : valueOrderCompletedAmount);
        String textOrderUncompletedAmount = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), getString(R.string.order_summary_value_format), null == valueOrderUncompletedAmount ? BigDecimal.ZERO : valueOrderUncompletedAmount);

        mTextTodayEarnings.setText(textTodayEarnings);
        mTextOrderTotalNumber.setText(String.valueOf(null == valueOrderTotalNumber ? 0 : valueOrderTotalNumber));
        mTextOrderTotalAmount.setText(textOrderTotalAmount);
        mTextOrderCompletedNumber.setText(String.valueOf(null == valueOrderCompletedNumber ? 0 : valueOrderCompletedNumber));
        mTextOrderUncompletedNumber.setText(String.valueOf(null == valueOrderUncompletedNumber ? 0 : valueOrderUncompletedNumber));
        mTextOrderCompletedAmount.setText(textOrderCompletedAmount);
        mTextOrderUncompletedAmount.setText(textOrderUncompletedAmount);
    }

    //================================================================================
    // onClick
    //================================================================================
    @OnClick(R.id.layout_paid_order_summary)
    public void onClickLayoutSummary() {
        startActivity(new Intent(getActivity(), OrderSummaryActivity.class));
    }

    @OnClick(R.id.layout_paid_order_detail)
    public void onClickLayoutDetail() {
        startActivity(new Intent(getActivity(), OrderParticularsActivity.class));
    }

    //================================================================================
    // OrderCompleteContract.View
    //================================================================================
    @Override
    public void setPresenter(OrderCompleteContract.Presenter presenter) {
        mPresenter = (OrderCompletePresenter) presenter;
    }

    @Override
    public void loadOrderCompleteDataSuccess(CompleteBillVo completeBillVo) {
        updateContentView(completeBillVo);
        setContentViewVisible(true);
        showContentView();
        mRefreshLayoutOrderComplete.setRefreshing(false);
    }

    @Override
    public void loadOrderFindDataError(String errorMessage) {
        setContentViewVisible(false);
        toastMsg(errorMessage);
        showErrorView(errorMessage);
        mRefreshLayoutOrderComplete.setRefreshing(false);
    }

    //================================================================================
    // load
    //================================================================================
    private void loadNetWorkData() {
        String date = TimeUtils.date2String(new Date(), TimeUtils.FORMAT_PATTERN_yyyyMMdd);
        mPresenter.loadOrderCompleteData(date);
    }

    //================================================================================
    // event bus
    //================================================================================
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

    @Subscribe
    public void refreshByPush(RouterBaseEvent event) {
        if (event != null && event == RouterBaseEvent.CommonEvent.EVENT_RELOAD_SEAT_LIST) {
            if (mIsPausingFragment) {
                mNeedReloadData = true;
            } else {
                loadNetWorkData();
            }
        }
    }
}
