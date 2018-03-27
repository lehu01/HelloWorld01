package com.zmsoft.ccd.module.main.order.particulars.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.module.main.order.particulars.OrderParticularsActivity;
import com.zmsoft.ccd.lib.base.fragment.BaseFragment;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterGroup;
import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexItem;
import com.zmsoft.ccd.lib.widget.flextboxlayout.CustomFlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/18 10:10.
 */

public class OrderParticularsFilterFragment extends BaseFragment {

    @BindView(R.id.ll_filters)
    LinearLayout mllFilters;

    private CustomFlexboxLayout mCustomFlexboxLayoutSource;
    private CustomFlexboxLayout mCustomFlexboxLayoutCashier;
    private CustomFlexboxLayout mCustomFlexboxLayoutDate;

    // 点击“确定”后的选项
    private OrderParticularsFilterModel mOrderParticularsFilterModel;

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
        if (null == mCustomFlexboxLayoutCashier || null == mCustomFlexboxLayoutDate) {
            return;
        }
        mCustomFlexboxLayoutSource.checkSelected(0);
        mCustomFlexboxLayoutCashier.checkSelected(0);
        mCustomFlexboxLayoutDate.checkSelected(0);
    }

    @OnClick(R.id.text_item_filter_finish)
    public void clickFilterFinish() {
        String codeSource = getSelectedCode(mCustomFlexboxLayoutSource, OrderRightFilterItem.CodeSource.CODE_ALL);
        mOrderParticularsFilterModel.setSourceCode(codeSource);
        String codeCashier = getSelectedCode(mCustomFlexboxLayoutCashier, OrderRightFilterItem.CodeCashier.CODE_ALL);
        mOrderParticularsFilterModel.setCashierCode(codeCashier);
        String codeDate = getSelectedCode(mCustomFlexboxLayoutDate, OrderRightFilterItem.CodeDate.CODE_TODAY);
        mOrderParticularsFilterModel.setDateCode(codeDate);

        if (getActivity() instanceof OrderParticularsActivity) {
            OrderParticularsActivity activity = (OrderParticularsActivity) getActivity();
            activity.refreshByConditions(mOrderParticularsFilterModel);
        }
    }

    //================================================================================
    // init
    //================================================================================
    private void initFilterData() {
        mOrderParticularsFilterModel = new OrderParticularsFilterModel();
    }

    private void initFilterView() {
        mllFilters.removeAllViews();

        List<OrderRightFilterGroup> orderRightFilterGroupList = OrderParticularsFilterSource.getOrderParticularsFilterGroupList();

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

            if (OrderRightFilterItem.Type.TYPE_SOURCE == orderRightFilterGroup.getType()) {
                mCustomFlexboxLayoutSource = flexboxLayout;
            } else if (OrderRightFilterItem.Type.TYPE_CASHIER == orderRightFilterGroup.getType()) {
                mCustomFlexboxLayoutCashier = flexboxLayout;
            } else if (OrderRightFilterItem.Type.TYPE_DATE == orderRightFilterGroup.getType()) {
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
        mCustomFlexboxLayoutSource.checkSelected(mOrderParticularsFilterModel.getSourceCode());
        mCustomFlexboxLayoutCashier.checkSelected(mOrderParticularsFilterModel.getCashierCode());
        mCustomFlexboxLayoutDate.checkSelected(mOrderParticularsFilterModel.getDateCode());
    }
}
