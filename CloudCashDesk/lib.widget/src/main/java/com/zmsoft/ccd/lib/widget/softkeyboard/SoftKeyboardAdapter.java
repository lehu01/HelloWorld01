package com.zmsoft.ccd.lib.widget.softkeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.lib.widget.R;

import java.util.ArrayList;

import static com.zmsoft.ccd.lib.widget.softkeyboard.KeyboardModel.ITEM_TYPE_NORMAL;


/**
 * @author DangGui
 * @create 2016/12/23.
 */

public class SoftKeyboardAdapter extends KeyboardBaseRecyclerAdapter<KeyboardModel> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<KeyboardModel> mDatas;
    private RecyclerView mRecyclerView;

    public SoftKeyboardAdapter(Context context, RecyclerView view, ArrayList<KeyboardModel> datas) {
        super(view, datas, 0);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.mRecyclerView = view;
    }

    @Override
    public KeyboardBaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_NORMAL:
                return new SoftKeyboardNormalViewHolder(mContext
                        , mLayoutInflater.inflate(R.layout.softkeyboard_grid_item_normal, parent, false)
                        , this);
            default:
                return new KeyboardBaseRecyclerHolder(mContext, new LinearLayout(mContext), this) {
                    @Override
                    public void initView(KeyboardBaseRecyclerHolder holder, Object item, int position) {

                    }
                };
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDatas.size()) {
            return ITEM_TYPE_NORMAL;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
