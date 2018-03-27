package com.zmsoft.ccd.module.receipt.receipt.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptRecyclerItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptBaseViewholder extends BaseHolder {
    protected Context mContext;
    protected ReceiptRecyclerItem mReceiptRecyclerItem;
    protected View mItemView;
    protected DialogUtil mDialogUtil;

    public ReceiptBaseViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null != bean && bean instanceof ReceiptRecyclerItem) {
            mReceiptRecyclerItem = (ReceiptRecyclerItem) bean;
        }
    }

    /**
     * 获取DialogUtil类
     *
     * @return
     */
    protected DialogUtil getDialogUtil() {
        if (null == mDialogUtil) {
            mDialogUtil = new DialogUtil(mContext);
        }
        return mDialogUtil;
    }
}