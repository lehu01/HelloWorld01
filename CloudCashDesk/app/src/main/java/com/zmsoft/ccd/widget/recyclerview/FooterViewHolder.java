package com.zmsoft.ccd.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zmsoft.ccd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/2 14:21.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {

    public interface FOOTER_STATE {
        int GONE = 0;       // 不显示
        int LOADING = 1;    // 显示正在加载
    }

    public interface OnLoadMoreListener {
        void loadMore(int pageIndex);
    }

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_load_more)
    TextView tvLoadMore;

    public FooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
