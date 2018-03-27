package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zmsoft.ccd.lib.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseListAdapter
 * Created by kumu on 2017/3/3.
 */

public abstract class BaseListAdapter extends RecyclerView.Adapter<BaseHolder> {

    private final static int FOOTER_TYPE = 99;
    private final static int EMPTY_TYPE = 100;

    private boolean mShowFooter;
    private boolean mShowEmpty;

    protected Context mContext;
    private LayoutInflater mInflater;

    private int mEmptyLayout;

    private boolean mFooterClickable;
    private FooterClick mFooterClick;

    private OnItemClickListener mOnItemClickListener;/*item点击监听*/

    //内部维护数据源
    private List<Object> list = new ArrayList<>();

    public BaseListAdapter(Context context, FooterClick footerClick) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mFooterClick = footerClick;
    }

    public BaseListAdapter(Context context, FooterClick footerClick, @LayoutRes int emptyLayout) {
        this.mContext = context;
        this.mEmptyLayout = emptyLayout;
        this.mFooterClick = footerClick;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 使用onMyCreateViewHolder
     *
     * @see BaseListAdapter#onMyCreateViewHolder(ViewGroup, int)
     */
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case EMPTY_TYPE:
                return new EmptyHolder(mContext, inflateLayout(mEmptyLayout, viewGroup));
            case FOOTER_TYPE:
                return new FooterViewHolder(mContext, inflateLayout(R.layout.footer_loading_layout, viewGroup), this);
            default:
                return onMyCreateViewHolder(viewGroup, viewType);
        }
    }


    @Override
    public void onBindViewHolder(BaseHolder viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(getOnClickListener(position));
        viewHolder.itemView.setOnLongClickListener(getOnLongClickListener(position));
        int type = getItemViewType(position);
        switch (type) {
            case EMPTY_TYPE:
            case FOOTER_TYPE:
                viewHolder.bindView(list, null);
                break;
            default:
                viewHolder.bindView(list, list.get(position));
                break;
        }
    }


    @Override
    public int getItemCount() {
        return getMyItemCount() + (mShowFooter ? 1 : 0) + (mShowEmpty ? 1 : 0);
    }

    public abstract BaseHolder onMyCreateViewHolder(ViewGroup parent, int viewType);

    protected int getMyItemViewType(int position) {
        return 0;
    }

    public List getList() {
        return list;
    }

    public void appendItems(List items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        hideEmpty();
        list.addAll(items);
        notifyDataSetChanged();
    }

    public void appendItems(int position, List items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        list.addAll(position, items);
        notifyDataSetChanged();
    }

    public void appendItem(Object item) {
        if (item == null) {
            return;
        }
        list.add(item);
        notifyDataSetChanged();
    }


    public void appendItemAtPosition(int position, Object item) {
        if (item == null) {
            return;
        }
        list.add(position, item);
        notifyDataSetChanged();
        //notifyItemInserted(position);
    }

    public void updateItem(int position) {
        notifyItemChanged(position);
    }


    public void updateItems(int startPosition, int itemCount) {
        notifyItemRangeChanged(startPosition, itemCount);
    }

    public void setList(List list) {
        this.list = list;
    }

    public void removeAll() {
        list.clear();
        hideFooter();
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position < 0) {
            return;
        }
        list.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position);
    }

    public void removeItems(List items) {
        if (items != null && !items.isEmpty()) {
            list.removeAll(items);
            notifyDataSetChanged();
        }
    }

    public void removeObject(Object object) {
        if (object != null) {
            list.remove(object);
            notifyDataSetChanged();
        }

    }

    public Object getModel(int position) {
        if (list.size() == 0) {
            return null;
        }
        return list.get(position);
    }

    public int getMyItemCount() {
        return list.size();
    }

    /**
     * @return list.size()
     */
    public int getListCount() {
        return list.size();
    }

    public BaseHolder getUnKnowViewHolder(ViewGroup parent) {
        TextView tvUnknown = new TextView(parent.getContext());
        tvUnknown.setText(R.string.list_item_debug);
        return new UnknownViewHolder(mContext, tvUnknown);
    }

    public String getString(int strId) {
        return mContext.getString(strId);
    }

    private static class FooterViewHolder extends BaseHolder {
        ProgressBar progressBar;
        TextView tvLoadMore;
        BaseListAdapter adapter;

        FooterViewHolder(Context context, View itemView, BaseListAdapter adapter) {
            super(context, itemView);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            this.tvLoadMore = (TextView) itemView.findViewById(R.id.tv_load_more);
            this.adapter = adapter;
        }

        @Override
        protected void bindView(List source, Object bean) {
            if (adapter.mFooterClickable) {
                tvLoadMore.setText(R.string.list_loading_more_failed);
                progressBar.setVisibility(View.GONE);
            } else {
                tvLoadMore.setText(R.string.list_loading_more);
                progressBar.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.mFooterClickable && adapter.mFooterClick != null) {
                        //点击后继续
                        adapter.mFooterClickable = false;
                        adapter.notifyItemChanged(getAdapterPosition());
                        adapter.mFooterClick.onFooterClick();
                    }
                }
            });
        }
    }

    private static class EmptyHolder extends BaseHolderEmpty {
        EmptyHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }

    public void setFooterClickable(boolean clickable) {
        this.mFooterClickable = clickable;
    }

    public void showFooter() {
        hideEmpty();
        mShowFooter = true;
        notifyItemChanged(getItemCount() - 1);
    }

    public void hideFooter() {
        mShowFooter = false;
        notifyItemChanged(getItemCount());
    }

    public void showEmpty() {
        if (mEmptyLayout == 0) {
            return;
        }
        if (mShowEmpty) {
            return;
        }
        mShowEmpty = true;
        mShowFooter = false;
        notifyItemChanged(0);
    }

    public void hideEmpty() {
        if (mShowEmpty) {
            mShowEmpty = false;
            notifyItemChanged(0);
        }
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowEmpty) {
            return EMPTY_TYPE;
        } else if (mShowFooter && position == getMyItemCount()) {
            return FOOTER_TYPE;
        }
        return getMyItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mOnItemClickListener != null && v != null) {
                    mOnItemClickListener.onItemClick(v, position < list.size() ? list.get(position) : null, position);
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null && v != null) {
                    return mOnItemClickListener.onItemLongClick(v, position < list.size() ? list.get(position) : null, position);
                }
                return true;
            }
        };
    }

    /**
     * 是否可以显示空View,一般重写此接口的情况出现在自己有维护另外一个head,或者多个
     */
    protected boolean hasShowEmpty() {
        return true;
    }


    public View inflateLayout(int layoutId, ViewGroup parent) {
        return mInflater.inflate(layoutId, parent, false);
    }

    public interface FooterClick {
        void onFooterClick();
    }

    public interface AdapterClick {
        void onAdapterClick(int type, View view, Object data);
        void onAdapterClickEdit(int type, View view, Object data);
    }

    private static class UnknownViewHolder extends BaseHolderEmpty {
        UnknownViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, Object data, int position);/*item点击监听*/

        boolean onItemLongClick(View view, Object data, int position);/*item长按监听*/
    }
}