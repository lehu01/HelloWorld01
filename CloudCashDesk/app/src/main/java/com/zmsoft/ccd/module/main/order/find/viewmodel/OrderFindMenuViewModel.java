package com.zmsoft.ccd.module.main.order.find.viewmodel;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.desk.TabMenuVO;
import com.zmsoft.ccd.module.main.order.find.recyclerview.OrderFindItem;

import java.util.ArrayList;
import java.util.List;

/**
 * menu和recycler view数据源
 *
 * @author : heniu@2dfire.com
 * @time : 2017/9/21 20:13.
 */

public class OrderFindMenuViewModel {
    private String areaId;
    private String areaName;
    private final List<OrderFindItem> orderFindItemList;

    public OrderFindMenuViewModel(TabMenuVO tabMenuVO) {
        this.areaId = tabMenuVO.getAreaId();
        // 零售单时，APP本地定义名称
        if (null == this.areaId) {
            this.areaName = GlobalVars.context.getString(R.string.retail_order);
        } else {
            this.areaName = tabMenuVO.getAreaName();
        }
        this.orderFindItemList = new ArrayList<>();
    }

    /**
     * 更新用餐时间和是否超时
     */
    void updateEatTime(int timeOutMinutes) {
        if (orderFindItemList == null) {
            return;
        }
        for (OrderFindItem orderFindItem : orderFindItemList) {
            orderFindItem.updateEatTime(timeOutMinutes);
        }
    }

    public String getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public List<OrderFindItem> getOrderFindItemList() {
        return orderFindItemList;
    }
}
