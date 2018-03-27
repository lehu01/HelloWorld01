package com.zmsoft.ccd.module.takeout.order.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.utils.ToastUtils;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexboxLayout;
import com.zmsoft.ccd.lib.widget.time.DatePickerDialog;
import com.zmsoft.ccd.module.takeout.DaggerCommentManager;
import com.zmsoft.ccd.module.takeout.R;
import com.zmsoft.ccd.module.takeout.R2;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutFilterFragmentContract;
import com.zmsoft.ccd.module.takeout.order.presenter.TakeoutFilterFragmentPresenter;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.DaggerTakeoutFilterFragmentComponent;
import com.zmsoft.ccd.module.takeout.order.presenter.dagger.TakeoutFilterFragmentPresenterModule;
import com.zmsoft.ccd.takeout.bean.Config;
import com.zmsoft.ccd.takeout.bean.FilterConditionResponse;
import com.zmsoft.ccd.takeout.bean.OrderListRequest;
import com.zmsoft.ccd.takeout.bean.OrderStatusRequest;
import com.zmsoft.ccd.takeout.bean.TakeoutConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/17.
 */

public class TakeoutListFilterFragment extends BaseFragment implements TakeoutFilterFragmentContract.View {

    @BindView(R2.id.ll_filters)
    LinearLayout mllFilters;
    @BindView(R2.id.rl_menu)
    View mRlMenu;
    @BindView(R2.id.text_click_retry)
    View mTextClickRetry;
    @BindView(R2.id.text_item_filter_reset)
    View mTextItemFilterReset;
    @BindView(R2.id.text_item_filter_finish)
    View mTextItemFilterFinish;

    @Inject
    TakeoutFilterFragmentPresenter mPresenter;

    private long mStarTime;
    private long mEndTime;
    private TextView mTextStartTime;
    private TextView mTextEndTime;

    //private List<CustomFlexboxLayout> mCustomFlexboxLayouts;

    //value是用户确认后的选中条件
    private Map<CustomFlexboxLayout, CustomFlexItem> mCustomFlexboxLayouts;

    @Override
    protected int getLayoutId() {
        return R.layout.module_takeout_fragment_takeout_list_filter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.content).getBackground().mutate().setAlpha((int) (255 * 0.85));

        DaggerTakeoutFilterFragmentComponent.builder()
                .takeoutFilterFragmentPresenterModule(new TakeoutFilterFragmentPresenterModule(this))
                .takeoutSourceComponent(DaggerCommentManager.get().getTakeoutSourceComponent())
                .build()
                .inject(this);


