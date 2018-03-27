package com.zmsoft.ccd.lib.bean.order;

import java.util.List;

/**
 * Created by huaixi on 2017/11/1.
 */

public class GetBillDetailListResponse {
    List<DateBillDetailList> dateBillDetailList;

    public List<DateBillDetailList> getDateBillDetailList() {
        return dateBillDetailList;
    }

    public void setDateBillDetailList(List<DateBillDetailList> dateBillDetailList) {
        this.dateBillDetailList = dateBillDetailList;
    }
}
