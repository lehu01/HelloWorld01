package com.zmsoft.ccd.lib.base.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    private List<T> mRealData; /*数据源*/
    private int mItemLayoutId = 0;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;/*item点击监听*/

    public interface OnItemClickListener {

        void onItemClick(View view, Object data, int position);/*item点击监听*/

        boolean onItemLongClick(View view, Object data, int position);/*item长按监听*/
    }

    public BaseRecyclerAdapter(RecyclerView v, Collection<T> data) {
        if (data == null) {
            mRealData = new ArrayList<>();
        } else if (data instanceof List) {
            mRealData = (List<T>) data;
        } else {
            mRealData = new ArrayList<>(data);
        }
        mContext = v.getContext();
    }

    public BaseRecyclerAdapter(RecyclerView v, Collection<T> data, int itemLayoutId) {
        if (data == null) {
            mRealData = new ArrayList<>();
        } else if (data instanceof List) {
            mRealData = (List<T>) data;
        } else {
            mRealData = new ArrayList<>(data);
        }
        mItemLayoutId = itemLayoutId;
        mContext = v.getContext();
    }

    /**
     * Recycler适配器填充方法，需实现自己的viewholder
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public void convert(BaseRecyclerHolder holder, T item, int position) {
        if (holder != null) {
            holder.initView(holder, item, position);
        }
    }

    @Override
    public abstract BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (null != holder) {
            convert(holder, position < mRealData.size() ? mRealData.get(position) : null, position);
            holder.itemView.setOnClickListener(getOnClickListener(position));
            holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return mRealData == null ? 0 : mRealData.size();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mOnItemClickListener != null && v != null) {
                    mOnItemClickListener.onItemClick(v, position < mRealData.size() ? mRealData.get(position) : null, position);
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null && v != null) {
                    return mOnItemClickListener.onItemLongClick(v, position < mRealData.size() ? mRealData.get(position) : null, position);
                }
                return true;
            }
        };
    }

    /**
     * 刷新数据源
     *
     * @param datas 新的数据源
     * @return
     */
    public BaseRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (datas == null) {
            mRealData = new ArrayList<>();
        } else if (datas instanceof List) {
            mRealData = (List<T>) datas;
        } else {
            mRealData = new ArrayList<>(datas);
        }
        return this;
    }

    /**
     * 获取当前数据源
     *
     * @return 当前数据源
     */
    public List<T> getDatasList() {
        return this.mRealData;
    }

    /**
     * 实现者根据需求自己实现mList的排序
     */
    public void sort() {
    }

    /**
     * 在列表末尾添加一个数据
     *
     * @param t
     */
    public void addItem(T t) {
        if (t == null) return;
        if (!mRealData.contains(t)) {
            mRealData.add(t);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    /**
     * 在列表头部添加一个数据
     *
     * @param t
     */
    public void addFirstItem(T t) {
        if (t == null) return;
        if (!mRealData.contains(t)) {
            mRealData.add(0, t);
        }
        notifyItemInserted(0);
        notifyItemRangeChanged(0, getItemCount());
    }

    /**
     * 在列表末尾添加一个list集合
     *
     * @param list
     */
    public void addItems(List<T> list) {
        if (list == null || list.size() == 0) return;
        for (T t : list) {
            if (!mRealData.contains(t)) {
                mRealData.add(t);
            }
        }
        sort();
        notifyDataSetChanged();
    }

    /**
     * 在列表头部添加一个list集合
     *
     * @param list
     */
    public void addFirstItems(List<T> list) {
        if (list == null || list.size() == 0) return;

        for (int i = list.size() - 1; i >= 0; i--) {
            if (!mRealData.contains(list.get(i))) {
                mRealData.add(0, list.get(i));
            }
        }
        sort();
        notifyDataSetChanged();
    }

    /**
     * 循环刷新多条数据
     *
     * @param list
     */
    public void updateItems(List<T> list) {
        if (list == null || list.size() == 0) return;

        for (T t : list)
            updateItem(t);
//        notifyDataSetChanged();
    }

    /**
     * 循环查找要刷新的数据所在position，刷新
     *
     * @param t
     */
    public void updateItem(T t) {
        if (t == null) return;
        if (mRealData == null) return;
        int position = mRealData.indexOf(t);
        if (position == -1) {
            addItem(t);
        } else {
            //mRealData.set(position, t);
            notifyItemChanged(position);
        }
    }

    /**
     * remove指定position数据
     * #测试发现notifyItemRemoved、notifyItemRangeChanged会导致position错乱，找到好的解决方案，及时更新该方法#
     *
     * @param position
     */
    public void remove(int position) {
        mRealData.remove(position);
//        notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    /**
     * 把指定list数据集合从数据源中remove掉
     * #测试发现notifyItemRemoved、notifyItemRangeChanged会导致position错乱，找到好的解决方案，及时更新该方法#
     *
     * @param list
     * @param position
     */
    public void removeItems(List<T> list, int position) {
        if (list == null || list.size() == 0) return;

        mRealData.removeAll(list);

        sort();
//        notifyItemRangeRemoved(position, list.size());
        notifyDataSetChanged();
    }
}
