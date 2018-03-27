package com.zmsoft.ccd.module.personal.attention.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;
import com.zmsoft.ccd.module.personal.attention.adapter.viewholder.AddAttenDeskSectionViewholder;
import com.zmsoft.ccd.module.personal.attention.adapter.viewholder.AddAttenDeskViewholder;

import java.util.ArrayList;

/**
 * @author DangGui
 * @create 2016/12/19.
 */

public class AddAttenDeskSectionAdapter extends BaseListAdapter {
    private static final int ITEM_TYPE_SECTION = 1;//section
    private static final int ITEM_TYPE_ITEM = 2;//item
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public AddAttenDeskSectionAdapter(Context context, FooterClick footerClick) {
        super(context, footerClick);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SECTION:
                return new AddAttenDeskSectionViewholder(mContext, mLayoutInflater.inflate(R.layout.item_header_recyclerview_add_atten_desk_view, parent, false));
            case ITEM_TYPE_ITEM:
                return new AddAttenDeskViewholder(mContext, mLayoutInflater.inflate(R.layout.item_recyclerview_add_atten_desk, parent, false), (ArrayList<ViewHolderSeat>) getList());
            default:
                return getUnKnowViewHolder(parent);
        }
    }

    @Override
    protected int getMyItemViewType(int position) {
        if (position < getList().size()) {
            ViewHolderSeat collectionInfo = (ViewHolderSeat) getList().get(position);
            if (null == collectionInfo)
                return -1;
            if (collectionInfo.isHeader())
                return ITEM_TYPE_SECTION;
            return ITEM_TYPE_ITEM;
        }
        return -1;
    }
}
