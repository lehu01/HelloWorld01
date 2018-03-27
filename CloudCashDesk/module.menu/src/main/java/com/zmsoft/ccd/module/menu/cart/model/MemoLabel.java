package com.zmsoft.ccd.module.menu.cart.model;

import java.io.Serializable;

/**
 * @author DangGui
 * @create 2017/4/26.
 */

public class MemoLabel implements Serializable {
    private String labelId;
    private String labelName;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

}
