package com.zmsoft.ccd.lib.widget.softkeyboard;

/**
 * @author DangGui
 * @create 2017/7/31.
 */

public class KeyboardModel {
    /**
     * 普通按钮
     */
    public static final int ITEM_TYPE_NORMAL = 1;

    /**
     * 类型（数字、删除、确认按钮）
     */
    private int type;
    /**
     * 键盘上的位置
     */
    private int position;
    /**
     * 数值
     */
    private String value;

    /**
     * 按键背景
     */
    private int bcgRes;

    /**
     * 按键文字颜色
     */
    private int textColor;

    public KeyboardModel(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getBcgRes() {
        return bcgRes;
    }

    public void setBcgRes(int bcgRes) {
        this.bcgRes = bcgRes;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
