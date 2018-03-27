package com.zmsoft.ccd.module.main.order.particulars.filter;

import com.zmsoft.ccd.lib.bean.order.rightfilter.OrderRightFilterItem;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/10/24 17:49.
 */

public class OrderParticularsFilterModel {
    private String sourceCode;
    private String cashierCode;
    private String dateCode;

    public OrderParticularsFilterModel() {
        super();
        reset();
    }

    public void reset() {
        this.sourceCode = OrderRightFilterItem.CodeSource.CODE_ALL;
        this.cashierCode = OrderRightFilterItem.CodeCashier.CODE_ALL;
        this.dateCode = OrderRightFilterItem.CodeDate.CODE_TODAY;
    }

    public void update(OrderParticularsFilterModel orderParticularsFilterModel) {
        this.sourceCode = orderParticularsFilterModel.sourceCode;
        this.cashierCode = orderParticularsFilterModel.cashierCode;
        this.dateCode = orderParticularsFilterModel.dateCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getCashierCode() {
        return cashierCode;
    }

    public void setCashierCode(String cashierCode) {
        this.cashierCode = cashierCode;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }
}
