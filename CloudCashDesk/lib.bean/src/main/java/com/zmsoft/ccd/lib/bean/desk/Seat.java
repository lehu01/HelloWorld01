package com.zmsoft.ccd.lib.bean.desk;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * 关注的桌位
 *
 * @author DangGui
 * @create 2017/2/9.
 */

public class Seat extends Base {
    public static class SeatKind {
        //无桌位单
        public static final int DESK_KIND_NODESK = -1;
        //散座
        public static final int DESK_KIND_RANDOM = 1;
        //包厢
        public static final int DESK_KIND_BOX = 2;
        //卡座
        public static final int DESK_KIND_CARD = 3;
    }

    private String id;// ID
    private String areaId;//区域ID
    private String name;// 座位名称
    private String code;//座位代码
    private int adviseNum = -1;//建议人数
    /**
     * 座位类型
     * 1代表散座，2代表包厢，3代表卡座
     */
    private int seatKind = SeatKind.DESK_KIND_NODESK;
    private int sortCode = -1;//顺序
    private String memo;//备注
    private int isReserve = -1;//是否接受预定
    private boolean isBind;//是否被选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAdviseNum() {
        return adviseNum;
    }

    public void setAdviseNum(int adviseNum) {
        this.adviseNum = adviseNum;
    }

    public int getSeatKind() {
        return seatKind;
    }

    public void setSeatKind(int seatKind) {
        this.seatKind = seatKind;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(int isReserve) {
        this.isReserve = isReserve;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        this.isBind = bind;
    }
}
