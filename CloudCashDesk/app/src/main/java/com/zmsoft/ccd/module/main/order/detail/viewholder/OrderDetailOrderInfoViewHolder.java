package com.zmsoft.ccd.module.main.order.detail.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.event.orderdetail.ClearDiscountEvent;
import com.zmsoft.ccd.helper.CommonHelper;
import com.zmsoft.ccd.helper.OrderHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.bean.order.detail.OrderDetailItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderItem;
import com.zmsoft.ccd.lib.bean.order.detail.OrderVo;
import com.zmsoft.ccd.lib.bean.order.detail.PromotionVo;
import com.zmsoft.ccd.lib.bean.order.detail.servicebill.ServiceBill;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.StringUtils;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.lib.widget.dialogutil.DialogUtil;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zmsoft.ccd.app.GlobalVars.context;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/21 18:28
 */
public class OrderDetailOrderInfoViewHolder extends OrderDetailBaseViewHolder {

    @BindView(R.id.text_order_from)
    TextView mTextOrderFrom;
    @BindView(R.id.text_table_number)
    TextView mTextTableNumber;
    @BindView(R.id.text_eat_number_count)
    TextView mTextEatNumberCount;
    @BindView(R.id.text_order_number)
    TextView mTextOrderNumber;
    @BindView(R.id.text_open_time)
    TextView mTextOpenTime;
    @BindView(R.id.text_opened_time)
    TextView mTextOpenedTime;
    @BindView(R.id.text_remarks)
    TextView mTextRemarks;
    @BindView(R.id.text_receive_price)
    TextView mTextReceivePrice;
    @BindView(R.id.text_in_revenue_price)
    TextView mTextInRevenuePrice;
    @BindView(R.id.text_take_out_price)
    TextView mTextTakeOutPrice;
    @BindView(R.id.text_service_price)
    TextView mTextServicePrice;
    @BindView(R.id.text_other_fee_name)
    TextView mTextOtherFeeName;
    @BindView(R.id.text_least_price)
    TextView mTextLeastPrice;
    @BindView(R.id.text_offers_price)
    TextView mTextOffersPrice;
    @BindView(R.id.linear_order_info)
    LinearLayoutCouponView mLinearOrderInfo;
    @BindView(R.id.image_order_state)
    ImageView mImageOrderState;
    @BindView(R.id.relative_lowest_fee)
    RelativeLayout mRelativeLowestFee;
    @BindView(R.id.relative_discount)
    RelativeLayout mRelativeDiscount;
    @BindView(R.id.text_need_pay)
    TextView mTextNeedPay;
    @BindView(R.id.text_need_pay_text)
    TextView mTextNeedPayText;
    @BindView(R.id.linear_need_pay)
    LinearLayout mLinearNeedPay;
    @BindView(R.id.image_pay_prompt)
    ImageView mImagePayPrompt;
    @BindView(R.id.image_update_service_fee)
    ImageView mImageUpdateServiceFee;
    @BindView(R.id.linear_update_fee)
    LinearLayout mLinearUpdateFee;
    @BindView(R.id.linear_discount_detail_record)
    LinearLayout mLinearDiscountDetailRecord;
    @BindView(R.id.text_order_innerCode)
    TextView mTextOrderInnerCode;

    public OrderDetailOrderInfoViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (bean != null && bean instanceof OrderDetailItem) {
            OrderDetailItem item = (OrderDetailItem) bean;
            fillView(item.getOrderVo());
        }
    }

    @OnClick(R.id.linear_update_fee)
    void updateServicePrice() {
        // 未上班
        if (!UserHelper.getWorkStatus()) {
            CommonHelper.showOffWorkDialog(getContext(), new DialogUtil(getContext()));
            return;
        }
        EventBusHelper.post(BaseEvents.CommonEvent.EVENT_SHOW_FEE_PLAN_BOTTOM_DIALOG);
    }

    private void fillView(final OrderItem order) {
        if (order != null) {
            OrderVo orderVo = order.getOrderVo();
            ServiceBill serviceBill = order.getServiceBill();
            if (orderVo == null || serviceBill == null) {
                return;
            }

            OrderHelper.setOrderFromText(orderVo.getOrderFrom(), mTextOrderFrom, getContext());
            OrderHelper.setOrderDetailPayImage(orderVo.getStatus(), order.getOrderDetailStatus(), mImageOrderState);
            mTextEatNumberCount.setText(StringUtils.appendStr(getContext().getString(R.string.people_number), orderVo.getPeopleCount()));
            mTextOrderNumber.setText(StringUtils.appendStr(getContext().getString(R.string.No), orderVo.getCode()));
            mTextOrderInnerCode.setText(String.format(getContext().getString(R.string.order_inner_code)
                    , StringUtils.notNull(orderVo.getInnerCode())));
            // 客单备注
            String memo = orderVo.getMemo();
            if (!StringUtils.isEmpty(memo)) {
                mTextRemarks.setVisibility(View.VISIBLE);
                mTextRemarks.setText(StringUtils.appendStr(getContext().getString(R.string.order_memo), memo));
            } else {
                mTextRemarks.setVisibility(View.GONE);
            }

            // 开单时间
            mTextOpenTime.setText(String.format(GlobalVars.context.getString(R.string.open_time), TimeUtils.getTimeStr(orderVo.getOpenTime(), TimeUtils.FORMAT_MONTH_DAY_TIME)));
            if (orderVo.getStatus() == OrderHelper.OrderDetail.Order.END_PAY) {
                mTextOpenedTime.setText(String.format(GlobalVars.context.getString(R.string.end_pay_time), TimeUtils.getTimeStr(orderVo.getEndTime(), TimeUtils.FORMAT_MONTH_DAY_TIME)));
            } else {
                // 用餐超时
                if (order.isLimitTime()) {
                    if (order.isOverTime()) {
                        mTextOpenedTime.setTextColor(getContext().getResources().getColor(R.color.red));
                        mTextOpenedTime.setText(getContext().getString(R.string.eating_time_out));
                    } else {
                        mTextOpenedTime.setTextColor(getContext().getResources().getColor(R.color.secondaryTextColor));
                        mTextOpenedTime.setText(String.format(context.getResources()
                                .getString(R.string.eat_after_time_out), String.valueOf(order.getMinuteToOverTime())));
                    }
                } else {
                    mTextOpenedTime.setTextColor(getContext().getResources().getColor(R.color.secondaryTextColor));
                    mTextOpenedTime.setText(StringUtils.appendStr(GlobalVars.context.getString(R.string.opened_order), order.getMinuteSinceOpen(), GlobalVars.context.getString(R.string.minute)));
                }
            }

            mTextTableNumber.setText(StringUtils.getNullStr(order.getSeatName()));
            mTextReceivePrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFee(serviceBill.getFinalAmount())));
            mTextInRevenuePrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , FeeHelper.getDecimalFee(serviceBill.getPaidFee())));

            // 支付金额和应收金额对比
            double needPay = serviceBill.getNeedPayFee();
            if (needPay > 0) {
                mLinearNeedPay.setVisibility(View.VISIBLE);
                if (OrderHelper.OrderDetail.Order.END_PAY == orderVo.getStatus()) {
                    mTextNeedPayText.setText(getContext().getString(R.string.clear_zero));
                } else {
                    mTextNeedPayText.setText(getContext().getString(R.string.need_pay));
                }
                mTextNeedPay.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , FeeHelper.getDecimalFee(needPay)));
            } else if (needPay < 0) {
                mLinearNeedPay.setVisibility(View.VISIBLE);
                mTextNeedPayText.setText(GlobalVars.context.getString(R.string.pay_more));
                mTextNeedPay.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                        , FeeHelper.getDecimalFee(Math.abs(needPay))));
            } else {
                mLinearNeedPay.setVisibility(View.GONE);
            }

            // 服务费金额
            if (OrderHelper.isTakeoutOrder(orderVo.getOrderFrom())) {
                mTextOtherFeeName.setText(GlobalVars.context.getString(R.string.distribution_fee));
                mTextServicePrice.setText(String.format(GlobalVars.context.getString(R.string.yuan_format)
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFee(serviceBill.getOutFee()))));
            } else {
                mTextOtherFeeName.setText(GlobalVars.context.getString(R.string.service_money));
                mTextServicePrice.setText(String.format(GlobalVars.context.getString(R.string.yuan_format)
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFee(serviceBill.getOriginServiceCharge()))));
            }
            if (order.isUpdateServiceFee()) {
                if (OrderHelper.isEndPay(orderVo.getStatus())) { // 结账完毕订单
                    mImageUpdateServiceFee.setVisibility(View.GONE);
                    mLinearUpdateFee.setEnabled(false);
                } else {
                    mImageUpdateServiceFee.setVisibility(View.VISIBLE);
                    mLinearUpdateFee.setEnabled(true);
                }
            } else {
                mImageUpdateServiceFee.setVisibility(View.GONE);
                mLinearUpdateFee.setEnabled(false);
            }

            // 优惠金额
            // 需要判断是否使用了优惠，如果是1，无论优惠金额是多少都要展示，如果是0，不展示
            PromotionVo promotionVo = order.getPromotionVo();
            if (promotionVo == null) {
                mRelativeDiscount.setVisibility(View.GONE);
                mLinearDiscountDetailRecord.setVisibility(View.GONE);
            } else if (promotionVo.getUsePromotion() == Base.INT_TRUE) {
                mRelativeDiscount.setVisibility(View.VISIBLE);
                mLinearDiscountDetailRecord.setVisibility(View.VISIBLE);
                mRelativeDiscount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!order.isEndPay()) {
                            showDiscountDialog(order.getPromotionVo());
                        }
                    }
                });
                initDiscountRecord(order.getPromotionVo());
                if (serviceBill.getDiscountAmount() >= 0) {
                    mTextOffersPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFee(serviceBill.getDiscountAmount()), true));
                } else {
                    mTextOffersPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFee(Math.abs(serviceBill.getDiscountAmount())), true));
                }
            } else if (promotionVo.getUsePromotion() == Base.INT_FALSE) {
                mRelativeDiscount.setVisibility(View.VISIBLE);
                mLinearDiscountDetailRecord.setVisibility(View.GONE);
                mRelativeDiscount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!order.isEndPay()) {
                            showDiscountDialog(order.getPromotionVo());
                        }
                    }
                });
                if (serviceBill.getDiscountAmount() >= 0) {
                    mTextOffersPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFee(serviceBill.getDiscountAmount()), true));
                } else {
                    mTextOffersPrice.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFee(Math.abs(serviceBill.getDiscountAmount())), true));
                }
            }

            // 结账完毕：不显示删除图标
            if (order.isEndPay()) {
                mTextOffersPrice.setCompoundDrawables(null, null, null, null);
            }

            // 最低消费
            if (serviceBill.getOriginLeastAmount() > 0) {
                mRelativeLowestFee.setVisibility(View.VISIBLE);
                mTextLeastPrice.setText(String.format(GlobalVars.context.getString(R.string.yuan_format)
                        , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                                , FeeHelper.getDecimalFee(serviceBill.getOriginLeastAmount()))));
            } else {
                mRelativeLowestFee.setVisibility(View.GONE);
            }
            mTextTakeOutPrice.setText(String.format(GlobalVars.context.getString(R.string.yuan_format)
                    , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , FeeHelper.getDecimalFee(serviceBill.getOriginAmount()))));

            // 支付三角符号是否展示
            if (order.isPayCount()) {
                mImagePayPrompt.setVisibility(View.VISIBLE);
            } else {
                mImagePayPrompt.setVisibility(View.INVISIBLE);
            }

            // 外卖单反结账之后，受搜索引擎影响
            if (OrderHelper.isTakeoutOrder(orderVo.getOrderFrom())) {
                mImageUpdateServiceFee.setVisibility(View.GONE);
                mLinearUpdateFee.setEnabled(false);
                mTextOffersPrice.setCompoundDrawables(null, null, null, null);
            }
        }
    }

    /**
     * init discount , update discount view
     */
    private void initDiscountRecord(final PromotionVo promotion) {
        if (promotion == null) {
            return;
        }

        mLinearDiscountDetailRecord.removeAllViews();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_discount_record_item, null);
        TextView mTextDisCountName = (TextView) view.findViewById(R.id.text_discount_name);
        TextView mTextDisCountClear = (TextView) view.findViewById(R.id.text_discount_clear);
        ImageView mImageDiscountIcon = (ImageView) view.findViewById(R.id.image_discount_icon);

        mTextDisCountName.setText(promotion.getName());
        if (promotion.getMode() == PromotionVo.DISCOUNT) {
            mTextDisCountClear.setText(String.format(getContext().getString(R.string.ratio_discount), String.valueOf((double) promotion.getRatio() / 10)));
        } else {
            mTextDisCountClear.setText(FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                    , promotion.getFee(), true));
        }
        mImageDiscountIcon.setImageResource(R.drawable.icon_vip_card);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiscountDialog(promotion);
            }
        });
        mLinearDiscountDetailRecord.addView(view);
    }

    /**
     * show discount dialog
     */
    private void showDiscountDialog(PromotionVo promotion) {
        ClearDiscountEvent discountEvent = new ClearDiscountEvent();
        discountEvent.setPromotionVo(promotion);
        EventBusHelper.post(discountEvent);
    }
}
