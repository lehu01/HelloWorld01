package com.zmsoft.ccd.module.receipt.dagger;

import com.zmsoft.ccd.module.receipt.receipt.source.dagger
        .DaggerReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.receipt.source.dagger.ReceiptSourceComponent;
import com.zmsoft.ccd.module.receipt.vipcard.source.dagger.DaggerVipCardSourceComponent;
import com.zmsoft.ccd.module.receipt.vipcard.source.dagger.VipCardSourceComponent;

/**
 * @author DangGui
 * @create 2017/5/24.
 */

public class ComponentManager {

    private static ComponentManager instance;

    private ReceiptSourceComponent mReceiptSourceComponent;
    private VipCardSourceComponent mVipCardSourceComponent;

    private ComponentManager() {
    }

    public static synchronized ComponentManager get() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    public ReceiptSourceComponent getMenuSourceComponent() {
        if (mReceiptSourceComponent == null) {
            mReceiptSourceComponent = DaggerReceiptSourceComponent.builder().build();
        }
        return mReceiptSourceComponent;
    }


    public VipCardSourceComponent getVipCardSourceComponent() {
        if (mVipCardSourceComponent == null) {
            mVipCardSourceComponent = DaggerVipCardSourceComponent.builder().build();
        }
        return mVipCardSourceComponent;
    }
}
