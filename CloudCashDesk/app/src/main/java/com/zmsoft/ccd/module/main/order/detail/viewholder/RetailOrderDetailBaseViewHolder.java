package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.lib.widget.couponview.RelativeLayoutCouponView;
import com.zmsoft.ccd.module.main.order.detail.adapter.RetailOrderDetailAdapter;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/26 18:40
 */
public class RetailOrderDetailBaseViewHolder extends BaseHolder {

    protected View itemView;
    private List<OrderDetailItem> data;
    private RetailOrderDetailAdapter orderDetailAdapter;

    public RetailOrderDetailBaseViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView);
        if (null != adapter && adapter instanceof RetailOrderDetailAdapter) {
            orderDetailAdapter = (RetailOrderDetailAdapter) adapter;
        }
        this.data = orderDetailAdapter.getList();
        this.itemView = itemView;
    }


    /**
     * 特殊处理订单相关数据，锯齿在内部
     *
     * @param top
     * @param bottom
     */
    private void setOrderInfoSemicircle(boolean top, boolean bottom) {
        LinearLayoutCouponView mLinearOrderInfo = (LinearLayoutCouponView) itemView.findViewById(R.id.linear_order_info);
        mLinearOrderInfo.setSemicircleTop(top);
        mLinearOrderInfo.setSemicircleBottom(bottom);
    }

    private void setSemicircle(boolean top, boolean bottom) {
        if (itemView instanceof LinearLayoutCouponView) {
            ((LinearLayoutCouponView) itemView).setSemicircleTop(top);
            ((LinearLayoutCouponView) itemView).setSemicircleBottom(bottom);
        } else if (itemView instanceof RelativeLayoutCouponView) {
            ((RelativeLayoutCouponView) itemView).setSemicircleTop(top);
            ((RelativeLayoutCouponView) itemView).setSemicircleBottom(bottom);
        }
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            initViewSemicircle(getAdapterPosition(), item);
        }
    }

    private void initViewSemicircle(int position, OrderDetailItem item) {
        if (position == data.size() - 1) { // 最后一项
            setSemicircle(true, true);
        } else {
            int mItemType = item.getType();
            switch (mItemType) {
                case OrderDetailItem.ITEM_TYPE_ORDER_INFO: // 订单
                    if (data.get(position + 1).getType() == OrderDetailItem.ITEM_TYPE_PAY_INFO) {
                        setOrderInfoSemicircle(true, false);
                    } else {
                        setOrderInfoSemicircle(true, true);
                    }
                    break;
                case OrderDetailItem.ITEM_TYPE_PAY_INFO: // 支付消息
                    if (data.get(position + 1).getType() == OrderDetailItem.ITEM_TYPE_PAY_INFO) {
                        setSemicircle(false, false);
                    } else {
                        setSemicircle(false, true);
                    }
                    break;
                case OrderDetailItem.ITEM_TYPE_USER_INFO: // 用户信息
                    setSemicircle(true, false);
                    break;
                case OrderDetailItem.ITEM_TYPE_INSTANCE: // 菜肴列表
                    int itemType = data.get(position + 1).getType();
                    if (itemType == OrderDetailItem.ITEM_TYPE_INSTANCE || itemType == OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE) {
                        setSemicircle(false, false);
                    } else {
                        setSemicircle(false, true);
                    }
                    break;
                case OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE: // 套菜子菜
                    int type1 = data.get(position + 1).getType();
                    if (type1 == OrderDetailItem.ITEM_TYPE_SUIT_CHILD_INSTANCE || type1 == OrderDetailItem.ITEM_TYPE_SUIT_INSTANCE || type1 == OrderDetailItem.ITEM_TYPE_INSTANCE) {
                        setSemicircle(false, false);
                    } else {
                        setSemicircle(false, true);
                    }
                    break;
            }
        }
    }
}
