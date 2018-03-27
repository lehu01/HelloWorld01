package com.zmsoft.ccd.module.main.order.find.recyclerview;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/2 11:28.
 */

public class OrderFindViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.layout_content_grid_desk)
    RelativeLayout mLayoutContentGridDesk;

    @BindView(R.id.text_desk_name)
    TextView mTextDeskName;

    @BindView(R.id.layout_desk_empty)
    RelativeLayout mLayoutDeskEmpty;
    @BindView(R.id.text_desk_desc)
    TextView mTextDeskDesc;

    @BindView(R.id.layout_desk_open_order)
    RelativeLayout mLayoutDeskOpenOrder;
    @BindView(R.id.image_customer_number)
    ImageView mImageViewCustomerNumber;
    @BindView(R.id.text_customer_number)
    TextView mTextCustomerNumber;

    @BindView(R.id.layout_desk_paid)
    RelativeLayout mLayoutDeskPaid;

    @BindView(R.id.layout_desk_eat_time)
    RelativeLayout mLayoutDeskEatTime;
    @BindView(R.id.image_desk_eat_time)
    ImageView mImageViewEatTime;
    @BindView(R.id.text_eat_time)
    TextView mTextEatTime;

    @BindView(R.id.image_order_find_print)
    ImageView mImageOrderFindPrint;

    public OrderFindViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateView(OrderFindItem item) {
        if (item.isEmpty()) {
            mLayoutContentGridDesk.setVisibility(View.GONE);
            mImageOrderFindPrint.setVisibility(View.GONE);
            return;
        }

        mLayoutContentGridDesk.setVisibility(View.VISIBLE);
        mLayoutDeskEmpty.setVisibility(View.GONE);
        mLayoutDeskOpenOrder.setVisibility(View.GONE);
        mLayoutDeskEatTime.setVisibility(View.GONE);
        mLayoutDeskPaid.setVisibility(View.GONE);
        mImageOrderFindPrint.setVisibility(View.GONE);
        // 座位名称
        mTextDeskName.setText(item.getName());
        if (item.isRetail()) {
            mTextDeskName.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.common_front_black));
        } else {
            if (item.getOrderType() == OrderFindItem.orderTypeConstant.OPEN_ORDER) {
                mTextDeskName.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.primaryColor));
            } else {
                mTextDeskName.setTextColor(ContextCompat.getColor(GlobalVars.context, com.zmsoft.ccd.R.color.white80));
            }
        }
        // 座位描述
        if (!item.isRetail()) {
            if (item.getOrderType() == OrderFindItem.orderTypeConstant.EMPTY) {
                mLayoutDeskEmpty.setVisibility(View.VISIBLE);
                mTextDeskDesc.setText(item.getDesc());
            }
        }
        // 背景
        if (item.isRetail()) {
            mLayoutContentGridDesk.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.shape_order_find_retail_grid_item));
        } else {
            if (item.getOrderType() == OrderFindItem.orderTypeConstant.OPEN_ORDER) {
                mLayoutContentGridDesk.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_seat_bg));
            } else {
                mLayoutContentGridDesk.setBackground(ContextCompat.getDrawable(GlobalVars.context, R.drawable.shape_order_find_grid_item));
            }
        }
        // 顾客人数
        if (item.getOrderType() == OrderFindItem.orderTypeConstant.OPEN_ORDER) {
            mLayoutDeskOpenOrder.setVisibility(View.VISIBLE);
            mTextCustomerNumber.setText(item.getCustomerNumber());
            if (item.isRetail()) {
                mImageViewCustomerNumber.setImageDrawable(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_order_find_retail_customer_number));
                mTextCustomerNumber.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.order_find_retail_customer_number_text_color));
            } else {
                mImageViewCustomerNumber.setImageDrawable(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_order_find_desk_customer_number));
                mTextCustomerNumber.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.order_find_desk_customer_number_text_color));
            }
        }
        // 用餐时间
        if (item.getOrderType() == OrderFindItem.orderTypeConstant.OPEN_ORDER) {
            mLayoutDeskEatTime.setVisibility(View.VISIBLE);
            mTextEatTime.setText(item.getEatTime());
            if (item.isTimeOut()) {
                mImageViewEatTime.setImageDrawable(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_order_find_eat_time_out));
                mTextEatTime.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.accentColor));
            } else {
                if (item.isRetail()) {
                    mImageViewEatTime.setImageDrawable(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_order_find_retail_eat_time_normal));
                    mTextEatTime.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.primaryTextColor));
                } else {
                    mImageViewEatTime.setImageDrawable(ContextCompat.getDrawable(GlobalVars.context, R.drawable.icon_order_find_eat_time_normal));
                    mTextEatTime.setTextColor(ContextCompat.getColor(GlobalVars.context, R.color.accentInverseTextColor));
                }
            }
        }
        // 已付清
        if (item.getOrderType() == OrderFindItem.orderTypeConstant.PAID_ALL) {
            mLayoutDeskPaid.setVisibility(View.VISIBLE);
        }
        // 已打印
        if (item.isPrint()) {
            mImageOrderFindPrint.setVisibility(View.VISIBLE);
        }
    }
}
