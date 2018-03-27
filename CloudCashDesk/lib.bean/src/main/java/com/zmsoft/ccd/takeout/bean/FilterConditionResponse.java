package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class FilterConditionResponse {

    List<Config> configVos;

    public List<Config> getConfigVos() {
        return configVos;
    }

    public void setConfigVos(List<Config> configVos) {
        this.configVos = configVos;
    }
}
