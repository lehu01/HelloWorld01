package com.zmsoft.ccd.module.main.order.hangup.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zmsoft.ccd.R;
import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.base.adapter.BaseHolder;
import com.zmsoft.ccd.lib.base.helper.UserHelper;
import com.zmsoft.ccd.lib.bean.order.hangup.HangUpOrder;
import com.zmsoft.ccd.lib.utils.FeeHelper;
import com.zmsoft.ccd.lib.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/8/17 15:13
 *     desc  : 挂单view
 * </pre>
 */
public class HangUpOrderViewHolder extends BaseHolder {

    @BindView(R.id.text_need_pay_money)
    TextView mTextNeedPayMoney;
    @BindView(R.id.text_instance_list)
    TextView mTextInstanceList;
    @BindView(R.id.text_hang_up_time)
    TextView mTextHangUpTime;
    @BindView(R.id.text_hang_up_people)
    TextView mTextHangUpPeople;

    public HangUpOrderViewHolder(Context context, View itemView) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void bindView(List source, Object bean) {
        if (bean == null) {
            return;
        }
        if (bean instanceof HangUpOrder) {
            HangUpOrder order = (HangUpOrder) bean;
            mTextNeedPayMoney.setText(String.format(GlobalVars.context.getString(R.string.yuan_format)
                    , FeeHelper.jointMoneyWithCurrencySymbol(UserHelper.getCurrencySymbol()
                            , String.valueOf(order.getPrice()))));
            mTextInstanceList.setText(getInstanceListStr(order.getItemNameList()));
            mTextHangUpTime.setText(String.format(GlobalVars.context.getString(R.string.hand_up_time)
                    , TimeUtils.getTimeStr(order.getRetainTime(), TimeUtils.FORMAT_DATE_TIME)));
            mTextHangUpPeople.setText(String.format(GlobalVars.context.getString(R.string.hand_up_people), order.getRetainUserName()));
        }
    }

    private String getInstanceListStr(List<String> list) {
        if (list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            sb.append("，");
        }
        return sb.toString().length() > 0 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }
}
