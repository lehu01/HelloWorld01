package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.TakeoutHelper;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailTakeoutInfoVo;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/4 15:03
 *     desc  : 外卖单配送信息
 * </pre>
 */
public class OrderDetailTakeoutDistributionInfoViewHolder extends OrderDetailBaseViewHolder {

    @BindView(R.id.text_receive_people_name_and_phone)
    TextView mTextReceivePeopleNameAndPhone;
    @BindView(R.id.text_receive_people_address)
    TextView mTextReceivePeopleAddress;
    @BindView(R.id.text_distribution_way)
    TextView mTextDistributionWay;
    @BindView(R.id.text_time_way)
    TextView mTextTimeWay;
    @BindView(R.id.text_time)
    TextView mTextTime;

    public OrderDetailTakeoutDistributionInfoViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getTakeoutInfo());
        }
    }

    public void fillView(OrderDetailTakeoutInfoVo info) {
        if (info == null) {
            return;
        }
        mTextReceivePeopleNameAndPhone.setText(String.format(getContext().getString(R.string.receive_name_and_phone)
                , StringUtils.notNull(info.getName())
                , StringUtils.notNull(info.getPhone())));
        mTextDistributionWay.setText(String.format(getContext().getString(R.string.receive_distribution_way)
                , TakeoutHelper.getDeliveryTypeStr(info.getIsThirdShipping())));
        mTextTime.setText(TimeUtils.getTimeStr(info.getSendTime(), TimeUtils.FORMAT_DATE_TIME));

        // 配送方式
        if (info.getIsThirdShipping() == TakeoutHelper.DELIVERY_INVITE) {
            mTextTimeWay.setText(getContext().getString(R.string.customer_invite_time));
        } else {
            mTextTimeWay.setText(getContext().getString(R.string.customer_request_time));
        }

        // 配送地址
        if (StringUtils.isEmpty(info.getAddress())) {
            mTextReceivePeopleAddress.setVisibility(View.GONE);
        } else {
            mTextReceivePeopleAddress.setVisibility(View.VISIBLE);
            mTextReceivePeopleAddress.setText(info.getAddress());
        }
    }
}
