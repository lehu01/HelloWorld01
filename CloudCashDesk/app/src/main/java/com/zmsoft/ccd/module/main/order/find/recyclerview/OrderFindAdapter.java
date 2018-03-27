package com.zmsoft.ccd.module.main.order.find.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.widget.recyclerview.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/2 11:30.
 */

public class OrderFindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int PAGE_SIZE = 3 * 10;               // 每行显示3列，显示10行
    public static final int PAGE_INDEX_INITIAL = 1;

    public interface VIEW_HOLDER_TYPE {
        int NORMAL = 0;
        int FOOTER = 1;
    }

    public interface OnItemClickListener {
        void onItemClick(OrderFindItem orderFindItem);
    }

    private LayoutInflater mLayoutInflater;
    final private List<OrderFindItem> mOrderFindItemList;

    private OnItemClickListener mOnItemClickListener;
    final private FooterViewHolder.OnLoadMoreListener mOnLoadMoreListener;

    private int mFooterState;
    private int mPageIndex;
    private boolean mIsLoadingMore;

    //================================================================================
    // construct
    //================================================================================
    public OrderFindAdapter(Context context, FooterViewHolder.OnLoadMoreListener onLoadMoreListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mOrderFindItemList = new ArrayList<>();
        mFooterState = FooterViewHolder.FOOTER_STATE.GONE;
        mOnLoadMoreListener = onLoadMoreListener;
        resetPageIndex();
        mIsLoadingMore = false;
    }

    //================================================================================
    // RecyclerView.Adapter
    //================================================================================
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_HOLDER_TYPE.NORMAL:
                return new OrderFindViewHolder(mLayoutInflater.inflate(R.layout.item_desk_grid, parent, false));
            case VIEW_HOLDER_TYPE.FOOTER:
                return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_footer, parent, false));
            default:
                return new OrderFindViewHolder(mLayoutInflater.inflate(R.layout.item_desk_grid, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == mOrderFindItemList.size()) {
            /**
             *  最后添加一栏
             *  FooterViewHolder.STATE.GONE == mFooterState时，显示空白OrderFindViewHolder，防止挡住“开单按钮”
             *  否则，显示footer
             */
            switch (mFooterState) {
                case FooterViewHolder.FOOTER_STATE.GONE:
                    ((OrderFindViewHolder) holder).updateView(new OrderFindItem(true));
                    break;
                case FooterViewHolder.FOOTER_STATE.LOADING:
                    if (!mIsLoadingMore) {
                        mIsLoadingMore = true;
                        mOnLoadMoreListener.loadMore(mPageIndex);
                    }
                    break;
            }
            return;
        }
        OrderFindItem orderFindItem = mOrderFindItemList.get(position);
        holder.itemView.setOnClickListener(getOnClickListener(orderFindItem));
        ((OrderFindViewHolder) holder).updateView(orderFindItem);
    }

    @Override
    public int getItemCount() {
        if (0 == mOrderFindItemList.size()) {
            return 0;
        }
        /**
         *  最后添加一栏
         *  FooterViewHolder.STATE.GONE == mFooterState时，显示空白OrderFindViewHolder，防止挡住“开单按钮”
         *  否则，显示footer
         */
        return mOrderFindItemList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mOrderFindItemList.size()) {
            if (FooterViewHolder.FOOTER_STATE.GONE == mFooterState) {
                return VIEW_HOLDER_TYPE.NORMAL;
            } else {
                return VIEW_HOLDER_TYPE.FOOTER;
            }
        } else {
            return VIEW_HOLDER_TYPE.NORMAL;
        }
    }

    //================================================================================
    // update data and notify
    //================================================================================
    public void updateEatTimeAndNotify(int timeOutMinutes) {
        for (OrderFindItem orderFindItem : mOrderFindItemList) {
            if (orderFindItem.isEmpty()) {
                continue;
            }
            orderFindItem.updateEatTime(timeOutMinutes);
        }
        notifyDataSetChangedOnMainThread();
    }

    public void updateDataAndNotify(List<OrderFindItem> orderFindItemList) {
        mOrderFindItemList.clear();
        if (null != orderFindItemList) {
            mOrderFindItemList.addAll(orderFindItemList);
        }
        notifyDataSetChangedOnMainThread();
    }

    private void notifyDataSetChangedOnMainThread() {
        Observable.just(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    //================================================================================
    // item click
    //================================================================================
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private View.OnClickListener getOnClickListener(final OrderFindItem orderFindItem) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mOnItemClickListener != null && v != null) {
                    mOnItemClickListener.onItemClick(orderFindItem);
                }
            }
        };
    }

    //================================================================================
    // haveMoreData
    //================================================================================
    public void setHaveMoreData(boolean haveMoreData) {
        this.mFooterState = haveMoreData ? FooterViewHolder.FOOTER_STATE.LOADING: FooterViewHolder.FOOTER_STATE.GONE;
    }

    //================================================================================
    // page index
    //================================================================================
    public void resetPageIndex() {
        mPageIndex = PAGE_INDEX_INITIAL;
        mIsLoadingMore = false;
    }

    public void increasePageIndex() {
        mPageIndex++;
        mIsLoadingMore = false;
    }
}
