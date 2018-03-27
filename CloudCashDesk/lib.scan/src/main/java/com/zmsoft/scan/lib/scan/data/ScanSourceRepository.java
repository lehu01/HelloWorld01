package com.zmsoft.scan.lib.scan.data;

import com.zmsoft.ccd.data.Callback;
import com.zmsoft.ccd.data.source.Remote;
import com.zmsoft.ccd.lib.bean.scan.ScanMenu;
import com.zmsoft.ccd.lib.bean.scan.ScanSeat;

import javax.inject.Inject;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/7/21 10:44
 */
public class ScanSourceRepository implements IScanSource {

    private final IScanSource mBaseRemoteSource;

    @Inject
    public ScanSourceRepository(@Remote IScanSource iScanSource) {
        this.mBaseRemoteSource = iScanSource;
    }

    @Override
    public void getScanSeatByQr(String paramString, Callback<ScanSeat> callback) {
        mBaseRemoteSource.getScanSeatByQr(paramString, callback);
    }

    @Override
    public void getScanMenuByQr(String paramString, Callback<ScanMenu> callback) {
        mBaseRemoteSource.getScanMenuByQr(paramString, callback);
    }
}
