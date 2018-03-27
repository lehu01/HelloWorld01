package com.zmsoft.ccd.module.mistake.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseRecyclerHolder;
import com.zmsoft.ccd.lib.bean.mistakes.Mistake;
import com.zmsoft.ccd.lib.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jihuo on 2016/12/26.
 */

public class MistakeViewHolder extends BaseRecyclerHolder<Mistake> {

    @BindView(R.id.text_time)
    TextView mTextViewTime;
    @BindView(R.id.text_id)
    TextView mTextViewId;
    @BindView(R.id.text_seat_code)
    TextView mTextViewSeatCode;
    @BindView(R.id.text_mistake_content)
    TextView mTextViewMistakeContent;
    @BindView(R.id.seat_name_text)
    TextView mTextViewSeatName;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public MistakeViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void initView(BaseRecyclerHolder holder, Mistake item, int position) {
        mTextViewTime.setText(dateFormat.format(new Date(item.getMistakeTime())));
        mTextViewId.setText("No."+item.getOrderNO());
        if (!StringUtils.isEmpty(item.getSeatName())){
            mTextViewSeatCode.setText(item.getSeatName());
        }else {
            mTextViewSeatName.setVisibility(View.GONE);
        }
        mTextViewMistakeContent.setText(item.getMistakeContent());
    }
}
