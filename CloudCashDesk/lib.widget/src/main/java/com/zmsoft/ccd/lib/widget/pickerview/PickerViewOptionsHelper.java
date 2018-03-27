package com.zmsoft.ccd.lib.widget.pickerview;

import android.content.Context;
import android.graphics.Color;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/9/5 17:09
 *     desc  : pickerview 配置信息
 * </pre>
 */
public class PickerViewOptionsHelper {

    private static int CANCEL_COLOR = Color.GRAY;
    private static int SUBMIT_COLOR = Color.RED;


    public static OptionsPickerView createDefaultPrickerView(Context context, String title) {
        return new OptionsPickerView.Builder(context, null)
                .setTitleText(title)
                .setTitleSize(18)
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(CANCEL_COLOR)
                .setSubmitColor(SUBMIT_COLOR)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideCancelable(true)
                .build();
    }

    public static OptionsPickerView createDefaultPrickerView(Context context, int title) {
        return new OptionsPickerView.Builder(context, null)
                .setTitleText(context.getString(title))
                .setTitleSize(18)
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(Color.BLACK)
                .setCancelColor(CANCEL_COLOR)
                .setSubmitColor(SUBMIT_COLOR)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setOutSideCancelable(true)
                .build();
    }
}
