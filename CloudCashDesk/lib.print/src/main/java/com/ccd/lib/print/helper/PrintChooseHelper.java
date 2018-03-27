package com.ccd.lib.print.helper;

import com.zmsoft.ccd.app.GlobalVars;
import com.zmsoft.ccd.lib.utils.SPUtils;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/17 11:41
 */
public class PrintChooseHelper {

    public static final String KEY_NO_PROMPT = "key_no_prompt";

    public static void saveNoPrompt(boolean isCheck) {
        SPUtils.getInstance(GlobalVars.context).putBoolean(KEY_NO_PROMPT, isCheck);
    }

    public static boolean getNoPrompt() {
        return SPUtils.getInstance(GlobalVars.context).getBoolean(KEY_NO_PROMPT);
    }

}
