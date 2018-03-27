package com.zmsoft.ccd.module.main.message.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.module.main.message.takeout.TakeoutDetailActivity;

/**
 * 消息中心——外卖 消息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class TakeoutViewholder extends BaseDeskMsgViewholder {

    public TakeoutViewholder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
    }

    @Override
    protected void initInfoView(int position) {
        super.initInfoView(position);
        String title = mDeskMessage.getTl();
        if (!TextUtils.isEmpty(title)) {
            if (title.contains(mContext.getString(R.string.takeout_order_from_name_meituan))) {
                mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_meituan, 0, 0);
            } else if (title.contains(mContext.getString(R.string.takeout_order_from_name_xiaoer))) {
                mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_xiaoer, 0, 0);
            } else if (title.contains(mContext.getString(R.string.takeout_order_from_name_baidu))) {
                mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_baidu, 0, 0);
            } else if (title.contains(mContext.getString(R.string.takeout_order_from_name_eleme))) {
                mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_eleme, 0, 0);
            } else {
                mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_xiaoer, 0, 0);
            }
        } else {
            mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_xiaoer, 0, 0);
        }
    }

    @Override
    protected void onTvIKnowClicked(int position) {
        super.onTvIKnowClicked(position);
        if (mDeskMessage.getSu() != MessageHelper.MsgState.STATE_UN_HANDLE)
            return;
        boolean isValid = position < mDatas.size() && !TextUtils.isEmpty(mDatas.get(position).getId());
        if (isValid) {
            if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK
                    || mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE) {
                TakeoutDetailActivity.launchActivity(mContext, mDatas.get(position).getB_id()
                        , mDatas.get(position).getId(), position, mDeskMessage.getTy());
            } else {
                BaseEvents.CommonEvent event = BaseEvents.CommonEvent.EVENT_MSG_CENTER_IKNOW;
                event.setObject(position);
                EventBusHelper.post(event);
            }
        }
    }
}
