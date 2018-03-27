package com.zmsoft.ccd.module.main.seat.selectseat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.desk.SelectSeatVo;
import com.zmsoft.ccd.module.main.seat.selectseat.viewholder.SelectAreaViewHolder;
import com.zmsoft.ccd.module.main.seat.selectseat.viewholder.SelectSeatViewHolder;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:24
 */
public class SelectSeatAdapter extends BaseListAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public SelectSeatAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        SelectSeatVo seatVo = (SelectSeatVo) getList().get(position);
        return seatVo.getType();
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SelectSeatVo.ITEM_TYPE_AREA: // 1.区域
                return new SelectAreaViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_selectseat_area, parent, false));
            case SelectSeatVo.ITEM_TYPE_SEAT: // 2.桌位
                return new SelectSeatViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_add_atten_desk, parent, false),this);
            default:
                return new BaseHolder(mContext, new LinearLayout(mContext)) {
                    @Override
                    protected void bindView(List source, Object bean) {

                    }
                };
        }
    }
}
