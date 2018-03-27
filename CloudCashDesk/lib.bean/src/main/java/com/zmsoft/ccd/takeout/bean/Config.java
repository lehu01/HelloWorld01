package com.zmsoft.ccd.takeout.bean;

import java.util.List;

/**
 * Description：
 * <br/>
 * Created by kumu on 2017/8/21.
 */

public class Config {

    private String key;

    private int value = -1;

    private String desc;

    private List<Config> childConfigVos;

    private long begin = -1;

    private long end = -1;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Config> getChildConfigVos() {
        return childConfigVos;
    }

    public void setChildConfigVos(List<Config> childConfigVos) {
        this.childConfigVos = childConfigVos;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
