package com.zmsoft.ccd.lib.utils.phone;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/1/16 10:38
 */
public class PhoneUtil {

    // 香港电话
    public static final String HONG_KONG = "^([6|9])\\d{7}$";
    // 澳门
    public static final String AO_ME = "^[0][9]\\d{8}$";


    /**
     * +86：中国
     * +65:新加坡
     * +852:中国香港
     * +853:中国澳门
     * +886:中国台湾
     *
     * @param phone
     * @return
     */
    public static String getPhoneArea(String phone) {
        String result = "+86";
        if (phone.matches(HONG_KONG)) {
            result = "+852";
        } else if (phone.matches(AO_ME)) {
            result = "+853";
        }
        return result;
    }
}
