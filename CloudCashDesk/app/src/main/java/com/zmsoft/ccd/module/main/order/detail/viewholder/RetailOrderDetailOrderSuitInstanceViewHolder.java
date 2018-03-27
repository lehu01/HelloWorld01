package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.InstanceHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.instance.Instance;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.NumberUtils;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/21 18:31
 */
public class RetailOrderDetailOrderSuitInstanceViewHolder extends RetailOrderDetailBaseViewHolder {

    @BindView(R.id.text_suit_name)
    TextView mTextSuitName;
    @BindView(R.id.text_suit_price)
    TextView mTextSuitPrice;
    @BindView(R.id.text_suit_count)
    TextView mTextSuitCount;

    public RetailOrderDetailOrderSuitInstanceViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getInstance());
        }
    }

    private void fillView(Instance instance) {
        if (instance != null) {
            boolean isReject = InstanceHelper.isRejectInstance(instance.getStatus());
            if (isReject) {
                mTextSuitName.setText(SpannableStringUtils.getBuilder(getContext(), instance.getName())
                        .setStrikethrough()
                        .create());
                mTextSuitPrice.setText(SpannableStringUtils.getBuilder(getContext()
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFee(instance.getFee())))
                        .setStrikethrough()
                        .create());
                mTextSuitCount.setText(SpannableStringUtils.getBuilder(getContext(), StringUtils.appendStr(NumberUtils.doubleToStr(instance.getNum()), getContext().getString(R.string.part)))
                        .setStrikethrough()
                        .create());
            } else {
                if (Base.SHORT_TRUE == instance.getIsWait()) {
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder builder = new SpannableStringBuilder(StringUtils.appendStr(getContext().getString(R.string.wait), instance.getName()));
                    builder.setSpan(redSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTextSuitName.setText(builder);
                } else {
                    mTextSuitName.setText(instance.getName());
                }
                mTextSuitPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , FeeHelper.getDecimalFee(instance.getFee())));
                mTextSuitCount.setText(StringUtils.appendStr(NumberUtils.doubleToStr(instance.getNum()), getContext().getString(R.string.part)));
            }
        }
    }
}
