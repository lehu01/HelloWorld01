package com.zmsoft.ccd.helper;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.bean.order.feeplan.FeePlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/4/26 20:08
 */
public class FeePlanHelper {

    public static List<FeePlan> getAddDefaultFeePlanList(List<FeePlan> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        FeePlan feePlan = new FeePlan();
        feePlan.setName(GlobalVars.context.getString(R.string.nix));
        feePlan.setId("-1");
        data.add(0, feePlan);
        return data;
    }
}
