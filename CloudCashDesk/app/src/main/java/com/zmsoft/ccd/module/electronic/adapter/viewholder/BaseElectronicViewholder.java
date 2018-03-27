package com.zmsoft.ccd.module.electronic.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.bean.electronic.ElePaymentItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 电子支付明细
 *
 * @author DangGui
 * @create 2017/08/12.
 */

public class BaseElectronicViewholder extends BaseHolder {
    protected ElePaymentItem mElePaymentItem;
    protected Context mContext;

    BaseElectronicViewholder(Context context, View itemView) {
        super(context, itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (null == bean)
            return;
        if (null == source)
            return;
        if (bean instanceof ElePaymentItem)
            mElePaymentItem = (ElePaymentItem) bean;
        if (null == mElePaymentItem)
            return;
    }
}
