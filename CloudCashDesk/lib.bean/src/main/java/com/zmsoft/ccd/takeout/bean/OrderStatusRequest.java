package com.zmsoft.ccd.takeout.bean;

import com.zmsoft.ccd.shop.IndustryTypeUtils;
import com.zmsoft.ccd.shop.bean.IndustryType;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class OrderStatusRequest extends BaseRequest {


    private short type;


    private OrderStatusRequest(short type, String entityId) {
        setType(type);
        setEntityId(entityId);
    }

    public static OrderStatusRequest createForCatering(String entityId) {
        return new OrderStatusRequest(IndustryTypeUtils.getIndustryType(IndustryType.CATERING), entityId);
    }

    public static OrderStatusRequest createForRetail(String entityId) {
        return new OrderStatusRequest(IndustryTypeUtils.getIndustryType(IndustryType.RETAIL), entityId);
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
