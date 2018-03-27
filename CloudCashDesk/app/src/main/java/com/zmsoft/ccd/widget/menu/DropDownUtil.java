package com.zmsoft.ccd.widget.menu;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @email：danshen@2dfire.com
 * @time : 2016/12/17 14:46
 */
public class DropDownUtil {

    public static int dpTpPx(Context context, float value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
