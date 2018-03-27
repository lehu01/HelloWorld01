package com.zmsoft.ccd.lib.utils.view;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zmsoft.ccd.lib.utils.ConvertUtils;
import com.zmsoft.ccd.lib.utils.R;


/**
 * @author DangGui
 * @create 2017/3/2.
 */

public class CustomViewUtil {
    /**
     * 获取一个指定高度 <code>dpValue</code> 的空view
     *
     * @param dpValue view高度
     * @return view
     */
    public static View generateFootView(Context context, int dpValue) {
        View view = new View(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(context, dpValue)));
        return view;
    }

    /**
     * 适用于点击输入框，直接让输入框内容被全部选中，用户输入可以直接覆盖旧内容的场景
     *
     * @param editText
     */
    public static void                                                                                                                                                                                                                                                                                                                                                                               initEditViewFocousAll(final EditText editText) {
        editText.setSelectAllOnFocus(true);
        //点击输入框，让输入框内容全部被选中，方便用户直接输入覆盖内容
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.clearFocus();
                editText.requestFocus();
            }
        });
    }
}
