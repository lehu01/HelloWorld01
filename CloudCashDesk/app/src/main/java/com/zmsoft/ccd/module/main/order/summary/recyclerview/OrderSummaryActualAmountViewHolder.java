package com.zmsoft.ccd.module.main.order.summary.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 14:30.
 */

public class OrderSummaryActualAmountViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_order_summary_value_actual_amount)
    TextView mTextOrderSummaryActualAmount;
    @BindView(R.id.text_order_summary_value_disregard_turnover)
    TextView mTextOrderSummaryDisregardTurnover;

    public OrderSummaryActualAmountViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
