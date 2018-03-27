package com.zmsoft.ccd.module.main.seat.selectseat.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.event.seat.UpdateSelectSeatCheckEvent;
import com.zmsoft.ccd.helper.DeskHelper;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.desk.SelectSeatVo;
import com.zmsoft.ccd.module.main.seat.selectseat.adapter.SelectSeatAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:27
 */
public class SelectSeatViewHolder extends BaseHolder {

    @BindView(R.id.checkbox_desk)
    CheckBox mCheckboxDesk;
    @BindView(R.id.text_desk_name)
    TextView mTextDeskName;
    @BindView(R.id.text_desk_name_remark)
    TextView mTextDeskNameRemark;
    @BindView(R.id.text_desk_desc)
    TextView mTextDeskDesc;
    @BindView(R.id.linear_root)
    LinearLayout mLinearRoot;
    @BindView(R.id.view_divider)
    View mViewDivider;
    @BindView(R.id.layout_jag_up)
    View mLayoutJagUp;
    @BindView(R.id.layout_jag_bottom)
    View mLayoutJagBottom;

    private List<SelectSeatVo> mData;
    private SelectSeatAdapter mSelectSeatAdapter;

    public SelectSeatViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        if (null != adapter && adapter instanceof SelectSeatAdapter) {
            mSelectSeatAdapter = (SelectSeatAdapter) adapter;
        }
        mData = mSelectSeatAdapter.getList();
    }

    private void initListener() {
        mLinearRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCheckboxDesk.performClick();
            }
        });
    }

    @OnClick(R.id.checkbox_desk)
    void select() {
        int position = getLayoutPosition();
        UpdateSelectSeatCheckEvent event = new UpdateSelectSeatCheckEvent();
        event.setPosition(position);
        event.setCheck(mCheckboxDesk.isChecked());
        EventBusHelper.post(event);
    }

    private void fillView(SelectSeatVo seatVo) {
        Seat seat = seatVo.getSeat();
        if (seat != null) {
            mTextDeskName.setText(seat.getName());
            if (seat.getSeatKind() == Seat.SeatKind.DESK_KIND_NODESK) {
                mTextDeskDesc.setVisibility(View.GONE);
            } else {
                mTextDeskDesc.setVisibility(View.VISIBLE);
                String kind = DeskHelper.getSeatKindString(seat.getSeatKind());
                mTextDeskDesc.setText(String.format(getContext().getResources().getString(R.string.msg_attention_desk_kind), seat.getAdviseNum(), kind));
            }
            mCheckboxDesk.setChecked(seatVo.isCheck());
        }
    }

    private void initViewSemicircle(int position) {
        // seat开始之前，必定有title，所以 position-1 成立的
        if (mData.get(position - 1).getType() == SelectSeatVo.ITEM_TYPE_AREA) {
            if (position == mData.size() - 1) { // 判断是否是最后一项，不然position+1越界
                setSemicircle(true, true);
                mViewDivider.setVisibility(View.GONE);
            } else {
                if (mData.get(position + 1).getType() == SelectSeatVo.ITEM_TYPE_AREA) {
                    setSemicircle(true, true);
                    mViewDivider.setVisibility(View.GONE);
                } else if (mData.get(position + 1).getType() == SelectSeatVo.ITEM_TYPE_SEAT) {
                    setSemicircle(true, false);
                    mViewDivider.setVisibility(View.VISIBLE);
                }
            }
        } else if (mData.get(position - 1).getType() == SelectSeatVo.ITEM_TYPE_SEAT) {
            if (position == mData.size() - 1) { // 判断是否是最后一项，不然position+1越界
                setSemicircle(false, true);
                mViewDivider.setVisibility(View.GONE);
            } else {
                if (mData.get(position + 1).getType() == SelectSeatVo.ITEM_TYPE_SEAT) {
                    setSemicircle(false, false);
                    mViewDivider.setVisibility(View.VISIBLE);
                } else if (mData.get(position + 1).getType() == SelectSeatVo.ITEM_TYPE_AREA) {
                    setSemicircle(false, true);
                    mViewDivider.setVisibility(View.GONE);
                }
            }
        }
    }

    private void setSemicircle(boolean top, boolean bottom) {
        if (top) {
            mLayoutJagUp.setVisibility(View.VISIBLE);
        } else {
            mLayoutJagUp.setVisibility(View.GONE);
        }

        if (bottom) {
            mLayoutJagBottom.setVisibility(View.VISIBLE);
        } else {
            mLayoutJagBottom.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean != null && bean instanceof SelectSeatVo) {
            SelectSeatVo item = (SelectSeatVo) bean;
            fillView(item);
            initViewSemicircle(getAdapterPosition());
            initListener();
        }
    }
}
