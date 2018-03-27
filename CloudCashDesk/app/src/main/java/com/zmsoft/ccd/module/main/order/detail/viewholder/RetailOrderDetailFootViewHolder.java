package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.bean.instance.statistics.CategoryInfo;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/27 14:54
 */
public class RetailOrderDetailFootViewHolder extends RetailOrderDetailBaseViewHolder {

    @BindView(R.id.text_order_instance_content)
    TextView mTextOrderInstanceContent;

    public RetailOrderDetailFootViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getCategoryInfoVo());
        }
    }

    public void fillView(CategoryInfo categoryInfo) {
        if (categoryInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getContext().getString(R.string.total));
            sb.append(categoryInfo.getTotalNum());
            sb.append(getContext().getString(R.string.retail_item));
            mTextOrderInstanceContent.setText(sb.toString());
        }
    }
}

