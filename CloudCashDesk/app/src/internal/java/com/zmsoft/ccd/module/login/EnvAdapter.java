package com.zmsoft.ccd.module.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/14 17:35
 *     desc  : 环境切换adapter
 * </pre>
 */
public class EnvAdapter extends BaseListAdapter {

    private final LayoutInflater mLayoutInflater;
    private Context mContext;
    private IRadioClick iRadioClick;

    public void setRadioClick(IRadioClick iRadioClick) {
        this.iRadioClick = iRadioClick;
    }

    public EnvAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new EnvViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_env_item, parent, false), iRadioClick);
    }
}
