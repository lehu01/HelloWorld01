package com.zmsoft.ccd.module.receipt.receipt.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.helper.BusinessHelper;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.receipt.adapter.items.ReceiptMethodRecyclerItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ReceiptMethodViewholder extends ReceiptBaseViewholder {
    @BindView(R2.id.text_method)
    TextView mTextMethod;
    @BindView(R2.id.layout_method)
    LinearLayout mLayoutMethod;

    private View mItemView;
    private ReceiptMethodRecyclerItem mMethodRecyclerItem;

    public ReceiptMethodViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mItemView = itemView;
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mReceiptRecyclerItem) {
            mMethodRecyclerItem = mReceiptRecyclerItem.getMethodRecyclerItem();
            if (null != mMethodRecyclerItem) {
                mTextMethod.setText(mMethodRecyclerItem.getName());
                mTextMethod.setCompoundDrawablesWithIntrinsicBounds(BusinessHelper.getPayTypeIcon(mMethodRecyclerItem.getMethod()), 0, 0, 0);
            }
        }
        initListener();
    }

    private void initListener() {
        RxView.clicks(mLayoutMethod).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mItemView.performClick();
                    }
                });
    }

}
