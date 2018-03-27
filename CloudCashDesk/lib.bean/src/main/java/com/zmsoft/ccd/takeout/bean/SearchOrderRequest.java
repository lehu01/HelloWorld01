package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/29.
 */

public class SearchOrderRequest extends BaseRequest {


    private String condition;
    private int pageSize;
    private String cursorMark = "*";


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCursorMark() {
        return cursorMark;
    }

    public void setCursorMark(String cursorMark) {
        this.cursorMark = (cursorMark == null ? TakeoutConstants.CURSOR_MARK : cursorMark);
    }
}
