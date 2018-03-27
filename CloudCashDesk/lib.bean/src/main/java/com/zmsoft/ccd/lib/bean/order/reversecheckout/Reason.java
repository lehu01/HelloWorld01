package com.zmsoft.ccd.lib.bean.order.reversecheckout;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/23 14:55
 */

import com.zmsoft.ccd.lib.bean.Base;
import com.zmsoft.ccd.lib.widget.pickerview.model.IPickerViewData;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/21 18:22
 */
public class Reason extends Base implements IPickerViewData {

    private String id;
    private String dicId;
    private String sortCode;
    private String name;
    private String val;
    private String systemTypeId;
    private int ownerType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getSystemTypeId() {
        return systemTypeId;
    }

    public void setSystemTypeId(String systemTypeId) {
        this.systemTypeId = systemTypeId;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
