package com.zmsoft.ccd.lib.bean.desk;

import java.io.Serializable;

/**
 * 用于选座recycleview中item的bean对象
 *
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 13:56
 */
public class SelectSeatVo implements Serializable {

    public static final int ITEM_TYPE_AREA = 0; // 区域
    public static final int ITEM_TYPE_SEAT = 1; // 座位

    private String areaName; // 区域名称
    private String areaId; // 区域id
    private Seat seat; // 座位
    private boolean isCheck; // 是否选中
    private int type; // 类型

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
