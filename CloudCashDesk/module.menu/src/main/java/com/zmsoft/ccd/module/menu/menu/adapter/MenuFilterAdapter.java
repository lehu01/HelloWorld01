package com.zmsoft.ccd.module.menu.menu.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.module.menu.R;
import com.zmsoft.ccd.module.menu.R2;
import com.zmsoft.ccd.module.menu.menu.bean.vo.MenuGroupVO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/4/18.
 */

public class MenuFilterAdapter extends BaseListAdapter {

    public static final int SHOW_TYPE_CATEGORY = 1;
    public static final int SHOW_TYPE_LETTER = 2;
    private int mShowType = SHOW_TYPE_CATEGORY;
    AdapterClick mAdapterClick;

    public MenuFilterAdapter(Context context, AdapterClick adapterClick) {
        super(context, null);
        mAdapterClick = adapterClick;
    }

    @Override
    public BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterHolder(getContext(), inflateLayout(R.layout.module_menu_item_menu_filter, parent));
    }

    public void setShowType(int showType) {
        mShowType = showType;
    }

    class FilterHolder extends BaseHolder {

        @BindView(R2.id.text_filter)
        TextView mTextFilter;
        @BindView(R2.id.view_divider)
        View viewDivider;

        FilterHolder(Context context, View itemView) {
            super(context, itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView(List source, final Object bean) {
            if (bean instanceof MenuGroupVO) {
                MenuGroupVO category = (MenuGroupVO) bean;
                mTextFilter.setText(category.getGroupName());
                mTextFilter.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                mTextFilter.setText(bean.toString());
                mTextFilter.setGravity(Gravity.CENTER);
            }
            viewDivider.setVisibility(getAdapterPosition() == source.size() - 1 ? View.GONE : View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterClick.onAdapterClick(mShowType, v, bean);
                }
            });
        }
    }
}
