package com.zmsoft.ccd.lib.bean.order.particulars;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 14:33.
 */

public class DateOrderParticularsListResult extends Base{
    private List<OrderParticularsListResult> dateBillDetailList;

    public List<OrderParticularsListResult> getDateBillDetailList() {
        return dateBillDetailList;
    }
}
