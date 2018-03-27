package com.zmsoft.ccd.takeout.bean;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class OrderListRequest extends BaseRequest {


    private short reserveStatus = TakeoutConstants.ReserveStatus.ALL;

    private short orderFrom = TakeoutConstants.OrderFrom.ALL;

    private short status;

    private long beginDate = -1;

    private long endDate = -1;

    private int pageSize = TakeoutConstants.PAGE_COUNT;

    private int pageIndex = 1;

    private OrderListRequest() {

    }

    public static OrderListRequest create() {
        return new OrderListRequest();
    }


    public static OrderListRequest create(short reserveStatus, short orderFrom, short status,
                                          String entityId, long beginDate, long endDate, int pageSize, int pageIndex) {
        OrderListRequest request = new OrderListRequest();
        request.reserveStatus = reserveStatus;
        request.orderFrom = orderFrom;
        request.status = status;
        request.beginDate = beginDate;
        request.endDate = endDate;
        request.pageSize = pageSize;
        request.pageIndex = pageIndex;
        request.setEntityId(entityId);
        return request;
    }

    public short getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(short reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    public short getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(short orderFrom) {
        this.orderFrom = orderFrom;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
