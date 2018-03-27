package com.zmsoft.ccd.module.main.message.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chiclaim.modularization.router.MRouter;
import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.module.main.message.detail.MessageDetailActivity;

/**
 * 消息中心——加菜 消息
 *
 * @author DangGui
 * @create 2016/12/21.
 */

public class DeskMsgAddFoodViewholder extends BaseDeskMsgViewholder {

    public DeskMsgAddFoodViewholder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
    }

    @Override
    protected void initInfoView(int position) {
        super.initInfoView(position);
        if (mDeskMessage.getTy() == MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT) {
            mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_pay, 0, 0);
        } else {
            mTvTime.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_message_add_instance, 0, 0);
        }
    }

    @Override
    protected void onTvIKnowClicked(int position) {
        super.onTvIKnowClicked(position);
        if (mDeskMessage.getSu() != MessageHelper.MsgState.STATE_UN_HANDLE)
            return;
        boolean isValid = position < mDatas.size() && !TextUtils.isEmpty(mDatas.get(position).getId());
        if (isValid) {
            MRouter.getInstance()
                    .build(MessageDetailActivity.PATH_MESSAGE_DETAIL)
                    .putInt(MessageDetailActivity.EXTRA_MSG_POSITION, position)
                    .putString(MessageDetailActivity.EXTRA_MSG_ID, mDatas.get(position).getId())
                    .navigation(mContext);
        }
    }
}
