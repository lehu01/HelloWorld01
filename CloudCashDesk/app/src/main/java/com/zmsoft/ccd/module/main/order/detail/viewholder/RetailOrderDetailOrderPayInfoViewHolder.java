package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.pay.Pay;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/23 13:48
 */
public class RetailOrderDetailOrderPayInfoViewHolder extends RetailOrderDetailBaseViewHolder {

    @BindView(R.id.image_pay_info_icon)
    ImageView mImagePayInfoIcon;
    @BindView(R.id.text_pay_info)
    TextView mTextPayInfo;
    @BindView(R.id.image_clear)
    ImageView mImageClear;

    public RetailOrderDetailOrderPayInfoViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getPay());
        }
    }

    private void fillView(Pay pay) {
        if (pay != null) {
            if (pay.isEndPay() || pay.isTakeOutOrder()) {
                mImageClear.setVisibility(View.GONE);
            } else {
                mImageClear.setVisibility(View.VISIBLE);
            }
            mImagePayInfoIcon.setImageResource(BusinessHelper.getPayTypeIcon(pay.getPayType()));
            StringBuilder stringBuilder = new StringBuilder();
            String name = pay.getCustomerName();
            if (StringUtils.isEmpty(name)) {
                name = getContext().getString(R.string.default_customer_name);
            }
            stringBuilder.append(name);
            stringBuilder.append(getContext().getString(R.string.in));
            stringBuilder.append(TimeUtils.getTimeStr(pay.getPayTime(), TimeUtils.FORMAT_TIME_SECONDS));
            stringBuilder.append(pay.getKindPayName());
            stringBuilder.append(getContext().getString(R.string.payed));
            stringBuilder.append(UserHelper.getCurrencySymbol());
            stringBuilder.append(FeeHelper.getDecimalFee(pay.getPayAmount()));
            mTextPayInfo.setText(stringBuilder.toString());

            if (!StringUtils.isEmpty(pay.getPayNo())) {
                StringBuilder sb = new StringBuilder();
                sb.append(getContext().getString(R.string.left_brackets));
                if (pay.getPayType() == BusinessHelper.ReceiptMethod.RECEIPT_METHOD_VIP) {
                    sb.append(getContext().getString(R.string.vip_number));
                    sb.append(pay.getCode());
                } else {
                    sb.append(pay.getKindPayName());
                    sb.append(getContext().getString(R.string.order_pay_order_number));
                    sb.append(pay.getPayNo());
                }
                sb.append(getContext().getString(R.string.right_brackets));
            }
        }
    }
}
