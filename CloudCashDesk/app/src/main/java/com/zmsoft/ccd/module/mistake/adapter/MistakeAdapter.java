package com.zmsoft.ccd.module.mistake.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerAdapter;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.bean.mistakes.Mistake;
import com.zmsoft.ccd.module.mistake.ViewHolder.MistakeViewHolder;

import java.util.List;

/**
 * Created by jihuo on 2016/12/24.
 */

public class MistakeAdapter extends BaseRecyclerAdapter<Mistake> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Mistake> mDataList;

    public MistakeAdapter(Context mContext, RecyclerView v,List<Mistake> mDataList) {
        super(v, mDataList);
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MistakeViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_recycleview_mistake, parent, false), this);
    }

}
