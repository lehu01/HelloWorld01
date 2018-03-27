package com.zmsoft.ccd.lib.bean.order.feeplan;

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.widget.pickerview.model.IPickerViewData;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/4/10 10:44
 */
public class FeePlan extends Base implements IPickerViewData {

    private String id; // 服务费方案id
    private String name; // 服务费方案名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
