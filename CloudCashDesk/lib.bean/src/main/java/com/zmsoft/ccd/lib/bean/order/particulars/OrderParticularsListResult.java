package com.zmsoft.ccd.lib.bean.order.particulars;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 14:31.
 */

public class OrderParticularsListResult extends Base {
    private String date;
    private long longDate;
    private List<OrderParticulars> billDetails;

    public String getDate() {
        return date;
    }

    public long getLongDate() {
        return longDate;
    }

    public List<OrderParticulars> getBillDetails() {
        return billDetails;
    }
}
