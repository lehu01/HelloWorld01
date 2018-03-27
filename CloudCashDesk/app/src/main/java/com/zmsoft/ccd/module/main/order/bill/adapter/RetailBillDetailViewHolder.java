package com.zmsoft.ccd.module.main.order.bill.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.RetailBillDetailHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.BillDetail;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.lib.widget.couponview.RelativeLayoutCouponView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zmsoft.ccd.R.string.order_find_retail_code;

/**
 * Created by huaixi on 2017/11/01.
 */
public class RetailBillDetailViewHolder extends BaseHolder {

    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_cashier)
    TextView tvCashier;
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    public RetailBillDetailViewHolder(Context context, View itemView, BaseListAdapter.AdapterClick adapterClick) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 设置锯齿
     */
    private void setSemicircle(boolean top, boolean bottom) {
        if (itemView instanceof LinearLayoutCouponView) {
            ((LinearLayoutCouponView) itemView).setSemicircleTop(top);
            ((LinearLayoutCouponView) itemView).setSemicircleBottom(bottom);
        } else if (itemView instanceof RelativeLayoutCouponView) {
            ((RelativeLayoutCouponView) itemView).setSemicircleTop(top);
            ((RelativeLayoutCouponView) itemView).setSemicircleBottom(bottom);
        }
    }

    public void fillView(Object obj) {
        if (obj instanceof BillDetail) {
            BillDetail billDetail = (BillDetail) obj;
            tvOrderNumber.setText(getString(order_find_retail_code, billDetail.getCode()));
            tvPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , NumberUtils.getDecimalFee(billDetail.getRealFee(), 2)));
            tvTime.setText(TimeUtils.getTimeStr(billDetail.getEndTime(), TimeUtils.FORMAT_TIME));
            tvSerialNumber.setText(getString(R.string.retail_serial_code, billDetail.getOrderNum()));
            tvCashier.setText("(" + billDetail.getCashier() + ")");
            tvSource.setText(getString(R.string.retail_order_from, RetailBillDetailHelper.getRetailOrderFrom(getContext(), billDetail.getOrderFrom())));
        }
    }

    @Override
    protected void bindView(List source, Object bean) {
        fillView(bean);
    }
}
