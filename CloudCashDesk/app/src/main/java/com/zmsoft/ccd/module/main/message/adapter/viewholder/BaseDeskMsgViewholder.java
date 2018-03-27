package com.zmsoft.ccd.module.main.message.adapter.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.lib.utils.TimeUtils;
import com.zmsoft.ccd.module.main.message.adapter.DeskMessageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 消息中心
 *
 * @author DangGui
 * @create 2016/12/21.
 */

class BaseDeskMsgViewholder extends BaseHolder {
    protected Context mContext;
    ArrayList<DeskMessage> mDatas;
    DeskMessageAdapter mRecyclerItemAdapter;
    DeskMessage mDeskMessage;
    //item 布局
    protected TextView mTvDeskId;
    protected TextView mTvMsgContent;
    TextView mTvTime;
    private TextView mTvIKnow;

    BaseDeskMsgViewholder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView);
        this.mContext = context;
        if (null != adapter && adapter instanceof DeskMessageAdapter)
            mRecyclerItemAdapter = (DeskMessageAdapter) adapter;
        this.mDatas = (ArrayList<DeskMessage>) mRecyclerItemAdapter.getList();
    }

    @Override
    protected void bindView(List source, Object bean) {
        mTvDeskId = (TextView) itemView.findViewById(R.id.text_desk_id);
        mTvMsgContent = (TextView) itemView.findViewById(R.id.text_msg_content);
        mTvTime = (TextView) itemView.findViewById(R.id.text_time);
        mTvIKnow = (TextView) itemView.findViewById(R.id.text_iknow);
        if (null == bean)
            return;
        if (bean instanceof DeskMessage)
            mDeskMessage = (DeskMessage) bean;
        initInfoView(getAdapterPosition());
    }

    protected void initInfoView(int position) {
        if (!TextUtils.isEmpty(mDeskMessage.getTl())) {
            mTvDeskId.setText(mDeskMessage.getTl());
        } else {
            mTvDeskId.setText("");
        }
        mTvMsgContent.setText(mDeskMessage.getCot());
        switch (mDeskMessage.getSu()) {
            case MessageHelper.MsgState.STATE_UN_HANDLE:
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_handle_now));
                    mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
                    mTvIKnow.setBackgroundResource(R.drawable.shape_red_stroke_corner);
                } else {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_iknow));
                    mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.common_blue));
                    mTvIKnow.setBackgroundResource(R.drawable.shape_blue_stroke_corner);
                }
                mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getCt(), TimeUtils.FORMAT_TIME));
                initListener(position, false);
                break;
            case MessageHelper.MsgState.STATE_HANDLED_SUCCESS:
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_accepted));
                } else {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_handled));
                }
                mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.common_front_green));
                mTvIKnow.setBackgroundResource(android.R.color.transparent);
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null
                            , ContextCompat.getDrawable(mContext, R.drawable.icon_item_right), null);
                } else {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
                initListener(position, true);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getMt(), TimeUtils.FORMAT_TIME));
                break;
            case MessageHelper.MsgState.STATE_HANDLE_REJECTED:
                mTvIKnow.setText(mContext.getString(R.string.desk_msg_rejected));
                mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
                mTvIKnow.setBackgroundResource(android.R.color.transparent);
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null
                            , ContextCompat.getDrawable(mContext, R.drawable.icon_item_right), null);
                } else {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
                initListener(position, true);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getMt(), TimeUtils.FORMAT_TIME));
                break;
            case MessageHelper.MsgState.STATE_TIMEOUT:
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_timeout));
                    mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.accentColor));
                } else {
                    mTvIKnow.setText(mContext.getString(R.string.desk_msg_handled));
                    mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.common_front_green));
                }
                mTvIKnow.setBackgroundResource(android.R.color.transparent);
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null
                            , ContextCompat.getDrawable(mContext, R.drawable.icon_item_right), null);
                } else {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
                initListener(position, true);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getMt(), TimeUtils.FORMAT_TIME));
                break;
            case MessageHelper.MsgState.STATE_HANDLE_FAIL:
                mTvIKnow.setText(mContext.getString(R.string.desk_msg_handled));
                mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.common_front_green));
                mTvIKnow.setBackgroundResource(android.R.color.transparent);
                if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT
                        || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null
                            , ContextCompat.getDrawable(mContext, R.drawable.icon_item_right), null);
                } else {
                    mTvIKnow.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
                initListener(position, true);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getMt(), TimeUtils.FORMAT_TIME));
                break;
            default:
                mTvIKnow.setText(mContext.getString(R.string.desk_msg_iknow));
                mTvIKnow.setTextColor(ContextCompat.getColor(mContext, R.color.common_blue));
                mTvIKnow.setBackgroundResource(R.drawable.shape_blue_stroke_corner);
                mTvTime.setText(TimeUtils.getTimeStr(mDeskMessage.getCt(), TimeUtils.FORMAT_TIME));
                initListener(position, false);
                break;
        }
    }

    protected void initListener(final int position, final boolean perfromClick) {
        //防止按钮重复点击
        RxView.clicks(mTvIKnow).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (perfromClick) {
                            itemView.performClick();
                        } else {
                            onTvIKnowClicked(position);
                        }
                    }
                });
    }

    protected void onTvIKnowClicked(int position) {

    }
}
