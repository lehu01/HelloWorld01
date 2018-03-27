package com.zmsoft.ccd.lib.base.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zmsoft.ccd.lib.base.R;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;

import java.util.List;

/**
 * 列表界面的基类, 封装了如下功能:
 * <p>
 * 1.加载更多
 * <br/>
 * 2.加载更多失败后点击Item重试
 * <br/>
 * 3.下拉刷新
 * <br/>
 * <p>
 * <br/>
 * <p>
 * 内部已经内置布局, 包含了列表控件(RecyclerView)和刷新控件(SwipeRefreshLayout)
 * <p>
 * 你也可以通过{@link BaseFragment#createView(LayoutInflater, ViewGroup)}来自定义布局.
 * <p>
 * 自定义布局的时候 <b>RecyclerView</b> 的id必须是refresh_layout, <b>SwipeRefreshLayout</b> 的id必须是recycler_view
 * <p>
 * 使用的时候需要注意通过{@link #createAdapter()}构建界面的Adapter
 * <p>
 * 加载数据的操作可以放在{@link #loadListData()}方法里
 * <p>
 * 如果当前界面不分页, 一次性加载了所有的内容, 子类可以重写{@link #canLoadMore()}方法, 把返回值设置为false
 * <p>
 * 如果请求列表数据的时候需要传递 <b>pageCount</b> 参数, 必须统一通过{@link #getPageCount()}方法获取, 也可以自己设置每页加载的条数 {@link #setPageCount(int)}
 * <p>
 * 内部已经维护了一个 pageIndex, 如果请求的时候需要传递页码可以通过{@link #getPageIndex()} 方法获取
 * <p>
 * Created by kumu on 2017/3/3.
 * </p>
 */

public abstract class BaseListFragment extends BaseFragment implements BaseListAdapter.FooterClick {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private BaseListAdapter mAdapter;

    private boolean mAutoRefresh = true;
    private boolean mdisableRefresh;

    /**
     * 是否正在加载更多
     */
    private boolean mIsLoadingMore;
    /**
     * 是否正在刷新
     */
    private boolean mIsRefreshing;

    /**
     * 当前列表总条数
     */
    private int mCurrentCount;

    /**
     * 当前页数
     */
    private int mCurrentPage;

    private int mActualPerCount;
    /**
     * 每页加载的个数, 默认10条
     */
    private int mPageItemCount = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initListWidget(view);