        RxView.clicks(mTextClickRetry).throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showLoading(false);
                        mPresenter.getFilterConfigs(OrderStatusRequest.createForCatering(UserHelper.getEntityId()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        mTextItemFilterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomFlexboxLayouts == null) {
                    return;
                }
                resetSelectTime();
                for (CustomFlexboxLayout flexboxLayout : mCustomFlexboxLayouts.keySet()) {
                    flexboxLayout.checkSelected(0);
                }
            }
        });

        mTextItemFilterFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateConditions();

                if (getActivity() instanceof TakeoutListActivity) {
                    TakeoutListActivity activity = (TakeoutListActivity) getActivity();
                    OrderListRequest request = OrderListRequest.create();
                    for (CustomFlexboxLayout flexboxLayout : mCustomFlexboxLayouts.keySet()) {
                        List<CustomFlexItem> items = flexboxLayout.getCheckedItemList();
                        if (items == null || items.isEmpty()) {
                            continue;
                        }
                        CustomFlexItem item = items.get(0);
                        Object data = item.getData();
                        if (!(data instanceof Config)) {
                            continue;
                        }
                        Config config = (Config) data;
                        if (TakeoutConstants.DATE_TYPE.equals(flexboxLayout.getTag())) {
                            if (config.getValue() == TakeoutConstants.DateType.CUSTOM) {
                                if (mStarTime == 0) {
                                    ToastUtils.showShortToast(getContext(), R.string.module_takeout_please_select_start_time);
                                    return;
                                }
                                if (mEndTime == 0) {
                                    ToastUtils.showShortToast(getContext(), R.string.module_takeout_please_select_end_time);
                                    return;
                                }
                                config.setBegin(mStarTime);
                                config.setEnd(mEndTime);
                            }
                            request.setBeginDate(config.getBegin());
                            request.setEndDate(config.getEnd());
                        } else if (TakeoutConstants.ORDER_SOURCE.equals(flexboxLayout.getTag())) {
                            request.setOrderFrom((short) config.getValue());
                        } else if (TakeoutConstants.ORDER_TYPE.equals(flexboxLayout.getTag())) {
                            request.setReserveStatus((short) config.getValue());
                        }
                    }
                    activity.refreshByConditions(request);
                }
            }
        });

        mPresenter.getFilterConfigs(OrderStatusRequest.createForCatering(UserHelper.getEntityId()));


    }

    private void renderView(List<Config> list) {

        mllFilters.removeAllViews();

        mRlMenu.setVisibility(View.VISIBLE);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int j = 0; j < list.size(); j++) {
            Config filter = list.get(j);
            View viewConditions = inflater.inflate(R.layout.module_takeout_item_filter_group, null);
            viewConditions.setTag(filter);
            TextView textLabel = (TextView) viewConditions.findViewById(R.id.text_order_label);
            textLabel.setText(filter.getDesc());

            View viewDivider = viewConditions.findViewById(R.id.view_divider_filters);
            viewDivider.setVisibility(j == list.size() - 1 ? View.GONE : View.VISIBLE);

            CustomFlexboxLayout flexboxLayout = (CustomFlexboxLayout) viewConditions.findViewById(R.id.layout_order_filters);
            if (mCustomFlexboxLayouts == null) {
                mCustomFlexboxLayouts = new LinkedHashMap<>();
            }
            flexboxLayout.setTag(filter.getKey());

            flexboxLayout.setItemLayoutRes(R.layout.module_takeout_item_filter);
            List<Config> children = filter.getChildConfigVos();
            if (children != null && !children.isEmpty()) {
                List<CustomFlexItem> items = new ArrayList<>(children.size());
                for (int i = 0; i < children.size(); i++) {
                    Config child = children.get(i);
                    items.add(new CustomFlexItem(child.getValue() + "", child.getDesc(), child.getKey(), child, i == 0));
                }

                mCustomFlexboxLayouts.put(flexboxLayout, items.get(0));

                flexboxLayout.initSource(items, true, false);

                if (TakeoutConstants.DATE_TYPE.equals(filter.getKey())) {
                    final View viewSelectTime = viewConditions.findViewById(R.id.rl_select_time);
                    mTextStartTime = (TextView) viewSelectTime.findViewById(R.id.text_start_time);
                    mTextEndTime = (TextView) viewSelectTime.findViewById(R.id.text_end_time);
                    mTextStartTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar c = Calendar.getInstance();
                            int year = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH);
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog startTimeDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    StringBuilder build = new StringBuilder();
                                    build.append(year).append("-").append(month + 1 >= 10 ? month + 1 : "0" + (month + 1)).append('-')
                                            .append(dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                                    mTextStartTime.setText(build);
                                    mStarTime = getMillSeconds(year, month, dayOfMonth, 0);
                                }
                            }, year, month, day);
                            if (mEndTime != 0) {
                                startTimeDialog.getDatePicker().setMaxDate(mEndTime);
                            } else {
                                startTimeDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            }
                            startTimeDialog.show();
                        }
                    });

                    mTextEndTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar c = Calendar.getInstance();
                            int year = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH);
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog endTimeDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    StringBuilder build = new StringBuilder();
                                    build.append(year).append("-").append(month + 1 >= 10 ? month + 1 : "0" + (month + 1))
                                            .append('-').append(dayOfMonth >= 10 ? dayOfMonth : "0" + dayOfMonth);
                                    mTextEndTime.setText(build);
                                    mEndTime = getMillSeconds(year, month, dayOfMonth, 1);
                                }
                            }, year, month, day);
                            if (mStarTime != 0) {
                                endTimeDialog.getDatePicker().setMinDate(mStarTime);
                            }
                            endTimeDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                            endTimeDialog.show();
                        }
                    });


                    flexboxLayout.setOnItemChangeListener(new CustomFlexboxLayout.OnItemChangeListener() {
                        @Override
                        public void onItemChanged(List<CustomFlexItem> checkedFlexItemList) {
                            boolean custom = false;
                            if (checkedFlexItemList != null) {
                                for (CustomFlexItem item : checkedFlexItemList) {
                                    if (String.valueOf(TakeoutConstants.DateType.CUSTOM).equals(item.getId())) {//自定义
                                        custom = true;
                                    }
                                }
                            }
                            viewSelectTime.setVisibility(custom ? View.VISIBLE : View.GONE);
                        }
                    });
                }
            }

            mllFilters.addView(viewConditions);
        }
    }

    /**
     * @param year
     * @param month
     * @param dayOfMonth
     * @param timeType   0：表示一天的最开始的时间；1：一天的最后结束时间；其他的值：年月日是传进来的，时分秒为当前的时分秒
     * @return
     */
    long getMillSeconds(int year, int month, int dayOfMonth, int timeType) {
        final Calendar c = Calendar.getInstance();
        if (timeType == 0) {
            c.set(year, month, dayOfMonth, 0, 0, 0);
        } else if (timeType == 1) {
            c.set(year, month, dayOfMonth, 23, 59, 59);
        } else {
            c.set(year, month, dayOfMonth);
        }
        return c.getTimeInMillis();
    }

    /**
     * 恢复上次选中的条件
     */
    public void restoreConditions() {
        if (mCustomFlexboxLayouts != null) {
            for (Map.Entry<CustomFlexboxLayout, CustomFlexItem> entry : mCustomFlexboxLayouts.entrySet()) {
                CustomFlexboxLayout layout = entry.getKey();
                CustomFlexItem item = entry.getValue();
                List<CustomFlexItem> checkList = layout.getCheckedItemList();
                if (checkList == null || checkList.isEmpty()) {
                    continue;
                }
                if (checkList.get(0) != item) {
                    layout.checkSelected(item);
                }
            }
        }
    }


    /**
     * 更新用户选中的条件
     */
    public void updateConditions() {
        if (mCustomFlexboxLayouts != null) {
            for (Map.Entry<CustomFlexboxLayout, CustomFlexItem> entry : mCustomFlexboxLayouts.entrySet()) {
                CustomFlexboxLayout layout = entry.getKey();
                CustomFlexItem item = entry.getValue();
                List<CustomFlexItem> checkList = layout.getCheckedItemList();
                if (checkList == null || checkList.isEmpty()) {
                    mCustomFlexboxLayouts.put(layout, null);
                } else {
                    if (checkList.get(0) != item) {
                        mCustomFlexboxLayouts.put(layout, checkList.get(0));
                    }
                }
            }
        }
    }

    public void resetSelectTime() {
        mStarTime = 0;
        mEndTime = 0;
        if (mTextStartTime != null) {
            mTextStartTime.setText(R.string.module_takeout_please_select_time);
        }
        if (mTextEndTime != null) {
            mTextEndTime.setText(R.string.module_takeout_please_select_time);
        }
    }


    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {
        if (this.mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void getFilterConfigsSuccess(FilterConditionResponse response) {
        hideLoading();
        mTextClickRetry.setVisibility(View.GONE);
        if (response == null || response.getConfigVos() == null) {
            return;
        }
        renderView(response.getConfigVos());
    }

    @Override
    public void getFilterConfigsFailed(String errorCode, String errorMsg) {
        hideLoading();
        ToastUtils.showShortToast(getContext(), errorMsg);
        mTextClickRetry.setVisibility(View.VISIBLE);
        mRlMenu.setVisibility(View.GONE);
    }
}
