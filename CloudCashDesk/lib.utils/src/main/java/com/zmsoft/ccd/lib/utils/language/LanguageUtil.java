package com.zmsoft.ccd.lib.utils.language;

import java.util.Locale;

/**
 * @author : heniu@2dfire.com
 * @date : 2017/12/14 16:37.
 */

public class LanguageUtil {

    /**
     * 判断是否是中文（包括简体和繁体）
     * 华为手机比较特殊，为zh-CN_#Hans，只能提取language后进行比较
     * @return
     */
    public static boolean isChineseGroup() {
        return Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage());
    }

    public static boolean isEnglish() {
        return Locale.getDefault().equals(Locale.ENGLISH);
    }
}
