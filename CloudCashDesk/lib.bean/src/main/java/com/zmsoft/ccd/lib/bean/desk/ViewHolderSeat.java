package com.zmsoft.ccd.lib.bean.desk;

/**
 * 从Seat转化来的RecyclerView所需的bean
 *
 * @author DangGui
 * @create 2017/2/14.
 */

public class ViewHolderSeat {
    /**
     * DEFAULT_AREA_ID，针对“无桌位订单”，方便页面做 选中/反选 的处理
     */
    public static final String DEFAULT_AREA_ID = "defaultAreaId";
    private boolean isHeader;
    private String areaId;//当前section的id
    private Seat seat;
    private String areaName;
    /**
     * 当前section是否被选中，即此section下的所有item被选中
     */
    private boolean hasChecked;

    public ViewHolderSeat(Seat seat) {
        this.seat = seat;
    }

    public ViewHolderSeat(boolean isHeader, String areaId, String areaName) {
        this.isHeader = isHeader;
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isHasChecked() {
        return hasChecked;
    }

    public void setHasChecked(boolean hasChecked) {
        this.hasChecked = hasChecked;
    }
}
