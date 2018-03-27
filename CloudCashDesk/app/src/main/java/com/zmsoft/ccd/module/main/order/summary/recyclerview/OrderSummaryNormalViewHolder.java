package com.zmsoft.ccd.module.main.order.summary.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 14:22.
 */

public class OrderSummaryNormalViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_order_summary_key)
    public TextView mTextOrderSummaryKey;
    @BindView(R.id.text_order_summary_medium_value)
    public TextView mTextOrderSummaryMediumValue;
    @BindView(R.id.text_order_summary_value)
    public TextView mTextOrderSummaryValue;

    public OrderSummaryNormalViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
