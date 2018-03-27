package com.zmsoft.ccd.module.main.order.memo.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.remark.UpdateRemarkCheckEvent;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.order.remark.Memo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/5 19:36
 */
public class MemoViewHolder extends BaseHolder {

    @BindView(R.id.checkbox_remark)
    CheckBox mCheckboxRemark;
    @BindView(R.id.text_remark_content)
    TextView mTextRemarkContent;
    @BindView(R.id.linear_root)
    LinearLayout mLinearRoot;

    public MemoViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    private void initListener() {
        mCheckboxRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                UpdateRemarkCheckEvent event = new UpdateRemarkCheckEvent();
                event.setPosition(position);
                event.setCheck(mCheckboxRemark.isChecked());
                EventBusHelper.post(event);
            }
        });
        mLinearRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckboxRemark.performClick();
            }
        });
    }

    private void fillView(Memo item) {
        mCheckboxRemark.setChecked(item.isCheck());
        mTextRemarkContent.setText(item.getName());
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean != null && bean instanceof Memo) {
            Memo memo = (Memo) bean;
            fillView(memo);
            initListener();
        }
    }
}
