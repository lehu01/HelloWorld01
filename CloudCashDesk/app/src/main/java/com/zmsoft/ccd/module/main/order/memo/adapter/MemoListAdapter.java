package com.zmsoft.ccd.module.main.order.memo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.main.order.memo.viewholder.MemoViewHolder;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 16:58
 */
public class MemoListAdapter extends BaseListAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public MemoListAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemoViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_remark, parent, false));
    }
}
