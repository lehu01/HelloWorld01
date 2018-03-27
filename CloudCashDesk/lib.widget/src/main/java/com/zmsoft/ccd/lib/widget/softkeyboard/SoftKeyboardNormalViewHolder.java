package com.zmsoft.ccd.lib.widget.softkeyboard;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.lib.widget.R;


/**
 * @author DangGui
 * @create 2017/7/29.
 */

public class SoftKeyboardNormalViewHolder extends KeyboardBaseRecyclerHolder<KeyboardModel> implements Divided{
    TextView mBtnKeys;
    private KeyboardModel mKeyboardModel;

    public SoftKeyboardNormalViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        mBtnKeys = (TextView)itemView.findViewById(R.id.btn_keys);
    }

    @Override
    public void initView(KeyboardBaseRecyclerHolder holder, KeyboardModel item, int position) {
        if (null == holder)
            return;
        if (null == item)
            return;
        mKeyboardModel = item;
        mBtnKeys.setText(mKeyboardModel.getValue());
    }
}
