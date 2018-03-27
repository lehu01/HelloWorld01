package com.zmsoft.ccd.lib.bean.view;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/16.
 */

public class JagVO {

    private boolean up;

    public JagVO(boolean up) {
        this.up = up;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
