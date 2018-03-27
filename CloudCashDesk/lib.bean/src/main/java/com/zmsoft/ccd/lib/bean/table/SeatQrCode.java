package com.zmsoft.ccd.lib.bean.table;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * @email：danshen@2dfire.com
 * @time : 2017/2/15 10:27
 */
public class SeatQrCode extends Base {

    private String seatCode; // 座位编码
    private String seatName; // 座位名称
    private String shortUrl; // 座位码url

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
