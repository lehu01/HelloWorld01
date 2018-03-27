package com.zmsoft.ccd.module.login;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/15 10:48
 *     desc  :
 * </pre>
 */
public class EnvViewHolder extends BaseHolder {

    @BindView(R.id.radio_check)
    RadioButton mRadioCheck;
    @BindView(R.id.text_env_name)
    TextView mTextEnvName;

    private IRadioClick iRadioClick;

    public EnvViewHolder(Context context, View itemView, IRadioClick iRadioClick) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        this.iRadioClick = iRadioClick;
    }

    private void initListener(final EnvBeanModel item) {
        mRadioCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRadioClick != null) {
                    iRadioClick.radioClick(item);
                }
            }
        });
    }

    private void fillView(EnvBeanModel model) {
        mRadioCheck.setChecked(model.isCheck());
        mTextEnvName.setText(model.getName());
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean != null && bean instanceof EnvBeanModel) {
            EnvBeanModel item = (EnvBeanModel) bean;
            fillView(item);
            initListener(item);
        }
    }
}
