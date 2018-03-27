package com.zmsoft.ccd.event.message;

/**
 * @author DangGui
 * @create 2017/1/12.
 */

public class NotifyDataChangeEvent {
    private int mPosition;
    private boolean mIsAgreed;
    private String messageId;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public boolean isAgreed() {
        return mIsAgreed;
    }

    public void setAgreed(boolean agreed) {
        mIsAgreed = agreed;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
