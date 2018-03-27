package com.zmsoft.ccd.module.main.order.find.viewmodel;

import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.lib.bean.order.Order;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/21 20:42.
 */

public class OrderFindViewModel {

    public static final String SELECTED_AREA_ID_INIT = "SELECTED_AREA_ID_INIT";

    /**
     * 本次加载后，可能还有更多数据
     * data.size == pageSize
     */
    private boolean mHaveMoreData;

    /**
     * 默认：SELECTED_AREA_ID_INIT
     * 零售单：null
     * 桌位
     */
    private String mSelectedAreaId;
    private String mSelectedAreaName;
    private final List<OrderFindMenuViewModel> mOrderFindMenuViewModelList; // 从网络下载的页面所有数据

    public OrderFindViewModel() {
        mHaveMoreData = false;
        mSelectedAreaId = SELECTED_AREA_ID_INIT;
        mSelectedAreaName = "";
        mOrderFindMenuViewModelList = new ArrayList<>();
    }

    public List<OrderFindMenuViewModel> getOrderFindMenuViewModelList() {
        return mOrderFindMenuViewModelList;
    }

    public void setAreaInfo(String areaName) {
        mSelectedAreaName = areaName;
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            if (areaName.equals(orderFindMenuViewModel.getAreaName())) {
                mSelectedAreaId = orderFindMenuViewModel.getAreaId();
            }
        }
    }

    public String getSelectedAreaId() {
        return mSelectedAreaId;
    }

    public String getSelectedAreaName() {
        return mSelectedAreaName;
    }

    public boolean isHaveMoreData() {
        return mHaveMoreData;
    }

    //================================================================================
    // fill data
    //================================================================================
    // 1.获取关注的区域
    public void initWatchArea(List<TabMenuVO> tabMenuVOList) {
        mOrderFindMenuViewModelList.clear();
        for (TabMenuVO tabMenuVO : tabMenuVOList) {
            OrderFindMenuViewModel orderFindMenuViewModel = new OrderFindMenuViewModel(tabMenuVO);
            mOrderFindMenuViewModelList.add(orderFindMenuViewModel);
        }
        updateSelectedAreaId();
    }

    // 2.1.按区域获取关注桌位的订单
    public void fillSeatOrderData(String selectedAreaId, List<com.zmsoft.ccd.lib.bean.table.Seat> orderSeatList, boolean isFirstPage, int pageSize) {
        // 获取指定区域的recycler view数据
        List<OrderFindItem> originOrderFindItemList = null;
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            String areaId = orderFindMenuViewModel.getAreaId();
            if (null == areaId) {
                continue;
            }
            if (areaId.equals(selectedAreaId)) {
                originOrderFindItemList = orderFindMenuViewModel.getOrderFindItemList();
                break;
            }
        }
        // 本次返回null数据
        if (null == orderSeatList) {
            mHaveMoreData = false;
            if (null != originOrderFindItemList) {
                if (isFirstPage) {
                    originOrderFindItemList.clear();
                }
            }
            return;
        }
        // 正常解析
        mHaveMoreData = (orderSeatList.size() == pageSize);
        List<OrderFindItem> gridItemList = new ArrayList<>();
        for (com.zmsoft.ccd.lib.bean.table.Seat seat : orderSeatList) {
            gridItemList.add(new OrderFindItem(seat));
        }
        if (null != originOrderFindItemList) {
            if (isFirstPage) {
                originOrderFindItemList.clear();
            }
            originOrderFindItemList.addAll(gridItemList);
        }
    }

    // 2.2.获取零售单信息
    public void fillRetailOrderData(List<Order> orderList, boolean isFirstPage, int pageSize) {
        // 获取零售单的recycler view数据
        List<OrderFindItem> originOrderFindItemList = null;
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            String areaId = orderFindMenuViewModel.getAreaId();
            if (null == areaId) {
                originOrderFindItemList = orderFindMenuViewModel.getOrderFindItemList();
                break;
            }
        }
        // 本次返回null数据
        if (null == orderList) {
            mHaveMoreData = false;
            if (null != originOrderFindItemList) {
                if (isFirstPage) {
                    originOrderFindItemList.clear();
                }
            }
            return;
        }
        // 正常解析
        mHaveMoreData = (orderList.size() == pageSize);
        List<OrderFindItem> retailGridItemList = new ArrayList<>();
        for (Order order : orderList) {
            String areaId = order.getAreaId();
            if (areaId == null) {
                retailGridItemList.add(new OrderFindItem(order));
            }
        }
        if (null != originOrderFindItemList) {
            if (isFirstPage) {
                originOrderFindItemList.clear();
            }
            originOrderFindItemList.addAll(retailGridItemList);
        }
    }

    //================================================================================
    // update data
    //================================================================================
    // 更新用餐时间和是否超时
    public void updateEatTime(int timeOutMinutes) {
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            if (orderFindMenuViewModel == null) {
                continue;
            }
            orderFindMenuViewModel.updateEatTime(timeOutMinutes);
        }
    }


    //================================================================================
    // property
    //================================================================================
    public boolean isContentViewEmpty() {
        return 0 == mOrderFindMenuViewModelList.size();
    }

    private void updateSelectedAreaId() {
        if (0 == mOrderFindMenuViewModelList.size()) {
            return;
        }
        // 第一次load数据，默认选中第一项menu
        if (SELECTED_AREA_ID_INIT.equals(mSelectedAreaId)) {
            OrderFindMenuViewModel orderFindMenuViewModel = mOrderFindMenuViewModelList.get(0);
            mSelectedAreaId = orderFindMenuViewModel.getAreaId();
            mSelectedAreaName = orderFindMenuViewModel.getAreaName();
            return;
        }
        // 上次选中menu是否仍旧存在
        boolean isExistSelectedAreaId = false;
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            String areaId = orderFindMenuViewModel.getAreaId();
            if (null == mSelectedAreaId) {
                if (null == areaId) {
                    isExistSelectedAreaId = true;
                }
            } else {
                if (mSelectedAreaId.equals(areaId)) {
                    isExistSelectedAreaId = true;
                }
            }
        }
        // 上次选中menu不存在的话，默认选择第一项menu
        if (!isExistSelectedAreaId) {
            OrderFindMenuViewModel orderFindMenuViewModel = mOrderFindMenuViewModelList.get(0);
            mSelectedAreaId = orderFindMenuViewModel.getAreaId();
            mSelectedAreaName = orderFindMenuViewModel.getAreaName();
        }
    }

    public List<OrderFindItem> getSelectedDeskGridItemList() {
        if (0 == mOrderFindMenuViewModelList.size()) {
            return new ArrayList<>();
        }
        // 第一次load数据，默认选中第一项menu
        if (SELECTED_AREA_ID_INIT.equals(mSelectedAreaId)) {
            return new ArrayList<>();
        }
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            String areaId = orderFindMenuViewModel.getAreaId();
            if (null == mSelectedAreaId) {
                if (null == areaId) {
                    return orderFindMenuViewModel.getOrderFindItemList();
                }
            } else {
                if (mSelectedAreaId.equals(areaId)) {
                    return orderFindMenuViewModel.getOrderFindItemList();
                }
            }
        }
        return new ArrayList<>();
    }

    // 当前是否关注零售单
    public boolean isContainRetailOrder() {
        for (OrderFindMenuViewModel orderFindMenuViewModel : mOrderFindMenuViewModelList) {
            if (null == orderFindMenuViewModel.getAreaId()) {
                return true;
            }
        }
        return false;
    }
}
