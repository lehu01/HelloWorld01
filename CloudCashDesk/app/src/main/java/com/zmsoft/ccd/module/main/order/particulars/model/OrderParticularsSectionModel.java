package com.zmsoft.ccd.module.main.order.particulars.model;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.module.main.order.particulars.recyclerview.OrderParticularsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * recycler view中分段数据，以日期作为分割
 * @author : heniu@2dfire.com
 * @time : 2017/11/7 09:54.
 */

public class OrderParticularsSectionModel extends Base {

    private String mTimeYYYYMMDD;
    private final List<OrderParticularsItem> mOrderParticularsItemList;

    public OrderParticularsSectionModel(String timeYYYYMMDD) {
        mTimeYYYYMMDD = timeYYYYMMDD;
        mOrderParticularsItemList = new ArrayList<>();
    }

    public OrderParticularsItem get(int i) {
        if (i >= mOrderParticularsItemList.size()) {
            return null;
        }
        return mOrderParticularsItemList.get(i);
    }

    public void add(OrderParticularsItem orderParticularsItem) {
        mOrderParticularsItemList.add(orderParticularsItem);
    }

    public int getSize() {
        return mOrderParticularsItemList.size();
    }

    public String getTimeYYYYMMDD() {
        return mTimeYYYYMMDD;
    }
}
