package com.zmsoft.ccd.module.main.message.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;

/**
 * 消息中心——无法识别的消息
 *
 * @author DangGui
 * @create 2017/12/12.
 */

public class DeskMsgUnKnownViewholder extends BaseDeskMsgViewholder {
    public DeskMsgUnKnownViewholder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
    }

    @Override
    protected void initInfoView(int position) {
        super.initInfoView(position);
        mTvDeskId.setText(String.format(mContext.getResources()
                .getString(R.string.msg_center_unknown_type_title), mDeskMessage.getTy()));
        mTvMsgContent.setText(mContext.getString(R.string.msg_center_unknown_type_content));
        mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_unknown, 0, 0);
    }

    @Override
    protected void onTvIKnowClicked(int position) {
        super.onTvIKnowClicked(position);
        if (mDeskMessage.getSu() != MessageHelper.MsgState.STATE_UN_HANDLE)
            return;
        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.EVENT_MSG_CENTER_IKNOW;
        event.setObject(position);
        EventBusHelper.post(event);
    }
}
