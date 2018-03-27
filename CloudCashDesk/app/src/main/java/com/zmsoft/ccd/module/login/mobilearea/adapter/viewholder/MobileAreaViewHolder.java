package com.zmsoft.ccd.module.login.mobilearea.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.bean.login.mobilearea.MobileArea;
import com.zmsoft.ccd.lib.utils.language.LanguageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : heniu@2dfire.com
 * @time : 2017/11/21 17:40.
 */

public class MobileAreaViewHolder extends BaseHolder {

    @BindView(R.id.text_mobile_area)
    TextView mTextMobileArea;
    @BindView(R.id.text_mobile_area_number)
    TextView mTextMobileAreaNumber;

    public MobileAreaViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean == null) {
            return;
        }
        if (bean instanceof MobileArea) {
            MobileArea mobileArea = (MobileArea) bean;
            if (LanguageUtil.isChineseGroup()) {
                mTextMobileArea.setText(mobileArea.getArea());
            } else {
                mTextMobileArea.setText(mobileArea.geteName());
            }
            mTextMobileAreaNumber.setText(mobileArea.getNumber());
        }
    }
}
