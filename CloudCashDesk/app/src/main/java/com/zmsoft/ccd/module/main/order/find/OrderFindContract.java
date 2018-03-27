package com.zmsoft.ccd.module.main.order.find;

import com.zmsoft.ccd.lib.base.BasePresenter;
import com.zmsoft.ccd.lib.base.BaseView;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;
import com.zmsoft.ccd.module.main.order.find.viewmodel.OrderFindViewModel;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/9/20 15:41.
 */

public class OrderFindContract {

    public interface Presenter extends BasePresenter {
        void loadWatchedArea();                                                                                 // 加载关注的区域
        void onTabSelected(String areaName);                                                                    // 点击其他tab
        List<OrderFindItem> getSelectedData();
        void loadSelectedAreaData(int pageIndex);                                                               // 加载当前指定区域的详细信息
        void updateEatTime(int timeOutMinutes);
        void handleShowRetailArea();                                                                            // 触发切换到零售单区域
    }

    public interface View extends BaseView<OrderFindContract.Presenter> {
        void performShowRetailAreaChecked();                                                                        // 切换到零售单区域
        void loadWatchAreaSuccess(OrderFindViewModel orderFindViewModel);                                       // 加载关注区域成功
        void loadOrderFindDataSuccess(OrderFindViewModel orderFindViewModel);                                   // 加载当前选择区域数据成功
        void loadOrderFindDataError(String errorMessage, boolean showNetErrorView);
    }
}
