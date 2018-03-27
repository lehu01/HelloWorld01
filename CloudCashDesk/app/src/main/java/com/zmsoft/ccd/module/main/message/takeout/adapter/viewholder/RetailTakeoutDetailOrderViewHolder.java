package com.zmsoft.ccd.module.main.message.takeout.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageDetailHelper;
import com.zmsoft.ccd.lib.base.activity.CcdWebViewAcitivity;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.base.helper.LocationHelper;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailOrderInfoRecyclerItem;
import com.zmsoft.ccd.module.main.message.takeout.adapter.items.TakeoutDetailRecyclerItem;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class RetailTakeoutDetailOrderViewHolder extends RetailTakeoutDetailBaseViewholder {
    @BindView(R.id.text_takeout_order_original)
    TextView mTextTakeoutOrderOriginal;
    @BindView(R.id.text_takeout_order_delivery_way)
    TextView mTextTakeoutOrderDeliveryWay;
    @BindView(R.id.text_takeout_order_appointment_flag)
    TextView mTextTakeoutOrderAppointmentFlag;
    @BindView(R.id.text_takeout_order_take_time)
    TextView mTextTakeoutOrderTakeTime;
    @BindView(R.id.text_takeout_order_person_lable)
    TextView mTextTakeoutOrderPersonLable;
    @BindView(R.id.layout_address)
    RelativeLayout mLayoutAddress;
    @BindView(R.id.text_takeout_order_person_name)
    TextView mTextTakeoutOrderPersonName;
    @BindView(R.id.text_takeout_order_person_phone)
    TextView mTextTakeoutOrderPersonPhone;
    @BindView(R.id.text_takeout_order_address)
    TextView mTextTakeoutOrderAddress;
    @BindView(R.id.text_takeout_order_distance)
    TextView mTextTakeoutOrderDistance;
    @BindView(R.id.view_divider)
    View mViewDivider;
    @BindView(R.id.text_food)
    TextView mTextFood;
    @BindView(R.id.text_people_count)
    TextView mTextPeopleCount;
    @BindView(R.id.text_total)
    TextView mTextTotal;
    @BindView(R.id.text_remark)
    TextView mTextRemark;
    @BindView(R.id.image_msg_state)
    ImageView mImageMsgState;
    private TakeoutDetailOrderInfoRecyclerItem mOrderInfo;

    public RetailTakeoutDetailOrderViewHolder(Context context, View itemView, RecyclerView recyclerView
            , RecyclerView.Adapter adapter) {
        super(context, itemView, recyclerView, adapter);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, TakeoutDetailRecyclerItem msgDetailRecyclerItemObj
            , int position) {
        super.initView(holder, msgDetailRecyclerItemObj, position);
        mOrderInfo = msgDetailRecyclerItemObj.getOrderInfo();
        if (null == mOrderInfo)
            return;
        initInfoView(position);
        initListener(position);
    }

    protected void initInfoView(int position) {
        if (!TextUtils.isEmpty(mOrderInfo.getSource())) {
            if (mOrderInfo.getOrderFrom() != TakeoutConstants.OrderFrom.XIAOER
                    && !TextUtils.isEmpty(mOrderInfo.getOutId())) {
                String stringBuilder = mOrderInfo.getSource() +
                        "-" +
                        mOrderInfo.getOutId();
                mTextTakeoutOrderOriginal.setText(stringBuilder);
            } else {
                mTextTakeoutOrderOriginal.setText(mOrderInfo.getSource());
            }
        } else {
            mTextTakeoutOrderOriginal.setText("");
        }
        switch (mOrderInfo.getOrderFrom()) {
            case TakeoutConstants.OrderFrom.XIAOER:
            case TakeoutConstants.OrderFrom.WEIDIAN:
                mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_xiaoer, 0, 0);
                break;
            case TakeoutConstants.OrderFrom.MEITUAN:
                mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_meituan, 0, 0);
                break;
            case TakeoutConstants.OrderFrom.BAIDU:
                mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_baidu, 0, 0);
                break;
            case TakeoutConstants.OrderFrom.ERLEME:
                mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_order_from_eleme, 0, 0);
                break;
            default:
                mTextTakeoutOrderOriginal.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
        }
        if (!TextUtils.isEmpty(mOrderInfo.getDeliveryMethod())) {
            mTextTakeoutOrderDeliveryWay.setText(mOrderInfo.getDeliveryMethod());
        } else {
            mTextTakeoutOrderDeliveryWay.setText("");
        }
        switch (mOrderInfo.getReserveStatus()) {
            case TakeoutConstants.ReserveStatus.DELIVERY_IMMEDIATELY:
                mTextTakeoutOrderAppointmentFlag.setVisibility(View.GONE);
                break;
            case TakeoutConstants.ReserveStatus.APPOINTMENT:
                mTextTakeoutOrderAppointmentFlag.setVisibility(View.VISIBLE);
                mTextTakeoutOrderAppointmentFlag.setText(R.string.takeout_msg_pay_detail_subscribe);
                break;
            default:
                mTextTakeoutOrderAppointmentFlag.setVisibility(View.GONE);
                break;
        }
        if (!TextUtils.isEmpty(mOrderInfo.getSendTime())) {
            mTextTakeoutOrderTakeTime.setText(mOrderInfo.getSendTime());
        } else {
            mTextTakeoutOrderTakeTime.setText("");
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
        if (!TextUtils.isEmpty(mOrderInfo.getReceiverName()) || !TextUtils.isEmpty(mOrderInfo.getReceiverPhone())) {
            mTextTakeoutOrderPersonLable.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mOrderInfo.getReceiverName())) {
                mTextTakeoutOrderPersonName.setVisibility(View.VISIBLE);
                mTextTakeoutOrderPersonName.setText(mOrderInfo.getReceiverName());
            } else {
                mTextTakeoutOrderPersonName.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mOrderInfo.getReceiverPhone())) {
                mTextTakeoutOrderPersonPhone.setVisibility(View.VISIBLE);
                mTextTakeoutOrderPersonPhone.setText("(" + mOrderInfo.getReceiverPhone() + ")");
            } else {
                mTextTakeoutOrderPersonPhone.setVisibility(View.GONE);
            }
        } else {
            mTextTakeoutOrderPersonLable.setVisibility(View.GONE);
        }
        // 到店自取的不显示地理位置
        if (mOrderInfo.getSendType() == TakeoutConstants.SendType.SELF_TAKE) {
            mLayoutAddress.setVisibility(View.GONE);
        } else {
            mLayoutAddress.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(mOrderInfo.getAddress())) {
            mTextTakeoutOrderAddress.setText(mOrderInfo.getAddress());
        } else {
            mTextTakeoutOrderAddress.setText("");
        }
        if (!TextUtils.isEmpty(mOrderInfo.getDistance())) {
            mTextTakeoutOrderDistance.setText(mOrderInfo.getDistance());
        } else {
            mTextTakeoutOrderDistance.setText("");
        }
        mTextPeopleCount.setText(String.format(mContext.getString(R.string.takeout_people_count)
                , mOrderInfo.getPeopleCount()));
        mTextTotal.setText(String.format(mContext.getString(R.string.takeout_foods_count)
                , mOrderInfo.getFoodsTotalCount()));
        if (!TextUtils.isEmpty(mOrderInfo.getRemark())) {
            mTextRemark.setVisibility(View.VISIBLE);
            mTextRemark.setText(String.format(context.getResources().getString(R.string.takeout_msg_detail_remark)
                    , mOrderInfo.getRemark()));
        } else {
            mTextRemark.setVisibility(View.GONE);
        }
    }

    protected void initListener(final int position) {
        mTextTakeoutOrderPersonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mOrderInfo.getReceiverPhone())) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mOrderInfo.getReceiverPhone()));
                    mContext.startActivity(dialIntent);
                }
            }
        });
        mTextTakeoutOrderDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CcdWebViewAcitivity.launchActivity(mContext, LocationHelper.getMapUrl(String.valueOf(mOrderInfo.getLongitude())
                        , String.valueOf(mOrderInfo.getLatitude()), mOrderInfo.getAddress()));
            }
        });
    }
}
