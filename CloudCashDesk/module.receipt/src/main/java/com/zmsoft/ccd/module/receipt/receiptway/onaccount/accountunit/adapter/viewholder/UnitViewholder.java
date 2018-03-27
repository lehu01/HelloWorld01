package com.zmsoft.ccd.module.receipt.receiptway.onaccount.accountunit.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.module.receipt.R2;
import com.zmsoft.ccd.module.receipt.events.BaseEvents;

import java.util.List;

import butterknife.BindView;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class UnitViewholder extends UnitBaseViewholder {

    @BindView(R2.id.text_name)
    TextView mTextName;
    @BindView(R2.id.checkbox_staff)
    CheckBox mCheckboxStaff;
    @BindView(R2.id.layout_sign_staff)
    RelativeLayout mLayoutSignStaff;

    private View mItemView;

    public UnitViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mItemView = itemView;
    }

    @Override
    protected void bindView(List source, Object bean) {
        super.bindView(source, bean);
        if (null != mUnitRecyclerItem) {
            mTextName.setText(mUnitRecyclerItem.getUnitName());
            mCheckboxStaff.setChecked(mUnitRecyclerItem.isChecked());
        }
        initListener();
    }

    private void initListener() {
        mCheckboxStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUnitRecyclerItem.setChecked(((CheckBox) v).isChecked());
                BaseEvents.CommonEvent event = BaseEvents.CommonEvent.RECEIPT_UNIT_CHECKED_ITEM_EVENT;
                event.setObject(mUnitRecyclerItem);
                EventBusHelper.post(event);
            }
        });
        mLayoutSignStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxStaff.performClick();
            }
        });
    }

}
