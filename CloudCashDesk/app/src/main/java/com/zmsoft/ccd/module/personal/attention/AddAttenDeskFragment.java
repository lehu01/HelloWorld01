package com.zmsoft.ccd.module.personal.attention;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.data.DataMapLayer;
import com.zmsoft.ccd.event.BaseEvents;
import com.zmsoft.ccd.helper.MsgSettingType;
import com.zmsoft.ccd.lib.base.adapter.BaseListAdapter;
import com.zmsoft.ccd.lib.base.fragment.BaseListFragment;
import com.zmsoft.ccd.lib.base.helper.EventBusHelper;
import com.zmsoft.ccd.lib.bean.desk.Seat;
import com.zmsoft.ccd.lib.bean.desk.SeatArea;
import com.zmsoft.ccd.lib.bean.desk.ViewHolderSeat;
import com.zmsoft.ccd.module.personal.attention.adapter.AddAttenDeskSectionAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author DangGui
 * @create 2016/12/15.
 */

public class AddAttenDeskFragment extends BaseListFragment implements AddAttenDeskContract.View {
    @BindView(R.id.fab_checkall)
    TextView mFabCheckall;
    @BindView(R.id.fab_checknone)
    TextView mFabChecknone;
    @BindView(R.id.layout_checkall)
    FrameLayout mLayoutCheckall;
    @BindView(R.id.layout_checknone)
    FrameLayout mLayoutChecknone;

    private AddAttenDeskPresenter mPresenter;
    /**
     * 是否新增/删除 了已关注的桌位
     */
    private boolean isModifyed;
    /**
     * 外卖单桌位ID，如果不为空，全选/全不选的时候都要传给服务端
     */
    private String mTakeoutDeskId;

    public static AddAttenDeskFragment newInstance() {
        return new AddAttenDeskFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        disableAutoRefresh();
        disableRefresh();
        showLoadingView();
    }

    @Override
    public void notifyDataChange() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadDataSuccess() {
        mLayoutCheckall.setVisibility(View.VISIBLE);
        mLayoutChecknone.setVisibility(View.VISIBLE);
    }

    @Override
    public void bindSeatsError(String errorMessage) {
        toastMsg(errorMessage);
    }

    @Override
    public void loadDataError(String errorMessage, boolean showNetErrorView) {
        if (getAdapter().getList().isEmpty()) {
            showErrorView(errorMessage);
        }
        loadListFailed();
        toastMsg(errorMessage);
    }

    @Override
    public void showAttentionDeskList(List<SeatArea> data) {
        getAdapter().removeAll();
        List<ViewHolderSeat> deskSectionList = DataMapLayer.getAllSeatsList(data, false);
        filterTakeoutDesk(data);
        renderListData(deskSectionList);
        showContentView();
        enableRefresh();
    }

    @Override
    public void finishView() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void setPresenter(AddAttenDeskContract.Presenter presenter) {
        this.mPresenter = (AddAttenDeskPresenter) presenter;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_atten_desk_recyclerview;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void loadListData() {
        startLoad();
    }

    @Override
    protected BaseListAdapter createAdapter() {
        return new AddAttenDeskSectionAdapter(getActivity(), null);
    }

    @Override
    public boolean onBackPressed() {
        if (!isModifyed) {
            return false;
        }
        String jsonStr = mPresenter.getAllCheckedSeats((ArrayList<ViewHolderSeat>) getAdapter().getList(), mTakeoutDeskId);
        if (!TextUtils.isEmpty(jsonStr)) {
            mPresenter.bindCheckedSeats(jsonStr);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void clickRetryView() {
        super.clickRetryView();
        startLoad();
    }

    @Override
    protected boolean canLoadMore() {
        return false;
    }

    private void initView() {

    }

    private void startLoad() {
        mPresenter.loadAttentionDeskList();
    }

    @OnClick({R.id.fab_checkall, R.id.fab_checknone})
    public void onClick(View view) {
        BaseEvents.CommonEvent event = BaseEvents.CommonEvent.CHECK_ALL_ITEMS;
        switch (view.getId()) {
            case R.id.fab_checkall:
                event.setObject(true);
                break;
            case R.id.fab_checknone:
                event.setObject(false);
                break;
        }
        EventBusHelper.post(event);
    }

    @Subscribe
    public void checkedSectionItems(BaseEvents.CommonEvent event) {
        if (event == BaseEvents.CommonEvent.CHECKED_SECTION_ITEMS_EVENT) {
            isModifyed = true;
            if (null != event.getObject()) {
                ViewHolderSeat deskSection = (ViewHolderSeat) event.getObject();
                mPresenter.checkedSectionItems((ArrayList<ViewHolderSeat>) getAdapter().getList(), deskSection.getAreaId(), deskSection.isHasChecked());
            }
        } else if (event == BaseEvents.CommonEvent.CHECK_IF_SECTION_ITEM_CHECKED) {
            isModifyed = true;
            if (null != event.getObject()) {
                Seat desk = (Seat) event.getObject();
                mPresenter.checkIfSectionItemChecked((ArrayList<ViewHolderSeat>) getAdapter().getList(), desk.getAreaId(), desk.isBind());
            }
        } else if (event == BaseEvents.CommonEvent.CHECK_ALL_ITEMS) {
            isModifyed = true;
            if (null != event.getObject()) {
                mPresenter.checkallItems((ArrayList<ViewHolderSeat>) getAdapter().getList(), (Boolean) event.getObject());
            }
        }
    }

    /**
     * 过滤掉外卖单
     *
     * @param data
     */
    private void filterTakeoutDesk(List<SeatArea> data) {
        boolean hasTakeout = false;
        for (SeatArea seatArea : data) {
            List<Seat> seatList = seatArea.getSeatList();
            if (null != seatList && !seatList.isEmpty()) {
                for (Seat seat : seatList) {
                    if (null != seat && !TextUtils.isEmpty(seat.getCode()) && seat.getCode().equals(MsgSettingType.TAKE_OUT_SEAT_CODE)
                            && seat.isBind() && !TextUtils.isEmpty(seat.getId())) {
                        mTakeoutDeskId = seat.getId();
                        hasTakeout = true;
                        break;
                    }
                }
            }
            if (hasTakeout) {
                break;
            }
        }
    }

    @Override
    public void unBindPresenterFromView() {
        mPresenter.unsubscribe();
    }
}
