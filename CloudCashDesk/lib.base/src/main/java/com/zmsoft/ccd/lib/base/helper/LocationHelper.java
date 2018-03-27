package com.zmsoft.ccd.lib.base.helper;

import com.zmsoft.ccd.lib.utils.SPUtils;

import static com.zmsoft.ccd.app.GlobalVars.context;
import static com.zmsoft.ccd.lib.utils.SPUtils.getInstance;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/5/11 16:31
 */
public class LocationHelper {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static void saveToSp(double latitude, double longitude) {
        SPUtils spUtils = getInstance(context);
        spUtils.putString(LATITUDE, String.valueOf(latitude));
        spUtils.putString(LONGITUDE, String.valueOf(longitude));
    }

    /**
     * 纬度
     */
    public static String getLatitude() {
        return getInstance(context).getString(LATITUDE);
    }

    /**
     * 经度
     */
    public static String getLongitude() {
        return getInstance(context).getString(LONGITUDE);
    }

    /**
     * 获取导航地图的URL
     *
     * @param longitude 经度
     * @param latitude  维度
     * @param address   具体地理位置
     * @return
     */
    public static String getMapUrl(String longitude, String latitude, String address) {
        return "https://uri.amap.com/marker?position=" + longitude + "," + latitude + "&name=" + address;
    }
}
