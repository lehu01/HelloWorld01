package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.utils.dialogutil.DialogUtil;
import com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.items.UnitRecyclerItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitBaseViewholder extends BaseHolder {
    protected Context mContext;
    protected UnitRecyclerItem mUnitRecyclerItem;
    protected View mItemView;
    protected DialogUtil mDialogUtil;

    public UnitBaseViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        this.mItemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null != bean && bean instanceof UnitRecyclerItem) {
            mUnitRecyclerItem = (UnitRecyclerItem) bean;
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