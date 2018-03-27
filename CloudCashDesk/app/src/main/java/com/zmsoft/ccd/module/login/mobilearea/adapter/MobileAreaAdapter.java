package com.zmsoft.ccd.module.login.mobilearea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.login.mobilearea.adapter.viewholder.MobileAreaViewHolder;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/21 17:38.
 */

public class MobileAreaAdapter extends BaseListAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public MobileAreaAdapter(Context context) {
        super(context, null);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new MobileAreaViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_mobile_area, parent, false));
    }
}
