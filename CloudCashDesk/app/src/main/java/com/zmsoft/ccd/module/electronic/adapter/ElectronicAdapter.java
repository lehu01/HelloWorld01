package com.zmsoft.ccd.module.electronic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.adapter.JagHolder;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentItem;
import com.zmsoft.ccd.module.electronic.adapter.viewholder.ElectronicViewholder;

/**
 * @author DangGui
 * @create 2017/08/12.
 */
public class ElectronicAdapter extends BaseListAdapter {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public ElectronicAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            Object item = getList().get(position);
            if (null != item && item instanceof ElePaymentItem) {
                ElePaymentItem elePaymentItem = (ElePaymentItem) item;
                return elePaymentItem.getType();
            }
        }
        return -1;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ElePaymentItem.ItemType.TYPE_NORMAL:
                return new ElectronicViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_electronic, parent, false));
            case ElePaymentItem.ItemType.TYPE_JAG_UP:
                return new JagHolder(mContext, mLayoutInflater.inflate(R.layout.item_divider_jag, parent, false)
                        , true, R.dimen.jag_gap_height);
            case ElePaymentItem.ItemType.TYPE_JAG_DOWN:
                return new JagHolder(mContext, mLayoutInflater.inflate(R.layout.item_divider_jag, parent, false)
                        , false, R.dimen.jag_gap_height);
            default:
                return getUnKnowViewHolder(parent);
        }
    }
}
