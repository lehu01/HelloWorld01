package com.zmsoft.ccd.module.personal.attention.adapter.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public class AddAttenDeskViewholder extends BaseHolder {
    private Context mContext;
    private ArrayList<ViewHolderSeat> mDatas;
    private Seat mDesk;
    //item 头部布局
    private LinearLayout mItemView;
    private CheckBox mCbDesk;
    private TextView mTvDeskName;
    private TextView mTvDeskDesc;
    private TextView mTvDeskRemark;
    private View mDividerView;
    private View mJagUp;
    private View mJagBottom;
    private ViewHolderSeat mDeskSection;

    public AddAttenDeskViewholder(Context context, View itemView, ArrayList<ViewHolderSeat> viewHolderSeats) {
        super(context, itemView);
        this.mContext = context;
        this.mDatas = viewHolderSeats;
        this.mItemView = (LinearLayout) itemView.findViewById(R.id.linear_root);
        mCbDesk = (CheckBox) itemView.findViewById(R.id.checkbox_desk);
        mTvDeskName = (TextView) itemView.findViewById(R.id.text_desk_name);
        mTvDeskDesc = (TextView) itemView.findViewById(R.id.text_desk_desc);
        mTvDeskRemark = (TextView) itemView.findViewById(R.id.text_desk_name_remark);
        mDividerView = itemView.findViewById(R.id.view_divider);
        mJagUp = itemView.findViewById(R.id.layout_jag_up);
        mJagBottom = itemView.findViewById(R.id.layout_jag_bottom);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null == bean)
            return;
        if (bean instanceof ViewHolderSeat)
            mDeskSection = (ViewHolderSeat) bean;
        if (null == mDeskSection)
            return;
        mDesk = mDeskSection.getSeat();
        if (null == mDesk)
            return;
        initInfoView(getAdapterPosition());
        initListener(getAdapterPosition());
    }

    private void initInfoView(int position) {
        if (!TextUtils.isEmpty(mDesk.getName())) {
            mTvDeskName.setText(mDesk.getName());
        } else {
            mTvDeskName.setText("");
        }
        if (!TextUtils.isEmpty(mDesk.getMemo())) {
            // 只有在中文环境下，才显示memo信息
            if(LanguageUtil.isChineseGroup()) {
                mTvDeskRemark.setText("(" + mDesk.getMemo() + ")");
            }
        } else {
            mTvDeskRemark.setText("");
        }
        mTvDeskDesc.setVisibility(View.VISIBLE);
        String kind = mContext.getResources().getString(R.string.msg_attention_desk_kind_random);
        switch (mDesk.getSeatKind()) {
            case Seat.SeatKind.DESK_KIND_RANDOM:
                kind = mContext.getResources().getString(R.string.msg_attention_desk_kind_random);
                break;
            case Seat.SeatKind.DESK_KIND_BOX:
                kind = mContext.getResources().getString(R.string.msg_attention_desk_kind_box);
                break;
            case Seat.SeatKind.DESK_KIND_CARD:
                kind = mContext.getResources().getString(R.string.msg_attention_desk_kind_card);
                break;
            default:
                mTvDeskDesc.setVisibility(View.GONE);
                break;
        }
        mTvDeskDesc.setText(String.format(mContext.getResources().getString(R.string.msg_attention_desk_kind)
                , mDesk.getAdviseNum(), kind));
        mCbDesk.setChecked(mDesk.isBind());
        initViewSemicircle(position);
    }

    private void initListener(final int position) {
        mCbDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDesk.setBind(((CheckBox) v).isChecked());
                BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CHECK_IF_SECTION_ITEM_CHECKED;
                event.setObject(mDesk);
                EventBusHelper.post(event);
            }
        });
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCbDesk.performClick();
            }
        });
    }

    private void initViewSemicircle(int position) {
        if (mDatas.get(position - 1).isHeader()) {
            if (position + 1 >= mDatas.size()) {
                setSemicircle(true, true);
                mDividerView.setVisibility(View.GONE);
            } else {
                if (mDatas.get(position + 1).isHeader()) {
                    setSemicircle(true, true);
                    mDividerView.setVisibility(View.GONE);
                } else {
                    setSemicircle(true, false);
                    mDividerView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (position + 1 >= mDatas.size()) {
                setSemicircle(false, true);
                mDividerView.setVisibility(View.GONE);
            } else {
                if (mDatas.get(position + 1).isHeader()) {
                    setSemicircle(false, true);
                    mDividerView.setVisibility(View.GONE);
                } else {
                    setSemicircle(false, false);
                    mDividerView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 设置item上下边框用不用锯齿
     *
     * @param top    上边框
     * @param bottom 下边框
     */
    private void setSemicircle(boolean top, boolean bottom) {
        mJagUp.setVisibility(top ? View.VISIBLE : View.GONE);
        mJagBottom.setVisibility(bottom ? View.VISIBLE : View.GONE);
    }
}
