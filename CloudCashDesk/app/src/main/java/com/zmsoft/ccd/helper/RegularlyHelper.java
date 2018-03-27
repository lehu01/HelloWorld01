package com.zmsoft.ccd.helper;

import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author DangGui
 * @create 2017/3/8.
 */

public class RegularlyHelper {
    /**
     * 密码必须是6到20位字母或数字
     */
    private static final String PASSWORD_REGULARLY = "[0-9A-Za-z]{6,20}$";

    public static boolean isPwdValid(String pwd) {
        Pattern pwdPattern = Pattern.compile(PASSWORD_REGULARLY);
        return pwdPattern.matcher(pwd).matches();
    }
}
