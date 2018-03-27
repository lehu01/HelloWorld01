package com.zmsoft.ccd.module.login;

import java.io.Serializable;

/**
 * <pre>
 *     author: danshen@2dfire.com
 *     time  : 2017/11/14 17:36
 *     desc  : 环境变量
 * </pre>
 */
public class EnvBean implements Serializable {

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
