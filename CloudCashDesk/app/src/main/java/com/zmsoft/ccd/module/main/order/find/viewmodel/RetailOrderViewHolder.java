package com.zmsoft.ccd.module.main.order.find.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.Order;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zmsoft.ccd.R.string.order_find_retail_code;

/**
 * Created by huaixi on 2017/10/28.
 */

public class RetailOrderViewHolder extends BaseHolder {

    @BindView(R.id.text_order_number)
    TextView textOrderNumber;
    @BindView(R.id.text_order_role)
    TextView textOrderRole;
    @BindView(R.id.text_order_serial_number)
    TextView textOrderSerialNumber;
    @BindView(R.id.text_order_price)
    TextView textOrderPrice;
    @BindView(R.id.text_order_open_time)
    TextView textOrderOpenTime;
    @BindView(R.id.image_order_sate)
    ImageView imageOrderSate;
    @BindView(R.id.text_order_state)
    TextView textOrderState;

    private BaseListAdapter.AdapterClick mAdapterClick;
    private Order order;

    public RetailOrderViewHolder(Context context, View itemView, BaseListAdapter.AdapterClick adapterClick) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        this.mAdapterClick = adapterClick;
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null != bean)
            order = (Order) bean;
        if (null != order) {
            textOrderNumber.setText(getString(order_find_retail_code, order.getCode()));
            textOrderPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , NumberUtils.getDecimalFee(order.getRealPrice(), 2)));
            textOrderOpenTime.setText(TimeUtils.getTimeStr(order.getOpenTime(), TimeUtils.FORMAT_DATE_TIME));
            textOrderRole.setText("(" + order.getCashier() + ")");
            textOrderSerialNumber.setText(getString(R.string.retail_serial_code, order.getInnerCode()));
            setOrderState(order);
        }
    }

    private void setOrderState(Order order) {
        int type = order.getPayStatus();
        switch (type) {
            case 1:
                imageOrderSate.setImageResource(R.drawable.icon_seat_and_order_list_no_pay);
                textOrderState.setTextColor(getColor(R.color.order_list_red));
                textOrderState.setText(getString(R.string.no_pay));
                break;
            case 2:
                imageOrderSate.setImageResource(R.drawable.icon_seat_and_order_list_pay_all);
                textOrderState.setTextColor(getColor(R.color.order_list_green));
                textOrderState.setText(getString(R.string.pay_all));
                break;
            case 3:
                imageOrderSate.setImageResource(R.drawable.icon_seat_and_order_list_no_pay_all);
                textOrderState.setTextColor(getColor(R.color.order_list_orange));
                textOrderState.setText(getString(R.string.no_pay_all));
                break;
        }
    }
}
