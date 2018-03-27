package com.zmsoft.scan.lib.scan.data;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/21 10:41
 */
public interface IScanSource {

    /**
     * 根据短码或者长码获取seat对象
     *
     * @param paramString 短码连接
     * @param callback    回调
     */
    void getScanSeatByQr(String paramString, Callback<ScanSeat> callback);

    /**
     * 根据短码获取menu对象
     *
     * @param paramString 短码连接
     * @param callback    回调
     */
    void getScanMenuByQr(String paramString, Callback<ScanMenu> callback);

}