        initRecyclerLayoutManager();
        mRecyclerView.setAdapter(mAdapter = createAdapter());
    }

    public void initRecyclerLayoutManager() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void initListener() {

    }

    private void initListWidget(View view) {
        this.mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        this.mRefreshLayout.setEnabled(!mdisableRefresh);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (mRecyclerView != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (bottom(recyclerView)) {
                        if (canLoadMore() && mAdapter.getListCount() != 0) {
                            startLoadMore();
                        }

                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                private boolean bottom(RecyclerView recyclerView) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                        return manager.findLastVisibleItemPosition() == getAdapter().getItemCount() - 1;
                    }
                    return false;
                }
            });
        }

        if (mRefreshLayout != null) {
            if (getAccentColor() > 0) {
                mRefreshLayout.setColorSchemeResources(getAccentColor(), getAccentColor(),
                        getAccentColor(), getAccentColor());
            }
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    startRefresh();
                }
            });
        }
    }

    /**
     * 列表是否已经加载完毕
     *
     * @return
     */
    protected boolean canLoadMore() {
        return checkLoadMoreByPage();
    }

    protected final boolean checkLoadMoreByPage() {
        return mActualPerCount >= mPageItemCount;
    }

    @Override
    protected void lazyLoad() {
        if (mAdapter != null && refreshThreshold()) {
            startRefresh();
        }
    }

    protected boolean refreshThreshold() {
        return mAdapter.getListCount() == 0;
    }


    protected final void resetParameters() {
        mCurrentPage = 1;
    }

    public final void startRefresh(boolean clean, boolean showLoading) {
        //是否符合刷新条件  是否正在刷新
        //mIsRefreshing主要解决多次调用startRefresh的问题（如推送多个通知导致一个界面startRefresh调用多次）
        if (!canRefresh() || mIsRefreshing) {
            //finishRefresh();
            return;
        }

        resetParameters();
        if (showLoading) {
            showLoading(false);
        }
        if (clean) {
            mAdapter.removeAll();
        }

        mIsRefreshing = true;
        if (mRefreshLayout != null && !showLoading) {
            refresh();
        } else {
            loadListData();
        }

    }

    /**
     * 能否刷新，子类根据自己的情况来实现该方法（如搜索界面如果没有关键字则不能执行搜索）
     * 如果在loadListData方法判断,然后return掉，没有执行网络请求，mIsRefresh则不会置为false（因为只要执行startRefresh会把mIsRefresh置为true）
     *
     * @return
     */
    public boolean canRefresh() {
        return true;
    }

    public final void startRefresh() {
        startRefresh(false, false);
    }

    public final void startRefresh(boolean clean) {
        startRefresh(clean, false);
    }


    /**
     * 下拉刷新
     */
    private void refresh() {
        mIsRefreshing = true;
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (!mAutoRefresh) {
                    mRefreshLayout.setRefreshing(false);
                    mAutoRefresh = true;
                } else {
                    mRefreshLayout.setRefreshing(true);
                }

                loadListData();
                // ^_^ avoid refresh widget dismiss fast ^_^
//                mRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        loadListData();
//                    }
//                }, 800);
            }
        });
    }

    public BaseListAdapter getAdapter() {
        return mAdapter;
    }

    public int getPageIndex() {
        return mCurrentPage;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mRefreshLayout;
    }

    /**
     * 每页加载的条数, 如果不设置默认是20条
     *
     * @return
     */
    public int getPageCount() {
        return mPageItemCount;
    }

    /**
     * 设置每页加载的条数
     *
     * @param pageCount int
     */
    public void setPageCount(int pageCount) {
        mPageItemCount = pageCount;
    }


    protected final void finishRefresh() {
        if (mIsRefreshing) {
            mIsRefreshing = false;
            if (mRefreshLayout != null) {
                mRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * 禁止一进界面就出现下拉刷新控件。但是{@link BaseListFragment#loadListData()} 方法还是为会执行
     */
    public void disableAutoRefresh() {
        mAutoRefresh = false;
    }

    public void disableRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnabled(false);
        }
        disableAutoRefresh();
        mdisableRefresh = true;
    }

    public void enableRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnabled(true);
        }
    }

    protected void cleanAllIfRefresh() {
        if (mIsRefreshing) {
            cleanAll();
        }
    }

    protected final void cleanAll() {
        mCurrentCount = 0;
        mAdapter.removeAll();
    }

    private void startLoadMore() {
        if (!mIsLoadingMore) {
            mIsLoadingMore = true;
            loadListData();
        }
    }

    protected final void resetLoadMoreState() {
        if (mIsLoadingMore) {
            mIsLoadingMore = false;
        }
    }


    protected void processSuccess(List content) {
        processSuccess(content, -1);
    }

    /**
     * @param content     界面展示的item list
     * @param actualCount 后台返回的条数（如果没有把后台返回的对象打散到list里，则actualCount=content.size()，可以传递-1，否则传入后台返回的实际条数）
     */
    protected void processSuccess(List content, int actualCount) {
        cleanAllIfRefresh();
        if (content != null && !content.isEmpty()) {
            mCurrentPage++;
            mActualPerCount = actualCount == -1 ? content.size() : actualCount;
            mCurrentCount += mActualPerCount;
            mAdapter.appendItems(content);
        } else {
            mActualPerCount = 0;
            if (mAdapter.getList().isEmpty()) {
                //show empty view if set
                mAdapter.showEmpty();
            }
        }

        toggleFooter();
        resetLoadMoreState();
        finishRefresh();
    }

    /**
     * 如果没有更多则不显示Footer
     */
    private void toggleFooter() {
        toggleFooter(false);
    }

    private void toggleFooter(boolean footerClickable) {
        if (canLoadMore()) {
            mAdapter.setFooterClickable(footerClickable);
            mAdapter.showFooter();
        } else {
            mAdapter.hideFooter();
        }
    }

    /**
     * 加载列表数据操作, 加载更多的时候自动调用
     */
    protected abstract void loadListData();

    /**
     * 获取主题色，用于设置刷新进度条颜色
     *
     * @return
     */
    protected int getAccentColor() {
        return R.color.accentColor;
    }

    /**
     * 创建一个Adapter
     *
     * @return BaseListAdapter
     */
    protected abstract BaseListAdapter createAdapter();

    public void setAdapter(BaseListAdapter adapter) {
        this.mAdapter = adapter;
        resetParameters();
        resetLoadMoreState();
        mCurrentCount = adapter.getListCount();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * @param data List
     */
    public void renderListData(List data) {
        renderListData(data, -1);
    }

    /**
     * @param data        界面展示的item list
     * @param actualCount 后台返回的条数（如果没有把后台返回的对象打散到list里，则actualCount=content.size()，可以传递-1，否则传入后台返回的实际条数）
     */
    public void renderListData(List data, int actualCount) {
        processSuccess(data, actualCount);
    }

    /**
     * 加载列表失败
     */
    public void loadListFailed() {
        finishRefresh();
        resetLoadMoreState();
        toggleFooter(true);
    }

    @Override
    public void onFooterClick() {
        toggleFooter(false);
        startLoadMore();
    }

    public boolean isRefreshing() {
        return mIsRefreshing;
    }
}
