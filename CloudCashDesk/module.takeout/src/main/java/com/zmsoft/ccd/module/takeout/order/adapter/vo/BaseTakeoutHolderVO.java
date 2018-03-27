package com.zmsoft.ccd.module.takeout.order.adapter.vo;

import com.zmsoft.ccd.takeout.bean.Takeout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/25.
 */

public class BaseTakeoutHolderVO {

    private Takeout takeout;

    public BaseTakeoutHolderVO(Takeout takeout) {
        this.takeout = takeout;
    }

    public Takeout getTakeout() {
        return takeout;
    }

    public void setTakeout(Takeout takeout) {
        this.takeout = takeout;
    }
}
