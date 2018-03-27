package com.zmsoft.ccd.event.remark;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/6 14:48
 */
public class UpdateRemarkCheckEvent {

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
