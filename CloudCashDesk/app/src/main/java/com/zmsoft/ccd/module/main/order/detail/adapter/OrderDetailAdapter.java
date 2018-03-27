package com.zmsoft.ccd.module.main.order.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailFootViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderInfoViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderInstanceViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderPayInfoViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderSuitChildInstanceViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderSuitInstanceViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailOrderUserInfoViewHolder;
import com.zmsoft.ccd.module.main.order.detail.viewholder.OrderDetailTakeoutDistributionInfoViewHolder;

import java.util.List;


/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/21 14:15
 */
public class OrderDetailAdapter extends BaseListAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public OrderDetailAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case OrderDetailItem.ITEM_TYPE_ORDER_INFO: // 2.订单信息
                return new OrderDetailOrderInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_info, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_PAY_INFO: // 3.支付信息
                return new OrderDetailOrderPayInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_pay_lsit, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_USER_INFO: // 5.用户信息
                return new OrderDetailOrderUserInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_user, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_INSTANCE: // 4.菜肴列表
                return new OrderDetailOrderInstanceViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_list_instance, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE: // 6.套菜
                return new OrderDetailOrderSuitInstanceViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_suit_list_header, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE: // 7.套菜子菜
                return new OrderDetailOrderSuitChildInstanceViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_list_suit_child_instance, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_INSTANCE_ALL: // 8.菜肴统计
                return new OrderDetailFootViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_foot_view, parent, false), this);
            case OrderDetailItem.ITEM_TYPE_TAKEOUT_ADDRESS: // 9.外卖信息
                return new OrderDetailTakeoutDistributionInfoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_order_detail_takeout_distribution, parent, false), this);
            default:
                return new BaseHolder(mContext, new LinearLayout(mContext)) {
                    @Override
                    protected void bindView(List source, Object bean) {

                    }
                };
        }
    }

    @Override
    public int getItemViewType(int position) {
        OrderDetailItem item = (OrderDetailItem) getList().get(position);
        return item.getType();
    }
}
