package com.zmsoft.ccd.module.personal.attention.adapter.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;

import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public class AddAttenDeskSectionViewholder extends BaseHolder {
    private ViewHolderSeat mDeskSection;
    //item 头部布局
    private FrameLayout mItemView;
    private CheckBox mCbScope;
    private TextView mTextScopeName;

    public AddAttenDeskSectionViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mItemView = (FrameLayout) itemView.findViewById(R.id.linear_root);
    }

    @Override
    protected void bindView(List source, Object bean) {
        mCbScope = (CheckBox) itemView.findViewById(R.id.checkbox_scope);
        mTextScopeName = (TextView) itemView.findViewById(R.id.text_scope_name);
        if (null == bean)
            return;
        if (bean instanceof ViewHolderSeat)
            mDeskSection = (ViewHolderSeat) bean;
        if (null == mDeskSection)
            return;
        initInfoView();
        initListener();
    }

    private void initInfoView() {
        if (!TextUtils.isEmpty(mDeskSection.getAreaName()))
            mTextScopeName.setText(mDeskSection.getAreaName());
        mCbScope.setChecked(mDeskSection.isHasChecked());
    }

    private void initListener() {
        mCbScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeskSection.setHasChecked(((CheckBox) v).isChecked());
                BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CHECKED_SECTION_ITEMS_EVENT;
                event.setObject(mDeskSection);
                EventBusHelper.post(event);
            }
        });
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCbScope.performClick();
            }
        });
    }
}
