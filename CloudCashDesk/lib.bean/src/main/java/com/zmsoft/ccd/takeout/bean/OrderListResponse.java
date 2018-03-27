package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/23.
 */

public class OrderListResponse {

    private List<Takeout> takeoutOrderVos;
    private int waitNum;
    private String cursorMark;


    public List<Takeout> getTakeoutOrderVos() {
        return takeoutOrderVos;
    }

    public void setTakeoutOrderVos(List<Takeout> takeoutOrderVos) {
        this.takeoutOrderVos = takeoutOrderVos;
    }

    public int getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(int waitNum) {
        this.waitNum = waitNum;
    }

    public String getCursorMark() {
        return cursorMark;
    }

    public void setCursorMark(String cursorMark) {
        this.cursorMark = cursorMark;
    }
}
