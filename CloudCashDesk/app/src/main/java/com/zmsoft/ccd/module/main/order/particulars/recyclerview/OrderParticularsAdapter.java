package com.zmsoft.ccd.module.main.order.particulars.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.module.main.order.particulars.model.OrderParticularsModel;
import com.zmsoft.ccd.widget.recyclerview.FooterViewHolder;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/18 14:09.
 */

public class OrderParticularsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_INDEX_INITIAL = 1;

    public interface VIEW_HOLDER_TYPE {
        int NORMAL = 0;         // 正常订单信息
        int SECTION = 1;        // 日期分割
        int FOOTER = 2;         // 最底下的“正在加载”栏
    }

    public interface OnItemClickListener {
        void onItemClick(OrderParticularsItem orderParticularsItem);
    }

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private final OrderParticularsModel mOrderParticularsModel;

    private OnItemClickListener mOnItemClickListener;
    final private FooterViewHolder.OnLoadMoreListener mOnLoadMoreListener;

    private int mFooterState;
    private int mPageIndex;
    private boolean mIsLoadingMore;

    //================================================================================
    // construct
    //================================================================================
    public OrderParticularsAdapter(RecyclerView v, FooterViewHolder.OnLoadMoreListener onLoadMoreListener) {
        mContext = v.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        mOnLoadMoreListener = onLoadMoreListener;

        mFooterState = FooterViewHolder.FOOTER_STATE.GONE;
        resetPageIndex();
        mIsLoadingMore = false;
        mOrderParticularsModel = new OrderParticularsModel();
    }

    //================================================================================
    // setter
    //================================================================================
    public void updateDataAndNotify(List<OrderParticularsItem> orderParticularsItemList, boolean isFirstPage) {
        mOrderParticularsModel.appendData(orderParticularsItemList, isFirstPage);
        notifyDataSetChanged();
    }

    //================================================================================
    // RecyclerView.Adapter
    //================================================================================
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_HOLDER_TYPE.NORMAL == viewType) {
            return new OrderParticularsViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_order_particulars, parent, false));
        } else if (VIEW_HOLDER_TYPE.SECTION == viewType) {
            return new OrderParticularsSectionViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_order_particulars_section, parent, false));
        } else {    // VIEW_HOLDER_TYPE.FOOTER
            return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_recycler_view_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderParticularsViewHolder) {
            OrderParticularsItem orderParticularsItem = mOrderParticularsModel.getOrderParticularsItem(position);
            if (null == orderParticularsItem) {
                return;
            }
            OrderParticularsViewHolder orderParticularsViewHolder = (OrderParticularsViewHolder) holder;
            orderParticularsViewHolder.itemView.setOnClickListener(getOnClickListener(orderParticularsItem));
            orderParticularsViewHolder.updateView(orderParticularsItem);
        } else if (holder instanceof OrderParticularsSectionViewHolder) {
            String timeYYYYMMDD = mOrderParticularsModel.getTimeYYYYMMDD(position);
            OrderParticularsSectionViewHolder orderParticularsSectionViewHolder = (OrderParticularsSectionViewHolder) holder;
            orderParticularsSectionViewHolder.updateDate(timeYYYYMMDD);
        } else if (holder instanceof FooterViewHolder) {
            if (!mIsLoadingMore) {
                mIsLoadingMore = true;
                mOnLoadMoreListener.loadMore(mPageIndex);
            }
        }
    }

    @Override
    public int getItemCount() {
        int originItemCount = mOrderParticularsModel.getItemCount();    // 不计footer的itemCount
        if (FooterViewHolder.FOOTER_STATE.LOADING == mFooterState) {
            return originItemCount + 1;
        } else {
            return originItemCount;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mOrderParticularsModel.getViewType(position);
    }

    //================================================================================
    // item click
    //================================================================================
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private View.OnClickListener getOnClickListener(final OrderParticularsItem orderParticularsItem) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mOnItemClickListener != null && v != null) {
                    mOnItemClickListener.onItemClick(orderParticularsItem);
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
