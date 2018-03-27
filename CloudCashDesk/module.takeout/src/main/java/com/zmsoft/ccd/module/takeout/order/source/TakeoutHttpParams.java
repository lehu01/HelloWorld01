package com.zmsoft.ccd.module.takeout.order.source;

import com.zmsoft.ccd.data.source.BaseHttpParams;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public interface TakeoutHttpParams {

    interface OrderStatusList extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getTakeoutStatusList";
    }

    interface OrderConditions extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getTakeoutFilterCondition";
    }

    interface OrderList extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getTakeoutOrderList";
    }

    interface SearchOrder extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.searchTakeoutOrderList";
    }

    interface OperationOrderStatus extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.operateTakeoutOrder";
    }

    interface CancelTakeoutOrder extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.cancelTakeoutOrder";
    }

    interface DeliveryInfo extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getDeliveryInfo";
    }

    interface DeliveryOrderList extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getDeliveryOrderList";
    }

    interface CourierList extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.getDeliveryType";
    }

    interface DeliveryTakeout extends BaseHttpParams {
        String METHOD = "com.dfire.tp.client.service.ITradePlatformService.deliverTakeout";
    }


}
