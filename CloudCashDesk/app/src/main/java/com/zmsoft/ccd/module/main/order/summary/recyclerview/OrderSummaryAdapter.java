package com.zmsoft.ccd.module.main.order.summary.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.summary.BillSummaryVo;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.module.main.order.summary.model.OrderSummaryModel;
import com.zmsoft.ccd.module.main.order.summary.model.PayParticulars;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/26 14:20.
 */

public class OrderSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_BASIC_COUNT = 9;               // 总行数=ITEM_COUNT+payParticularsList.size

    private interface ITEM_TYPE {
        int ITEM_TYPE_NORMAL = 1;
        int ITEM_TYPE_DIVISION = 2;
        int ITEM_TYPE_ACTUAL_AMOUNT = 3;                   // 实收+不计营业额
    }

    private interface POSITION {
        int POSITION_COMPLETE_ORDER_NUM = 0;                // 已结账单数
        int POSITION_COMPLETE_ORDER_TOTAL_AMOUNT = 1;       // 已结账金额

        /* v1.6.0版本，因为服务端无法正确获取以下数据，临时隐藏
        int POSITION_NOT_COMPLETE_ORDER_NUM = 2;            // 未结账单数
        int POSITION_NOT_COMPLETE_ORDER_TOTAL_AMOUNT = 3;   // 未结账金额
        int POSITION_ORDER_TOTAL_NUM = 4;                   // 账单总数
        int POSITION_ORDER_TOTAL_AMOUNT = 5;                // 账单总金额
        */
        int POSITION_DIVISION = 2;

        // 收入详情行数 payParticularsList.size

        int POSITION_SOURCE_AMOUNT = 0;                     // 消费合计
        int POSITION_DISCOUNT_AMOUNT = 1;                    // 折扣合计
        int POSITION_PROFIT_LOSS_AMOUNT = 2;                // 损益合计
        int POSITION_WIPE_DISCOUNT = 3;                     // 抹零合计
        int POSITION_COUPON_DISCOUNT = 4;                   // 券优惠合计
        int POSITION_ACTUAL_AMOUNT = 5;                     // 实收+不计营业额
    }


    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final OrderSummaryModel mOrderSummaryModel;

    public OrderSummaryAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOrderSummaryModel = new OrderSummaryModel();
    }

    //================================================================================
    // RecyclerView.Adapter
    //================================================================================
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE.ITEM_TYPE_NORMAL:
                return new OrderSummaryNormalViewHolder(mLayoutInflater.inflate(R.layout.item_recyclerview_order_summary, parent, false));
            case ITEM_TYPE.ITEM_TYPE_DIVISION:
                return new OrderSummaryDivisionViewHolder(mLayoutInflater.inflate(R.layout.item_recyclerview_order_summary_division, parent, false));
            case ITEM_TYPE.ITEM_TYPE_ACTUAL_AMOUNT:
                return new OrderSummaryActualAmountViewHolder(mLayoutInflater.inflate(R.layout.item_recyclerview_order_summary_actual_amount, parent, false));
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (POSITION.POSITION_COMPLETE_ORDER_NUM == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_paid_order_number));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(String.valueOf(mOrderSummaryModel.getCompletedOrderNum()));
            return;
        } else if (POSITION.POSITION_COMPLETE_ORDER_TOTAL_AMOUNT == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_paid_order_money));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);

            String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getCompletedOrderTotalAmount());
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
            return;
        }
        /* v1.6.0版本，因为服务端无法正确获取以下数据，临时隐藏
        else if (POSITION.POSITION_NOT_COMPLETE_ORDER_NUM == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_unpaid_order_number));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(String.valueOf(mOrderSummaryModel.getUncompletedOrderNum()));
            return;
        } else if (POSITION.POSITION_NOT_COMPLETE_ORDER_TOTAL_AMOUNT == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_unpaid_order_money));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(String.format(mContext.getString(R.string.order_summary_value_format), mOrderSummaryModel.getUncompletedOrderTotalAmount()));
            return;
        } else if (POSITION.POSITION_ORDER_TOTAL_NUM == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_order_number));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(String.valueOf(mOrderSummaryModel.getOrderTotalNum()));
            return;
        } else if (POSITION.POSITION_ORDER_TOTAL_AMOUNT == position) {
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_order_money));
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(String.format(mContext.getString(R.string.order_summary_value_format), mOrderSummaryModel.getOrderTotalAmount()));
            return;
        }*/
        else if (POSITION.POSITION_DIVISION == position) {
            return;
        }
        // 收入明细
        List<PayParticulars> payParticularsList = mOrderSummaryModel.getPayParticularsList();
        int payParticularsCount = payParticularsList.size();
        int payParticularsStartPosition = POSITION.POSITION_DIVISION + 1;
        int payParticularsFinishPosition = POSITION.POSITION_DIVISION + 1 + payParticularsCount;  // 不包含
        if (position >= payParticularsStartPosition && position < payParticularsFinishPosition) {
            PayParticulars payParticulars = payParticularsList.get(position - payParticularsStartPosition);
            OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
            orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(payParticulars.getPayKindName());
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.VISIBLE);
            orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setText(String.format(mContext.getString(R.string.order_summary_middle_value_format), payParticulars.getPayCount()));

            String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), payParticulars.getPayAmount());
            orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
            return;
        }
        // 剩余项
        int newPosition = position - payParticularsFinishPosition;
        switch (newPosition) {
            case POSITION.POSITION_SOURCE_AMOUNT: {
                OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
                orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_consumption));
                orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);

                String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getSourceAmount());
                orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
                break;
            }
            case POSITION.POSITION_DISCOUNT_AMOUNT: {
                OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
                orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_discount));
                orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);

                String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getDiscountAmount());
                orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
                break;
            }
            case POSITION.POSITION_PROFIT_LOSS_AMOUNT: {
                OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
                orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);
                orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_profit_and_loss));

                String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getProfitLossAmount());
                orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
                break;
            }
            case POSITION.POSITION_WIPE_DISCOUNT: {
                OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
                orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_wiping_zero));
                orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);

                String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getWipeDiscountAmount());
                orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
                break;
            }
            case POSITION.POSITION_COUPON_DISCOUNT: {
                OrderSummaryNormalViewHolder orderSummaryNormalViewHolder = (OrderSummaryNormalViewHolder) holder;
                orderSummaryNormalViewHolder.mTextOrderSummaryKey.setText(mContext.getString(R.string.order_summary_total_discount_coupon));
                orderSummaryNormalViewHolder.mTextOrderSummaryMediumValue.setVisibility(View.GONE);

                String textOrderSummaryValue = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getCouponDiscountAmount());
                orderSummaryNormalViewHolder.mTextOrderSummaryValue.setText(textOrderSummaryValue);
                break;
            }
            case POSITION.POSITION_ACTUAL_AMOUNT: {
                OrderSummaryActualAmountViewHolder orderSummaryActualAmountViewHolder = (OrderSummaryActualAmountViewHolder) holder;

                String textOrderSummaryActualAmount = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getActualAmount());
                orderSummaryActualAmountViewHolder.mTextOrderSummaryActualAmount.setText(textOrderSummaryActualAmount);

                String textOrderSummaryDisregardTurnover = FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol(), GlobalVars.context.getString(R.string.order_summary_value_format), mOrderSummaryModel.getExcludeAmount());
                orderSummaryActualAmountViewHolder.mTextOrderSummaryDisregardTurnover.setText(textOrderSummaryDisregardTurnover);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return ITEM_BASIC_COUNT + mOrderSummaryModel.getPayParticularsList().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (POSITION.POSITION_DIVISION == position) {
            return ITEM_TYPE.ITEM_TYPE_DIVISION;
        } else if (getItemCount() - 1 == position) {
            return ITEM_TYPE.ITEM_TYPE_ACTUAL_AMOUNT;
        } else {
            return ITEM_TYPE.ITEM_TYPE_NORMAL;
        }
    }

    //================================================================================
    // setter
    //================================================================================
    public void updateDataAndNotify(List<BillSummaryVo> billSummaryVos) {
        mOrderSummaryModel.update(billSummaryVos);
        notifyDataSetChanged();
    }
}
