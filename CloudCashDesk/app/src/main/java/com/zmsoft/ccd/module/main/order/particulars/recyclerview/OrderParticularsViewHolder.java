package com.zmsoft.ccd.module.main.order.particulars.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/18 14:08.
 */

public class OrderParticularsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.content_view_order_particulars)
    RelativeLayout mRelativeLayoutContentView;
    @BindView(R.id.text_order_particulars_order_code)
    TextView mTextOrderParticularsOrderCode;
    @BindView(R.id.text_order_particulars_seat_name)
    TextView mTextOrderParticularsSeatName;
    @BindView(R.id.text_order_particulars_serial_number)
    TextView mTextOrderParticularsSerialNumber;
    @BindView(R.id.text_order_particulars_time_hhmm)
    TextView mTextOrderParticularsTimeHhmm;
    @BindView(R.id.text_order_particulars_consumption)
    TextView mTextOrderParticularsConsumption;


    public OrderParticularsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateView(OrderParticularsItem orderParticularsItem) {
        mTextOrderParticularsOrderCode.setText(orderParticularsItem.getCode());
        mTextOrderParticularsSeatName.setText(orderParticularsItem.getSeatName());
        mTextOrderParticularsSerialNumber.setText(orderParticularsItem.getSerialNumber());
        mTextOrderParticularsTimeHhmm.setText(orderParticularsItem.getTimeHHMM());
        mTextOrderParticularsConsumption.setText(orderParticularsItem.getConsumption());
    }
}
