package com.zmsoft.ccd.event.seat;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/7 16:59
 */
public class UpdateSelectSeatCheckEvent {

    private int oldPosition;
    private int position;
    private boolean isCheck;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
