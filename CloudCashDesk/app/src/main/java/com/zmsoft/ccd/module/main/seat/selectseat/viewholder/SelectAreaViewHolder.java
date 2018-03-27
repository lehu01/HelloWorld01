package com.zmsoft.ccd.module.main.seat.selectseat.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.bean.desk.SelectSeatVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 14:27
 */
public class SelectAreaViewHolder extends BaseHolder {

    @BindView(R.id.text_title)
    TextView mTextTitle;

    public SelectAreaViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    private void fillView(SelectSeatVo seatVo) {
        mTextTitle.setText(seatVo.getAreaName());
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean != null && bean instanceof SelectSeatVo) {
            SelectSeatVo item = (SelectSeatVo) bean;
            fillView(item);
        }
    }
}
