package com.zmsoft.ccd.lib.bean.desk;

import java.io.Serializable;
import java.util.List;

/**
 * @author DangGui
 * @create 2017/2/14.
 */

public class SeatArea implements Serializable {
    private String areaName;
    private String areaId;
    private List<Seat> seatList;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
