package com.zmsoft.ccd.module.main.order.summary.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterGroup;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexboxLayout;
import com.zmsoft.ccd.module.main.order.summary.OrderSummaryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class OrderSummaryFilterFragment extends BaseFragment {

    @BindView(R.id.ll_filters)
    LinearLayout mllFilters;

    private CustomFlexboxLayout mCustomFlexboxLayoutDate;

    // 点击“确定”后的选项
    private String mSelectedDateCode;

    //================================================================================
    // BaseFragment
    //================================================================================
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_right_filter;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initFilterData();
        initFilterView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void unBindPresenterFromView() {

    }

    //================================================================================
    // butter knife
    //================================================================================
    @OnClick(R.id.text_item_filter_reset)
    public void clickFilterReset() {
        if (null == mCustomFlexboxLayoutDate) {
            return;
        }
        mCustomFlexboxLayoutDate.checkSelected(0);
    }

    @OnClick(R.id.text_item_filter_finish)
    public void clickFilterFinish() {
        String codeDate = getSelectedCode(mCustomFlexboxLayoutDate, OrderRightFilterItem.CodeDate.CODE_TODAY);
        mSelectedDateCode = codeDate;

        if (getActivity() instanceof OrderSummaryActivity) {
            OrderSummaryActivity activity = (OrderSummaryActivity) getActivity();
            activity.refreshByConditions(codeDate);
        }
    }

    //================================================================================
    // init
    //================================================================================
    private void initFilterData() {
        mSelectedDateCode = OrderRightFilterItem.CodeDate.CODE_TODAY;
    }

    private void initFilterView() {
        mllFilters.removeAllViews();

        List<OrderRightFilterGroup> orderRightFilterGroupList = OrderSummaryFilterSource.getOrderSummaryFilterGroupList();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (OrderRightFilterGroup orderRightFilterGroup : orderRightFilterGroupList) {
            View viewConditions = inflater.inflate(R.layout.item_order_right_filter_group, null);
            // 组名
            TextView textLabel = (TextView) viewConditions.findViewById(R.id.text_order_label);
            textLabel.setText(orderRightFilterGroup.getGroupName());
            // 选项
            List<CustomFlexItem> customFlexItemList = new ArrayList<>();
            for (OrderRightFilterItem orderSummaryFilterItem : orderRightFilterGroup.getItemList()) {
                customFlexItemList.add(new CustomFlexItem(orderSummaryFilterItem.getCode(), orderSummaryFilterItem.getName(), orderSummaryFilterItem.isCheck()));
            }
            CustomFlexboxLayout flexboxLayout = (CustomFlexboxLayout) viewConditions.findViewById(R.id.layout_order_filters);
            flexboxLayout.setItemLayoutRes(R.layout.item_order_right_filter);
            // 不额外设置，与外卖中筛选UI风格统一
            // flexboxLayout.setResIdCheckedBackground(R.drawable.shape_right_filter_flexitem_checked_layers);
            flexboxLayout.initSource(customFlexItemList, true, false);

            if (OrderRightFilterItem.Type.TYPE_DATE == orderRightFilterGroup.getType()) {
                mCustomFlexboxLayoutDate = flexboxLayout;
                // 隐藏最后一组的下分割线
                View viewDividerFilter = viewConditions.findViewById(R.id.view_divider_filters);
                viewDividerFilter.setVisibility(View.GONE);
            }
            mllFilters.addView(viewConditions);
        }
    }

    //================================================================================
    // right filter
    //================================================================================
    private static String getSelectedCode(CustomFlexboxLayout customFlexboxLayout, String defaultCode) {
        String selectedCode = defaultCode;
        List<CustomFlexItem> checkItemList = customFlexboxLayout.getCheckedItemList();
        if (null != checkItemList && 0 != checkItemList.size()) {
            selectedCode = checkItemList.get(0).getId();
        }
        return selectedCode;
    }

    // 恢复上次点击“确定”时的选择项
    public void recoverLastSelectedView() {
        mCustomFlexboxLayoutDate.checkSelected(mSelectedDateCode);
    }
}
