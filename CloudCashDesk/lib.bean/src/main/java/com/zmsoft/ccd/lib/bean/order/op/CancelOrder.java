package com.zmsoft.ccd.lib.bean.order.op;

import com.zmsoft.ccd.lib.bean.Base;

import java.util.List;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/14 11:09
 */
public class CancelOrder extends Base {

    private List<String> instanceIds;

    public List<String> getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }
}
