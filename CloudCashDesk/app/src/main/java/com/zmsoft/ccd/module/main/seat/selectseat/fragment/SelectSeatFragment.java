package com.zmsoft.ccd.module.main.seat.selectseat.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.CcdApplication;
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.data.source.desk.dagger.DaggerDeskComponent;
import com.zmsoft.ccd.event.seat.UpdateSelectSeatCheckEvent;
import com.zmsoft.ccd.lib.base.activity.ToolBarActivity;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.SelectSeatVo;
import com.zmsoft.ccd.lib.bean.table.Seat;
import com.zmsoft.ccd.lib.utils.display.DisplayUtil;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;
import com.zmsoft.ccd.module.main.order.create.CreateOrUpdateOrderActivity;
import com.zmsoft.ccd.module.main.seat.selectseat.SelectSeatActivity;
import com.zmsoft.ccd.module.main.seat.selectseat.adapter.SelectSeatAdapter;
import com.zmsoft.ccd.module.main.seat.selectseat.dagger.DaggerSelectSeatPresenterComponent;
import com.zmsoft.ccd.module.main.seat.selectseat.dagger.SelectSeatPresenterModule;
import com.zmsoft.ccd.widget.bottomdailog.BottomDialog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 10:04
 */
public class SelectSeatFragment extends BaseListFragment implements SelectSeatContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private interface SELECTED_TYPE {
        int INIT = -1;          // 第一次下载数据并且显示完成后，被赋值
        int WATCHED = 0;        // 我关注的桌位
        int ALL = 1;            // 所有桌位
    }

    private interface DIALOG_ITEM {
        int DIALOG_ITEM_IS_WATCH = 0;      // dialog第一项，我关注的桌位
        int DIALOG_ITEM_ALL = 1;           // dialog第二项，所有桌位
    }

    private BottomDialog mSeatTypeBottomDialog;
    private String[] mDialogTextArray;

    private final List<SelectSeatVo> mSelectSeatList = new ArrayList<>();
    private SelectSeatAdapter mSelectSeatAdapter;

    @Inject
    SelectSeatPresenter mSelectSeatPresenter;

    private String mSelectSeatCode; // 选座的时候默认选中上一次
    private int mSelectedType;      // 当前选中的type
    private AlphaAnimation mRecyclerViewAnimation;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_seat;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        disableAutoRefresh();
        initFragmentData();
        initDagger();
        initBundleData();
        setTitleTextDrawable(getString(R.string.select_seat_watched_seat));
        setPageCount(Integer.MAX_VALUE);    // 服务端会默认返回所有数据，此处设置本页容量为MAX_VALUE
    }

    @Override
    protected void initListener() {
        initTitleListener();
    }

    @Override
    public void unBindPresenterFromView() {
        mSelectSeatPresenter.unsubscribe();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        setContentView(false);
        showLoadingView();
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startRefresh();
    }

    @Override
    protected void loadListData() {
        getAllSeat();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        mSelectSeatAdapter = new SelectSeatAdapter(getActivity(), null);
        return mSelectSeatAdapter;
    }

    //================================================================================
    // life
    //================================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mRecyclerViewAnimation) {
            mRecyclerViewAnimation.cancel();
        }
    }

    //================================================================================
    // SelectSeatContract.View
    //================================================================================
    @Override
    public void setPresenter(SelectSeatContract.Presenter presenter) {
        mSelectSeatPresenter = (SelectSeatPresenter) presenter;
    }

    @Override
    public void loadStateErrorView(String message) {
        loadListFailed();
        setContentView(false);
        showErrorView(message);
    }

    @Override
    public void loadDataSuccess(List<SeatArea> list, boolean isWatch) {
        setContentView(true);
        updateContentView(list, isWatch);
    }

    @Override
    public void loadDataError(String errorMessage) {
        showToast(errorMessage);
    }

    //================================================================================
    // init view
    //================================================================================
    private void initBundleData() {
        Bundle bundle = getArguments();
        mSelectSeatCode = bundle.getString(SelectSeatActivity.EXTRA_SEAT_CODE);
    }

    private void initFragmentData() {
        mSelectedType = SELECTED_TYPE.INIT;
        mDialogTextArray = getResources().getStringArray(R.array.select_seat_type);
    }

    private void initDagger() {
        DaggerSelectSeatPresenterComponent.builder()
                .deskComponent(DaggerDeskComponent.builder()
                        .appComponent(CcdApplication.getInstance().getAppComponent())
                        .build())
                .selectSeatPresenterModule(new SelectSeatPresenterModule(this))
                .build()
                .inject(this);
    }

    //================================================================================
    // init listener
    //================================================================================
    private void initTitleListener() {
        Activity activity = getActivity();
        if (activity instanceof ToolBarActivity) {
            ToolBarActivity toolBarActivity = (ToolBarActivity) activity;
            toolBarActivity.setTitleOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == mSeatTypeBottomDialog) {
                        mSeatTypeBottomDialog = new BottomDialog(getActivity());
                        // 设置内容
                        mSeatTypeBottomDialog.setItems(mDialogTextArray);
                        // 设置点击事件
                        mSeatTypeBottomDialog.setItemClickListener(new BottomDialog.PopupWindowItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                updateContentView(mSelectSeatPresenter.getDownloadData(), DIALOG_ITEM.DIALOG_ITEM_IS_WATCH == position);
                                mSeatTypeBottomDialog.dismiss();
                            }
                        });
                    }
                    if (!mSeatTypeBottomDialog.isShowing()) {
                        mSeatTypeBottomDialog.showPopupWindow();
                    }
                }
            });
        }
    }

    //================================================================================
    // update view
    //================================================================================
    public void updateContentView(List<SeatArea> list, boolean isWatch) {
        // 与上次选中相同，不处理
        int newSelectedType = isWatch ? SELECTED_TYPE.WATCHED : SELECTED_TYPE.ALL;
        if (newSelectedType == mSelectedType) {
            renderListData(mSelectSeatList);
            return;
        }

        // 设置标题
        String titleText = "";
        if (isWatch) {
            if (DIALOG_ITEM.DIALOG_ITEM_IS_WATCH < mDialogTextArray.length) {
                titleText = mDialogTextArray[DIALOG_ITEM.DIALOG_ITEM_IS_WATCH];
            }
        } else {
            if (DIALOG_ITEM.DIALOG_ITEM_ALL < mDialogTextArray.length) {
                titleText = mDialogTextArray[DIALOG_ITEM.DIALOG_ITEM_ALL];
            }
        }
        setTitleTextDrawable(titleText);
        // 更新model
        if (list != null) {
            mSelectSeatList.clear();
            mSelectSeatList.addAll(DataMapLayer.getSelectAllSeatList(list, mSelectSeatCode, isWatch));
        }
        // 更新view
        if (0 == mSelectSeatList.size()) {
            if (null != mStateView) {
                mStateView.setEmptyResource(R.layout.fragment_select_seat_empty);
            }
            showEmptyView();
        } else {
            showContentView();
            // 第一次加载，动画显示
            if (SELECTED_TYPE.INIT == mSelectedType) {
                mRecyclerViewAnimation = new AlphaAnimation(0.0f, 1.0f);
                mRecyclerViewAnimation.setDuration(500);
                mRecyclerView.startAnimation(mRecyclerViewAnimation);
            }
        }
        mSelectedType = newSelectedType;
        cleanAll();
        renderListData(mSelectSeatList);
    }

    // 设置title文字及右边图标
    private void setTitleTextDrawable(String text) {
        if (null == text || 0 == text.length()) {
            return;
        }
        Activity activity = getActivity();
        if (activity instanceof ToolBarActivity) {
            int SP_TEXT_SIZE = 18;                // 字体大小，sp
            if (!LanguageUtil.isChineseGroup()) {
                SP_TEXT_SIZE = 10;                 // 英文环境下
            }
            final int DP_SPACE_TEXT_DRAWABLE = 5;       // 文字和图片间距，dp
            final int DP_DRAWABLE_WIDTH = 12;           // 图片宽度，dp
            int textViewWidthPixels = DisplayUtil.sp2px(this.getContext(), text.length() * SP_TEXT_SIZE) + DisplayUtil.dip2px(this.getContext(), DP_SPACE_TEXT_DRAWABLE + DP_DRAWABLE_WIDTH);
            Drawable rightDrawable = getResources().getDrawable(R.drawable.icon_down_arror);

            ToolBarActivity toolBarActivity = (ToolBarActivity) activity;
            toolBarActivity.setTitleDrawable(text, textViewWidthPixels, null, null, rightDrawable, null);
        }
    }

    //================================================================================
    // event bus
    //================================================================================
    @Subscribe
    public void updateSelectSeatCheckList(UpdateSelectSeatCheckEvent event) {
        if (event != null) {
            Seat seat = new Seat();
            seat.setSeatName(mSelectSeatList.get(event.getPosition()).getSeat().getName());
            seat.setSeatCode(mSelectSeatList.get(event.getPosition()).getSeat().getCode());
            setResult(seat);
        }
    }

    @Override
    protected void registerEventBus() {
        super.registerEventBus();
        EventBusHelper.register(this);
    }

    @Override
    protected void unRegisterEventBus() {
        super.unRegisterEventBus();
        EventBusHelper.unregister(this);
    }

    //================================================================================
    // set result
    //================================================================================
    public void setResult(Seat seat) {
        Intent intent = new Intent();
        intent.putExtra(SelectSeatActivity.EXTRA_SEAT, seat);
        getActivity().setResult(CreateOrUpdateOrderActivity.RESULT_OK, intent);
        getActivity().finish();
    }

    private void getAllSeat() {
        mSelectSeatPresenter.downloadSeatData();
    }

    private void setContentView(boolean isShow) {
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRefreshLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
