package com.zmsoft.ccd.lib.bean.config;

import com.zmsoft.ccd.lib.bean.Base;

/**
 * Description：
 * <br/>
 * Create by danshen@2dfire.com
 * Time on 2017/6/17 15:32
 */
public class WorkModelConfig extends Base {

    private boolean isUseLocalCash;

    public boolean isUseLocalCash() {
        return isUseLocalCash;
    }

    public void setUseLocalCash(boolean useLocalCash) {
        isUseLocalCash = useLocalCash;
    }
}
