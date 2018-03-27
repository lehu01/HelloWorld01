package com.zmsoft.ccd.module.main.order.summary.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ccd.lib.print.helper.CcdPrintHelper;
import com.ccd.lib.print.constants.PrintBizTypeConstants;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.module.main.order.summary.OrderSummaryContract;
import com.zmsoft.ccd.module.main.order.summary.OrderSummaryPresenter;
import com.zmsoft.ccd.module.main.order.summary.recyclerview.OrderSummaryAdapter;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/17 09:55.
 */

public class OrderSummaryFragment extends BaseFragment implements OrderSummaryContract.View {

    @BindView(R.id.refresh_layout_order_summary)
    SwipeRefreshLayout mRefreshLayoutOrderSummary;
    @BindView(R.id.recycler_order_summary)
    public RecyclerView mRecyclerViewOrderSummary;
    @BindView(R.id.text_order_summary_print)
    TextView mTextOrderSummaryPrint;

    private OrderSummaryAdapter mOrderSummaryAdapter;

    @Inject
    public OrderSummaryPresenter mPresenter;

    // 点击“确定”后的选项
    private String mSelectedDateCode;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_summary;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initFragmentData();
        initFragmentView();

        showLoadingView();
        loadNetWorkData();
    }

    @Override
    protected void initListener() {
        mRefreshLayoutOrderSummary.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
    // OrderSummaryContract.View
    //================================================================================
    @Override
    public void setPresenter(OrderSummaryContract.Presenter presenter) {
        mPresenter = (OrderSummaryPresenter) presenter;
    }

    @Override
    public void loadOrderCompleteDataSuccess(List<BillSummaryVo> billSummaryVos) {
        mOrderSummaryAdapter.updateDataAndNotify(billSummaryVos);
        setContentViewVisible(true);
        showContentView();
        mRefreshLayoutOrderSummary.setRefreshing(false);
        mTextOrderSummaryPrint.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadOrderCompleteDataError(String errorMessage) {
        setContentViewVisible(false);
        toastMsg(errorMessage);
        showErrorView(errorMessage);
        mRefreshLayoutOrderSummary.setRefreshing(false);
        mTextOrderSummaryPrint.setVisibility(View.GONE);
    }

    //================================================================================
    // init
    //================================================================================
    private void initFragmentData() {
        mSelectedDateCode = OrderRightFilterItem.CodeDate.CODE_TODAY;
    }

    private void initFragmentView() {
        mRecyclerViewOrderSummary.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOrderSummaryAdapter = new OrderSummaryAdapter(getActivity());
        mRecyclerViewOrderSummary.setAdapter(mOrderSummaryAdapter);

        mRefreshLayoutOrderSummary.setColorSchemeResources(R.color.accentColor, R.color.accentColor, R.color.accentColor, R.color.accentColor);

        setContentViewVisible(false);
    }

    //================================================================================
    // update view
    //================================================================================
    private void setContentViewVisible(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mRefreshLayoutOrderSummary.setVisibility(visibility);
    }

    //================================================================================
    // onClick
    //================================================================================
    @OnClick(R.id.text_order_summary_print)
    public void onClickOrderSummaryPrint() {
        CcdPrintHelper.printBill(getActivity(), getBillType(mSelectedDateCode));
    }

    //================================================================================
    // load
    //================================================================================
    public void reloadByFilterCondition(String codeDate) {
        mSelectedDateCode = codeDate;
        showLoadingView();
        loadNetWorkData();
    }

    private void loadNetWorkData() {
        loadNetWorkData(mSelectedDateCode);
    }

    private void loadNetWorkData(String codeDate) {
        long todayLong = new Date().getTime();
        String today = TimeUtils.millis2String(todayLong, TimeUtils.FORMAT_PATTERN_yyyyMMdd);
        long yesterdayLong = todayLong - TimeUtils.DAY * 1000L;
        String yesterday = TimeUtils.millis2String(yesterdayLong, TimeUtils.FORMAT_PATTERN_yyyyMMdd);

        String startDate;
        String endDate;
        if (OrderRightFilterItem.CodeDate.CODE_TODAY.equals(codeDate)) {
            startDate = today;
            endDate = today;
        } else if (OrderRightFilterItem.CodeDate.CODE_YESTERDAY.equals(codeDate)) {
            startDate = yesterday;
            endDate = yesterday;
        } else if (OrderRightFilterItem.CodeDate.CODE_WITHIN_TWO_DAYS.equals(codeDate)) {
            startDate = yesterday;
            endDate = today;
        } else {
            return;
        }
        mPresenter.loadOrderSummaryData(startDate, endDate);
    }

    /**
     * 获取打印账单汇总所有打印的billType
     *
     * @param dateCode 筛选code
     * @return int
     */
    private int getBillType(String dateCode) {
        if (OrderRightFilterItem.CodeDate.CODE_TODAY.equals(dateCode)) {
            return PrintBizTypeConstants.OrderSummaryType.BILL_TYPE_TODAY;
        } else if (OrderRightFilterItem.CodeDate.CODE_YESTERDAY.equals(dateCode)) {
            return PrintBizTypeConstants.OrderSummaryType.BILL_TYPE_YESTERDAY;
        } else if (OrderRightFilterItem.CodeDate.CODE_WITHIN_TWO_DAYS.equals(dateCode)) {
            return PrintBizTypeConstants.OrderSummaryType.BILL_TYPE_TOW_DAYS;
        }
        return PrintBizTypeConstants.OrderSummaryType.BILL_TYPE_TODAY;
    }
}
