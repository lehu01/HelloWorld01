package com.zmsoft.ccd.module.main.message.detail.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.utils.SpannableStringUtils;
import com.zmsoft.ccd.lib.widget.couponview.LinearLayoutCouponView;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailAccountsInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailOrderInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.detail.adapter.items.DeskMsgDetailRecyclerItem;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class MessageDetailOrderViewholder extends MessageDetailBaseViewholder {
    //item 布局
    @BindView(R.id.layout_orderinfo)
    LinearLayoutCouponView mLayoutOrderInfo;
    @BindView(R.id.text_order_source)
    TextView mTvOrderSource;
    @BindView(R.id.text_order_title)
    TextView mTvOrderTitle;
    @BindView(R.id.text_desk_id_label)
    TextView mTvDeskIdLabel;
    @BindView(R.id.text_desk_id)
    TextView mTvDeskId;
    @BindView(R.id.text_people_count)
    TextView mTvPeopleCount;
    @BindView(R.id.text_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.text_order_remark)
    TextView mTvOrderRemark;
    @BindView(R.id.image_msg_state)
    ImageView mImageMsgState;
    @BindView(R.id.layout_payinfo)
    LinearLayout mLayoutPayInfo;
    @BindView(R.id.text_receiveable_fee)
    TextView mTextReceiveableFee;
    @BindView(R.id.text_received_fee)
    TextView mTextReceivedFee;
    @BindView(R.id.layout_receiveable_fee)
    LinearLayout mLayoutReceiveableFee;
    @BindView(R.id.text_exceed_fee)
    TextView mTextExceedFee;
    @BindView(R.id.layout_exceed_fee)
    LinearLayout mLayoutExceedFee;
    @BindView(R.id.text_consume_fee)
    TextView mTextConsumeFee;
    @BindView(R.id.layout_consume_fee)
    LinearLayout mLayoutConsumeFee;
    @BindView(R.id.text_service_fee)
    TextView mTextServiceFee;
    @BindView(R.id.layout_service_fee)
    LinearLayout mLayoutServiceFee;
    @BindView(R.id.text_minimum_fee)
    TextView mTextMinimumFee;
    @BindView(R.id.layout_minimum_fee)
    LinearLayout mLayoutMinimumFee;
    @BindView(R.id.text_discount_fee)
    TextView mTextDiscountFee;
    @BindView(R.id.layout_discount_fee)
    LinearLayout mLayoutDiscountFee;
    @BindView(R.id.view_accounts_divider)
    View mViewAccountsDivider;
    @BindView(R.id.text_checkout_detail)
    TextView mTextCheckoutDetail;

    private DeskMsgDetailOrderInfoRecyclerItem mOrderInfo;

    public MessageDetailOrderViewholder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, DeskMsgDetailRecyclerItem msgDetailRecyclerItemObj
            , int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mOrderInfo = msgDetailRecyclerItemObj.getOrderInfo();
        if (null == mOrderInfo)
            return;
        initInfoView(position);
        initListener(position);
    }

    protected void initInfoView(int position) {
        if (!TextUtils.isEmpty(mOrderInfo.getTitle())) {
            mTvOrderTitle.setText(mOrderInfo.getTitle());
        } else {
            mTvOrderTitle.setText("");
        }
        if (!TextUtils.isEmpty(mOrderInfo.getSource())) {
            mTvOrderSource.setText(String.format(context.getResources().getString(R.string.order_from)
                    , mOrderInfo.getSource()));
        } else {
            mTvOrderSource.setText("");
        }
        if (!TextUtils.isEmpty(mOrderInfo.getSeatName())) {
            mTvDeskId.setText(mOrderInfo.getSeatName());
        } else {
            mTvDeskId.setText("");
        }
        mTvPeopleCount.setText(String.format(context.getResources().getString(R.string.msg_detail_order_peoplecount)
                , mOrderInfo.getPeopleCount()));
        if (!TextUtils.isEmpty(mOrderInfo.getOrderCode())) {
            mTvOrderNo.setText(String.format(context.getResources().getString(R.string.msg_detail_order_code)
                    , mOrderInfo.getOrderCode()));
        } else {
            mTvOrderNo.setText("");
        }
        if (!TextUtils.isEmpty(mOrderInfo.getOrderMemo())) {
            mTvOrderRemark.setVisibility(View.VISIBLE);
            mTvOrderRemark.setText(String.format(context.getResources().getString(R.string.msg_detail_order_remark)
                    , mOrderInfo.getOrderMemo()));
        } else {
            mTvOrderRemark.setVisibility(View.GONE);
        }
        switch (mOrderInfo.getStatus()) {
            case MessageDetailHelper.OrderState.STATE_AGREED:
                mImageMsgState.setVisibility(View.VISIBLE);
                mImageMsgState.setImageResource(R.drawable.icon_order_agree);
                break;
            case MessageDetailHelper.OrderState.STATE_CHECK_PENDING:
                mImageMsgState.setVisibility(View.VISIBLE);
                mImageMsgState.setImageResource(R.drawable.icon_order_audit);
                break;
            case MessageDetailHelper.OrderState.STATE_REJECTED:
                mImageMsgState.setVisibility(View.VISIBLE);
                mImageMsgState.setImageResource(R.drawable.icon_order_rejected);
                break;
            case MessageDetailHelper.OrderState.STATE_TIMEOUT:
                mImageMsgState.setVisibility(View.VISIBLE);
                mImageMsgState.setImageResource(R.drawable.icon_order_time_out);
                break;
            default:
                mImageMsgState.setVisibility(View.GONE);
                break;
        }
        initAccountsInfoView(position);
    }

    /**
     * 收款信息
     */
    private void initAccountsInfoView(int position) {
        DeskMsgDetailAccountsInfoRecyclerItem accountsInfoRecyclerItem = mOrderInfo.getAccountsInfoRecyclerItem();
        if (null == accountsInfoRecyclerItem) {
            mLayoutPayInfo.setVisibility(View.GONE);
        } else {
            mLayoutPayInfo.setVisibility(View.VISIBLE);
            //应收、已收
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getReceivableFee())
                    || !TextUtils.isEmpty(accountsInfoRecyclerItem.getReceivedFee())) {
                mLayoutReceiveableFee.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getReceivableFee())) {
                    mTextReceiveableFee.setText(SpannableStringUtils
                            .getBuilder(context, context.getString(R.string.receivable_money))
                            .append(accountsInfoRecyclerItem.getReceivableFee())
                            .setForegroundColor(ContextCompat.getColor(context, R.color.accentColor))
                            .create());

                } else {
                    mTextReceiveableFee.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getReceivedFee())) {
                    mTextReceivedFee.setText(SpannableStringUtils
                            .getBuilder(context, context.getString(R.string.received_money))
                            .append(accountsInfoRecyclerItem.getReceivedFee())
                            .setForegroundColor(ContextCompat.getColor(context, R.color.common_front_green))
                            .create());
                } else {
                    mTextReceivedFee.setVisibility(View.GONE);
                }
            } else {
                mLayoutReceiveableFee.setVisibility(View.GONE);
            }

            //多收款
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getExceedFee())) {
                String exceedFeeLabel;
                if (accountsInfoRecyclerItem.isExceedMore()) {
                    exceedFeeLabel = context.getString(R.string.pay_more);
                } else {
                    exceedFeeLabel = context.getString(R.string.pay_less);
                }
                mTextExceedFee.setText(SpannableStringUtils
                        .getBuilder(context, exceedFeeLabel)
                        .append(accountsInfoRecyclerItem.getExceedFee())
                        .setForegroundColor(ContextCompat.getColor(context, R.color.accentColor))
                        .create());
            } else {
                mTextExceedFee.setVisibility(View.GONE);
                mLayoutExceedFee.setVisibility(View.GONE);
            }
            //消费金额
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getConsumeFee())) {
                mTextConsumeFee.setText(accountsInfoRecyclerItem.getConsumeFee());
            } else {
                mTextConsumeFee.setVisibility(View.GONE);
                mLayoutConsumeFee.setVisibility(View.GONE);
            }
            //服务费金额
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getServiceFee())) {
                mTextServiceFee.setText(accountsInfoRecyclerItem.getServiceFee());
            } else {
                mTextServiceFee.setVisibility(View.GONE);
                mLayoutServiceFee.setVisibility(View.GONE);
            }
            //最新消费金额
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getMinimumFee())) {
                mTextMinimumFee.setText(accountsInfoRecyclerItem.getMinimumFee());
            } else {
                mTextMinimumFee.setVisibility(View.GONE);
                mLayoutMinimumFee.setVisibility(View.GONE);
            }
            //优惠金额
            if (!TextUtils.isEmpty(accountsInfoRecyclerItem.getDiscountFee())) {
                mTextDiscountFee.setText(accountsInfoRecyclerItem.getDiscountFee());
            } else {
                mTextDiscountFee.setVisibility(View.GONE);
                mLayoutDiscountFee.setVisibility(View.GONE);
            }
            //收款详情
            if (position < mDatas.size() - 1) {
                if (mDatas.get(position + 1).getItemType() != DeskMsgDetailRecyclerItem.ItemType.TYPE_PAY_ITEM) {
                    mViewAccountsDivider.setVisibility(View.GONE);
                    mTextCheckoutDetail.setVisibility(View.GONE);
                    mLayoutOrderInfo.setSemicircleBottom(true);
                } else {
                    mViewAccountsDivider.setVisibility(View.VISIBLE);
                    mTextCheckoutDetail.setVisibility(View.VISIBLE);
                    mLayoutOrderInfo.setSemicircleBottom(false);
                }
            } else {
                mViewAccountsDivider.setVisibility(View.GONE);
                mTextCheckoutDetail.setVisibility(View.GONE);
                mLayoutOrderInfo.setSemicircleBottom(true);
            }
        }
    }

    protected void initListener(final int position) {
    }
}
