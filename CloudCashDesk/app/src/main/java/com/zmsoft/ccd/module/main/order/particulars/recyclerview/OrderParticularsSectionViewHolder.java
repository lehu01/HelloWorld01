package com.zmsoft.ccd.module.main.order.particulars.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/7 10:27.
 */

public class OrderParticularsSectionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_order_particulars_time_yyyymmdd)
    TextView mTextOrderParticularsTimeYyyymmdd;

    public OrderParticularsSectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateDate(String timeYYYYMMDD) {
        mTextOrderParticularsTimeYyyymmdd.setText(timeYYYYMMDD);
    }
}
