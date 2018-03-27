package com.zmsoft.ccd.module.main.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.helper.MessageHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.message.DeskMessage;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.DeskMsgAddFoodViewholder;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.DeskMsgAutoCheckViewholder;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.DeskMsgPayViewholder;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.DeskMsgServiceCallViewholder;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.DeskMsgUnKnownViewholder;
import com.zmsoft.ccd.module.main.message.adapter.viewholder.TakeoutViewholder;

/**
 * @author DangGui
 * @create 2017/08/12.
 */
public class DeskMessageAdapter extends BaseListAdapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public DeskMessageAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof DeskMessage) {
                DeskMessage deskMessage = (DeskMessage) item;
                return deskMessage.getTy();
            }
        }
        return -1;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MessageHelper.MsgType.TYPE_SERVICE_CALL: //服务铃
                return new DeskMsgServiceCallViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
            case MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_ORDER: //加菜消息，扫单
            case MessageHelper.MsgType.TYPE_ADD_FOOD_SCAN_DESK: //加菜消息，扫桌
            case MessageHelper.MsgType.TYPE_PREPAY_DOUBLE_UNIT: //双单位预付款非自动审核消息
                return new DeskMsgAddFoodViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
            case MessageHelper.MsgType.TYPE_PAY: //支付消息
                return new DeskMsgPayViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
            case MessageHelper.MsgType.TYPE_AUTO_CHECK: //自动审核消息
            case MessageHelper.MsgType.TYPE_AUTO_CHECK_PRE_PAY: //自动审核消息
            case MessageHelper.MsgType.TYPE_PREPAY_SCAN_DESK: //预付款情况下，收到的通知
                return new DeskMsgAutoCheckViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
            case MessageHelper.MsgType.TYPE_TAKEOUT_XIAOER_MANUAL_CHECK:
            case MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_MIXTURE:
            case MessageHelper.MsgType.TYPE_TAKEOUT_THIRD_INDEPENDENT:
            case MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_CANCLE_INDEPENDENT:
            case MessageHelper.MsgType.TYPE_TAKEOUT_ORDER_REMINDER:
            case MessageHelper.MsgType.TYPE_TAKEOUT_DELIVERY_REFRESH:
                return new TakeoutViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
            default: //无法识别的消息类型
                return new DeskMsgUnKnownViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_deskmsg_servicecall, parent, false), this);
        }
    }
}
