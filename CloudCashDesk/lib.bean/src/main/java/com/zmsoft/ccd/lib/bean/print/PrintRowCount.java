package com.zmsoft.ccd.lib.bean.print;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.widget.pickerview.model.IPickerViewData;

/**
 * Description：打印字符
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/12 19:53
 */
public class PrintRowCount extends Base implements IPickerViewData {

    private String displayName; // 显示文字
    private int value; // 实际用到的字符数

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getPickerViewText() {
        return displayName;
    }
}
