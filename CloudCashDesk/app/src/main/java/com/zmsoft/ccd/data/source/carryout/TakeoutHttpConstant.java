package com.zmsoft.ccd.data.source.carryout;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/7/18.
 */

public interface TakeoutHttpConstant {

    interface TakeoutPhoneList {
        String METHOD = "com.dfire.state.getTakeOutMobileList";
        String ENTITY_ID = "entity_id";
    }

    interface UpdateTakeoutPhoneSetting {
        String METHOD = "com.dfire.state.saveTakeOutMobileConfig";
        String ENTITY_ID = "entity_id";
        String MOBILE = "mobile";
        String OPEN_FLAG = "open_flag";
    }

    interface SendTakeoutPhone {
        String METHOD = "com.dfire.soa.cloudcash.callForTakeOut";
        String ENTITY_ID = "entity_id";
        String MOBILE = "mobile";
    }
}
